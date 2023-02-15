package dk.itu.moapd.scootersharing.jacj

object Shared {
    var currentScooter: Scooter = Scooter("","")

    fun setName(newName: String){
        currentScooter.name = newName
    }
    fun setLocation(newLocation: String){
        currentScooter.location = newLocation
    }
}