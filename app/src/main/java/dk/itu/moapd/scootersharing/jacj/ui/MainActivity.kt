package dk.itu.moapd.scootersharing.jacj.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.commit
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dk.itu.moapd.scootersharing.jacj.services.Location.LocationService
import dk.itu.moapd.scootersharing.jacj.ui.Login.LoginFragment
import dk.itu.moapd.scootersharing.jacj.ui.destinations.*
import kotlinx.coroutines.launch


sealed class Screen(val route: String) {
    object Maps : Screen("maps")
    object Location : Screen("location")
    object Permissions : Screen("permissions")

    object ScooterList : Screen("scooterlist")
    object Login : Screen("login")
}

/**
 * Main entry point for the ScooterSharing app.
 *
 * @author Jacob Møller Jensen
 * @since 0.1.0
 */
/*
class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        AppBar(
                            onNavigationIconClick = {
                                scope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            }
                        )
                    },
                    drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
                    drawerContent = {
                        DrawerHeader()
                        DrawerBody(
                            items = listOf(
                                MenuItem(
                                    id = "home",
                                    title = "Home",
                                    contentDescription = "Go to home screen",
                                    icon = Icons.Default.Home
                                ),
                                MenuItem(
                                    id = "location",
                                    title = "Location",
                                    contentDescription = "Go to location screen",
                                    icon = Icons.Default.LocationOn
                                ),
                                MenuItem(
                                    id = "permissions",
                                    title = "Permissions-Test",
                                    contentDescription = "Go to permissions screen",
                                    icon = Icons.Default.Settings
                                ),
                                MenuItem(
                                    id = "scooterlist",
                                    title = "ScooterList",
                                    contentDescription = "Go to scooter-list screen",
                                    icon = Icons.Default.List
                                ),
                                MenuItem(
                                    id = "login",
                                    title = "Log out",
                                    contentDescription = "Log out of your account",
                                    icon = Icons.Default.ArrowBack
                                )

                            ),
                            onItemClick = {
                                when (it.id) {
                                    "home" -> navController.navigate(Screen.Maps.route)
                                    "location" -> navController.navigate(Screen.Location.route)
                                    "permissions" -> navController.navigate(Screen.Permissions.route)
                                    "scooterlist" -> navController.navigate(Screen.ScooterList.route)
                                    "login" -> {
                                        //Logout
                                        AuthUI.getInstance()
                                            .signOut(this@MainActivity)
                                            .addOnCompleteListener {
                                                navController.navigate(Screen.Login.route)
                                            }
                                    }
                                }
                                scope.launch { scaffoldState.drawerState.close() }
                            }
                        )
                    },
                    content = {
                        it
                        val multiplePermissionsState = rememberMultiplePermissionsState(
                            listOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                            )
                        )
                        NavHost(
                            navController,
                            startDestination = Screen.Location.route
                        ) {
                            composable(Screen.Maps.route) { Test() }
                            composable(Screen.Location.route) { NewLocation() }
                            composable(Screen.Permissions.route) { Sample(multiplePermissionsState) }
                            composable(Screen.ScooterList.route) { ScooterListScreen() }
                            composable(Screen.Login.route) { LoginComposable() }
                        }
                        if (auth.currentUser == null)
                            navController.navigate(Screen.Login.route)
                    }
                )
            }
        }
    }

}*/

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DestinationsNavHost(navGraph = NavGraphs.root)
            ModalNavigationDrawerSampleDestination()
        }
    }
}

private lateinit var auth: FirebaseAuth

@Destination(start = true)
@Composable
fun ModalNavigationDrawerSample(navigator: DestinationsNavigator) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var context = LocalContext.current
    auth = FirebaseAuth.getInstance()
    androidx.compose.material.Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            DrawerHeader()
            DrawerBody(
                items = listOf(
                    MenuItem(
                        id = "home",
                        title = "Home",
                        contentDescription = "Go to home screen",
                        icon = Icons.Default.Home
                    ),
                    MenuItem(
                        id = "location",
                        title = "Location",
                        contentDescription = "Go to location screen",
                        icon = Icons.Default.LocationOn
                    ),
                    MenuItem(
                        id = "permissions",
                        title = "Permissions-Test",
                        contentDescription = "Go to permissions screen",
                        icon = Icons.Default.Settings
                    ),
                    MenuItem(
                        id = "scooterlist",
                        title = "ScooterList",
                        contentDescription = "Go to scooter-list screen",
                        icon = Icons.Default.List
                    ),
                    MenuItem(
                        id = "login",
                        title = "Log out",
                        contentDescription = "Log out of your account",
                        icon = Icons.Default.ArrowBack
                    )

                ),
                onItemClick = {
                    when (it.id) {
                        "home" -> navigator.navigate(MainScreenDestination)
                        "location" -> navController.navigate(Screen.Location.route)
                        "permissions" -> navController.navigate(Screen.Permissions.route)
                        "scooterlist" -> navigator.navigate(ScooterListScreenDestination)
                        "login" -> {
                            //Logout
                            AuthUI.getInstance()
                                .signOut(context)
                                .addOnCompleteListener {
                                    navController.navigate(Screen.Login.route)
                                }
                        }
                    }
                    scope.launch { scaffoldState.drawerState.close() }
                }
            )
        },
        content = {
            Box(modifier = Modifier.padding(it)) {
                MainScreen(navigator = navigator, context = context)
            }
        }
    )
    /*val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
    val selectedItem = remember { mutableStateOf(items[0]) }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                items.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item, contentDescription = null) },
                        label = { Text(item.name) },
                        selected = item == selectedItem.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem.value = item
                            navigator.navigate(TestScreenDestination)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {
            MainScreen(navigator, LocalContext.current)
        }
    )*/
}

@Destination
@Composable
fun MainScreen(navigator: DestinationsNavigator, context: Context) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row {
            Text(text = "hhiiii")
            Button(onClick = {
                AuthUI.getInstance()
                    .signOut(context)
                    .addOnCompleteListener {
                        Log.d("HEEEE", "logged out!")
                        //navigate to login
                    }
            }) {
                Text(text = "Log out")
            }
        }
        Button(onClick = {
            /*findNavController().navigate(
                R.id.show_newmaps
            )*/
            navigator.navigate(CheckPermissionsDestination)
        }) {
            Text(text = "To Map")
        }
        Button(onClick = {
            /*findNavController().navigate(
                R.id.show_scooterlist
            )*/
            navigator.navigate(ScooterListScreenDestination)
        }) {
            Text(text = "Scooter-List")
        }
        Button(onClick = {
            /*findNavController().navigate(
                R.id.show_location
            )*/
        }) {
            Text(text = "Location (fragment)")
        }
        Button(onClick = {
            Intent(context, LocationService::class.java).apply {
                action = LocationService.ACTION_START
                context.startService(this)
            }
        }) {
            Text(text = "Location-service start")
        }
        Button(onClick = {
            Intent(context, LocationService::class.java).apply {
                action = LocationService.ACTION_STOP
                context.startService(this)
            }
        }) {
            Text(text = "Location-service stop")
        }
    }
}

@Destination
@Composable
fun TestScreen(
    navigator: DestinationsNavigator
) {
    Button(onClick = { navigator.navigate(ModalNavigationDrawerSampleDestination)}) {
        Text(text = "Test-screen button")
    }
}
