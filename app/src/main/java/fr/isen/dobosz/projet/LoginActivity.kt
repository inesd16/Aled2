package fr.isen.dobosz.projet

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.Camera
import android.media.CamcorderProfile
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.InputType.*
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class LoginActivity : AppCompatActivity(), View.OnTouchListener {

    private var newTimeTouchLoginPage = true
    val goodIdentifier = ""
    val goodPassword = ""
    private var mAuth: FirebaseAuth? = null
    var posArray = ArrayList<String>()
    var nmbOfMoveOnStarImage:Int = 0


    private var mCamera: Camera? = null
    //private var emCamera: CameraManager? = null
    val camFront:Int = Camera.CameraInfo.CAMERA_FACING_FRONT
    private var mPreview: CameraPreview? = null
    var mediaRecorder: MediaRecorder? = null

    fun requestPermission(permissionToRequest: String, requestCode: Int, handler: ()-> Unit) {
        if(ContextCompat.checkSelfPermission(this, permissionToRequest) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, permissionToRequest)) {
                //display toast
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(permissionToRequest), requestCode)
            }
        } else {
            handler()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        requestPermission(Manifest.permission.RECORD_AUDIO, StarActivity.audioRequestCode) {
        }
        requestPermission(Manifest.permission.CAMERA, StarActivity.videoRequestCode) {
        }

        // Create an instance of Camera
        mCamera = getCameraInstance(camFront)
        //mCamera!!.setDisplayOrientation(90)
        mPreview = mCamera?.let {
            // Create our Preview view
            CameraPreview(this, it)
        }

        // Set the Preview view as the content of our activity.
       // mPreview?.also {
            //val preview: FrameLayout = findViewById(R.id.camera_preview)
            //preview.addView(it)
       // }
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        findViewById<Button>(R.id.validateButton).setOnClickListener {
            doLogin()
        }

        var passwShown = false

        toggleShowHidePasswButton.layoutParams.height = passwordEditText.height
        val a: Int = passwordEditText.height
        toggleShowHidePasswButton.layoutParams.width = a

        findViewById<ImageButton>(R.id.toggleShowHidePasswButton).setOnClickListener() {
            if (passwShown == false) {
                passwordEditText.inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
                passwShown = true
                toggleShowHidePasswButton.setBackgroundResource(R.drawable.hide_password)

            } else {
                passwordEditText.inputType = TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                passwShown = false
                toggleShowHidePasswButton.setBackgroundResource(R.drawable.show_password)

            }

        }

        //val user = FirebaseAuth.getInstance().currentUser

        findViewById<Button>(R.id.goRegisterButton).setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        newAccountTextView.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
        var isRecording = false
        connectWithFacialRecognationButton.setOnClickListener(){
            //StarActivity.requestPermission

                requestPermission(Manifest.permission.CAMERA, StarActivity.cameraRequestCode){
                    System.out.println("permission ok")

                    if (isRecording) {
                        System.out.println("RECPRDINF")
                        // stop recording and release camera
                        mediaRecorder?.stop() // stop the recording
                        mediaRecorder?.release()// release the MediaRecorder object
                        mCamera?.lock() // take camera access back from MediaRecorder

                        // inform the user that recording has stopped
                        //setCaptureButtonText("Capture")
                        connectWithFacialRecognationButton.setText("capture")
                        //videoView.setVideoURI()
                        // TODO - Put Authentification function to allow logIN
                        // TODO LoginWithFace()
                        isRecording = false
                    } else {

                        System.out.println("NOT RECORDING")
                        if (prepareVideoRecorder()) {
                            System.out.println("PREPARE RECORDING")
                            // Camera is available and unlocked, MediaRecorder is prepared,
                            // now you can start recording
                            mediaRecorder?.start()

                            // inform the user that recording has started
                            //setCaptureButtonText("Stop")
                            connectWithFacialRecognationButton.setText("stop")
                            isRecording = true

                        } else {
                            // prepare didn't work, release the camera
                            mediaRecorder?.release()
                            // inform user
                        }
                    }

                }
                //  }

        }

        val layout:View = findViewById(R.id.layoutLogin)
        layout.setOnTouchListener(){ v, event ->
            onTouch(v, event)
        }

    }




    fun doLogin() {
        val password = passwordEditText.getText().toString()
        val email = emailEditText.getText().toString()


        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                    Log.d("login", "signInWithEmail:success")
                    val user = mAuth!!.currentUser

                    val sharedPrefLogs: SharedPreferences =
                        getSharedPreferences("isConnected", Context.MODE_PRIVATE)
                    sharedPrefLogs.edit().putBoolean("isConn", true).apply()

                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    System.out.println(user!!.displayName)
                    startActivity(intent)
                    writeFile(posArray.toString(),"", this)
                    //updateUI(user)
                } else { // If sign in fails, display a message to the user.

                    finishAffinity()
                    Log.w("login", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this@LoginActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // ...
            }
    }

    fun autoLogin() {
        val stringId: String? = getValueString("prompt_email")
        val stringPassword: String? = getValueString("prompt_password")
        if (canLog(stringId.toString(), stringPassword.toString())) {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }

    fun canLog(identifier: String, password: String): Boolean {
        return (identifier == goodIdentifier && password == goodPassword)
    }


    fun getValueString(key_name: String): String? {
        val sharedPrefLogs: SharedPreferences =
            getSharedPreferences("identifiers", Context.MODE_PRIVATE)
        return sharedPrefLogs.getString(key_name, "")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_home -> intent = Intent(this, HomeActivity::class.java)
        }
        startActivity(intent)
        return true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?):Boolean {
        val action = event!!.action
        //System.out.println("DIMENSION IMAGE x y : "+starImageView.width+" "+starImageView.height)
        when(action){

            MotionEvent.ACTION_DOWN -> {
                // var newTime:Boolean = true
                val x = Math.round(event.x)
                val y = Math.round(event.y)

                    System.out.println("POSITION")
                    System.out.println("x : " + x)
                    System.out.println("y : " + y)
                    val jsonArrayVoid = JSONArray()
                    if(newTimeTouchLoginPage){
                        // is very first time (click down)
                        val jsonArray = JSONArray() //create new
                        val jsonObj = JSONObject()
                        jsonArray.put("LoginPage") //from starActint height = displayMetrics.heightPixels;
val displayMetrics = DisplayMetrics()
getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
val height = displayMetrics.heightPixels
val width = displayMetrics.widthPixels
                        jsonObj.put("dim_x", width)
                        jsonObj.put("idm_y", height)
                        jsonArray.put(jsonObj) //put dimensions
                        posArray.add(nmbOfMoveOnStarImage++,jsonArray.toString()) //Have to put image info
                        saveInJSON(jsonArrayVoid, x, y)
                        newTimeTouchLoginPage = false
                    } else{
                        saveInJSON(jsonArrayVoid,x,y)
                        //saveInJSON(readPreviousClick(),x,y)
                    }


            }

            MotionEvent.ACTION_MOVE -> {

                    // var newTime:Boolean = true
                    val x = Math.round(event.x)
                    val y = Math.round(event.y)

                    System.out.println("POSITION")
                    System.out.println("x : " + x)
                    System.out.println("y : " + y)

                    saveInJSON(readPreviousClick(), x,y)

            }

            MotionEvent.ACTION_UP -> {

                    // var newTime:Boolean = true
                    val x = Math.round(event.x)
                    val y = Math.round(event.y)
                        //startTextColor.setTextColor(getResources().getColor(R.color.colorGreen))

                        //saveInJSON(readPreviousClick(), x,y)
                        val sharedPrefPosition = this.getSharedPreferences("sharedLoginPosition", Context.MODE_PRIVATE)
                        val readString = sharedPrefPosition.getString("backupMicePos", "") ?:""
                        System.out.println("READSTRING "+readString)
                        posArray.add(nmbOfMoveOnStarImage,readString) //pos array contient toutes les series de move -> {sharedPrefPos1,sharedPrefPos2 ..}
                        nmbOfMoveOnStarImage++
                        System.out.println(posArray) //pour vérifier

            }


            MotionEvent.ACTION_CANCEL -> {

            }

            else ->{

            }
        }
       return true
    }
    fun saveInJSON(jsonArray: JSONArray, x:Int,y:Int){
        val jsonObj = JSONObject()
        val millis = System.currentTimeMillis()

        //Divide millis by 1000 to get the number of seconds.
        //Divide millis by 1000 to get the number of seconds.
        //val seconds = millis / 1000

        System.out.println(millis)
        jsonObj.put("X",x)
        jsonObj.put("Y",y)
        jsonObj.put("t",millis-1589542005463)
        jsonArray.put(jsonObj)
        val sharedPrefPosition = this.getSharedPreferences("sharedLoginPosition", Context.MODE_PRIVATE) ?: return
        with(sharedPrefPosition.edit()) {
            putString("backupMicePos", jsonArray.toString())
            //putBoolean("saveState",true)
            commit()
        }
        //System.out.println(jsonArray)
    }
    fun readPreviousClick():JSONArray{

        val sharedPrefPosition = this.getSharedPreferences("sharedLoginPosition", Context.MODE_PRIVATE)
        val readString = sharedPrefPosition.getString("backupMicePos", "") ?:""
        val jsonArray = JSONArray(readString)
        System.out.println("PREVIOUS CLICK"+jsonArray) //pour vérifier
        //System.out.println(posArray) //pour vérifier

        //System.out.println("READ"+readString)
        return(jsonArray)
    }

    fun writeFile(coords1: String, coords2: String, context: Context){
        //System.out.println("WRiTEFile"+coords1)
        //System.out.println("WRiTEFile"+HomeActivity.readString)
        val state = Environment.getExternalStorageState()
        var success = true
        //val rootTest = Environment.getExternalStorageDirectory()
        val root = getExternalFilesDir("DataToSend")

        if(Environment.MEDIA_MOUNTED.equals((state))){
            val dir = File(root!!.absolutePath)
            System.out.println(dir.toString())
            if(!dir.exists()){
                success = dir.mkdir()
                System.out.println("does not exists yet")
            }
            else{
                System.out.println("exists")
            }
            if (success) {
                val file = File(dir, "clickLoginPage.txt")
                System.out.println("success true")
                try {
                    file.createNewFile()
                    file.writeText(coords1)
                    //System.out.println(file.readText())
                    /* val fOut = FileOutputStream(file)
                     val myOutWriter =  OutputStreamWriter(fOut)
                     myOutWriter.append(coords1)
                     System.out.println("MYTEXT "+myOutWriter.toString())

                     val outputStreamWriter = OutputStreamWriter(context.openFileOutput("clickPos.txt", Context.MODE_PRIVATE))
                     outputStreamWriter.write(coords1)
                     outputStreamWriter.close()*/

                    /*val fos = FileOutputStream(file)
                    fos.write(readString!!.toByteArray())
                    fos.close()*/
                    //File(dir.name).writeText(coords1)
                    //File(dir.name).writeText(coords2)
                    System.out.println("SAVED")
                } catch (e: FileNotFoundException) {
                    System.out.println("E1")
                    e.printStackTrace()
                } catch (e: IOException) {
                    System.out.println("E2")
                    e.printStackTrace()
                }
            }
        }
    }
    fun read(){
        requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, StarActivity.readESRequestCode) {

            val root = getExternalFilesDir("DataToSend")
            val dir = File(root!!.absolutePath)
            val file = File(dir, "clickLoginPage.txt")

            val read = file.readText()
            System.out.println("VALEUR READ "+read)

            //Read text from file

            // val iStream = openFileInput(dir.toString())


        }
    }

    /** A safe way to get an instance of the Camera object. */
    fun getCameraInstance(camFront : Int): Camera? {
        return try {
            Camera.open(camFront) // attempt to get a Camera instance
        } catch (e: Exception) {
            // Camera is not available (in use or does not exist)
            null // returns null if camera is unavailable
        }
    }

    val MEDIA_TYPE_IMAGE = 1
    val MEDIA_TYPE_VIDEO = 2

    /** Create a file Uri for saving an image or video */
    private fun getOutputMediaFileUri(type: Int): Uri {
        return Uri.fromFile(getOutputMediaFile(type))
    }

    /** Create a File for saving an image or video */
    @SuppressLint("SimpleDateFormat")
    private fun getOutputMediaFile(type: Int): File? {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        val mediaStorageDir = File(getExternalFilesDir("videoDir"),"MyCameraApp")
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        System.out.println("DIR "+mediaStorageDir)
        // Create the storage directory if it does not exist
        mediaStorageDir.apply {
            if (!exists()) {
                if (!mkdirs()) {
                    System.out.println("MyCameraApp "+ "failed to create directory")
                    return null
                }
            }
        }

        // Create a media file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        return when (type) {
            MEDIA_TYPE_IMAGE -> {
                File("${mediaStorageDir.path}${File.separator}IMG_$timeStamp.jpg")
            }
            MEDIA_TYPE_VIDEO -> {
                File("${mediaStorageDir.path}${File.separator}VID_Behaviour_Analysis.mp4")
                //File("${mediaStorageDir.path}${File.separator}VID_$timeStamp.mp4")
            }
            else -> null
        }
    }

    @SuppressLint("NewApi")
    private fun prepareVideoRecorder(): Boolean {
        mediaRecorder = MediaRecorder()
        //mCamera!!.Size(200,200)
        // mediaRecorder!!.setVideoSize(200,200)
        System.out.println("MEDIARECORDER "+mediaRecorder)
        System.out.println("CAMERA "+mCamera)

        mCamera?.let { camera ->
            // Step 1: Unlock and set camera to MediaRecorder
            camera.unlock()

            mediaRecorder?.run {
                setCamera(camera)

                // Step 2: Set sources
                setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
                setVideoSource(MediaRecorder.VideoSource.CAMERA)

                // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
                setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH, CamcorderProfile.QUALITY_HIGH))

                // Step 4: Set output file
                //setOutputFile(getExternalFilesDir(Environment.DIRECTORY_PICTURES))
                setOutputFile(getOutputMediaFile(MEDIA_TYPE_VIDEO).toString())

                // Step 5: Set the preview output
                setPreviewDisplay(mPreview?.holder?.surface)
                try{
                    setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                    setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
                    setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT)

                }
                catch(e:Exception){
                    e.printStackTrace()
                }


                // Step 6: Prepare configured MediaRecorder
                return try {
                    prepare()
                    true
                } catch (e: IllegalStateException) {
                    Log.d("TAG", "IllegalStateException preparing MediaRecorder: ${e.message}")

                    mediaRecorder?.release()
                    false
                } catch (e: IOException) {
                    Log.d("TAG", "IOException preparing MediaRecorder: ${e.message}")
                    mediaRecorder?.release()
                    false
                }
            }

        }
        return false
    }
    override fun onPause() {
        super.onPause()
        releaseMediaRecorder() // if you are using MediaRecorder, release it first
        releaseCamera() // release the camera immediately on pause event
    }

    private fun releaseMediaRecorder() {
        mediaRecorder?.reset() // clear recorder configuration
        mediaRecorder?.release() // release the recorder object
        mediaRecorder = null
        mCamera?.lock() // lock camera for later use
    }

    private fun releaseCamera() {
        mCamera?.release() // release the camera for other applications
        mCamera = null
    }

    fun isReadStoragePermissionGranted():Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted1")
                return true
            } else {

                Log.v("TAG","Permission is revoked1")
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    StarActivity.readESRequestCode
                )
                return false
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted1")
            return true
        }
    }

    fun isWriteStoragePermissionGranted():Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted2")
                return true
            } else {

                Log.v("TAG","Permission is revoked2")
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    StarActivity.writeESRequestCode
                );
                return false
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted2")
            return true
        }
    }
}
