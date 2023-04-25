package dk.itu.moapd.scootersharing.jacj.ui.Maps

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dk.itu.moapd.scootersharing.jacj.DataState
import dk.itu.moapd.scootersharing.jacj.MainViewModel
import dk.itu.moapd.scootersharing.jacj.R
import dk.itu.moapd.scootersharing.jacj.models.Scooter
import dk.itu.moapd.scootersharing.jacj.ui.PermissionsTest.getTextToShowGivenPermissions
import dk.itu.moapd.scootersharing.jacj.ui.theme.AppTheme
import kotlinx.coroutines.launch



    @Destination
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun CheckPermissions(navigator: DestinationsNavigator) {
        val multiplePermissionsState = rememberMultiplePermissionsState(
            listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )
        if (multiplePermissionsState.allPermissionsGranted) {
            // If all permissions are granted, then show screen with the feature enabled
            TestScreenMap()
        } else {
            SideEffect {
                multiplePermissionsState.run { launchMultiplePermissionRequest() }
            }
            Column (modifier = Modifier.fillMaxSize()) {
                Text(
                    getTextToShowGivenPermissions(
                        multiplePermissionsState.revokedPermissions,
                        multiplePermissionsState.shouldShowRationale
                    )
                )
            }
        }
    }

    @Composable
    fun TestScreenMap(
        modifier: Modifier = Modifier
            .fillMaxSize()
            .wrapContentWidth(Alignment.CenterHorizontally),
        viewModel: MainViewModel = viewModel(),
    ) {
        when (val result = viewModel.response.value) {
            is DataState.Loading -> {
                AppTheme {
                    Box(
                        modifier = modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            is DataState.Success -> {
                AppTheme {
                    MapsScreenThis(result.data)
                }
            }
            is DataState.Failure -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = result.message)
                }
            }
            else -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error Fetching Data",
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize
                    )
                }
            }
        }
    }


    @Composable
    fun MapsScreenThis(scooters: MutableList<Scooter>) {
        //TODO: make the following a data class
        var isVisible by rememberSaveable { mutableStateOf(false) }
        var title by rememberSaveable { mutableStateOf("") }
        var subtitle by rememberSaveable { mutableStateOf("") }

        val defaultLocation = LatLng(55.6596, 12.5910)

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(defaultLocation, 16f)
        }
        Box {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = true)
            ) {
                scooters.forEach { scooter ->
                    Marker(
                        state = MarkerState(
                            position = LatLng(
                                scooter.coords!!.lat!!,
                                scooter.coords!!.long!!
                            ), //TODO: remove !!
                        ),
                        icon = getBitmapDescriptor(R.drawable.map_scooter_marker, LocalContext.current),
                        tag = 0,
                        onClick = {
                            isVisible = true
                            title = scooter.name.toString()
                            subtitle = scooter.location.toString()
                            false
                        }
                    )
                }
                /*Polyline(
                    points = list,
                    color = Color(0xFF66BB6A),
                    jointType = JointType.BEVEL,
                    width = 20f
                )*/
            }
            if(isVisible) {
                Card(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(16.dp, 16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp, 16.dp)
                            .wrapContentHeight()
                    ) {
                        Text(text = title)
                        Text(text = subtitle)
                    }
                }
            }
        }
    }

    //TODO: BASED ON https://gist.github.com/Ozius/1ef2151908c701854736
    private fun getBitmapDescriptor(id: Int, context: Context): BitmapDescriptor? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val vectorDrawable = AppCompatResources.getDrawable(
                context,
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

/*@Composable
fun Test(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .wrapContentWidth(Alignment.CenterHorizontally),
    viewModel: MainViewModel = viewModel(),
) {
    when (val result = viewModel.response.value) {
        is DataState.Loading -> {
            AppTheme {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        is DataState.Success -> {
            AppTheme {
                MapsScreen(result.data)
            }
        }
        is DataState.Failure -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = result.message)
            }
        }
        else -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error Fetching Data",
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize
                )
            }
        }
    }
}

@Composable
fun MapsScreen(scooters: MutableList<Scooter>) {
    val scooter1 = LatLng(55.661259, 12.600291)
    val scooter2 = LatLng(55.675989, 12.581817)
    val scooter3 = LatLng(55.680465, 12.586475)
    val list = listOf<LatLng>(scooter1, scooter2, scooter3)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(scooter1, 16f)
    }
    Box {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = true)
        ) {
            Marker(
                state = MarkerState(position = scooter1),
                //icon = BitmapDescriptorFactory
                tag = 0
            )
            scooters.forEach { scooter ->
                Marker(
                    state = MarkerState(
                        position = LatLng(
                            scooter.coords!!.lat!!,
                            scooter.coords!!.long!!
                        ), //TODO: remove !!
                    ),
                    //icon = BitmapDescriptorFactory.fromResource(R.drawable.map_scooter_marker),
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
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp, 16.dp)
                    .wrapContentHeight()
            ) {
                Text(text = "Scootername")
                Text(text = "Scootername")
            }
        }
    }
}

data class MapUIState(
    var cardIsVisible: Boolean = false,
    var cardScooter: Scooter? = null
)*/