package ar.edu.unsam.algo3.repositorio

import ar.edu.unsam.algo3.mock.ensaladaHuerta
import ar.edu.unsam.algo3.mock.hambuerguesa
import ar.edu.unsam.algo3.mock.pastaAlbondigas
import ar.edu.unsam.algo3.mock.pizzaMuzzaTomate
import ar.edu.unsam.algo3.mock.salmonGrillado
import ar.edu.unsam.algo3.modelo.plato.Plato

val repositorioPlato: Repositorio<Plato> = Repositorio<Plato>().apply {
    this.crear(pastaAlbondigas)
    this.crear(hambuerguesa)
    this.crear(ensaladaHuerta)
    this.crear(pizzaMuzzaTomate)
    this.crear(salmonGrillado)
}