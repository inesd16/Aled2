package fr.isen.dobosz.projet


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home_connected.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_menu_connected.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*


//import java.sql.Time

class HomeActivity : AppCompatActivity(), View.OnTouchListener, NavigationView.OnNavigationItemSelectedListener{
    //private val FILE_NAME:String = "ClickPosition.txt"
    //var jsonArray = JSONArray()
    //lateinit var gestureDet:GestureDetectorCompat

    companion object{
        var newTime:Boolean = true
        var readString:String? = null
        var height:Int = 0
        var width:Int = 0
    }




    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {


        val sharedPrefLogs : SharedPreferences = getSharedPreferences("isConnected", Context.MODE_PRIVATE)
        val stateConnection = sharedPrefLogs.getBoolean("isConn", false)

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),StarActivity.writeESRequestCode
            )
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),StarActivity.readESRequestCode
            )
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),StarActivity.cameraRequestCode
            )
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),StarActivity.callRequestCode
            )
        }

        //stateConnection = true
        if(stateConnection) {
            setTheme(R.style.AppTheme_NoActionBar)
            setContentView(R.layout.activity_menu_connected)

            super.onCreate(savedInstanceState)
            val toolbar_conn = findViewById<Toolbar>(R.id.toolbar_conn)
            setSupportActionBar(toolbar_conn)

            val toggle = ActionBarDrawerToggle(this,drawer_layout_conn, toolbar_conn,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
            val navigationView = findViewById<NavigationView>(R.id.nav_view_conn)
            navigationView.setNavigationItemSelectedListener(this)

            drawer_layout_conn.addDrawerListener(toggle)
            toggle.syncState()

            if(savedInstanceState == null){

                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_conn,HomeConnectedFragment()).commit()

                val header = navigationView.getHeaderView(0)
                val nameUserDrawer = header.findViewById<TextView>(R.id.nameUserTextView)
                val emailUserDrawer = header.findViewById<TextView>(R.id.emailUserTextView)
                val sharedNewUser = this.getSharedPreferences("sharedNewUser",Context.MODE_PRIVATE)
                val readString = sharedNewUser.getString("userInfo", "") ?:""
                System.out.println(readString)
                val jsonObj = JSONObject(readString)
                val localName= jsonObj.getString("name")
                val localSurname = jsonObj.getString("surname").toUpperCase(Locale.ROOT)

                nameUserDrawer.text = "$localName $localSurname"
                val localEmail= jsonObj.getString("email")
                emailUserDrawer.text = localEmail

                val root = this.getExternalFilesDir("ProfileInfo")
                val dir = File(root!!.absolutePath)
                val file = File(dir, "ProfilePic.jpg")
                val imageUserDrawer = header.findViewById<ImageView>(R.id.imageUser)
                if(file.exists()){
                    try{
                        imageUserDrawer.setImageURI(Uri.parse(file.toString()))

                        //val encodedImage: String = Base64.encodeToString(byteArrayImage, Base64.DEFAULT)
                        val json = JSONObject()
                        json.put("profilePic", Uri.parse(file.toString()))
                        System.out.println(json.get("profilePic").toString())
                    } catch(e:Exception){
                        e.printStackTrace()
                    }
                }

                //val imageUserDrawer = header.findViewById<ImageView>(R.id.imageUser)
                //imageUserDrawer.setImage
                //navigationView.setCheckedItem(R.id.nav_connect)
                //R.id.nav_connect.setOnClickListener(View.OnClickListener())
            }
//            val sharedNewUser = this.getSharedPreferences("sharedNewUser", Context.MODE_PRIVATE) ?: return
//            val readString = sharedNewUser.getString("userInfo", "") ?: ""
//            var jsonO = JSONObject(readString)
//            var totName = jsonO.getString("name").toString()
//            totName = totName + " "+jsonO.getString("surname").toString().toUpperCase()
//            findViewById<TextView>(R.id.nameUserTextView).setText(totName)
//            var email = jsonO.getString("email")
//            findViewById<TextView>(R.id.emailUserTextView).setText(email)

        }
        else{
            setTheme(R.style.AppTheme_NoActionBar)
            setContentView(R.layout.activity_main)

            super.onCreate(savedInstanceState)
            val toolbar = findViewById<Toolbar>(R.id.toolbar)
            setSupportActionBar(toolbar)

            val toggle = ActionBarDrawerToggle(this,drawer_layout, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
            val navigationView = findViewById<NavigationView>(R.id.nav_view)
            navigationView.setNavigationItemSelectedListener(this)

            drawer_layout.addDrawerListener(toggle)
            toggle.syncState()

            if(savedInstanceState == null){

                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,MessageFragment()).commit()
                //navigationView.setCheckedItem(R.id.nav_connect)
                //R.id.nav_connect.setOnClickListener(View.OnClickListener())
        }

            val header = navigationView.getHeaderView(0)
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
            /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
               val mail = header.findViewById(R.id.emailUserTextView) as TextView
               val name= header.findViewById(R.id.nameUserTextView) as TextView

            name.visibility = View.INVISIBLE
            mail.visibility = View.INVISIBLE
        }

    }
