package fr.isen.dobosz.projet


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import fr.isen.dobosz.projet.R.layout.fragment_profile

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)



        button.setOnClickListener(){
            System.out.println("I clicked the button")
        }




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

            //setContentView(R.layout.activity_login)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        when(item.getItemId()){
            // R.id.nav_connect -> supportFragmentManager.beginTransaction().replace(R.layout.activity_login,MessageFragment()).commit()
            R.id.nav_connect -> intent = Intent(this, LoginActivity::class.java)
            R.id.nav_sign_in -> intent = Intent(this, RegistrationActivity::class.java)

            R.id.nav_chat -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,ChatFragment()).commit()

            //R.id.nav_sign_in -> setContentView(R.layout.activity_registration)
            //R.id.nav_sign_in -> supportFragmentManager.beginTransaction().replace(R.layout.activity_registration,ProfileFragment()).commit()

            R.id.nav_contact -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,ContactFragment()).commit()
            R.id.nav_about_us -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,LoginFragment()).commit()
            //R.id.nav_contact -> Toast.makeText(this,"share", Toast.LENGTH_SHORT).show()
            //R.id.nav_client -> Toast.makeText(this,"send", Toast.LENGTH_SHORT).show()
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.test_menu, menu)
        return true
    }

}

