package fr.isen.dobosz.projet.form

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import fr.isen.dobosz.projet.R
import fr.isen.dobosz.projet.StarActivity
import kotlinx.android.synthetic.main.activity_form3.*
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class form3 : AppCompatActivity() {
    companion object{
        var writeESRequestCode = 9
        var readESRequestCode = 10
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form3)
        if(isWriteStoragePermissionGranted()){
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, StarActivity.writeESRequestCode) {
            }
        }
        if(isReadStoragePermissionGranted()){
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, StarActivity.readESRequestCode) {
            }
        }
        val form: Intent ?= intent
        val extras: Bundle ?= form?.extras
        button.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                when (Log.d("ret1", extras?.getInt("val").toString())) {
                    1 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form1Activity = Intent(this@form3, form1::class.java)
                        form1Activity.putExtra("val", 0)
                        startActivity(form1Activity)
                    }
                    2 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form2Activity = Intent(this@form3, form2::class.java)
                        form2Activity.putExtra("val", 0)
                        startActivity(form2Activity)
                    }
                    3 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form3Activity = Intent(this@form3, form3::class.java)
                        form3Activity.putExtra("val", 0)
                        startActivity(form3Activity)
                    }
                    4 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form4Activity = Intent(this@form3, form4::class.java)
                        form4Activity.putExtra("val", 0)
                        startActivity(form4Activity)
                    }
                    5 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form5Activity = Intent(this@form3, form5::class.java)
                        form5Activity.putExtra("val", 0)
                        startActivity(form5Activity)
                    }
                    6 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form6Activity = Intent(this@form3, form6::class.java)
                        form6Activity.putExtra("val", 0)
                        startActivity(form6Activity)
                    }
                    7 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form7Activity = Intent(this@form3, form7::class.java)
                        form7Activity.putExtra("val", 0)
                        startActivity(form7Activity)
                    }
                    8 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form8Activity = Intent(this@form3, form8::class.java)
                        form8Activity.putExtra("val", 0)
                        startActivity(form8Activity)
                    }
                    9 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form9Activity = Intent(this@form3, form9::class.java)
                        form9Activity.putExtra("val", 0)
                        startActivity(form9Activity)
                    }
                    10 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form10Activity = Intent(this@form3, form10::class.java)
                        form10Activity.putExtra("val", 0)
                        startActivity(form10Activity)
                    }
                    11 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form11Activity = Intent(this@form3, form11::class.java)
                        form11Activity.putExtra("val", 0)
                        startActivity(form11Activity)
                    }
                    12 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form12Activity = Intent(this@form3, form12::class.java)
                        form12Activity.putExtra("val", 0)
                        startActivity(form12Activity)
                    }
                    13 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form13Activity = Intent(this@form3, form13::class.java)
                        form13Activity.putExtra("val", 0)
                        startActivity(form13Activity)
                    }
                    14 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form14Activity = Intent(this@form3, form14::class.java)
                        form14Activity.putExtra("val", 0)
                        startActivity(form14Activity)
                    }
                    15 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form15Activity = Intent(this@form3, form15::class.java)
                        form15Activity.putExtra("val", 0)
                        startActivity(form15Activity)
                    }
                    16 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form16Activity = Intent(this@form3, form16::class.java)
                        form16Activity.putExtra("val", 0)
                        startActivity(form16Activity)
                    }
                    17 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form17Activity = Intent(this@form3, form17::class.java)
                        form17Activity.putExtra("val", 0)
                        startActivity(form17Activity)
                    }
                    18 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form18Activity = Intent(this@form3, form18::class.java)
                        form18Activity.putExtra("val", 0)
                        startActivity(form18Activity)
                    }
                    19 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form19Activity = Intent(this@form3, form19::class.java)
                        form19Activity.putExtra("val", 0)
                        startActivity(form19Activity)
                    }
                    20 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form20Activity = Intent(this@form3, form20::class.java)
                        form20Activity.putExtra("val", 0)
                        startActivity(form20Activity)
                    }
                    21 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form21Activity = Intent(this@form3, form21::class.java)
                        form21Activity.putExtra("val", 0)
                        startActivity(form21Activity)
                    }
                    22 -> {
                        saveData()
                        writeFile(text13.text.toString(), text23.text.toString(), text33.text.toString(), text43.text.toString(), this)
                        read()
                        val form22Activity = Intent(this@form3, form22::class.java)
                        form22Activity.putExtra("val", 0)
                        startActivity(form22Activity)
                    }
                    else -> {
                    }
                }
            }
        })
    }

    fun saveData(){
        val jsonObj = JSONObject()
        jsonObj.put("reponse 1", text13.text.toString())
        jsonObj.put("reponse 2", text23.text.toString())
        jsonObj.put("reponse 3", text33.text.toString())
        jsonObj.put("reponse 4", text43.text.toString())
        val sharedNewAnswer = this.getSharedPreferences("sharedNewAnswer", Context.MODE_PRIVATE) ?: return
        with(sharedNewAnswer.edit()) {
            putString("userResponse3", jsonObj.toString())
            commit()
        }
    }

    fun read() {
        requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, readESRequestCode) {
            val root = getExternalFilesDir("DataToSend")
            val dir = File(root!!.absolutePath)
            val file = File(dir, "answerForm3.txt")
            val read = file.readText()
            System.out.println("VALEUR READ " + read)
            //Read text from file
            // val iStream = openFileInput(dir.toString())
        }
    }

    fun writeFile(coords1: String, coords2: String, coords3: String, coords4: String, context: View.OnClickListener){
        val state = Environment.getExternalStorageState()
        var success = true
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
                val file = File(dir, "answerForm3.txt")
                System.out.println("success true")
                try {
                    file.createNewFile()
                    file.writeText(coords1)
                    file.writeText(coords2)
                    file.writeText(coords3)
                    file.writeText(coords4)
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
            9 -> {
                Log.d("TAG", "External storage2")
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v("TAG", "Permission: " + permissions[0] + "was " + grantResults[0])
                    //resume tasks needing this permission
                    //downloadPdfFile();
                } else {
                    //progress.dismiss();
                }
            }
            10 -> {
                Log.d("TAG", "External storage1")
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v("TAG", "Permission: " + permissions[0] + "was " + grantResults[0])
                    //resume tasks needing this permission
                    //SharePdfFile();
                } else {
                    //progress.dismiss();
                }
            }
        }
    }

    fun isReadStoragePermissionGranted():Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted1")
                return true
            } else {

                Log.v("TAG","Permission is revoked1")
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
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
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted2")
                return true
            } else {

                Log.v("TAG","Permission is revoked2")
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
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

