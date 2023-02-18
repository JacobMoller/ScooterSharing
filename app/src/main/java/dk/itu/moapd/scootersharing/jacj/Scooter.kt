package dk.itu.moapd.scootersharing.jacj

import java.text.SimpleDateFormat
import java.util.*

/**
 * A Scooter object
 *
 * This class represents a scooter.
 *
 * @param name the unique name of the scooter.
 * @param location the location of the scooter.
 * @constructor Creates a scooter, given a name and location.
 * @author Jacob Møller Jensen
 */
class Scooter(var name: String, _location: String, private var timestamp: Long = System.currentTimeMillis()){
    var location: String = _location
        set(newLocation) {
            field = newLocation
            timestamp = System.currentTimeMillis()
        }
    fun formatDate(date: Long): String {
        val date = Date(date)
        val format = SimpleDateFormat("HH:mm dd/MM yyyy")
        return format.format(date).toString()
    }
    override fun toString(): String {
        return "Scooter(name=$name, location=$location, timestamp=${formatDate(timestamp)})"
    }
}