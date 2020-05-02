package fr.isen.dobosz.projet

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_edit_pers_info_activity.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditPersInfoActivity.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditPersInfoActivity : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    fun read(view: View){

            val root = context!!.getExternalFilesDir("ProfileInfo")
            val dir = File(root!!.absolutePath)
            val file = File(dir, "ProfilePic.jpg")
            try{
            var picButt = view.findViewById<ImageButton>(R.id.changePicButton)
                picButt.setImageURI(Uri.parse(file.toString()))
            } catch(e:Exception){
                e.printStackTrace()
            }
            //val read = file.readText()
            //System.out.println("VALEUR READ "+read)
            //Read text from file
            // val iStream = openFileInput(dir.toString())

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_pers_info_activity, container, false)

        var confirmButton:Button = view.findViewById<Button>(R.id.confirmButton)
        confirmButton.setOnClickListener() {

            val name: EditText = view.findViewById(R.id.nameEditText)
            val surname: EditText = view.findViewById(R.id.surnameEditText)
            val email: EditText = view.findViewById(R.id.emailEditText)
            val phoneNo: EditText = view.findViewById(R.id.phoneNumber)
            val gender: Switch = view.findViewById(R.id.genderSwitch)
            val info: TextView = view.findViewById(R.id.info)

            val sharedSaveNewUser: SharedPreferences =
                this.activity!!.getSharedPreferences("sharedNewUser", Context.MODE_PRIVATE)
            val readString = sharedSaveNewUser.getString("userInfo", "") ?: ""
//            val jsonArray = JSONArray(readString)
            var jsonObj = JSONObject(readString)

            if (!name.getText().toString().equals("")) {
                jsonObj.put("name", name.getText())
                view.findViewById<TextView>(R.id.info).setText(R.string.allEdited)
            }
            if (!surname.getText().toString().equals("")) {
                jsonObj.put("surname", surname.getText())
                view.findViewById<TextView>(R.id.info).setText(R.string.allEdited)
            }
            if (!email.getText().toString().equals("")) {
                jsonObj.put("email", email.getText())
                view.findViewById<TextView>(R.id.info).setText(R.string.allEdited)
            }
            if (!phoneNo.getText().toString().equals("")) {
                if (phoneNo.length() < 10) {
                    view.findViewById<TextView>(R.id.info).setText(R.string.incorrectPhoneNo)
                } else {
                    jsonObj.put("phoneNo", phoneNo.getText())
                    view.findViewById<TextView>(R.id.info).setText(R.string.allEdited)
                }
            }
            gender.setOnClickListener() {
                if (gender.isChecked) {
                    System.out.println("ischecked " + gender.text)
                    jsonObj.put("Gender", gender.textOn)
                }
                if (!gender.isChecked) {
                    System.out.println("isNOTchecked " + gender.text)
                    jsonObj.put("Gender", gender.textOff)
                }
            }

            System.out.println("JSON APRES MODIF : " + jsonObj)
            with(sharedSaveNewUser.edit()) {
                putString("userInfo", jsonObj.toString())
                commit()
            }

            info.visibility = View.VISIBLE
            System.out.println(jsonObj.toString())
        }

        read(view)
        var picButton: ImageButton = view.findViewById(R.id.changePicButton)
        picButton.setOnClickListener(){
            onChangePhoto()
        }

        return view
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == StarActivity.cameraRequestCode &&
            resultCode == Activity.RESULT_OK) {


            val imageFileName = "profilePic.jpg"
            var bytearrayoutputstream = ByteArrayOutputStream()
            val state = Environment.getExternalStorageState()
            var success = true
            val root = context!!.getExternalFilesDir("ProfileInfo")

            if(data?.data != null) { // Gallery

                val pathURI = data.data!!.getPath()
                System.out.println("CHEMIN "+pathURI.toString())
                //val bitmap = data?.extras?.get("data") as? Bitmap
                //bitmap!!.compress(Bitmap.CompressFormat.PNG, 60, bytearrayoutputstream)
                if(Environment.MEDIA_MOUNTED.equals((state))){
                    val dir = File(root!!.absolutePath)
                    val dirTake = File(pathURI.toString())
                    System.out.println(dir.toString())
                    if(!dir.exists()){
                        success = dir.mkdir()
                        //System.out.println("does not exists yet")
                    }
                    else{
                        //System.out.println("exists")
                    }
                    if (success) {
                        val file = File(dir, imageFileName)
                        //System.out.println("success true")
                        try {
                            file.createNewFile()
                            var a = dirTake.readBytes()
                            file.writeBytes(dirTake.readBytes())
                            System.out.println("SAVED")
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }

                changePicButton.setImageURI(data.data)
            } else { // Camera
                val bitmap = data?.extras?.get("data") as? Bitmap

                bitmap?.let {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 60, bytearrayoutputstream)


                    if(Environment.MEDIA_MOUNTED.equals((state))){
                        val dir = File(root!!.absolutePath)
                        System.out.println(dir.toString())
                        if(!dir.exists()){
                            success = dir.mkdir()
                            //System.out.println("does not exists yet")
                        }
                        else{
                            //System.out.println("exists")
                        }
                        if (success) {
                            val file = File(dir, imageFileName)
                            //System.out.println("success true")
                            try {
                                file.createNewFile()
                                file.writeBytes(bytearrayoutputstream.toByteArray())
                                //System.out.println("SAVED")
                            } catch (e: FileNotFoundException) {
                                e.printStackTrace()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    }
                    changePicButton.setImageBitmap(it)
                }
            }
        }
    }
    fun onChangePhoto() {/*
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "IMAGE$timeStamp.jpg"
        val storageDir: File = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        )
        var pictureImagePath = storageDir.getAbsolutePath().toString() + "/" + imageFileName
        val file = File(pictureImagePath)
        val outputFileUri: Uri = Uri.fromFile(file)*/
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"

        val cameraIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
       // val cameraIntent2 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val intentChooser = Intent.createChooser(galleryIntent, "Choose your picture library")
        //var picture = Intent.EXTRA_INITIAL_INTENTS
        //cameraIntent2.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
        //intentChooser.putExtra(picture, arrayOf(cameraIntent))
        //intentChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
        intentChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))

        startActivityForResult(intentChooser, StarActivity.cameraRequestCode)
    }
}
