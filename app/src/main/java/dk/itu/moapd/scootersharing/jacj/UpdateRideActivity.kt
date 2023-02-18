package dk.itu.moapd.scootersharing.jacj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.google.android.material.snackbar.Snackbar
import dk.itu.moapd.scootersharing.jacj.databinding.ActivityUpdateRideBinding

class UpdateRideActivity : AppCompatActivity() {
    companion object {
        lateinit var s: Shared
    }

    private lateinit var binding: ActivityUpdateRideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateRideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        s = Shared

        with(binding)
        {
            editTextName.setText(s.currentScooter.name)
            //Start ride button
            updateRideButton.setOnClickListener {
                // Update the object attributes.
                val location = editTextLocation.text.toString().trim()
                s.setLocation(location) //ASK TA. Should i still have setLocation methods?

                Snackbar.make(binding.root.rootView, getString(R.string.ride_updated, s.currentScooter.toString()), Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}