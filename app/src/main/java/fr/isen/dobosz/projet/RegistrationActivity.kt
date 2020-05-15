package fr.isen.dobosz.projet

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class RegistrationActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
     var password: String = ""
     var email: String = ""
    private var mAuth: FirebaseAuth? = null
    var keyBordStrings:String = ""

    var weightValues = arrayOfNulls<String>(151)
    var heightValues = arrayOfNulls<String>(151)
    var age:Int? = null



    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        setContentView(R.layout.activity_registration)
        var i = 0
        while (i<=150){
            weightValues.set(i,i.toString()+" kg")
            heightValues.set(i,(i+70).toString()+" cm")
            i++
        }

        val spannable = SpannableString(acceptPolicyCheckBox.getText())

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // We display a Toast. You could do anything you want here.
                Toast.makeText(this@RegistrationActivity, "Clicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@RegistrationActivity, Policy::class.java)
                startActivity(intent)
            }
        }
        spannable.setSpan(clickableSpan, 10, acceptPolicyCheckBox.getText().length, 0)
        spannable.setSpan(ForegroundColorSpan(Color.CYAN), 10, acceptPolicyCheckBox.getText().length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        //spannable.setSpan(clickableSpan, 10, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


        acceptPolicyCheckBox.setText(spannable, TextView.BufferType.SPANNABLE)
        // this step is mandated for the url and clickable styles.
        acceptPolicyCheckBox.movementMethod = LinkMovementMethod.getInstance()

        acceptPolicyCheckBox.text = spannable


        //styledString.setSpan(clickableSpan, 10, acceptPolicyCheckBox.getText().length, 0)


        initializeSpinners()
        dateText.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus) {
                dateText.clearFocus()
                val dialog = DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        if(getAge(year, month, dayOfMonth)==-1){
                            Toast.makeText(
                                this,
                                "La date n'est pas correcte",
                                Toast.LENGTH_LONG
                            ).show()
                            ageText.setText("X")
                            ageText.visibility = View.VISIBLE

                        }
                        else{
                            ageText.setText((getAge(year, month, dayOfMonth).toString())+" ans")
                            ageText.visibility = View.VISIBLE
                            onDateChoose(year, month, dayOfMonth)
                        }

                    },
                    1970,
                    1,
                    1)
                dialog.show()
            }
        }
        //genderSwitch.setThumbResource(R.drawable.avatar)
        //genderSwitch.setThumbResource(R.id.female)
        genderSwitch.setOnClickListener(){
            if(genderSwitch.isChecked){
                System.out.println("ischecked "+genderSwitch.text)
            }
            if(!genderSwitch.isChecked){
                System.out.println("isNOTchecked "+genderSwitch.text)
            }

        }

        changePicButton.setOnClickListener(){
            onChangePhoto()
        }

        var database = FirebaseDatabase.getInstance()
