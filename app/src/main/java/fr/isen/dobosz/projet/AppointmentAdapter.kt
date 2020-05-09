package fr.isen.dobosz.projet


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_appointment.view.*

class AppointmentAdapter(val appointments: ArrayList<AppointmentModel>): RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentAdapter.AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_view_appointment, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return appointments.count()
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = appointments[position]
        holder.bind(contact)
    }

//////////////////////////////////////////////////////////////

    class ContactViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bind(contact: AppointmentModel) {
            view.contactDisplayNameTextView.text = contact.displayName
        }
    }
}