package fr.isen.dobosz.projet

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_edit_password.*
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditPasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditPasswordFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_edit_password, container, false)
        val confirmButton: Button = view.findViewById(R.id.confirmPasswButton)


        confirmButton.setOnClickListener() {
            val sharedSaveNewUser: SharedPreferences = this.activity!!.getSharedPreferences("sharedNewUser", Context.MODE_PRIVATE)
            val readString = sharedSaveNewUser.getString("userInfo", "") ?: ""
            //var jsonArray = JSONArray(readString)
           // var jsonObj = jsonArray.getJSONObject(0)
            val jsonObj = JSONObject(readString)
            System.out.println("JSONOBJET "+jsonObj)
            val passw = jsonObj.getInt("password")
            val nom = jsonObj.getString("name")
            System.out.println(nom)
            System.out.println("NEW "+formerPasswEditText.text.toString().hashCode())
            //val hash = passw.toString().hashCode()
            System.out.println("FORMER "+passw)
            System.out.println(passw.equals(formerPasswEditText.text.toString().hashCode()))


            if (passw.equals(formerPasswEditText.text.toString().hashCode()) && checkPasswords()) {
                System.out.println("PASSW OK")

                jsonObj.put("password", newPasswEditText.text.toString().hashCode())

                System.out.println("JSON APRES MODIF : "+jsonObj)
                with(sharedSaveNewUser.edit()) {
                    putString("userInfo", jsonObj.toString())
                    //putBoolean("saveState",true)
                    commit()
                }

                passwEdited.setText(R.string.passwEditedSuccessfully)
                passwEdited.visibility = View.VISIBLE

            }
            else{

                passwEdited.visibility = View.VISIBLE
                passwEdited.setText(getString(R.string.passwModifiedError))
            }

        }

            return view
        }

    fun checkPasswords(): Boolean {
        if (newPasswEditText.getText().toString().length == confirmNewPasswEditText.getText().toString().length) { //same length

            if (newPasswEditText.getText().toString().length < 8) { //length not enough
                //Toast.makeText(this, "Passwords must contain at least 8 characters", Toast.LENGTH_LONG ).show()
                return false
            } else if (newPasswEditText.getText().toString() != confirmNewPasswEditText.getText().toString()) { //different passw
                //Toast.makeText(this, "The passwords must be the same", Toast.LENGTH_LONG).show()

                return false
            } else { //password are the same


                var countNumber = false //passw does not contain number
                var countCapLetter = false //passw does not capital letter
                for (letter in newPasswEditText.getText().toString()) { //Check number in passw
                    try { //is letter a number ?
                        countNumber = true
                    } catch (e: Exception) {
                        //System.out.println("Je ne suis pas un entier, et alors ca te derange ?")
                    }
                    if (letter == letter.toUpperCase()) { // letter upCase
                        // Vérifier si le texte est en majuscule
                        countCapLetter = true
                    }
                }
                if (countNumber && countCapLetter){
                    return true

                }
                else {
                    passwEdited.setText(R.string.stringPasswordInfo)
                    return false
                }
            }
        } else { //password not same length

            return false
        }
    }
}
