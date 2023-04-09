package dk.itu.moapd.scootersharing.jacj.ui.Login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.snackbar.Snackbar
import dk.itu.moapd.scootersharing.jacj.R

class LoginFragment : Fragment() {

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result ->
        onSignInResult(result)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.AnonymousBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())

        // Create and launch sign-in intent.
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTheme(R.style.Theme_ScooterSharing)
            .setIsSmartLockEnabled(false)
            .build()
        signInLauncher.launch(signInIntent)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            // Sign in success, update UI with the signed-in user's information.
            Snackbar
                .make(this.requireView(), "User logged in the app.", Snackbar.LENGTH_SHORT)
                .show()
            startMainActivity()
        } else
        // If sign in fails, display a message to the user.
            Snackbar
            .make(this.requireView(), "Authentication failed.", Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun startMainActivity() {
        findNavController().navigate(
            R.id.show_main
        )
    }
}