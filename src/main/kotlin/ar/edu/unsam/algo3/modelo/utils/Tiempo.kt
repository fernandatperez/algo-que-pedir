package ar.edu.unsam.algo3.modelo.utils

import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun LocalDate.aniosHastaAhora(): Long {
    return ChronoUnit.YEARS.between(this, LocalDate.now())
}

fun LocalDate.diasHastaAhora(): Long {
    return ChronoUnit.DAYS.between(this, LocalDate.now())
}
