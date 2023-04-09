package dk.itu.moapd.scootersharing.jacj.ui.StartRide

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dk.itu.moapd.scootersharing.jacj.R
import dk.itu.moapd.scootersharing.jacj.models.RidesDB
import dk.itu.moapd.scootersharing.jacj.databinding.FragmentStartRideBinding
import dk.itu.moapd.scootersharing.jacj.models.Scooter
import dk.itu.moapd.scootersharing.jacj.ui.Main.DATABASE_URL

class StartRideFragment: Fragment() {
    private lateinit var auth: FirebaseAuth
    var database = Firebase.database(DATABASE_URL).reference
    private var _binding: FragmentStartRideBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    companion object {
        lateinit var ridesDB: RidesDB
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ridesDB = RidesDB.get(requireContext())
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartRideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding)
        {
            //Start ride button
            startRideButton.setOnClickListener {
                AlertDialog.Builder(context)
                    .setTitle(R.string.start_ride_dialog_title)
                    .setMessage(R.string.start_ride_dialog_description)
                    .setPositiveButton(
                        R.string.dialog_add
                    ) { _, _ ->
                        val name = editTextName.text.toString().trim()
                        val location = editTextLocation.text.toString().trim()
                        if (name.isNotEmpty()) {
                            val timestamp = System.currentTimeMillis()
                            val scooter = Scooter(name, location, timestamp)
                            val scooters = database.child("scooters")
                            scooters.child(name).setValue(scooter)
                        }

                        Snackbar.make(binding.root.rootView, getString(R.string.ride_started, ridesDB.getCurrentScooterInfo()), Snackbar.LENGTH_SHORT).show()
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