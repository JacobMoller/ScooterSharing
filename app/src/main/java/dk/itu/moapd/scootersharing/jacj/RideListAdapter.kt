package dk.itu.moapd.scootersharing.jacj

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast

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
        viewHolder.date.text = ride?.timestamp?.let { ride.formatDate(it) }
        view?.setOnClickListener {
            Toast.makeText(
                view.context,
                context.getString(R.string.list_ride_click, ride?.name),
                Toast.LENGTH_SHORT
            ).show()
        }

        return view!!
    }
}