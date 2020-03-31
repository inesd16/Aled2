package fr.isen.dobosz.projet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(){

    val goodIdentifier = ""
    val goodPassword = ""
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        findViewById<Button>(R.id.validateButton).setOnClickListener{
            doLogin()

            val user = FirebaseAuth.getInstance().currentUser
      /*      if (user != null) { // Name, email address, and profile photo Url
                val name = user.getDisplayName()

                val email = user.getEmail()
                val photoUrl = user.getPhotoUrl()
                nameUserTextView.setText(name)
                emailUserTextView.setText(email)
                imageUser.setImageURI(photoUrl)
                // Check if user's email is verified
                val emailVerified = user.isEmailVerified
                System.out.println(emailVerified)
                // The user's ID, unique to the Firebase project. Do NOT use this value to
// authenticate with your backend server, if you have one. Use
// FirebaseUser.getIdToken() instead.
                val uid = user.uid
            }           */

        }
        findViewById<Button>(R.id.testButton).setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        newAccountTextView.setOnClickListener{
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

    }



    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
        //updateUI(currentUser)
    }




    fun doLogin (){
       /* val sharedPrefLogs : SharedPreferences = getSharedPreferences("identifiers", Context.MODE_PRIVATE)
        sharedPrefLogs.edit().putString("prompt_email", "${usernameEditText.text}").apply()
        sharedPrefLogs.edit().putString("prompt_password", "${passwordEditText.text}").apply()
        if (canLog(usernameEditText.text.toString(), passwordEditText.text.toString())) {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
       } */
        val password = passwordEditText.getText().toString()
        val email = emailEditText.getText().toString()


        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                    Log.d("login", "signInWithEmail:success")
                    val user = mAuth!!.currentUser

                    val sharedPrefLogs : SharedPreferences = getSharedPreferences("isConnected", Context.MODE_PRIVATE)
                    sharedPrefLogs.edit().putBoolean("isConn", true).apply()

                     val intent = Intent(this, HomeActivity::class.java)
                     startActivity(intent)
             //updateUI(user)
         } else { // If sign in fails, display a message to the user.
             Log.w("login", "signInWithEmail:failure", task.exception)
             Toast.makeText(
                 this@LoginActivity, "Authentication failed.",
                 Toast.LENGTH_SHORT
             ).show()
         }
         // ...
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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
     val inflater: MenuInflater = menuInflater
     inflater.inflate(R.menu.user_menu, menu)
     return true
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
        }
        startActivity(intent)
        return true
        //return super.onOptionsItemSelected(item)
    }
}
