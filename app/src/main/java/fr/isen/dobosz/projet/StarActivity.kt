package fr.isen.dobosz.projet

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.media.CamcorderProfile
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import fr.isen.dobosz.projet.form.FormActivity
import kotlinx.android.synthetic.main.activity_star.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class StarActivity : AppCompatActivity() {
    private var newTimeStar = true
    lateinit var videoUri: Uri
    lateinit var videoPath:String
    companion object{
        var cameraRequestCode = 1
        var gpsRequestCode = 2
        var callRequestCode = 3
        var videoRequestCode = 4
        var audioRequestCode = 5
        var writeESRequestCode = 9
        var readESRequestCode = 10
    }
    var posArray = ArrayList<String>()
    var nmbOfMoveOnStarImage:Int = 0
    var firstRedPoint:Boolean = false
    //var secondRedPoint:Boolean = false

    private var mCamera: Camera? = null
    //private var emCamera: CameraManager? = null
    val camFront:Int = Camera.CameraInfo.CAMERA_FACING_FRONT
    private var mPreview: CameraPreview? = null
    var mediaRecorder: MediaRecorder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_star)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            emCamera = getSystemService(Context.CAMERA_SERVICE) as CameraManager?
//
//        }

        // Create an instance of Camera
        mCamera = getCameraInstance(camFront)
        //mCamera!!.setDisplayOrientation(90)
        mPreview = mCamera?.let {
            // Create our Preview view
            CameraPreview(this, it)
        }

        // Set the Preview view as the content of our activity.
        mPreview?.also {
            val preview: FrameLayout = findViewById(R.id.camera_preview)
            preview.addView(it)
        }
        requestPermission(Manifest.permission.RECORD_AUDIO, audioRequestCode) {
        }
        if(isWriteStoragePermissionGranted()){
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, writeESRequestCode) {

            }

        }
        if(isReadStoragePermissionGranted()){
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, readESRequestCode) {
            }
        }

        emergencyCallButton.setOnClickListener{
            requestPermission(Manifest.permission.CALL_PHONE, callRequestCode){
                startCall()
            }
        }

        nextButton.setOnClickListener(){
            //            info.facing = mCamera.CameraInfo.CAMERA_FACING_FRONT
//            mCamera.CameraInfo().facing
            val sharedPrefPosition = this.getSharedPreferences("sharedPrefPosition", Context.MODE_PRIVATE)
            val readString = sharedPrefPosition.getString("backupMicePos", "") ?:"" //posArray
            writeFile(posArray.toString(),"", this)
            videoButton.performClick()
            read()
            var intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }

        var isRecording = false
        videoButton.setOnClickListener{
            requestPermission(Manifest.permission.CAMERA, cameraRequestCode){
                System.out.println("permission ok")

                if (isRecording) {
                    System.out.println("RECPRDINF")
                    // stop recording and release camera
                    mediaRecorder?.stop() // stop the recording
                    mediaRecorder?.release()// release the MediaRecorder object
                    mCamera?.lock() // take camera access back from MediaRecorder

                    // inform the user that recording has stopped
                    //setCaptureButtonText("Capture")
                    videoButton.setText("capture")
                    //videoView.setVideoURI()
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
                        videoButton.setText("stop")
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


        starImageView.setOnTouchListener { _, event ->
            val action = event.action
            System.out.println("DIMENSION IMAGE x y : "+starImageView.width+" "+starImageView.height)
            when(action){

                MotionEvent.ACTION_DOWN -> {
                    // var newTime:Boolean = true
                    val x = Math.round(event.x)
                    val y = Math.round(event.y)

                    if(0<x && x<100 && 0<y && y<100){
                        firstRedPoint = true
                        if(!isRecording) //if not recording, begin
                            videoButton.performClick() // Video launched when clicked on fisrt red button
                       // startTextColor.setTextColor(getResources().getColor(R.color.colorConnect))
                        startTextColor.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorConnect))


                        System.out.println("POSITION")
                        System.out.println("x : " + x)
                        System.out.println("y : " + y)
                        System.out.println("NEwtIME "+newTimeStar)
                        val jsonArrayVoid = JSONArray()
                        if(newTimeStar){
                            // is very first time (click down)
                            val jsonArray = JSONArray() //create new
                            val jsonObj = JSONObject()
                            jsonArray.put("etoile") //from starAct
                            jsonObj.put("image_x", starImageView.width)
                            jsonObj.put("image_y", starImageView.height)
                            jsonArray.put(jsonObj) //put dimensions
                            posArray.add(nmbOfMoveOnStarImage++,jsonArray.toString()) //Have to put image info
                            saveInJSON(jsonArrayVoid, x, y)
                            newTimeStar = false
                        } else{
                            saveInJSON(jsonArrayVoid,x,y)
                            //saveInJSON(readPreviousClick(),x,y)
                        }
                    }
                    else {
                        firstRedPoint = false
                        //startTextColor.setTextColor(resources.getColor(R.color.colorPrimaryDark))
                        startTextColor.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorPrimaryDark))

                    }

                }

                MotionEvent.ACTION_MOVE -> {
                    System.out.println("firstRedPoint"+firstRedPoint)

                    if(firstRedPoint){
                        // var newTime:Boolean = true
                        val x = Math.round(event.x)
                        val y = Math.round(event.y)

                        System.out.println("POSITION")
                        System.out.println("x : " + x)
                        System.out.println("y : " + y)

                        saveInJSON(readPreviousClick(), x,y)

                    }

                }

                MotionEvent.ACTION_UP -> {

                    if(firstRedPoint){

                        // var newTime:Boolean = true
                        val x = Math.round(event.x)
                        val y = Math.round(event.y)
                        if(starImageView.width-100<x && x<starImageView.width && starImageView.height-100<y && y<starImageView.height){
                            //startTextColor.setTextColor(getResources().getColor(R.color.colorGreen))

                            startTextColor.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorGreen))
                            //saveInJSON(readPreviousClick(), x,y)
                            val sharedPrefPosition = this.getSharedPreferences("sharedPrefPosition", Context.MODE_PRIVATE)
                            val readString = sharedPrefPosition.getString("backupMicePos", "") ?:""
                            nextButton.visibility = View.VISIBLE
                            System.out.println("READSTRING "+readString)
                            posArray.add(nmbOfMoveOnStarImage,readString) //pos array contient toutes les series de move -> {sharedPrefPos1,sharedPrefPos2 ..}
                            nmbOfMoveOnStarImage++
                            System.out.println(posArray) //pour vérifier
                        }
                    }
                    else{
                        //startTextColor.setTextColor(resources.getColor(R.color.colorPrimaryDark))
                        startTextColor.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorPrimaryDark))

                    }
                }


                MotionEvent.ACTION_CANCEL -> {

                }

                else ->{

                }
            }
            true
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
                File("${mediaStorageDir.path}${File.separator}VID_Behavious_Analysis.mp4")
                //File("${mediaStorageDir.path}${File.separator}VID_$timeStamp.mp4")
            }
            else -> null
        }
    }



    fun readPreviousClick():JSONArray{

        val sharedPrefPosition = this.getSharedPreferences("sharedPrefPosition", Context.MODE_PRIVATE)
        val readString = sharedPrefPosition.getString("backupMicePos", "") ?:""
        val jsonArray = JSONArray(readString)
        System.out.println("PREVIOUS CLICK"+jsonArray) //pour vérifier
        //System.out.println(posArray) //pour vérifier

        //System.out.println("READ"+readString)
        return(jsonArray)
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
        jsonObj.put("t",millis-1588007005463)
        jsonArray.put(jsonObj)
        val sharedPrefPosition = this.getSharedPreferences("sharedPrefPosition", Context.MODE_PRIVATE) ?: return
        with(sharedPrefPosition.edit()) {
            putString("backupMicePos", jsonArray.toString())
            //putBoolean("saveState",true)
            commit()
        }
        //System.out.println(jsonArray)
    }
