package dk.itu.moapd.scootersharing.jacj

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

    override fun toString(): String {
        return "Scooter(name=$name, location=$location, timestamp=$timestamp)"
    }
}