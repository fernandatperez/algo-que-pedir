package ar.edu.unsam.algo3.mock

import ar.edu.unsam.algo3.dominio.Local
import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.modelo.utils.Direccion
import ar.edu.unsam.algo3.repositorio.RepositorioLocal
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import org.uqbar.geodds.Point

@Service
class LocalBootstrap(
    private val repositorioLocal: RepositorioLocal
) : InitializingBean {

    override fun afterPropertiesSet() {
        this.crearLocalInicial()
    }

    private fun crearLocalInicial() {
        if (repositorioLocal.objetosDeRepositorio().isEmpty()) {

            val localInicial = Local().apply {
                id = 1
                nombre = "Carlo's Bake Shop"
                url = "https://networthbro.com/wp-content/uploads/2019/07/buddy-valastro-networth-salary.jpg"
                regalias = 3.0
                porcentajeAcordado = 6.0
                mediosDePago = mutableSetOf(Pago.EFECTIVO)
                direccion = Direccion(
                    calle = "Av. Siempre Viva",
                    altura = 123,
                    ubicacion = Point(-34.603722, -58.381592)  // lat, long
                )

            }

            repositorioLocal.crear(localInicial)

            println("""
                Local inicial creado exitosamente:
                 Nombre: ${localInicial.nombre}
                 Dirección: ${localInicial.direccion.calle} ${localInicial.direccion.altura}
                 Ubicación: (${localInicial.direccion.ubicacion.x}, ${localInicial.direccion.ubicacion.y})
                 URL: ${localInicial.url}
                 Comisiones: App ${localInicial.regalias}% | Autor ${localInicial.porcentajeAcordado}%
            """.trimIndent())

        } else {
            val localExistente = repositorioLocal.obtenerObjeto(1)
            println(" Local existente: ${localExistente.nombre}")
        }
    }
}