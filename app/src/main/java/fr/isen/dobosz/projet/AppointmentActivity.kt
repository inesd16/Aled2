package fr.isen.dobosz.projet

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_appointment.*
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*

class AppointmentActivity : AppCompatActivity() {

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)
        //val del = findViewById<ImageButton>(R.id.deleteAppointmentButton)


        val app = "{\"name\":\"Phillipe\",\"surname\":\"monteil\",\"type\":\"Généraliste\",\"date\":\"20\\/04\\/2020\",\"time\":\"10h00\"}"
        val app2 = "{\"name\":\"Fabienne\",\"surname\":\"Constantin\",\"type\":\"Généraliste\",\"date\":\"04\\/11\\/2020\",\"time\":\"15h00\"}"
        val app3 = "{\"name\":\"Vincent\",\"surname\":\"navarre\",\"type\":\"Généraliste\",\"date\":\"29\\/08\\/2020\",\"time\":\"12h30\"}"
        val array: ArrayList<String> = arrayListOf(app,app2,app3)
        //var jsonO = JSONObject(app)
        val jsonArray = JSONArray(array.toString())

//        jsonO = JSONObject(app2)
//        jsonArray.put(jsonO)
        val a:ArrayList<AppointmentModel> = arrayListOf()
        //System.out.println(array.toString())
        var i = 0
        while(i<3){
            a.add(AppointmentModel())
            a[i].name = jsonArray.getJSONObject(i).getString("name")
            a[i].surname = jsonArray.getJSONObject(i).getString("surname")
            a[i].date = jsonArray.getJSONObject(i).getString("date")
            a[i].type = jsonArray.getJSONObject(i).getString("type")
            a[i].time = jsonArray.getJSONObject(i).getString("time")

            //val formatter = SimpleDateFormat("dd/MM/yyyy")
            //val dateApp = formatter.format(a[i].date)
            val components = a[i].date!!.split("/")
            a[i].past = isPast(components[2].toInt(),components[1].toInt(),components[0].toInt())
            i++
        }


        appRecycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        appRecycleView.adapter = AppointmentAdapter(a)

        System.out.println(a.count())
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

    @SuppressLint("SimpleDateFormat")
    fun isPast(year: Int, month: Int, day: Int): Boolean {
        val currentDate = Date()
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val dateString = formatter.format(currentDate)
        val components = dateString.split("/")

        var age = components[2].toInt() - year
        if(components[2].toInt() < year){
            return false
        }
        else if(components[1].toInt() < month) {
            return false
        }
        else if (components[1].toInt() == month &&
            components[0].toInt() < day){
            return false
        }
        else return true

//        if(age < 0 || age > 120) {
//            this.age = age
//            return -1
//        }
    }
}
