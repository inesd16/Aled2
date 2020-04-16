package fr.isen.dobosz.projet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.isen.dobosz.projet.R.layout.activity_home_connected
import fr.isen.dobosz.projet.R.layout.fragment_message

class ChatFragment: Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(activity_home_connected, container, false )
    }
}