package dk.itu.moapd.scootersharing.jacj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.google.android.material.snackbar.Snackbar
import dk.itu.moapd.scootersharing.jacj.databinding.ActivityStartRideBinding

class StartRideActivity : AppCompatActivity() {
    companion object {
        lateinit var ridesDB: RidesDB
    }

    private lateinit var binding: ActivityStartRideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityStartRideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ridesDB = RidesDB.get(this)

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
}