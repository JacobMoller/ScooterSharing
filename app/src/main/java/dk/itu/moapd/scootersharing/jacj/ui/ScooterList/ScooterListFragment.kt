package dk.itu.moapd.scootersharing.jacj.ui.ScooterList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import dk.itu.moapd.scootersharing.jacj.DataState
import dk.itu.moapd.scootersharing.jacj.MainViewModel
import dk.itu.moapd.scootersharing.jacj.R
import dk.itu.moapd.scootersharing.jacj.models.Coords
import dk.itu.moapd.scootersharing.jacj.models.Scooter
import dk.itu.moapd.scootersharing.jacj.ui.theme.*
import java.net.URL

class GlideImage(model: StorageReference, contentDescription: String = "dav")

class ScooterListFragment : Fragment()  {
    private val BUCKET_URL = "gs://scooter-sharing-5c9ca.appspot.com"
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ScooterListScreen()
            }
        }
    }

    @Composable
    fun ScooterListScreen(
        modifier: Modifier = Modifier
            .fillMaxSize()
            .wrapContentWidth(Alignment.CenterHorizontally)){
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
                    ShowLazyList(result.data)
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

    @Preview
    @Composable
    private fun ShowLazyListPreview() {
        val dummyScooters: List<Scooter> = listOf(Scooter("CPH001", "?", Coords(123.456,123.456)), Scooter("CPH002", "2", Coords(123.456,123.456)))
        LazyColumn {
            items(dummyScooters.toList()){scooter ->
                ScooterCard(scooter)
            }
        }
    }

    @Composable
    private fun ShowLazyList(scooters: MutableList<Scooter>) {
        LazyColumn {
            items(scooters.toList()){scooter ->
                ScooterCard(scooter)
            }
        }
    }

    @Composable
    private fun ScooterCard(
        scooter: Scooter,
        modifier: Modifier = Modifier) {
        val storage = Firebase.storage(BUCKET_URL)
        val imageRef = storage.getReferenceFromUrl(
            "https://firebasestorage.googleapis.com/v0/b/scooter-sharing-5c9ca.appspot.com/o/scooter.png")
        Log.v("FUUUUUUUU", imageRef.path);
        var fullPath = imageRef.downloadUrl

        AppTheme {
            Card(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(15.dp)
            ) {
                Column(modifier = modifier) {
                    GlideImage(
                        imageModel = { imageRef.downloadUrl }, // loading a network image using an URL.
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Fit,
                            alignment = Alignment.Center
                        )
                    )
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = modifier,
                            text = scooter.name.toString(),
                            style = MaterialTheme.typography.headlineSmall,
                        )
                        Text(
                            modifier = modifier,
                            text = scooter.location.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Text(text = "Reserve",
                            modifier = modifier
                                .padding(0.dp, 8.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(color = MaterialTheme.colorScheme.primary),
                                ) {
                                    //TODO: onclick action here
                                }
                        )
                    }
                }
            }
        }
    }
}