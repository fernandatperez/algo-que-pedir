package ar.edu.unsam.algo3.dto

// REQUEST - Lo que recibe el endpoint
data class AuthRequest(
    var correo: String,
    var password: String
) {}

// RESPONSE - Lo que devuelve el endpoint, lo que necesita el front
data class AuthResponse(
    //val token: String, // todo preguntar si se podria usar esto
    val nombre: String,
    val correo: String
) {}
// la respuesta nunca tiene que tener datos sensibles