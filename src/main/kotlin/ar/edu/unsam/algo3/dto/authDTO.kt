package ar.edu.unsam.algo3.dto

// REQUEST - Lo que recibe el endpoint
data class AuthRequest(
    var email: String,
    var password: String
) {}

// RESPONSE - Lo que devuelve el endpoint, lo que necesita el front
data class AuthResponse(
    val name: String,
    val email: String,
//    val nombreLocal: String
) {}

data class AuthRegisterRequest(
    val name: String, // Agregar esto en register
    val email: String,
//    val nombreLocal: String,
    val password: String
) {}