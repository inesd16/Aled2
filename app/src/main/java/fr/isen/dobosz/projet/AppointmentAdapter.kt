package fr.isen.dobosz.projet


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycle_view_appointment.view.*
import java.util.*

class AppointmentAdapter(val appointments: ArrayList<AppointmentModel>): RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_view_appointment, parent, false)
        return AppointmentViewHolder(view, parent.context)
    }

    override fun getItemCount(): Int {
        return appointments.count()
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment : AppointmentModel = appointments[position]
        holder.bind(appointment)
    }

//////////////////////////////////////////////////////////////

    class AppointmentViewHolder(val view: View, val context: Context): RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(appointment: AppointmentModel) {

view.deleteAppointmentButton.setOnClickListener(){
    if(!appointment.past!!) {
        System.out.println("Appointment past")
    }
}

            val name:String? = appointment.name
            val surname: String? = appointment.surname?.toUpperCase(Locale.getDefault())
            view.nameDocTextView.setText("$name $surname")
            view.dateTextView.text = appointment.date
            view.typeDocTextView.text = appointment.type
            view.timeTextView.text = appointment.time
            if(appointment.past!!){
                view.pastOrFuturAppointment.setText(context.getString(R.string.isPast))
                view.deleteAppointmentButton.visibility = View.INVISIBLE
            }
            else{
                view.pastOrFuturAppointment.setText(context.getString(R.string.isComing))
                view.deleteAppointmentButton.visibility = View.VISIBLE
            }

        }
    }
}