package dk.itu.moapd.scootersharing.jacj

import android.content.Context
import java.util.Random
import kotlin.collections.ArrayList

/**
 * The collection of rides in the system.
 * This can be accessed by other classes using the singleton pattern.
 * @author Jacob Møller Jensen
 * @since 0.5.0
 */
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

    /**
     * Returns the list of rides.
     *
     * @return The collection of rides in the system as a List of Scooter objects
     * @author Jacob Møller Jensen
     * @since 0.5.0
     */
    fun getRidesList(): List<Scooter> {
        return rides
    }

    /**
     * Adds a scooter to the list
     *
     * @param name the name of the new scooter
     * @param location the location of the new scooter
     * @author Jacob Møller Jensen
     * @since 0.5.0
     */
    fun addScooter(name: String , location: String) {
        //TODO: Name already exists. Must be unique. Propagate error to user some way?
        //if(!rides.any{ scooter -> scooter.name == name }){
        //    Log.d(TAG, "The duplicate is ${name} (from user)")
            rides.add(Scooter(name, location))
        //}
        //throw IllegalArgumentException("A scooter with that value is already in the list.")
    }

    /**
     * Update the location of your current scooter
     *
     * @param location the new location of the current scooter
     * @author Jacob Møller Jensen
     * @since 0.5.0
     */
    fun updateCurrentScooter(location: String) {
        rides.last().location = location
    }

    /**
     * Returns your current scooter
     *
     * @return The current scooter
     * @author Jacob Møller Jensen
     * @since 0.5.0
     */
    fun getCurrentScooter(): Scooter = rides.last()

    /**
     * Returns the current scooters information
     *
     * @return The information about the current scooter as a string
     * @see [Scooter.toString]
     * @author Jacob Møller Jensen
     * @since 0.5.0
     */
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


/**
 * The DBHolder that ensures that multiple threads do not access and/or manipulate at the same time
 * @author Christophe Beyls & Fabricio Batista Narcizo
 * @see Article "Kotlin singletons with argument" - <a href="https://bladecoder.medium.com/kotlin-singletons-with-argument-194ef06edd9e">https://bladecoder.medium.com/kotlin-singletons-with-argument-194ef06edd9e</a>
 * @since 0.5.0
 */
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