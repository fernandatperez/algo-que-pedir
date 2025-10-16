package ar.edu.unsam.algo3.dto

// REQUEST - Lo que recibe el endpoint
data class AuthRequest(
    var correo: String,
    var password: String
) {}

// RESPONSE - Lo que devuelve el endpoint, lo que necesita el front
data class AuthResponse(
    val nombre: String,
    val correo: String,
    val nombreLocal: String
) {}
// la respuesta nunca tiene que tener datos sensibles

data class AuthRegisterRequest(
    val nombre: String,
    val correo: String,
    val nombreLocal: String,
    val password: String,
) {}