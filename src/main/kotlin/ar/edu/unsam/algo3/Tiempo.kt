package ar.edu.unsam.algo2.algoQuePedir.ar.edu.unsam.algo3

import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun LocalDate.aniosHastaAhora(): Long {
    return ChronoUnit.YEARS.between(this, java.time.LocalDate.now())
}

fun LocalDate.diasHastaAhora(): Long {
    return ChronoUnit.DAYS.between(this, LocalDate.now())
}
