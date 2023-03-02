package dk.itu.moapd.scootersharing.jacj

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class RideListAdapter(
    context: Context,
    private var resource: Int,
    data: List<Scooter>
) : ArrayAdapter<Scooter>(context, R.layout.list_rides, data) {

    private class ViewHolder(view: View) {
        val name: TextView = view.findViewById(R.id.ride_name)
        val location: TextView = view.findViewById(R.id.ride_location)
        val date: TextView = view.findViewById(R.id.ride_date)
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var view = convertView
        val viewHolder: ViewHolder

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(resource, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }


        val ride = getItem(position)
        viewHolder.name.text = ride?.name
        viewHolder.location.text = ride?.location
        viewHolder.date.text = ride?.timestamp?.let { ride.dateFormatted() }
        view?.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle(R.string.delete_dialog_title)
                .setMessage(R.string.delete_dialog_description)
                .setPositiveButton(
                    R.string.delete_dialog_delete
                ) { dialog, which ->
                    removeItem(position)
                }
                .setNegativeButton(R.string.delete_dialog_cancel, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }

        return view!!
    }

    private fun removeItem(position: Int) {
        this.remove(this.getItem(position))
        notifyDataSetChanged()
    }
}