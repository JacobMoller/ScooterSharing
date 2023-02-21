package dk.itu.moapd.scootersharing.jacj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.google.android.material.snackbar.Snackbar
import dk.itu.moapd.scootersharing.jacj.databinding.ActivityUpdateRideBinding

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
            if(ridesDB.getCurrentScooter() == null){
                editTextName.setText(R.string.no_scooter_selected)
            }
            else {
                editTextName.setText(ridesDB.getCurrentScooter()?.name)
            }
            //Start ride button
            updateRideButton.setOnClickListener {
                // Update the object attributes.
                val location = editTextLocation.text.toString().trim()
                ridesDB.updateCurrentScooter(location)

                Snackbar.make(binding.root.rootView, getString(R.string.ride_updated, ridesDB.getCurrentScooterInfo()), Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}