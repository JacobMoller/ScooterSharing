package dk.itu.moapd.scootersharing.jacj

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dk.itu.moapd.scootersharing.jacj.databinding.FragmentMainBinding

/**
 * Main fragment for the ScooterSharing app.
 *
 * @author Jacob Møller Jensen
 * @since 0.3.0
 */

class MainFragment : Fragment() {

    //Setup view-binding for the fragment content
    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    //Declare the RidesDB singleton class
    companion object {
        lateinit var ridesDB: RidesDB
    }

    /**
     * Creates the fragment from a saved state and instantiates the RidesDB.
     *
     * @param savedInstanceState the saved data from main
     * @author Jacob Møller Jensen
     * @since 0.4.0
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Instantiate the ridesDB variable
        ridesDB = RidesDB.get(this@MainFragment.requireContext())
    }

    /**
     * Creates the view in a container from a layout.
     *
     * @param inflater the layout to be inflated
     * @param container the ViewGroup in which you want the view
     * @param savedInstanceState the saved data from main
     * @return the view, containing all the bindings.
     * @author Jacob Møller Jensen
     * @since 0.4.0
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Inflate the layout and save it for viewbinding
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Sets properties inside the view just after its creation
     *
     * @param view the view that was just created
     * @param savedInstanceState the saved data that should be used in the new view.
     * @author Jacob Møller Jensen
     * @since 0.4.0
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Set properties for the content of the layout
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
                if(rideListView.visibility == View.GONE){
                    rideListView.visibility = View.VISIBLE
                    listItemHeader.root.visibility = View.VISIBLE
                }
                else{
                    rideListView.visibility = View.GONE
                    listItemHeader.root.visibility = View.GONE
                }
            }

            // API Level
            val version = Build.VERSION.SDK_INT
            apiLevel.text = getString(R.string.version, version)

            //Create custom adapter
            val adapter = RideListAdapter(this@MainFragment.requireContext(), R.layout.list_rides, ridesDB.getRidesList())
            rideListView.adapter = adapter
        }
    }

    /**
     * Destroy the binding for garbage collection when it is not used anymore
     *
     * @author Jacob Møller Jensen
     * @since 0.4.0
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}