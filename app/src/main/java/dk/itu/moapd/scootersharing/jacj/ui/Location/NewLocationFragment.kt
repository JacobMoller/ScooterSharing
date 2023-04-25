package dk.itu.moapd.scootersharing.jacj.ui.Location

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment

class NewLocationFragment : Fragment()

@Composable
fun NewLocation(){
    Column {
        Text(text = "lat")
        Text(text = "latvalue")
        Text(text = "long")
        Text(text = "longvalue")
        Text(text = "Address")
        Text(text = "Addressvalue")
    }
}