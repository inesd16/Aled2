package fr.isen.dobosz.projet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : AppCompatActivity() {

    //var default_btn_material:Int = 0xD3D2D2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        homeButton.setOnClickListener {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
//            supportFragmentManager.beginTransaction().replace(R.id.editFragment,BlankFragment()).commit()
            supportFragmentManager.beginTransaction().replace(R.id.editFragment,EditPasswordFragment()).commit()
        }
        editPasswButton.setOnClickListener(){
            //editPasswButton.setBackgroundColor(resources.getColor(R.color.colorEditButton))
            editPasswButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorEditButton))
            editMedicalInfoButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorDefaultButton))
            editPersonalInfoButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorDefaultButton))

            //editMedicalInfoButton.setBackgroundColor(resources.getColor(R.color.colorDefaultButton))
            //editPersonalInfoButton.setBackgroundColor(resources.getColor(R.color.colorDefaultButton))
            supportFragmentManager.beginTransaction().replace(R.id.editFragment,EditPasswordFragment()).commit()

        }
        editMedicalInfoButton.setOnClickListener(){


            editPasswButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorDefaultButton))
            editMedicalInfoButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorEditButton))
            editPersonalInfoButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorDefaultButton))
            supportFragmentManager.beginTransaction().replace(R.id.editFragment,EditMedicalInfoFragment()).commit()
        }
        editPersonalInfoButton.setOnClickListener(){

            editPasswButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorDefaultButton))
            editMedicalInfoButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorDefaultButton))
            editPersonalInfoButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorEditButton))
            supportFragmentManager.beginTransaction().replace(R.id.editFragment,EditPersInfoActivity()).commit()
        }
//
//        confirmButton.setOnClickListener(){
//            val sharedSaveNewUser : SharedPreferences = getSharedPreferences("sharedNewUser", Context.MODE_PRIVATE)
//            val readString = sharedSaveNewUser.getString("userInfo", "") ?:""
//            val jsonArray = JSONArray(readString)
//            var jsonObj = jsonArray.getJSONObject(0)
//            var passw = jsonObj.getInt("password")
//            var nom = jsonObj.getString("name")
//            System.out.println(nom)
//
//            if(passw.equals(formerPasswEditText) && checkPasswords()){
//                jsonObj.put("password", newPasswEditText)
//                jsonArray.put(jsonObj)
//            }
//
//
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater

        val sharedPrefLogs : SharedPreferences = getSharedPreferences("isConnected", Context.MODE_PRIVATE)
        val stateConnection = sharedPrefLogs.getBoolean("isConn", false)
        inflater.inflate(R.menu.user_menu, menu)
        return true
        //return false
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val sharedPrefLogs : SharedPreferences = getSharedPreferences("isConnected", Context.MODE_PRIVATE)
        //var stateConnection = sharedPrefLogs.getBoolean("isConn", false)

        when (item.getItemId()) {
            R.id.log_out -> {with(sharedPrefLogs.edit()) {
                putBoolean("isConn", false)
                //putBoolean("saveState",true)
                commit()
            }
                intent = Intent(this, HomeActivity::class.java)
            }
            R.id.action_user_info -> intent = Intent(this, UserInfoActivity::class.java)
        }
        startActivity(intent)
        return true
        //return super.onOptionsItemSelected(item)
    }


//    fun checkPasswords(): Boolean {
//        if (newPasswEditText.getText().toString().length == confirmNewPasswEditText.getText().toString().length) { //same length
//            if (newPasswEditText.getText().toString().length < 8) { //length not enough
//                Toast.makeText(
//                    this,
//                    "Passwords must contain at least 8 characters",
//                    Toast.LENGTH_LONG
//                ).show()
//                return false
//            } else if (newPasswEditText.getText().toString() != confirmNewPasswEditText.getText().toString()) { //different passw
//                Toast.makeText(this, "The passwords must be the same", Toast.LENGTH_LONG).show()
//                return false
//            } else { //password are the same
//                var countNumber = false //passw does not contain number
//                var countCapLetter = false //passw does not capital letter
//                for (letter in newPasswEditText.getText().toString()) { //Check number in passw
//                    try { //is letter a number ?
//                        // val i = Integer.parseInt(letter.toString())
//                        //System.out.println("C'est un entier")
//                        informations.setText("")
//                        countNumber = true
//                    } catch (e: Exception) {
//                        //System.out.println("Je ne suis pas un entier, et alors ca te derange ?")
//                    }
//                    if (letter == letter.toUpperCase()) { // letter upCase
//                        // Vérifier si le texte est en majuscule
//                        countCapLetter = true
//                    }
//                }
//                if (countNumber && countCapLetter)
//                    return true
//                else {
//                    informations.setText(R.string.stringPasswordInfo)
//                    return false
//                }
//            }
//        } else { //password not same length
//            Toast.makeText(this, "The passwords must be the same", Toast.LENGTH_LONG).show()
//            return false
//        }
//    }
}
