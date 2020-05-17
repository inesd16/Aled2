package fr.isen.dobosz.projet.form

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import fr.isen.dobosz.projet.R


class FormActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {
    private val table = arrayOfNulls<Chip>(23)
    private var mNext: Button? = null
    private var count = 0
    private var ID = 0
    private val genename = "activity_form0"
    private var name: String? = null
    private var i = 0
    private var ret1 = 0
    private var ret2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form0)
        this.mNext = findViewById(R.id.activity_form0_button1)
        i = 0
        while (i < table.size) {
            name = genename + i
            ID = resources.getIdentifier(name, "id", packageName)
            table[i] = findViewById(ID)
            table[i]?.setOnCheckedChangeListener(this)
            table[i]?.setTag(i)
            i++
        }
        mNext?.setClickable(false)
        table[0]?.isCheckable = false
        mNext?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                i = 0
                while (i < table.size) {
                    if (table[i]?.isChecked!!) {
                        ret2 = ret1
                        ret1 = i
                    }
                    i++
                }
                when (ret1) {
                    1 -> {
                        val form1Activity = Intent(this@FormActivity, form1::class.java)
                        form1Activity.putExtra("val", ret2)
                        startActivity(form1Activity)
                    }
                    2 -> {
                        val form2Activity = Intent(this@FormActivity, form2::class.java)
                        form2Activity.putExtra("val", ret2)
                        startActivity(form2Activity)
                    }
                    3 -> {
                        val form3Activity = Intent(this@FormActivity, form3::class.java)
                        form3Activity.putExtra("val", ret2)
                        startActivity(form3Activity)
                    }
                    4 -> {
                        val form4Activity = Intent(this@FormActivity, form4::class.java)
                        form4Activity.putExtra("val", ret2)
                        startActivity(form4Activity)
                    }
                    5 -> {
                        val form5Activity = Intent(this@FormActivity, form5::class.java)
                        form5Activity.putExtra("val", ret2)
                        startActivity(form5Activity)
                    }
                    6 -> {
                        val form6Activity = Intent(this@FormActivity, form6::class.java)
                        form6Activity.putExtra("val", ret2)
                        startActivity(form6Activity)
                    }
                    7 -> {
                        val form7Activity = Intent(this@FormActivity, form7::class.java)
                        form7Activity.putExtra("val", ret2)
                        startActivity(form7Activity)
                    }
                    8 -> {
                        val form8Activity = Intent(this@FormActivity, form8::class.java)
                        form8Activity.putExtra("val", ret2)
                        startActivity(form8Activity)
                    }
                    9 -> {
                        val form9Activity = Intent(this@FormActivity, form9::class.java)
                        form9Activity.putExtra("val", ret2)
                        startActivity(form9Activity)
                    }
                    10 -> {
                        val form10Activity = Intent(this@FormActivity, form10::class.java)
                        form10Activity.putExtra("val", ret2)
                        startActivity(form10Activity)
                    }
                    11 -> {
                        val form11Activity = Intent(this@FormActivity, form11::class.java)
                        form11Activity.putExtra("val", ret2)
                        startActivity(form11Activity)
                    }
                    12 -> {
                        val form12Activity = Intent(this@FormActivity, form12::class.java)
                        form12Activity.putExtra("val", ret2)
                        startActivity(form12Activity)
                    }
                    13 -> {
                        val form13Activity = Intent(this@FormActivity, form13::class.java)
                        form13Activity.putExtra("val", ret2)
                        startActivity(form13Activity)
                    }
                    14 -> {
                        val form14Activity = Intent(this@FormActivity, form14::class.java)
                        form14Activity.putExtra("val", ret2)
                        startActivity(form14Activity)
                    }
                    15 -> {
                        val form15Activity = Intent(this@FormActivity, form15::class.java)
                        form15Activity.putExtra("val", ret2)
                        startActivity(form15Activity)
                    }
                    16 -> {
                        val form16Activity = Intent(this@FormActivity, form16::class.java)
                        form16Activity.putExtra("val", ret2)
                        startActivity(form16Activity)
                    }
                    17 -> {
                        val form17Activity = Intent(this@FormActivity, form17::class.java)
                        form17Activity.putExtra("val", ret2)
                        startActivity(form17Activity)
                    }
                    18 -> {
                        val form18Activity = Intent(this@FormActivity, form18::class.java)
                        form18Activity.putExtra("val", ret2)
                        startActivity(form18Activity)
                    }
                    19 -> {
                        val form19Activity = Intent(this@FormActivity, form19::class.java)
                        form19Activity.putExtra("val", ret2)
                        startActivity(form19Activity)
                    }
                    20 -> {
                        val form20Activity = Intent(this@FormActivity, form20::class.java)
                        form20Activity.putExtra("val", ret2)
                        startActivity(form20Activity)
                    }
                    21 -> {
                        val form21Activity = Intent(this@FormActivity, form21::class.java)
                        form21Activity.putExtra("val", ret2)
                        startActivity(form21Activity)
                    }
                    22 -> {
                        val form22Activity = Intent(this@FormActivity, form22::class.java)
                        form22Activity.putExtra("val", ret2)
                        startActivity(form22Activity)
                    }
                    else -> {
                    }
                }
            }
        })
    }

    private fun shrek(chipu: Chip?) {
        if (chipu?.isChecked!!) {
            count + 1
        } else count - 1
        if (count == 2) {
            i = 0
            while (i < table.size) {
                if (!table[i]?.isChecked!!) {
                    table[i]?.isCheckable = false
                }
                mNext?.setClickable(true)
                i++
            }
        } else {
            i = 0
            while (i < table.size) {
                table[i]?.isCheckable = true
                i++
            }
        }
        table[0]?.isCheckable = count == 1
    }

    override fun onCheckedChanged(
        buttonView: CompoundButton,
        isChecked: Boolean
    ) {
        val index = buttonView.tag as Int
        shrek(table[index])
    }
}
