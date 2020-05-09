package fr.isen.dobosz.projet


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycle_view_appointment.view.*
import kotlinx.android.synthetic.main.recycler_view_appointment.view.*

class AppointmentAdapter(val appointments: ArrayList<AppointmentModel>): RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentAdapter.AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_view_appointment, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return appointments.count()
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val contact = appointments[position]
        holder.bind(contact)
    }

//////////////////////////////////////////////////////////////

    class AppointmentViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bind(appointment: AppointmentModel) {

            val name:String? = appointment.name
            val surname: String? = appointment.surname
            view.nameDocTextView.setText(name+" "+surname)
            view.dateTextView.text = appointment.date
            view.typeDocTextView.text = appointment.type
            view.timeTextView.text = appointment.time
            //view.fNameTextView.text = user.name?.last

        }
    }
}