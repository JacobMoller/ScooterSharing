package dk.itu.moapd.scootersharing.jacj

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import dk.itu.moapd.scootersharing.jacj.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

            // API Level
            val version = Build.VERSION.SDK_INT
            apiLevel.text = getString(R.string.version, version)
        }
    }
}