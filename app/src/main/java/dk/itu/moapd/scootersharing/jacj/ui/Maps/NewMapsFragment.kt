package dk.itu.moapd.scootersharing.jacj.ui.Maps

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import dk.itu.moapd.scootersharing.jacj.DataState
import dk.itu.moapd.scootersharing.jacj.MainViewModel
import dk.itu.moapd.scootersharing.jacj.R
import dk.itu.moapd.scootersharing.jacj.models.Scooter
import dk.itu.moapd.scootersharing.jacj.ui.theme.AppTheme


class NewMapsFragment : Fragment(), OnMapReadyCallback {

    companion object {
        private const val ALL_PERMISSIONS_RESULT = 1011
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestUserPermissions()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (checkPermission())
            return ComposeView(requireContext()).apply {
                setContent {
                    Text(text = "Permissions Missing.")
                }
            }
        else
            return ComposeView(requireContext()).apply {
                setContent {
                    Test()
                }
            }
    }

    @Composable
    fun Test(modifier: Modifier = Modifier
        .fillMaxSize()
        .wrapContentWidth(Alignment.CenterHorizontally)) {
        val viewModel: MainViewModel by viewModels()
        when(val result = viewModel.response.value) {
            is DataState.Loading -> {
                AppTheme() {
                    Box(
                        modifier = modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            is DataState.Success -> {
                AppTheme() {
                    MapsScreen(result.data)
                }
            }
            is DataState.Failure -> {
                Box(modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center) {
                    Text(text = result.message)
                }
            }
            else -> {
                Box(modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center) {
                    Text(text = "Error Fetching Data", fontSize = MaterialTheme.typography.headlineMedium.fontSize)
                }
            }
        }
    }

    @Composable
    fun MapsScreen (scooters: MutableList<Scooter>) {
        val scooter1 = LatLng(55.661259, 12.600291)
        val scooter2 = LatLng(55.675989, 12.581817)
        val scooter3 = LatLng(55.680465, 12.586475)
        val list = listOf<LatLng>(scooter1,scooter2,scooter3)

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(scooter1, 16f)
        }
        Box() {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = true)
            ) {
                Marker(
                    state = MarkerState(position = scooter1),
                    icon = getBitmapDescriptor(R.drawable.map_scooter_marker),
                    tag = 0
                )
                scooters.forEach { scooter ->
                    Marker(
                        state = MarkerState(
                            position = LatLng(scooter.coords!!.lat!!,scooter.coords!!.long!!), //TODO: remove !!
                        ),
                        icon = getBitmapDescriptor(R.drawable.map_scooter_marker),
                        tag = 0
                    )
                }
                /*Polyline(
                    points = list,
                    color = Color(0xFF66BB6A),
                    jointType = JointType.BEVEL,
                    width = 20f
                )*/
            }
            Card(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp, 16.dp),
            ){
                Column(modifier = Modifier
                    .padding(16.dp, 16.dp)
                    .wrapContentHeight()) {
                    Text(text = "Scootername")
                    Text(text = "Scootername")
                }
            }
        }
    }

    //TODO: BASED ON https://gist.github.com/Ozius/1ef2151908c701854736
    private fun getBitmapDescriptor(id: Int): BitmapDescriptor? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val vectorDrawable = AppCompatResources.getDrawable(
                requireContext(),
                id
            ) as VectorDrawable
            val h = vectorDrawable.intrinsicHeight
            val w = vectorDrawable.intrinsicWidth
            vectorDrawable.setBounds(0, 0, w, h)
            val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bm)
            vectorDrawable.draw(canvas)
            BitmapDescriptorFactory.fromBitmap(bm)
        } else {
            BitmapDescriptorFactory.fromResource(id)
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
            if (checkSelfPermission(requireContext(), permission) != PermissionChecker.PERMISSION_GRANTED)
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

    override fun onMapReady(map: GoogleMap) {

    }


}