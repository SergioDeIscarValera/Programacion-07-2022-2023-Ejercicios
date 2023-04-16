package models

abstract class Persona(
    var id: Long = 0, // Lo he tenido que poner var para que funcione el copy...
    val nombre: String
){
    abstract fun toCsvRow(): String
    //Copy method like data class
    abstract fun copy(id: Long = this.id, nombre: String = this.nombre): Persona
}
