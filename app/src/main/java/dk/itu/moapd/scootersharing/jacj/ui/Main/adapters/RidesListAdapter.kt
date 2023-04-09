package dk.itu.moapd.scootersharing.jacj.ui.Main.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import dk.itu.moapd.scootersharing.jacj.R
import dk.itu.moapd.scootersharing.jacj.models.Scooter
import dk.itu.moapd.scootersharing.jacj.databinding.ListItemRideBinding

class ViewHolder(
    val binding: ListItemRideBinding
) : RecyclerView.ViewHolder(binding.root)

class RideListAdapter(
    private val context: Context,
    options: FirebaseRecyclerOptions<Scooter>
) : FirebaseRecyclerAdapter<Scooter, ViewHolder>(options) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemRideBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int, scooter: Scooter) {
        val view = holder.itemView

        holder.apply {
            binding.rideName.text = scooter.name
            binding.rideLocation.text = scooter.location
            binding.rideDate.text = scooter.timestamp.let { scooter.dateFormatted() }
        }
        view.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle(R.string.delete_dialog_title)
                .setMessage(R.string.delete_dialog_description)
                .setPositiveButton(
                    R.string.delete_dialog_delete
                ) { _, _ ->
                    //removeItem(position)
                }
                .setNegativeButton(R.string.dialog_cancel, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }

    }
    /*override fun getItemCount() = rides.size

    private fun removeItem(position: Int) {
        rides.removeAt(position)
        notifyItemRemoved(position)
    }*/
}