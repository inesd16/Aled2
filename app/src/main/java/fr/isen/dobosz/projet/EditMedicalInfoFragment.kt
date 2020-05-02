package fr.isen.dobosz.projet

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_edit_medical_info.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
var weightValues = arrayOfNulls<String>(151)
var heightValues = arrayOfNulls<String>(151)
var weightChanged:Boolean = false
var heightChanged:Boolean = false
/**
 * A simple [Fragment] subclass.
 * Use the [EditMedicalInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditMedicalInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var age:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        var i = 0
        while (i<=150){
            weightValues.set(i,i.toString()+" kg")
            heightValues.set(i,(i+70).toString()+" cm")
            i++
        }

    }

    fun initializeSpinners(view:View,wSpin :Spinner,hSpin:Spinner) {

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aaWeight = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, weightValues)
        // Set layout to use when the list of choices appear
        aaWeight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        wSpin?.setAdapter(aaWeight)
        wSpin?.setSelection(70)

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aaHeight = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, heightValues)
        // Set layout to use when the list of choices appear
        aaHeight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        hSpin?.setAdapter(aaHeight)
        hSpin?.setSelection(90)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_edit_medical_info, container, false)

        val wSpin = view.findViewById<Spinner>(R.id.weightSpinner)
        val hSpin = view.findViewById<Spinner>(R.id.heightSpinner)





        wSpin.setOnItemSelectedListener(
            object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View, position: Int, id: Long
                ) {
                    weightChanged = true
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    System.out.println("Spinner1: unselected")
                }
            })
        hSpin.setOnItemSelectedListener(
            object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View, position: Int, id: Long
                ) {
                    heightChanged = true
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    System.out.println("Spinner1: unselected")
                }
            })



        initializeSpinners(view, wSpin ,hSpin)
        var date = view.findViewById<EditText>(R.id.dateEditText)
        date.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus) {
                date.clearFocus()
                val dialog = context?.let {
                    DatePickerDialog(
                        it,
                        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                            if(getAge(year, month, dayOfMonth)==-1){
                                Toast.makeText(context, "La date n'est pas correcte", Toast.LENGTH_LONG).show()

                            }
                            else
                                onDateChoose(year, month, dayOfMonth)
                        },
                        1970,
                        1,
                        1)
                }
                dialog!!.show()
            }
        }

        var confirmButton:Button = view.findViewById<Button>(R.id.confirmButton)
        confirmButton.setOnClickListener() {

            var secuEditText:EditText = view.findViewById(R.id.secuEditText)

            System.out.println("SECu & Date "+secuEditText.getText() + date.getText())
            val sharedSaveNewUser: SharedPreferences = this.activity!!.getSharedPreferences("sharedNewUser", Context.MODE_PRIVATE)
            val readString = sharedSaveNewUser.getString("userInfo", "") ?: ""
//            val jsonArray = JSONArray(readString)
            var jsonObj = JSONObject(readString)


            if(!date.getText().toString().equals("")){
                jsonObj.put("birthDate",date.getText())
                view.findViewById<TextView>(R.id.info).setText(R.string.allEdited)
            }
            if(weightChanged){
                jsonObj.put("weight",wSpin.selectedItem)
                view.findViewById<TextView>(R.id.info).setText(R.string.allEdited)
            }
            if(heightChanged){
                jsonObj.put("height",hSpin.selectedItem)
                view.findViewById<TextView>(R.id.info).setText(R.string.allEdited)
            }
            //System.out.println(jsonArray.toString())

            if(!secuEditText.getText().toString().equals("")){
                if(secuEditText.length() < 15){
                    view.findViewById<TextView>(R.id.info).setText(R.string.incorrectSecuNo)
                }
                else{
                    jsonObj.put("secuNo",secuEditText.getText())
                    view.findViewById<TextView>(R.id.info).setText(R.string.allEdited)
                }
            }

                //jsonArray.put(jsonObj)
                System.out.println("JSON APRES MODIF : "+jsonObj)
                with(sharedSaveNewUser.edit()) {
                    putString("userInfo", jsonObj.toString())
                    //putBoolean("saveState",true)
                    commit()
                }

                info.visibility = View.VISIBLE


        }
        return view
    }

    fun onDateChoose(year: Int, month: Int, day: Int) {
        dateEditText.setText(String.format("%02d/%02d/%04d",day,month+1,year))
        //Toast.makeText(this, "date : ${dateText.text.toString()}", Toast.LENGTH_LONG).show()
    }
    fun getAge(year: Int, month: Int, day: Int): Int {
        val currentDate = Date()
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val dateString = formatter.format(currentDate)
        val components = dateString.split("/")

        var age = components[2].toInt() - year
        if(components[1].toInt() < month-1) {
            age--
        } else if (components[1].toInt() == month &&
            components[0].toInt() < day){
            age --
        }
        if(age < 0 || age > 120) {
            this.age = age
            return -1
        }
        //System.out.println("TU AS "+age)
        // field_age.setText("Vous avez ${getAge(components[2].toInt(), components[1].toInt(), components[0].toInt())} ans")
        this.age = age
        return age
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditMedicalInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditMedicalInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
