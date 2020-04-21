package fr.isen.dobosz.projet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        homeButton.setOnClickListener {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        }
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
}