/*

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        when (action) {
            MotionEvent.ACTION_UP -> {
            }

            MotionEvent.ACTION_DOWN -> {

                // var newTime:Boolean = true
                val x = Math.round(event.x)
                val y = Math.round(event.y)
//
//                System.out.println("POSITION")
//                System.out.println("x : "+x)
//                System.out.println("y : "+y)
                if(newTime){
                    val jsonArray = JSONArray()
                    saveInJSON(jsonArray, x, y)
                    newTime = false
                }

                else{
                    saveInJSON(readPreviousClick(), x,y)
                }
                //writeFile()
            }
            MotionEvent.ACTION_MOVE -> {

                System.out.println("ACTION_MOVE")
                // var newTime:Boolean = true
                val x = Math.round(event.x)
                val y = Math.round(event.y)
//
//                System.out.println("POSITION")
//                System.out.println("x : "+x)
//                System.out.println("y : "+y)
                if(newTime){
                    val jsonArray = JSONArray()
                    jsonArray.put("screen_accueil")
                    saveInJSON(jsonArray, x, y)
                    newTimeStar = false

                }

                else{
                    saveInJSON(readPreviousClick(), x,y)
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
                if(newTimeStar){
                    val jsonArray = JSONArray()
                    saveInJSON(jsonArray, x, y)
                    newTimeStar = false
                }

                else{
                    saveInJSON(readPreviousClick(), x,y)
                }
            }



        }
        return true
    }
*/


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

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            9 ->{Log.d("TAG", "External storage2")
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.v("TAG","Permission: "+permissions[0]+ "was "+grantResults[0])
                    //resume tasks needing this permission
                    //downloadPdfFile();
                }else{
                    //progress.dismiss();
                }
            }
            10 -> {
                Log.d("TAG", "External storage1")
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.v("TAG","Permission: "+permissions[0]+ "was "+grantResults[0])
                    //resume tasks needing this permission
                    //SharePdfFile();
                }else{
                    //progress.dismiss();
                }
            }

        }
        if (requestCode == cameraRequestCode) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.v("TAG","Permission: "+permissions[0]+ "was "+grantResults[0])
                //resume tasks needing this permission
                //SharePdfFile();
                startCall()
            }
            System.out.println("PERMISSION CAMERA")
        }
