package dk.itu.moapd.scootersharing.jacj

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dk.itu.moapd.scootersharing.jacj.databinding.FragmentMainBinding

/**
 * Main fragment for the ScooterSharing app.
 *
 * @author Jacob Møller Jensen
 * @since 0.1.0
 */

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
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
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.rideRecyclerView.layoutManager = LinearLayoutManager(context)

        val rides = ridesDB.getRidesList()
        val adapter = RideListAdapter(requireContext(), rides as MutableList<Scooter>)
        binding.rideRecyclerView.adapter = adapter

        //Add swipe here

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding)
        {
            //Start ride button
            startRideButton.setOnClickListener {
                findNavController().navigate(
                    R.id.show_start_ride
                )
            }

            updateRideButton.setOnClickListener {
                findNavController().navigate(
                    R.id.show_update_ride
                )
            }

            listRidesButton.setOnClickListener {
                if(rideRecyclerView.visibility == View.GONE){
                    rideRecyclerView.visibility = View.VISIBLE
                    listItemHeader.listItemRideHeader.visibility = View.VISIBLE
                }
                else{
                    rideRecyclerView.visibility = View.GONE
                    listItemHeader.listItemRideHeader.visibility = View.GONE
                }
            }

            // API Level
            val version = Build.VERSION.SDK_INT
            apiLevel.text = getString(R.string.version, version)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}