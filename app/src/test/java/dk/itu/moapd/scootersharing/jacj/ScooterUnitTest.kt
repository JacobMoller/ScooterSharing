package dk.itu.moapd.scootersharing.jacj

import org.junit.Test

import org.junit.Assert.*

class ScooterUnitTest {
    @Test
    fun scooterName_isCorrect() {
        var scooter: Scooter = Scooter("CPH001", "ITU")

        var actual = scooter.name

        var expected = "CPH001"

        assertEquals(expected, actual)
    }

    @Test
    fun scooterLocation_isCorrect() {
        var scooter: Scooter = Scooter("CPH001", "ITU")

        var actual = scooter.location

        var expected = "ITU"

        assertEquals(expected, actual)
    }

    //TODO: Test currentTimeFormatted before this
    @Test
    fun scooterToString_isCorrect() {
        var name = "CPH001"
        var location = "ITU"
        var scooter: Scooter = Scooter(name, location)

        var actual = scooter.toString()

        var currentTimeFormatted = scooter.dateFormatted()

        var expected = "Scooter(name=$name, location=$location, timestamp=$currentTimeFormatted)"

        assertEquals(expected, actual)
    }
}