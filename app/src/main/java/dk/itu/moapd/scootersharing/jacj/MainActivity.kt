package dk.itu.moapd.scootersharing.jacj

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.material.snackbar.Snackbar
import dk.itu.moapd.scootersharing.jacj.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val scooter: Scooter = Scooter("","")
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding)
        {
            //Start ride button
            startRideButton.setOnClickListener {
                    // Update the object attributes.
                    val name = editTextName.text.toString().trim()
                    scooter.name = name //ASK TA. Should i still have setName methods?
                    val location = editTextLocation.text.toString().trim()
                    scooter.location = location //ASK TA. Should i still have setLocation methods?

                    Snackbar.make(binding.root, getString(R.string.ride_started, scooter.toString()), Snackbar.LENGTH_SHORT).show()
            }

            // API Level
            val version = Build.VERSION.SDK_INT
            apiLevel.text = getString(R.string.version, version);
        }
    }
}