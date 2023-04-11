package dk.itu.moapd.scootersharing.jacj.ui.Location

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import dk.itu.moapd.scootersharing.jacj.databinding.FragmentLocationBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class LocationFragment : Fragment(){
    private var _binding: FragmentLocationBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    companion object {
        private const val ALL_PERMISSIONS_RESULT = 1011
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startLocationAware()
    }

    override fun onResume() {
        super.onResume()
        subscribeToLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        unsubscribeToLocationUpdates()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding)
        {

        }
    }

    private fun startLocationAware() {

        // Show a dialog to ask the user to allow the application to access the device's location.
        requestUserPermissions()

        // Start receiving location updates.
        fusedLocationProviderClient = LocationServices
            .getFusedLocationProviderClient(requireContext())

        // Initialize the `LocationCallback`.
        locationCallback = object : LocationCallback() {

            /**
             * This method will be executed when `FusedLocationProviderClient` has a new location.
             *
             * @param locationResult The last known location.
             */
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                // Updates the user interface components with GPS data location.
                locationResult.lastLocation?.let { location ->
                    updateUI(location)
                }
            }
        }
    }

    private fun requestUserPermissions() {

        // An array with location-aware permissions.
        val permissions: ArrayList<String> = ArrayList()
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)

        // Check which permissions is needed to ask to the user.
        val permissionsToRequest = permissionsToRequest(permissions)

        // Show the permissions dialogs to the user.
        if (permissionsToRequest.size > 0)
            requestPermissions(
                permissionsToRequest.toTypedArray(),
                ALL_PERMISSIONS_RESULT
            )
    }

    private fun permissionsToRequest(permissions: ArrayList<String>): ArrayList<String> {
        val result: ArrayList<String> = ArrayList()
        for (permission in permissions)
            if (checkSelfPermission(requireContext(),permission) != PermissionChecker.PERMISSION_GRANTED)
                result.add(permission)
        return result
    }

    private fun checkPermission() =
        ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED

    private fun subscribeToLocationUpdates() {

        // Check if the user allows the application to access the location-aware resources.
        if (checkPermission())
            return

        // Sets the accuracy and desired interval for active location updates.
        val locationRequest = LocationRequest
            .Builder(Priority.PRIORITY_HIGH_ACCURACY, 5)
            .build()

        // Subscribe to location changes.
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.getMainLooper()
        )
    }

    /**
     * Unsubscribes this application of getting the location changes from  the `locationCallback()`.
     */
    private fun unsubscribeToLocationUpdates() {
        // Unsubscribe to location changes.
        fusedLocationProviderClient
            .removeLocationUpdates(locationCallback)
    }

    /**
     * Return the timestamp as a `String`.
     *
     * @return The timestamp formatted as a `String` using the default locale.
     */
    private fun Long.toDateString() : String {
        val date = Date(this)
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }

    /**
     * Update the UI components based on the current device's location data.
     *
     * @param location The current location data.
     */
    private fun updateUI(location: Location) {
        //if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            binding.apply {
                latitudeTextField?.editText?.setText(location.latitude.toString())
                longitudeTextField?.editText?.setText(location.longitude.toString())
                timeTextField?.editText?.setText(location.time.toDateString())
            }
            setAddress(location.latitude, location.longitude)
    }

    /**
     * Use Geocoder API to convert the current location into a `String` address, and update the
     * corresponding UI component.
     *
     * @param latitude The current latitude coordinate.
     * @param longitude The current longitude coordinate.
     */
    private fun setAddress(latitude: Double, longitude: Double) {
        if (!Geocoder.isPresent())
            return

        // Create the `Geocoder` instance.
        val geocoder = Geocoder(requireContext(), Locale.getDefault())

        // Return an array of Addresses that attempt to describe the area immediately surrounding
        // the given latitude and longitude.
        if (Build.VERSION.SDK_INT >= 33) {
            // After `Tiramisu Android OS`, it is needed to use a listener to avoid blocking the main
            // thread waiting for results.
            val geocodeListener = Geocoder.GeocodeListener { addresses ->
                addresses.firstOrNull()?.toAddressString()?.let { address ->
                    binding.addressTextField?.editText?.setText(address)
                }
            }
            geocoder.getFromLocation(latitude, longitude, 1, geocodeListener)
        }
        else {
            geocoder.getFromLocation(latitude, longitude, 1)?.let { addresses ->
                addresses.firstOrNull()?.toAddressString()?.let { address ->
                    binding.addressTextField?.editText?.setText(address)
                }
            }
        }
    }

    /**
     * Converts the `Address` instance into a `String` representation.
     *
     * @return A `String` with the current address.
     */
    private fun Address.toAddressString() : String {
        val address = this

        // Create a `String` with multiple lines.
        val stringBuilder = StringBuilder()
        stringBuilder.apply {
            append(address.getAddressLine(0)).append("\n")
            append(address.postalCode).append(" ")
            append(address.locality).append("\n")
            append(address.countryName)
        }

        return stringBuilder.toString()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}