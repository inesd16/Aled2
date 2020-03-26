package fr.isen.dobosz.projet


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import fr.isen.dobosz.projet.R.layout.fragment_contact
import kotlinx.android.synthetic.main.fragment_contact.*

class ContactFragment: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myAccountTextView.setOnClickListener(){
            intent = Intent(this, UserInfoActivity::class.java)
        }
    }
}