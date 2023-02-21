package dk.itu.moapd.scootersharing.jacj

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import dk.itu.moapd.scootersharing.jacj.databinding.ListRidesBinding

/*class RideHolder(
    private val binding: ListRidesBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(ride: Scooter) {
        binding.rideName.text = ride.name
        binding.rideLocation.text = ride.location
        binding.rideDate.text = ride.formatDate(ride.timestamp)

        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${ride.name} clicked!",
                Toast.LENGTH_SHORT
            ).show() }
    }
}*/

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
            view.setTag(viewHolder);
        } else {
            viewHolder = view.tag as ViewHolder
        }


        val ride = getItem(position)
        viewHolder.name.text = ride?.name
        viewHolder.location.text = ride?.location
        viewHolder.date.text = ride?.timestamp?.let { ride?.formatDate(it) }

        return view!!
    }
}

/*class RideListAdapter(
    private val rides: List<Scooter>
) : RecyclerView.Adapter<RideHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : RideHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListRidesBinding.inflate(inflater, parent, false)
        return RideHolder(binding)
    }
    override fun onBindViewHolder(holder: RideHolder, position: Int) {
        val scooter = rides[position]
        holder.bind(scooter)
    }
    override fun getItemCount() = rides.size
}*/