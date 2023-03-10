package dk.itu.moapd.scootersharing.jacj

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
/**
 * Main entry point for the ScooterSharing app.
 *
 * @author Jacob Møller Jensen
 * @since 0.1.0
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Set the content to the activity_main layout file
        setContentView(R.layout.activity_main)
    }
}