//        if(requestCode == writeESRequestCode) {
//            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                Log.v("TAG","Permission: "+permissions[0]+ "was "+grantResults[0])
//                //resume tasks needing this permission
//                //recordVideo()
//            }
//                System.out.println("PERMISSION CAMERA")
//            }


        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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
                val file = File(dir, "clickPos.txt")
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
        requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, readESRequestCode) {

            val root = getExternalFilesDir("DataToSend")
            val dir = File(root!!.absolutePath)
            val file = File(dir, "clickPos.txt")

            val read = file.readText()
            System.out.println("VALEUR READ "+read)

            //Read text from file

            // val iStream = openFileInput(dir.toString())


        }
    }

    fun startCall(){
        val intentcall = Intent()
        intentcall.action = Intent.ACTION_CALL
        intentcall.data = Uri.parse("tel:+33698305732") // set the Uri
        startActivity(intentcall)
    }

    /** Check if this device has a camera */
    private fun checkCameraHardware(context: Context): Boolean {
        if (context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            // this device has a camera
            return true
        } else {
            // no camera on this device
            return false
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



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == videoRequestCode && resultCode == Activity.RESULT_OK) {
            System.out.println("PERMISSION VIDEO")
            //video_view.setVideoUri(videoUri)
            //video_view.start()
            //addToGallery()

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
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), readESRequestCode)
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
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), writeESRequestCode);
                return false
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted2")
            return true
        }
    }
}







/** A basic Camera preview class */
@SuppressLint("ViewConstructor")
class CameraPreview(
    context: Context,
    private val mCamera: Camera
) : SurfaceView(context), SurfaceHolder.Callback {

    private val mHolder: SurfaceHolder = holder.apply {
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        addCallback(this@CameraPreview)
        // deprecated setting, but required on Android versions prior to 3.0
        setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        mCamera.apply {
            try {
                setPreviewDisplay(holder)
                startPreview()
            } catch (e: IOException) {
                Log.d("TAG", "Error setting camera preview: ${e.message}")
            }
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, w: Int, h: Int) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
        if (mHolder.surface == null) {
            // preview surface does not exist
            return
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview()
        } catch (e: Exception) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        mCamera.apply {
            try {
                setPreviewDisplay(mHolder)
                startPreview()
            } catch (e: Exception) {
                Log.d("TAG", "Error starting camera preview: ${e.message}")
            }
        }
    }



}