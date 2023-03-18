package dk.itu.moapd.scootersharing.jacj.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dk.itu.moapd.scootersharing.jacj.R

/**
 * Main entry point for the ScooterSharing app.
 *
 * @author Jacob Møller Jensen
 * @since 0.1.0
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}