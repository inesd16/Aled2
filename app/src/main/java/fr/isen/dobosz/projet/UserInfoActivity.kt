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
        historyButton.setOnClickListener {
            val intent = Intent(this, AppointmentActivity::class.java)
            startActivity(intent)
        }
            homeButton.setOnClickListener {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            supportFragmentManager.beginTransaction().replace(R.id.editFragment,EditPasswordFragment()).commit()
        }
        editPasswButton.setOnClickListener(){
            editPasswButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorEditButton))
            editMedicalInfoButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorDefaultButton))
            editPersonalInfoButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorDefaultButton))

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

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater

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


}
