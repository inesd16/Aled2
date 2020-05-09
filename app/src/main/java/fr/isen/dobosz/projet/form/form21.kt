package fr.isen.dobosz.projet.form

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import fr.isen.dobosz.projet.R

class form21 : AppCompatActivity() {
    private var Seekbar1: SeekBar? = null
    private var Switch2: Switch? = null
    private var Switch3: Switch? = null
    private var Switch4: Switch? = null
    private var Switch5: Switch? = null
    private var Go: Button? = null
    private var ret1 = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form21)
        Seekbar1 = findViewById(R.id.form21_seekBar1)
        Switch2 = findViewById(R.id.form21_switch2)
        Switch3 = findViewById(R.id.form21_switch3)
        Switch4 = findViewById(R.id.form21_switch4)
        Switch5 = findViewById(R.id.form21_switch5)
        Go = findViewById(R.id.form21_button1)
        val form = intent
        val extras = form.extras
        ret1 = extras!!.getInt("val")
        Go.setOnClickListener(object : OnClickListener() {
            fun onClick(v: View?) {
                when (ret1) {
                    1 -> {
                        val form1Activity = Intent(this@form21, form1::class.java)
                        form1Activity.putExtra("val", 0)
                        startActivity(form1Activity)
                    }
                    2 -> {
                        val form2Activity = Intent(this@form21, form2::class.java)
                        form2Activity.putExtra("val", 0)
                        startActivity(form2Activity)
                    }
                    3 -> {
                        val form3Activity = Intent(this@form21, form3::class.java)
                        form3Activity.putExtra("val", 0)
                        startActivity(form3Activity)
                    }
                    4 -> {
                        val form4Activity = Intent(this@form21, form4::class.java)
                        form4Activity.putExtra("val", 0)
                        startActivity(form4Activity)
                    }
                    5 -> {
                        val form5Activity = Intent(this@form21, form5::class.java)
                        form5Activity.putExtra("val", 0)
                        startActivity(form5Activity)
                    }
                    6 -> {
                        val form6Activity = Intent(this@form21, form6::class.java)
                        form6Activity.putExtra("val", 0)
                        startActivity(form6Activity)
                    }
                    7 -> {
                        val form7Activity = Intent(this@form21, form7::class.java)
                        form7Activity.putExtra("val", 0)
                        startActivity(form7Activity)
                    }
                    8 -> {
                        val form8Activity = Intent(this@form21, form8::class.java)
                        form8Activity.putExtra("val", 0)
                        startActivity(form8Activity)
                    }
                    9 -> {
                        val form9Activity = Intent(this@form21, form9::class.java)
                        form9Activity.putExtra("val", 0)
                        startActivity(form9Activity)
                    }
                    10 -> {
                        val form10Activity = Intent(this@form21, form10::class.java)
                        form10Activity.putExtra("val", 0)
                        startActivity(form10Activity)
                    }
                    11 -> {
                        val form11Activity = Intent(this@form21, form11::class.java)
                        form11Activity.putExtra("val", 0)
                        startActivity(form11Activity)
                    }
                    12 -> {
                        val form12Activity = Intent(this@form21, form12::class.java)
                        form12Activity.putExtra("val", 0)
                        startActivity(form12Activity)
                    }
                    13 -> {
                        val form13Activity = Intent(this@form21, form13::class.java)
                        form13Activity.putExtra("val", 0)
                        startActivity(form13Activity)
                    }
                    14 -> {
                        val form14Activity = Intent(this@form21, form14::class.java)
                        form14Activity.putExtra("val", 0)
                        startActivity(form14Activity)
                    }
                    15 -> {
                        val form15Activity = Intent(this@form21, form15::class.java)
                        form15Activity.putExtra("val", 0)
                        startActivity(form15Activity)
                    }
                    16 -> {
                        val form16Activity = Intent(this@form21, form16::class.java)
                        form16Activity.putExtra("val", 0)
                        startActivity(form16Activity)
                    }
                    17 -> {
                        val form17Activity = Intent(this@form21, form17::class.java)
                        form17Activity.putExtra("val", 0)
                        startActivity(form17Activity)
                    }
                    18 -> {
                        val form18Activity = Intent(this@form21, form18::class.java)
                        form18Activity.putExtra("val", 0)
                        startActivity(form18Activity)
                    }
                    19 -> {
                        val form19Activity = Intent(this@form21, form19::class.java)
                        form19Activity.putExtra("val", 0)
                        startActivity(form19Activity)
                    }
                    20 -> {
                        val form20Activity = Intent(this@form21, form20::class.java)
                        form20Activity.putExtra("val", 0)
                        startActivity(form20Activity)
                    }
                    21 -> {
                        val form21Activity = Intent(this@form21, form21::class.java)
                        form21Activity.putExtra("val", 0)
                        startActivity(form21Activity)
                    }
                    22 -> {
                        val form22Activity = Intent(this@form21, form22::class.java)
                        form22Activity.putExtra("val", 0)
                        startActivity(form22Activity)
                    }
                    else -> {
                    }
                }
            }
        })
    }
}