//        var myRef = database.getReferenceFromUrl("user/Email")
  //      myRef.setValue("HelloWorld");
        registerButton.setOnClickListener() {
            if(checkForm()){
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }

            System.out.println(passwField.getText().toString() + confirmPasswField.getText().toString())
        }
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
        //return super.onOptionsItemSelected(item)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        //val currentUser = mAuth?.getCurrentUser()
        //updateUI(currentUser)

    }

    fun checkPasswords(): Boolean {
        if (passwField.getText().toString().length == confirmPasswField.getText().toString().length) { //same length
            if (passwField.getText().toString().length < 8) { //length not enough
                Toast.makeText(
                    this,
                    "Passwords must contain at least 8 characters",
                    Toast.LENGTH_LONG
                ).show()
                return false
            } else if (passwField.getText().toString() != confirmPasswField.getText().toString()) { //different passw
                Toast.makeText(this, "The passwords must be the same", Toast.LENGTH_LONG).show()
                return false
            } else { //password are the same
                var countNumber = false //passw does not contain number
                var countCapLetter = false //passw does not capital letter
                for (letter in passwField.getText().toString()) { //Check number in passw
                    try { //is letter a number ?
                        // val i = Integer.parseInt(letter.toString())
                        //System.out.println("C'est un entier")
                        informations.setText("")
                        countNumber = true
                    } catch (e: Exception) {
                        //System.out.println("Je ne suis pas un entier, et alors ca te derange ?")
                    }
                    if (letter == letter.toUpperCase()) { // letter upCase
                        // Vérifier si le texte est en majuscule
                        countCapLetter = true
                    }
                }
                if (countNumber && countCapLetter)
                    return true
                else {
                    informations.setText(R.string.stringPasswordInfo)
                    return false
                }
            }
        } else { //password not same length
            Toast.makeText(this, "The passwords must be the same", Toast.LENGTH_LONG).show()
            return false
        }
    }


    fun checkFields(): Boolean {
        if (nameField.getText().toString() == "" || surnameField.getText().toString() == "" ||
            emailField.getText().toString() == "" || passwField.getText().toString() == "" ||
            confirmPasswField.getText().toString() == "" || dateText.getText().toString() == "") {

            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show()
            return false
        } else
            return true
    }

    fun checkMail(): Boolean {
        val masque =
            "^[a-zA-Z]+[a-zA-Z0-9\\._-]*[a-zA-Z0-9]@[a-zA-Z]+" + "[a-zA-Z0-9\\._-]*[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$"
        val pattern = Pattern.compile(masque)
        val controler = pattern.matcher(emailField.getText().toString())
        if (controler.matches()) {
            //System.out.println("Ok")
            return true

        } else {
            informations.setText(R.string.emailError)
            return false
        }
    }

    fun checkNames(): Boolean {
        for (letter in nameField.getText().toString()) {
            if (letter.toString() == " ") {
                informations.setText(R.string.noSpace)
                return false
            }
        }
        for (letter in surnameField.getText().toString()) {
            if (letter.toString() == " ") {
                informations.setText(R.string.noSpace)
                return false
            }
        }
        return true
    }
    fun checkSecretAnswer(): Boolean {
        for (letter in secretQuestionEdit.getText().toString()) {
            if (letter.toString() == " ") {
                informations.setText(R.string.noSpaceSecretAnswer)
                return false
            }
        }
        return true
    }

    fun checkBoxes():Boolean{
        if(acceptDiagnosisCheckBox.isChecked && acceptPolicyCheckBox.isChecked){
            informations.setText(R.string.acceptConditions)
            return true
        }
        return false
    }
    fun checkBirthDate():Boolean{
        return(age!!>0)
    }


    fun checkForm(): Boolean{
        if (checkFields()) {
            if (checkNames()) {
                if (checkMail()) {
                    if (checkPasswords()) {
                        if (checkSecretAnswer()) {
                            if (checkBoxes()) {
                                if (checkBirthDate()){

                                System.out.println("SAnswer OK")
                                // change activity
                                password = passwField.getText().toString()
                                email = emailField.getText().toString()

                                mAuth = FirebaseAuth.getInstance()
                                mAuth!!.createUserWithEmailAndPassword(
                                    email,
                                    password
                                )
                                    .addOnCompleteListener(
                                        this
                                    ) { task ->
                                        if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                                            //Log.d(FragmentActivity.TAG, "createUserWithEmail:success")
                                            informations.setText(R.string.ok)
                                            val user = mAuth!!.currentUser

                                            //updateUI(user)
                                        } else { // If sign in fails, display a message to the user.
                                            val TAG = "EmailPassword"
                                            Log.w(
                                                TAG, "createUserWithEmail:failure",
                                                task.exception
                                            )
                                            Toast.makeText(
                                                this@RegistrationActivity, "Authentication failed.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            informations.setText(R.string.emailAlreadyUsed)
                                            //updateUI(null)
                                        }

                                    }

                                Toast.makeText(
                                    this,
                                    "Vous avez bien été inscrit", Toast.LENGTH_LONG
                                ).show()
                                saveNewUser()
                                return true
                            }
                        }
                        }
                    }
                }
            }
            //if checkPasswords()
        }
    return false

    }

    fun initializeSpinners() {
        val spinner = findViewById<Spinner>(R.id.secretQuestionSpinner)
        val adapter = ArrayAdapter.createFromResource(this, R.array.secretQuestion, android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aaWeight = ArrayAdapter(this, android.R.layout.simple_spinner_item, weightValues)
        // Set layout to use when the list of choices appear
        aaWeight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        weightSpinner!!.setAdapter(aaWeight)
        weightSpinner.setSelection(70)

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aaHeight = ArrayAdapter(this, android.R.layout.simple_spinner_item, heightValues)
        // Set layout to use when the list of choices appear
        aaHeight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        heightSpinner!!.setAdapter(aaHeight)
        heightSpinner.setSelection(90)


    }

    fun onDateChoose(year: Int, month: Int, day: Int) {

        dateText.setText(String.format("%02d/%02d/%04d",day,month+1,year))

        //Toast.makeText(this, "date : ${dateText.text.toString()}", Toast.LENGTH_LONG).show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        System.out.println("ELEMENT SELECTIONNE"+parent!!.getItemAtPosition(position))
    }
    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback

    }

    fun saveNewUser(){
        val jsonObj = JSONObject()
        //val jsonArray = JSONArray()

        jsonObj.put("name",nameField.getText().toString())
        jsonObj.put("surname",surnameField.getText())
        jsonObj.put("email",emailField.getText())
        val hash = passwField.text.toString().hashCode()

        System.out.println("HASH"+hash)
        jsonObj.put("password",hash)
        jsonObj.put("birthDate",dateText.getText())
        jsonObj.put("weight",weightSpinner.selectedItem)
        jsonObj.put("height",heightSpinner.selectedItem)
        jsonObj.put("secretQuestion",secretQuestionSpinner.selectedItem)
        jsonObj.put("secretAnswer",secretQuestionEdit.getText())
        jsonObj.put("age",age)

        if(genderSwitch.isChecked)      jsonObj.put("Gender",genderSwitch.textOn)
        if(!genderSwitch.isChecked)     jsonObj.put("Gender",genderSwitch.textOff)

        //jsonArray.put(jsonObj) //just to register in sharedPref
        val sharedNewUser = this.getSharedPreferences("sharedNewUser", Context.MODE_PRIVATE) ?: return
        with(sharedNewUser.edit()) {
            putString("userInfo", jsonObj.toString())
            //putBoolean("saveState",true)
            commit()
        }
        System.out.println("JSON"+jsonObj.toString())
    }

    fun String.toSHA(): String {
        val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
        return bytes.toHex()
    }

    fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
    }

    @SuppressLint("SimpleDateFormat")
    fun getAge(year: Int, month: Int, day: Int): Int {
        val currentDate = Date()
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val dateString = formatter.format(currentDate)
        val components = dateString.split("/")

        var age = components[2].toInt() - year
        if(components[1].toInt() < month) {
            age--
        } else if (components[1].toInt() == month &&
            components[0].toInt() < day){
            age --
        }
        if(age < 0 || age > 120) {
            this.age = age
            return -1
        }
        System.out.println("TU AS "+age)
        // field_age.setText("Vous avez ${getAge(components[2].toInt(), components[1].toInt(), components[0].toInt())} ans")
        this.age = age
        return age
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == StarActivity.cameraRequestCode &&
            resultCode == Activity.RESULT_OK) {


            val imageFileName = "profilePic.jpg"
            val bytearrayoutputstream = ByteArrayOutputStream()
            val state = Environment.getExternalStorageState()
            var success = true
            val root = this.getExternalFilesDir("ProfileInfo")

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

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // val cameraIntent2 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val intentChooser = Intent.createChooser(galleryIntent, "Choose your picture library")
        //var picture = Intent.EXTRA_INITIAL_INTENTS
        //cameraIntent2.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
        //intentChooser.putExtra(picture, arrayOf(cameraIntent))
        //intentChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
        intentChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))

        startActivityForResult(intentChooser, StarActivity.cameraRequestCode)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        System.out.println(keyCode)
        return super.onKeyDown(keyCode, event)
    }
    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        var jsonKeyboard = JSONObject()
        keyBordStrings = keyBordStrings+keyCode.toString()
        return when (keyCode) {
            KeyEvent.KEYCODE_D -> {
                System.out.println("D APPUYE")
                true
            }
            KeyEvent.KEYCODE_F -> {
                true
            }
            KeyEvent.KEYCODE_J -> {
                true
            }
            KeyEvent.KEYCODE_K -> {
                true
            }
            else -> { System.out.println(keyBordStrings)
                super.onKeyUp(keyCode, event)}
        }
    }
}

