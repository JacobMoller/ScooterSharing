package dk.itu.moapd.scootersharing.jacj.ui.UpdateRide

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import dk.itu.moapd.scootersharing.jacj.R
import dk.itu.moapd.scootersharing.jacj.databinding.FragmentUpdateRideBinding
import dk.itu.moapd.scootersharing.jacj.models.Coords

class UpdateRideFragment : Fragment() {
    private var _binding: FragmentUpdateRideBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateRideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding)
        {
            editTextName.setText("something")
            //Update ride button
            updateRideButton.setOnClickListener {
                AlertDialog.Builder(context)
                    .setTitle(R.string.update_ride_dialog_title)
                    .setMessage(R.string.update_ride_dialog_description)
                    .setPositiveButton(
                        R.string.dialog_update
                    ) { _, _ ->
                        val location = editTextLocation.text.toString().trim()

                        Snackbar.make(binding.root.rootView, getString(R.string.ride_updated, "?"), Snackbar.LENGTH_SHORT).show()
                    }
                    .setNegativeButton(R.string.dialog_cancel, null)
                    .setIcon(android.R.drawable.ic_input_add)
                    .show()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}