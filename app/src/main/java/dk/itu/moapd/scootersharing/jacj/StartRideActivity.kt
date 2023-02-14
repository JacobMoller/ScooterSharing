package dk.itu.moapd.scootersharing.jacj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.google.android.material.snackbar.Snackbar
import dk.itu.moapd.scootersharing.jacj.databinding.ActivityStartRideBinding

class StartRideActivity : AppCompatActivity() {
    companion object {
        lateinit var s: Shared
    }

    private lateinit var binding: ActivityStartRideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityStartRideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        s = Shared

        with(binding)
        {
            //Start ride button
            startRideButton.setOnClickListener {
                // Update the object attributes.
                val name = editTextName.text.toString().trim()
                s.setName(name)
                val location = editTextLocation.text.toString().trim()
                s.setLocation(location) //ASK TA. Should i still have setLocation methods?

                Snackbar.make(binding.root, getString(R.string.ride_started, s.currentScooter.toString()), Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}