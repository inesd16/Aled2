package fr.isen.dobosz.projet

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    // var drawer: DrawerLayout? = null
    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*val sharedPrefLogs : SharedPreferences = getSharedPreferences("isConnected", Context.MODE_PRIVATE)
        var stateConnection = sharedPrefLogs.getBoolean("isConn", false)
        if(stateConnection){

        }*/
        //val openDialog = Dialog(this)
        //openDialog.setContentView(R.layout.custom_dialog)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(this,drawer_layout, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
       navigationView.setNavigationItemSelectedListener(this)

       drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        if(savedInstanceState == null){

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,MessageFragment()).commit()
        //navigationView.setCheckedItem(R.id.nav_connect)
        //R.id.nav_connect.setOnClickListener(View.OnClickListener())
            }

    }
    @SuppressLint("ResourceType")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(item.getItemId() == R.id.nav_connect){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        when(item.getItemId()){
            R.id.nav_connect -> intent = Intent(this, LoginActivity::class.java)
            R.id.nav_sign_in -> intent = Intent(this, RegistrationActivity::class.java)
            //R.id.nav_contact -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,LoginFragment()).commit()
            //R.id.nav_about_us -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,LoginFragment()).commit()

        }
        startActivity(intent)
        drawer_layout.closeDrawer(GravityCompat.START)


        return true
    }
    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.isDrawerOpen(GravityCompat.START)
        } else
        super.onBackPressed()
    }

}
