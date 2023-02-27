package dk.itu.moapd.scootersharing.jacj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import dk.itu.moapd.scootersharing.jacj.databinding.ActivityUpdateRideBinding


/**
 * The Activity UI to update a ride, updating the location of the current scooter.
 *
 * @see [RidesDB.updateCurrentScooter]
 * @author Jacob Møller Jensen
 * @since 0.4.0
 */
class UpdateRideActivity : AppCompatActivity() {
    companion object {
        lateinit var ridesDB: RidesDB
    }

    private lateinit var binding: ActivityUpdateRideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateRideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ridesDB = RidesDB.get(this)

        with(binding)
        {
            editTextName.setText(ridesDB.getCurrentScooter().name)
            //Update ride button
            updateRideButton.setOnClickListener {
                // Update the object attributes.
                val location = editTextLocation.text.toString().trim()
                ridesDB.updateCurrentScooter(location)

                Snackbar.make(binding.root.rootView, getString(R.string.ride_updated, ridesDB.getCurrentScooterInfo()), Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}