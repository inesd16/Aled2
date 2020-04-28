package fr.isen.dobosz.projet

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_registration.dateText
import kotlinx.android.synthetic.main.fragment_edit_pers_info_activity.*
import org.json.JSONArray

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditPersInfoActivity.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditPersInfoActivity : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_pers_info_activity, container, false)
        var confirmButton: Button = view.findViewById(R.id.confirmButton)
        confirmButton.setOnClickListener(){
            val sharedSaveNewUser : SharedPreferences = this.activity!!.getSharedPreferences("sharedNewUser", Context.MODE_PRIVATE)
            val readString = sharedSaveNewUser.getString("userInfo", "") ?:""
            val jsonArray = JSONArray(readString)
            var jsonObj = jsonArray.getJSONObject(0)
                jsonObj.put("name", nameEditText)
                jsonObj.put("surname", surnameEditText)
                jsonObj.put("email", emailEditText)
                jsonArray.put(jsonObj)

        }
        var date = view.findViewById<EditText>(R.id.dateEditText)
        date.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus) {
                dateText.clearFocus()
                val dialog = context?.let {
                    DatePickerDialog(
                        it,
                        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                            onDateChoose(year, month, dayOfMonth)
                        },
                        1970,
                        1,
                        1)
                }
                dialog!!.show()
            }
        }
        return view
    }

    fun onDateChoose(year: Int, month: Int, day: Int) {
        dateText.setText(String.format("%02d/%02d/%04d",day,month+1,year))
        //Toast.makeText(this, "date : ${dateText.text.toString()}", Toast.LENGTH_LONG).show()
    }
}
