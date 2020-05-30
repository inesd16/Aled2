package fr.isen.dobosz.projet


import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_contact.*
import org.json.JSONObject
import java.util.*


class ContactFragment: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.fragment_contact)
        emailTextView.setOnClickListener{
            sendEmail()
        }
        nameTextView.setOnClickListener(){
            openWebURL("https://boushoku.alwaysdata.net/")
        }
    }

    protected fun sendEmail() {
        Log.i("Send email", "")
        val TO = arrayOf("contact@aled.com")
        val CC = arrayOf("ines.dobosz@isen.fr")
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO)
        emailIntent.putExtra(Intent.EXTRA_CC, CC)
        val sharedNewUser = this.getSharedPreferences("sharedNewUser", Context.MODE_PRIVATE)
        val readString = sharedNewUser.getString("userInfo", "") ?:""
        System.out.println(readString)
        val jsonObj = JSONObject(readString)
        val localName= jsonObj.getString("name")
        val localSurname = jsonObj.getString("surname").toUpperCase(Locale.ROOT)

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, localName+" "+localSurname)
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here")  // add name user


        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."))
            finish()
            Log.i("Finished sending email", "")
        } catch (ex: ActivityNotFoundException) {
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_home -> intent = Intent(this, HomeActivity::class.java)
        }
        startActivity(intent)
        return true
        //return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
            return true
        }

    fun openWebURL(inURL: String?) {
        val browse = Intent(Intent.ACTION_VIEW, Uri.parse(inURL))
        startActivity(browse)
    }
}