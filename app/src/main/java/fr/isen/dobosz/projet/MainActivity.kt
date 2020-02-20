package fr.isen.dobosz.projet

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout

import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

   // var drawer: DrawerLayout? = null
    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val openDialog = Dialog(this)
        //openDialog.setContentView(R.layout.custom_dialog)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(this,drawer_layout, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        System.out.println("FVBID "+findViewById(R.id.drawer_layout))
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
       val contact = findViewById<View>(R.id.nav_share)
       //contact.setOnClickListener(View.OnClickListener())

    }
    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.isDrawerOpen(GravityCompat.START)
        } else
        super.onBackPressed()
    }

    //override fun onCreateOptionsMenu(menu: Menu): Boolean {
      //  val inflater: MenuInflater = menuInflater
        //inflater.inflate(R.menu.drawer_menu, menu)
        //return true
   // }

}
