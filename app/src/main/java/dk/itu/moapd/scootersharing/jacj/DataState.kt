package dk.itu.moapd.scootersharing.jacj

import android.provider.ContactsContract.Data
import dk.itu.moapd.scootersharing.jacj.models.Scooter

sealed class DataState {
    class Success(val data: MutableList<Scooter>) : DataState()
    class Failure(val message: String) : DataState()
    object Loading : DataState()
    object Empty : DataState()
}