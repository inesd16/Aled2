package fr.isen.dobosz.projet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_appointment.*
import org.json.JSONArray

class AppointmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)

        val app = "{\"name\":\"Phillipe\",\"surname\":\"monteil\",\"type\":\"Généraliste\",\"date\":\"20\\/05\\/2020\",\"time\":\"10h00\"}"
        val app2 = "{\"name\":\"Fabienne\",\"surname\":\"Constantin\",\"type\":\"Généraliste\",\"date\":\"04\\/11\\/2020\",\"time\":\"15h00\"}"
        val app3 = "{\"name\":\"Vincent\",\"surname\":\"navarre\",\"type\":\"Généraliste\",\"date\":\"29\\/08\\/2020\",\"time\":\"12h30\"}"
        val array: ArrayList<String> = arrayListOf(app,app2,app3)
        //var jsonO = JSONObject(app)
        val jsonArray = JSONArray(array.toString())
//        jsonO = JSONObject(app2)
//        jsonArray.put(jsonO)
        val a:ArrayList<AppointmentModel> = arrayListOf()
        var i = 0
        while(i<3){
            a.add(AppointmentModel())
            a[i].name = jsonArray.getJSONObject(i).getString("name")
            a[i].surname = jsonArray.getJSONObject(i).getString("surname")
            a[i].date = jsonArray.getJSONObject(i).getString("date")
            a[i].type = jsonArray.getJSONObject(i).getString("type")
            a[i].time = jsonArray.getJSONObject(i).getString("time")
            i++
        }


        appRecycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        appRecycleView.adapter = AppointmentAdapter(a)

        System.out.println(a.count())
    }
}
