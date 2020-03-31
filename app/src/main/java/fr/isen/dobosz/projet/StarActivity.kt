package fr.isen.dobosz.projet

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.telecom.InCallService
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import fr.isen.dobosz.projet.HomeActivity.Companion.newTime
import kotlinx.android.synthetic.main.activity_star.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.*



class StarActivity : AppCompatActivity() {
    private var newTimeStar = true
    lateinit var videoUri: Uri
    lateinit var videoPath:String

    companion object{
        var cameraRequestCode = 1
        var gpsRequestCode = 2
        var callRequestCode = 3
        var videoRequestCode = 4
        var writeESRequestCode = 9
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_star)

        requestPermission(Manifest.permission.CAMERA, cameraRequestCode) {

        }
        emergencyCallButton.setOnClickListener{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.CALL_PHONE),callRequestCode)
            } else {
                startCall()
            }
        }
        videoButton.setOnClickListener{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    writeESRequestCode)
            } else {
                recordVideo()
            }
        }

        starImageView.setOnTouchListener { v, event ->
            val action = event.action
            when(action){

                MotionEvent.ACTION_DOWN -> {
                    System.out.println("BUTTON ACTION Down")
                    startTextColor.setTextColor(getResources().getColor(R.color.colorConnect))
                    // var newTime:Boolean = true
                    val x = Math.round(event.x)
                    val y = Math.round(event.y)

                    System.out.println("POSITION")
                    System.out.println("x : " + x)
                    System.out.println("y : " + y)
                                  // is new time (click down)
                    val jsonArray = JSONArray() //create new
                    jsonArray.put("etoile") //from starAct
                    save(jsonArray, x, y)
                    newTimeStar = false

                }

                MotionEvent.ACTION_MOVE -> {

                    System.out.println("ACTION_MOVE")
                    // var newTime:Boolean = true
                    val x = Math.round(event.x)
                    val y = Math.round(event.y)

                    System.out.println("POSITION")
                    System.out.println("x : " + x)
                    System.out.println("y : " + y)

                    save(readPreviousClick(), x,y)
                }

                MotionEvent.ACTION_UP -> {
                    System.out.println("BUTTON ACTION UP")
                    startTextColor.setTextColor(getResources().getColor(R.color.colorGreen))
                    // var newTime:Boolean = true
                    val x = Math.round(event.x)
                    val y = Math.round(event.y)

                    System.out.println("POSITION")
                    System.out.println("x : " + x)
                    System.out.println("y : " + y)

                    save(readPreviousClick(), x,y)
                    nextButton.visibility = View.VISIBLE
                }

                MotionEvent.ACTION_CANCEL -> {

                }

                else ->{

                }
            }
            true
        }

    }


    fun readPreviousClick():JSONArray{

        val sharedPrefDungeon = this.getSharedPreferences("sharedPrefDungeon", Context.MODE_PRIVATE)
        val readString = sharedPrefDungeon.getString("backupGameJson", "") ?:""
        val jsonArray = JSONArray(readString)
        System.out.println(jsonArray)
        Log.d("DungeonCardActivityREAD", jsonArray.toString())
        //System.out.println(jsonArray)
        //System.out.println("READ"+readString)
        return(jsonArray)
    }

    fun save(jsonArray: JSONArray, x:Int,y:Int){
        val jsonObj = JSONObject()
        val millis = System.currentTimeMillis()

//Divide millis by 1000 to get the number of seconds.
        //Divide millis by 1000 to get the number of seconds.
        //val seconds = millis / 1000

        var currentTime: Date = Calendar.getInstance().getTime()
        System.out.println(millis)
        jsonObj.put("clickX",x)
        jsonObj.put("clickY",y)
        jsonObj.put("timeClickedMillis",millis-1585154297320)
        jsonArray.put(jsonObj)
        val sharedPrefDungeon = this.getSharedPreferences("sharedPrefDungeon", Context.MODE_PRIVATE) ?: return
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
            MotionEvent.ACTION_UP -> {
            }

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
                    newTimeStar = false

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
                if(newTimeStar){
                    val jsonArray = JSONArray()
                    save(jsonArray, x, y)
                    newTimeStar = false
                }

                else{
                    save(readPreviousClick(), x,y)
                }
                //writeFile()
            }



        }
        return true
    }

    fun checkStar(){

    }

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

            if (requestCode == cameraRequestCode) {
                startCall()
                System.out.println("PERMISSION CAMERA")
            }
        if(requestCode == writeESRequestCode) {
                recordVideo()
                System.out.println("PERMISSION CAMERA")
            }


        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun startCall(){
        val intentcall = Intent()
        intentcall.action = Intent.ACTION_CALL
        intentcall.data = Uri.parse("+33698305732") // set the Uri

        startActivity(intentcall)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == videoRequestCode && resultCode == Activity.RESULT_OK) {

            //video_view.setVideoUri(videoUri)
            //video_view.start()

            addToGallery()

        }
    }
    fun addToGallery(){
        val mediaScan = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val file = File(videoPath)
        val uri = Uri.fromFile(file)
        mediaScan.setData(uri)
        this.sendBroadcast(mediaScan)

    }
    fun createVideoFile(): File {
        val fileName = "Video"
        val storageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()+"/Video")
        if(!storageDir.exists()){
            storageDir.mkdir()
        }
        return File.createTempFile(fileName,".mp4",storageDir)
    }
    fun recordVideo(){
        val videoFile: File = createVideoFile()
        videoPath = videoFile.absolutePath
        if(videoFile != null){
            videoUri = FileProvider.getUriForFile(this,"com.video.record.fileprovider",videoFile)
        }
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT,videoUri)
        startActivityForResult(intent,videoRequestCode)
    }
}
