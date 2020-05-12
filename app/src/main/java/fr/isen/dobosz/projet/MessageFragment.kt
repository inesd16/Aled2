package fr.isen.dobosz.projet

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment


class MessageFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

    val view = inflater.inflate(R.layout.activity_home, container, false)
    val sosButton: ImageButton = view.findViewById(R.id.sosButton)
    sosButton.setOnClickListener(){
        launchPopUpStartCall()
    }

        val historyButon:ImageButton = view.findViewById(R.id.historyButton)
        historyButon.setOnClickListener(){
            val intent = Intent(context, AppointmentActivity::class.java)
            startActivity(intent)
        }
    return view
}

    fun launchPopUpStartCall(){
        val builder = AlertDialog.Builder(this.context)
            .setTitle("Lancer un appel")
            .setMessage("Voulez-vous vraiment appelez un médecin?")
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            startCall()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
        }
        builder.show()
    }

    fun startCall() {
        val intentcall = Intent()
        intentcall.action = Intent.ACTION_CALL
        intentcall.data = Uri.parse("tel:+33698305732") // set the Uri
        startActivity(intentcall)
    }

}