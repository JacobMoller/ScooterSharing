package dk.itu.moapd.scootersharing.jacj

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet.Motion
import androidx.recyclerview.widget.RecyclerView
import dk.itu.moapd.scootersharing.jacj.databinding.ListItemRideBinding

class ViewHolder(
    val binding: ListItemRideBinding
) : RecyclerView.ViewHolder(binding.root)

class RideListAdapter(
    private val context: Context,
    private val rides: MutableList<Scooter>
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemRideBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView

        val ride = rides[position]
        holder.apply {
            binding.rideName.text = ride.name
            binding.rideLocation.text = ride.location
            binding.rideDate.text = ride.timestamp.let { ride.dateFormatted() }
        }
        view.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle(R.string.delete_dialog_title)
                .setMessage(R.string.delete_dialog_description)
                .setPositiveButton(
                    R.string.delete_dialog_delete
                ) { _, _ ->
                    removeItem(position)
                }
                .setNegativeButton(R.string.dialog_cancel, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }

    }
    override fun getItemCount() = rides.size

    private fun removeItem(position: Int) {
        rides.removeAt(position)
        notifyItemRemoved(position)
    }
}