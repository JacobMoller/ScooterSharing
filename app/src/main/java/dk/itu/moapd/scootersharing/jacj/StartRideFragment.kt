package dk.itu.moapd.scootersharing.jacj

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import dk.itu.moapd.scootersharing.jacj.databinding.FragmentStartRideBinding

class StartRideFragment: Fragment() {
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
        ridesDB = RidesDB.get(this@StartRideFragment.requireContext())
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
                // Update the object attributes.
                val name = editTextName.text.toString().trim()
                val location = editTextLocation.text.toString().trim()
                ridesDB.addScooter(name, location)

                Snackbar.make(binding.root.rootView, getString(R.string.ride_started, ridesDB.getCurrentScooterInfo()), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}