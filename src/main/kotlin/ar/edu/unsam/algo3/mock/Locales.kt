package ar.edu.unsam.algo3.mock

import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.local.Pago

object LocalPollos: Local(
    nombre = "Pollos Fire",
    direccion = Direccion1,
    porcentajeAcordado = 1.0,
    regalias = 2.0,
    mediosDePago = mutableSetOf(Pago.EFECTIVO, Pago.TRANSFERENCIA_BANCARIA, Pago.QR)
)