private fun isExternalStorageWritable():Boolean{
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            return true
        }
         else return false
    }


    /*fun writeFile(){
        System.out.println(readString)
        val state = Environment.getExternalStorageState()
        if(Environment.MEDIA_MOUNTED.equals((state))){

            val root = Environment.getExternalStorageDirectory()

            val dir = File(root.absolutePath+"/myAppFile")
            System.out.println(dir)
            if(!dir.exists()){
                dir.mkdir()
                System.out.println("does not exists yet")
            }
            else{

                System.out.println("exists")
            }
            val file = File(dir,"clickPos.txt")
            try {
                val fos = FileOutputStream(file)
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

    }*/

   /* fun readFile(){

        var myExternalFile = File(getExternalFilesDir("C:\\Users\\inesd\\OneDrive\\Documents\\M1\\Projet"), "ClickPositionsFile.txt")
        val filename = "ClickPositionsFile.txt"
        myExternalFile = File(getExternalFilesDir("C:\\Users\\inesd\\OneDrive\\Documents\\M1\\Projet"),filename)
        val fileInputStream = FileInputStream(myExternalFile)
        val inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ({ text = bufferedReader.readLine(); text }() != null) {
            stringBuilder.append(text)
        }
        System.out.println("StringBuilder : "+stringBuilder)
        fileInputStream.close()
    }
    fun onCapturedPointerEvent(motionEvent: MotionEvent): Boolean {
        // Get the coordinates required by your app
        //val verticalOffset: Float = motionEvent.y
        // Use the coordinates to update your view and return true if the event was
        // successfully processed
        return true
    }*/

    fun readPreviousClick():JSONArray{

            val sharedPrefPosition = this.getSharedPreferences("sharedPrefPosition",Context.MODE_PRIVATE)
            val readString = sharedPrefPosition.getString("backupMicePos", "") ?:""
        val jsonArray = JSONArray(readString)
        System.out.println("jsonArray"+jsonArray)
        //System.out.println(jsonArray)
        //System.out.println("READ"+readString)
        return(jsonArray)
    }

    fun save(jsonArray: JSONArray, x:Int,y:Int){
        val jsonObj = JSONObject()
        val millis = System.currentTimeMillis()

//Divide millis by 1000 to get the number of seconds.
        //Divide millis by 1000 to get the number of seconds.
       // val seconds = millis / 1000

        System.out.println(millis)
        jsonObj.put("clickX",x)
        jsonObj.put("clickY",y)
        jsonObj.put("timeClickedMillis",millis-1585154297320)
        jsonArray.put(jsonObj)
        val sharedPrefPosition = this.getSharedPreferences("sharedPrefPosition",Context.MODE_PRIVATE) ?: return
        with(sharedPrefPosition.edit()) {
            putString("backupMicePos", jsonArray.toString())
            //putBoolean("saveState",true)
            commit()
        }
        //System.out.println(jsonArray)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (view) {
            homeButton -> {
                when (event.action){
                    MotionEvent.ACTION_DOWN -> {

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

                    MotionEvent.ACTION_UP -> {


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

                    }
            }
            }
        }
        return true
    }

