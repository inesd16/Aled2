package fr.isen.dobosz.projet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.dobosz.projet.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val goodIdentifier = ""
    val goodPassword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //autoLogin()
        validateButton.setOnClickListener{
            doLogin()
        }
        testButton.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }
    fun doLogin (){
        val sharedPrefLogs : SharedPreferences = getSharedPreferences("identifiers", Context.MODE_PRIVATE)
        sharedPrefLogs.edit().putString("prompt_email", "${usernameEditText.text}").apply()
        sharedPrefLogs.edit().putString("prompt_password", "${passwordEditText.text}").apply()
        if (canLog(usernameEditText.text.toString(), passwordEditText.text.toString())) {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    fun autoLogin () {
        val stringId : String? = getValueString("prompt_email")
        val stringPassword : String? = getValueString("prompt_password")
        if (canLog(stringId.toString(), stringPassword.toString())){
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    fun canLog (identifier: String, password: String): Boolean{
        return (identifier == goodIdentifier && password == goodPassword)
    }

    fun getValueString(key_name : String): String? {
        val sharedPrefLogs : SharedPreferences = getSharedPreferences("identifiers", Context.MODE_PRIVATE)
        return sharedPrefLogs.getString(key_name, "")
    }
}
