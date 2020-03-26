package fr.isen.dobosz.projet


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.os.Environment
import android.util.DisplayMetrics
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.solver.widgets.Rectangle
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home_connected.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.util.*


//import java.sql.Time

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    private val FILE_NAME:String = "ClickPosition.txt"
    private var newTime:Boolean = true
    //var jsonArray = JSONArray()
    lateinit var gestureDet:GestureDetectorCompat

    companion object{
        var readString:String? = null
        var height:Int = 0
        var width:Int = 0
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        System.out.println("newtime :"+this.newTime)
        val sharedPrefLogs : SharedPreferences = getSharedPreferences("isConnected", Context.MODE_PRIVATE)
        var stateConnection = sharedPrefLogs.getBoolean("isConn", false)
        if(stateConnection){
//            val toolbar = findViewById<Toolbar>(R.id.toolbar)
//            setSupportActionBar(toolbar)
            setContentView(R.layout.activity_home_connected)

            homeButton.setOnClickListener{

            }



            historyButton.setOnClickListener{

            }
            mapButton.setOnClickListener{

            }
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            height = displayMetrics.heightPixels
            width = displayMetrics.widthPixels
            System.out.println("X Y : "+height+" "+width)
            var b = Bitmap.createBitmap(9*width/10, 4*height/5, Bitmap.Config.ARGB_8888);
            var c = Canvas(b);
            var p = Paint();

            // Dessiner l'intérieur d'une figure
            p.setStyle(Paint.Style.FILL);

            // Dessiner ses contours
            p.setStyle(Paint.Style.STROKE);

            // Dessiner les deux
            p.setStyle(Paint.Style.FILL_AND_STROKE);

            var r = Rect()
            r.set(10,10,10,10)
            c.drawRect(r,p)


        }
        else{
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

    }
private fun isExternalStorageWritable():Boolean{
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            return true
        }
         else return false
    }


    public fun writeFile(){
        System.out.println(readString)
        var state = Environment.getExternalStorageState()
        if(Environment.MEDIA_MOUNTED.equals((state))){

            var root = Environment.getExternalStorageDirectory()

            var dir = File(root.absolutePath+"/myAppFile");
            System.out.println(dir)
            if(!dir.exists()){
                dir.mkdir()
                System.out.println("does not exists yet")
            }
            else{

                System.out.println("exists")
            }
            var file = File(dir,"clickPos.txt")
            try {
                var fos = FileOutputStream(file)
                fos.write(readString!!.toByteArray())
                fos.close()
                System.out.println("SAVED")

            }
            catch(e: FileNotFoundException){
                e.printStackTrace()
            }
            catch(e: IOException){
                e.printStackTrace()
            }


        }

        else {


        }
    }

    public fun readFile(){

        var myExternalFile:File = File(getExternalFilesDir("C:\\Users\\inesd\\OneDrive\\Documents\\M1\\Projet"), "ClickPositionsFile.txt")
        val filename = "ClickPositionsFile.txt"
        myExternalFile = File(getExternalFilesDir("C:\\Users\\inesd\\OneDrive\\Documents\\M1\\Projet"),filename)
        var fileInputStream = FileInputStream(myExternalFile)
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ({ text = bufferedReader.readLine(); text }() != null) {
            stringBuilder.append(text)
        }
        System.out.println("StringBuilder : "+stringBuilder)
        fileInputStream.close()

    }


    private fun readPreviousClick():JSONArray{

            val sharedPrefDungeon = this.getSharedPreferences("sharedPrefDungeon",Context.MODE_PRIVATE)
            val readString = sharedPrefDungeon.getString("backupGameJson", "") ?:""
        val jsonArray = JSONArray(readString)
        System.out.println(jsonArray)
        Log.d("DungeonCardActivityREAD", jsonArray.toString())
        //System.out.println(jsonArray)
        //System.out.println("READ"+readString)
        return(jsonArray)
    }

    private fun save(jsonArray: JSONArray, x:Int,y:Int){

        val jsonObj = JSONObject()
        val millis = System.currentTimeMillis()

//Divide millis by 1000 to get the number of seconds.
        //Divide millis by 1000 to get the number of seconds.
        val seconds = millis / 1000

        var currentTime: Date = Calendar.getInstance().getTime()
        System.out.println(millis)
        jsonObj.put("clickX",x)
        jsonObj.put("clickY",y)
        jsonObj.put("timeClickedMillis",millis-1585154297320)
        jsonArray.put(jsonObj)
        val sharedPrefDungeon = this.getSharedPreferences("sharedPrefDungeon",Context.MODE_PRIVATE) ?: return
        with(sharedPrefDungeon.edit()) {
            putString("backupGameJson", jsonArray.toString())
            //putBoolean("saveState",true)
            commit()
        }
        //System.out.println(jsonArray)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                System.out.println("ACTION_DOWN")
                // var newTime:Boolean = true
                val x = Math.round(event.x)
                val y = Math.round(event.y)

                System.out.println("POSITION")
                System.out.println("x : "+x)
                System.out.println("y : "+y)
                if(newTime){
                    val jsonArray = JSONArray()
                    save(jsonArray, x, y)
                    newTime = false
                }

                else{
                    save(readPreviousClick(), x,y)
                }
                //writeFile()
            }
            MotionEvent.ACTION_MOVE -> {

                System.out.println("ACTION_MOVE")
                // var newTime:Boolean = true
                val x = Math.round(event.x)
                val y = Math.round(event.y)

                System.out.println("POSITION")
                System.out.println("x : "+x)
                System.out.println("y : "+y)
                if(newTime){
                    val jsonArray = JSONArray()
                    save(jsonArray, x, y)
                    newTime = false
                }

                else{
                    save(readPreviousClick(), x,y)
                }
                //writeFile()
            }

            MotionEvent.ACTION_POINTER_DOWN -> {

                System.out.println("ACTION_POINTER_DOWN")
                // var newTime:Boolean = true
                val x = Math.round(event.x)
                val y = Math.round(event.y)

                System.out.println("POSITION")
                System.out.println("x : "+x)
                System.out.println("y : "+y)
                if(newTime){
                    val jsonArray = JSONArray()
                    save(jsonArray, x, y)
                    newTime = false
                }

                else{
                    save(readPreviousClick(), x,y)
                }
                //writeFile()
            }



        }
        return true
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
            R.id.action_user_info -> intent = Intent(this, UserInfoActivity::class.java)
            //R.id.nav_sign_in -> setContentView(R.layout.activity_registration)
            //R.id.nav_sign_in -> supportFragmentManager.beginTransaction().replace(R.layout.activity_registration,ProfileFragment()).commit()

            R.id.nav_contact -> intent = Intent(this, ContactFragment::class.java)
                //supportFragmentManager.beginTransaction().replace(R.id.fragment_container,ContactFragment()).commit()
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

        System.out.println("ONCREATEOPTION")

        val sharedPrefLogs : SharedPreferences = getSharedPreferences("isConnected", Context.MODE_PRIVATE)
        var stateConnection = sharedPrefLogs.getBoolean("isConn", false)
        if(!stateConnection){

        val user = FirebaseAuth.getInstance().currentUser
    /*    if (user != null) { // Name, email address, and profile photo Url
            System.out.println("USER NON NULL")
            val name = user.getDisplayName()
            val email = user.getEmail()
            val photoUrl = user.getPhotoUrl()

            nameUserTextView.setText(name)
            emailUserTextView.setText(email!!)
            //imageUser.setImageURI(photoUrl!!)
            // Check if user's email is verified
            val emailVerified = user.isEmailVerified
            System.out.println(emailVerified)
            System.out.println(name)
            System.out.println(email)
            System.out.println(user)
            // The user's ID, unique to the Firebase project. Do NOT use this value to
// authenticate with your backend server, if you have one. Use
// FirebaseUser.getIdToken() instead.
            val uid = user.uid
        }       */
        return true
    }
        else{
            return true
        }
        return false
}

}

