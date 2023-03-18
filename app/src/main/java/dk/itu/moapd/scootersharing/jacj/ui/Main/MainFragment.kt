package dk.itu.moapd.scootersharing.jacj.ui.Main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dk.itu.moapd.scootersharing.jacj.R
import dk.itu.moapd.scootersharing.jacj.models.Scooter
import dk.itu.moapd.scootersharing.jacj.databinding.FragmentMainBinding
import dk.itu.moapd.scootersharing.jacj.models.RidesDB
import dk.itu.moapd.scootersharing.jacj.ui.Main.adapters.RideListAdapter

/**
 * Main fragment for the ScooterSharing app.
 *
 * @author Jacob Møller Jensen
 * @since 0.1.0
 */

class MainFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

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
        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser == null)
            //Switch to the login fragment.
            findNavController().navigate(
                R.id.show_login
            )
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

            //Set username
            username.text = "Welcome " + getEmail()

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

            logoutButton.setOnClickListener {
                AuthUI.getInstance()
                    .signOut(requireContext())
                    .addOnCompleteListener {
                        findNavController().navigate(
                            R.id.show_login
                        )
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

    private fun getEmail(): String {
        val user = Firebase.auth.currentUser
        return user?.email.toString()
    }
}