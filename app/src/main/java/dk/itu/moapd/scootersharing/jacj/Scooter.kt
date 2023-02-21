package dk.itu.moapd.scootersharing.jacj

import java.text.SimpleDateFormat
import java.util.*

/**
 * A Scooter object
 *
 * This class represents a scooter.
 *
 * @param name the unique name of the scooter.
 * @param _location the location of the scooter.
 * @param timestamp the timestamp of the creation of the scooter. If no param is provided it will use the current timestamp
 * @constructor Creates a scooter, given a name and location.
 * @author Jacob Møller Jensen
 */
class Scooter(val name: String, _location: String, var timestamp: Long = System.currentTimeMillis()){
    var location: String = _location
        set(newLocation) {
            field = newLocation
            timestamp = System.currentTimeMillis()
        }

    fun formatDate(input_date: Long): String {
        val date = Date(input_date)
        val format = SimpleDateFormat("HH:mm dd/MM yyyy", Locale.GERMANY)
        return format.format(date).toString()
    }
    override fun toString(): String {
        return "Scooter(name=$name, location=$location, timestamp=${formatDate(timestamp)})"
    }
}