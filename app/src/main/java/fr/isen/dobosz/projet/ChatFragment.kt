package fr.isen.dobosz.projet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import fr.isen.dobosz.projet.R.layout.activity_home_connected
import fr.isen.dobosz.projet.R.layout.fragment_message

class ChatFragment: Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(activity_home_connected, container, false)
        val b: ImageButton = view.findViewById(R.id.homeButton)
        b.setOnClickListener(){
            System.out.println("BUTOUN CLICKED")
        }
        return view
    }
}