/*

     @SuppressLint("Recycle")
     override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        when (action) {
                MotionEvent.ACTION_UP -> {// Return a VelocityTracker object back to be re-used by others.
                    System.out.println("ACTION_UP")
                }

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
                    jsonArray.put("screen_accueil")
                    save(jsonArray, x, y)
                    newTime = false
                }

                else{
                    save(readPreviousClick(), x,y)
                }
                //writeFile()
            }
            MotionEvent.ACTION_CANCEL -> {
                System.out.println("ACTION_CANCEL")

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
*/

    @SuppressLint("ResourceType")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var drawerPb = false
        when(item.getItemId()){
            R.id.nav_connect -> intent = Intent(this, LoginActivity::class.java)
            R.id.nav_sign_in -> intent = Intent(this, RegistrationActivity::class.java)
            R.id.nav_do_diagnosis -> intent = Intent(this,StarActivity::class.java)
            R.id.nav_disconnect ->{val sharedPrefLogs : SharedPreferences = getSharedPreferences("isConnected", Context.MODE_PRIVATE)
                with(sharedPrefLogs.edit()) {
            putBoolean("isConn", false)
            commit()
        }
            drawer_layout_conn.closeDrawer(GravityCompat.START)
                drawerPb = true
            intent = Intent(this, HomeActivity::class.java)
        }
            R.id.nav_edit_profile -> intent = Intent(this, UserInfoActivity::class.java)
            R.id.action_user_info -> intent = Intent(this, UserInfoActivity::class.java)
            R.id.nav_appointment -> intent = Intent(this, AppointmentActivity::class.java)
            R.id.policy -> {intent = Intent(this, Policy::class.java)}
            R.id.nav_contact -> intent = Intent(this, ContactFragment::class.java)
           // R.id.nav_call_doctor -> launchPopUpStartCall()
            //R.id.nav_about_us -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,LoginFragment()).commit()
        }

        startActivity(intent)
        val sharedPrefLogs : SharedPreferences = getSharedPreferences("isConnected", Context.MODE_PRIVATE)
        val stateConnection = sharedPrefLogs.getBoolean("isConn", false)
        if(!stateConnection){
            if(!drawerPb){
                drawer_layout.closeDrawer(GravityCompat.START)}}
        else if(stateConnection){
        drawer_layout_conn.closeDrawer(GravityCompat.START)}
        return true

    }
    override fun onBackPressed() {

        val sharedPrefLogs : SharedPreferences = getSharedPreferences("isConnected", Context.MODE_PRIVATE)
        val stateConnection = sharedPrefLogs.getBoolean("isConn", false)
        if(stateConnection) {
            if (drawer_layout_conn.isDrawerOpen(GravityCompat.START)) {
                drawer_layout_conn.closeDrawer(GravityCompat.START)
            } else
                super.onBackPressed()
        }
        else if(!stateConnection) {
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            } else
                super.onBackPressed()
        }
        else super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater

        val sharedPrefLogs : SharedPreferences = getSharedPreferences("isConnected", Context.MODE_PRIVATE)
        val stateConnection = sharedPrefLogs.getBoolean("isConn", false)
        if(stateConnection){
            inflater.inflate(R.menu.user_menu, menu)

        //val user = FirebaseAuth.getInstance().currentUser
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
                //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            R.id.action_user_info -> intent = Intent(this, UserInfoActivity::class.java)
        }
        startActivity(intent)
        return true
        //return super.onOptionsItemSelected(item)
    }

    fun launchPopUpStartCall(){
        val builder = AlertDialog.Builder(this)
            .setTitle("Lancer un appel")
            .setMessage("Voulez-vous vraiment appelez un médecin?")
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            startCall()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
        }
        builder.show()
    }

    fun startCall() {
        val intentcall = Intent()
        intentcall.action = Intent.ACTION_CALL
        intentcall.data = Uri.parse("tel:+33698305732") // set the Uri
        startActivity(intentcall)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == StarActivity.cameraRequestCode &&
            resultCode == Activity.RESULT_OK
        ) {
            val navigationView = findViewById<NavigationView>(R.id.nav_view_conn)
            navigationView.setNavigationItemSelectedListener(this)

            val header = navigationView.getHeaderView(0)
            val imageUser = header.findViewById<ImageView>(R.id.imageUser)

            val imageFileName = "profilePic.jpg"
            //val bytearrayoutputstream = ByteArrayOutputStream()
            val state = Environment.getExternalStorageState()
            var success = true
            val root = this.getExternalFilesDir("ProfileInfo")

            if (data?.data != null) { // Gallery

                val pathURI = data.data!!.getPath()
                System.out.println("CHEMIN " + pathURI.toString())
                //val bitmap = data?.extras?.get("data") as? Bitmap
                //bitmap!!.compress(Bitmap.CompressFormat.PNG, 60, bytearrayoutputstream)
                if (Environment.MEDIA_MOUNTED.equals((state))) {
                    val dir = File(root!!.absolutePath)
                    val dirTake = File(pathURI.toString())
                    System.out.println(dir.toString())
                    if (!dir.exists()) {
                        success = dir.mkdir()
                        //System.out.println("does not exists yet")
                    }
                    if (success) {
                        val file = File(dir, imageFileName)
                        //System.out.println("success true")
                        try {
                            file.createNewFile()
                            //var a = dirTake.readBytes()
                            file.writeBytes(dirTake.readBytes())
                            System.out.println("SAVED")
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }

                    imageUser.setImageURI(data.data)
            }
        }
    }

}

