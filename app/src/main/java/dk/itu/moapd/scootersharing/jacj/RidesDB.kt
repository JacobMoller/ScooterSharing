package dk.itu.moapd.scootersharing.jacj

import android.content.Context
import java.util.Random
import kotlin.collections.ArrayList

class RidesDB private constructor(context: Context) {

    private val rides = ArrayList <Scooter >()
    companion object : RidesDBHolder<RidesDB, Context>(::RidesDB)

    init {
        rides.add(
            Scooter("CPH001", "ITU", randomDate())
        )
        rides.add(
            Scooter("CPH002", "Fields", randomDate())
        )
        rides.add(
            Scooter("CPH003", "Lufthavn", randomDate())
        )
        rides.add(
            Scooter("CPH004", "Lufthavn", randomDate())
        )
        rides.add(
            Scooter("CPH005", "Lufthavn", randomDate())
        )
        rides.add(
            Scooter("CPH006", "Lufthavn", randomDate())
        )
        rides.add(
            Scooter("CPH007", "Lufthavn", randomDate())
        )
        rides.add(
            Scooter("CPH008", "Lufthavn", randomDate())
        )
        rides.add(
            Scooter("CPH009", "Lufthavn", randomDate())
        )
        rides.add(
            Scooter("CPH010", "Lufthavn", randomDate())
        )
        rides.add(
            Scooter("CPH011", "Lufthavn", randomDate())
        )
        rides.add(
            Scooter("CPH012", "Lufthavn", randomDate())
        )
    }

    fun getRidesList(): List<Scooter> {
        return rides
    }

    fun addScooter(name: String , location: String) {
        //TODO: Name already exists. Must be unique. Propagate error to user some way?
        //if(!rides.any{ scooter -> scooter.name == name }){
        //    Log.d(TAG, "The duplicate is ${name} (from user)")
            rides.add(Scooter(name, location))
        //}
        //throw IllegalArgumentException("A scooter with that value is already in the list.")
    }

    fun updateCurrentScooter(location: String) {
        rides.last().location = location
    }

    fun getCurrentScooter(): Scooter = rides.last()

    fun getCurrentScooterInfo(): String = getCurrentScooter().toString()

    /**
     * Generate a random timestamp in the last 365 days. *
     * @return A random timestamp in the last year.
     */
    private fun randomDate(): Long {
        val random = Random ()
        val now = System.currentTimeMillis()
        val year = random.nextDouble() * 1000 * 60 * 60 * 24 * 365
        return (now - year).toLong()
    }
}

open class RidesDBHolder<out T: Any, in A>(creator: (A) -> T) {

    private var creator: ((A) -> T)? = creator

    @Volatile private var instance: T? = null
    fun get(arg: A): T {
        val checkInstance = instance
        if (checkInstance != null)
            return checkInstance

        return synchronized(this) {
            val checkInstanceAgain = instance
            if (checkInstanceAgain != null)
                checkInstanceAgain
            else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}