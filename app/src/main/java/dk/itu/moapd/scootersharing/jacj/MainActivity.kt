package dk.itu.moapd.scootersharing.jacj

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import dk.itu.moapd.scootersharing.jacj.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var ridesDB: RidesDB
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ridesDB = RidesDB.get(this)

        with(binding)
        {
            //Start ride button
            startRideButton.setOnClickListener {
                val intent = Intent(baseContext, StartRideActivity::class.java)
                startActivity(intent)
            }

            updateRideButton.setOnClickListener {
                val intent = Intent(baseContext, UpdateRideActivity::class.java)
                startActivity(intent)
            }

            listRidesButton.setOnClickListener {
                if(rideListView.visibility == View.GONE){
                    rideListView.visibility = View.VISIBLE
                }
                else{
                    rideListView.visibility = View.GONE
                }
            }

            // API Level
            val version = Build.VERSION.SDK_INT
            apiLevel.text = getString(R.string.version, version)

            //Create custom adapter
            val adapter = RideListAdapter(this@MainActivity, R.layout.list_rides, ridesDB.getRidesList())
            binding = ActivityMainBinding.inflate(layoutInflater)
            rideListView.adapter = adapter
        }
    }
}