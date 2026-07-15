package ar.edu.unsam.algo3.modelo.utils

import ar.edu.unsam.algo3.servicios.InstanciaActualizador
import ar.edu.unsam.algo3.modelo.cupon.Cupon
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.repositorio.Repositorio

// todo: Preguntar al profesor si es un repositorio de cada tipo solamente o si hay por ejemplo cada local tiene un repositorio de platos
// Command
class ProcesoAdministracion {
    var acciones: MutableSet<AdministradorCommands> = mutableSetOf() // hago esto tipo command porque pone "setear algunas acciones"

    fun agregarAccion(accion: AdministradorCommands) {
        acciones.add(accion)
    }

    fun eliminarAccion(accion: AdministradorCommands) {
        acciones.remove(accion)
    }

    fun ejecutarAcciones() { // todo: Esto seria el Invoker que "invoca" a los comandos el Receiver seria a lo que le pegue cada accion
        acciones.forEach { it.execute() }
        acciones.clear()
    }
}

// Todos los procesos deben enviar un mail a admin@aqp.com.ar indicando el tipo de proceso que se realizó en el asunto y cuerpo. (Ej: Se realizó el proceso: <tipoProceso>).
// La solución dada, debe contemplar que en próximas iteraciones aparezcan nuevos procesos.
abstract class AdministradorCommands( //todo: Este seria el COMMAND
    var mailSender: MailSender,
    // var executeObserver : ObservadorProceso = MailNotificador(mailSender)
) {
    val classType = this.javaClass.simpleName // devuelve solo el nombre de la clase sin el PATH
    // template
    fun execute() {
        // primitiva
        this.doExecute()
        this.enviarMail()
        // No es observer por que se puede desactivar. Esto no queremos que se pueda sacar
//        executeObserver.notificar(classType)
    }

    abstract fun doExecute()

    fun enviarMail() {

            var mail = Mail(
                from = "app@process.admins.com",
                to = "admin@aqp.com.ar",
                subject = "Process $classType",
                content = "Se realizó el proceso: $classType"
            )
        mailSender.sendMail(mail)
    }
}

//Borrar Mensajes antiguos y leídos: estos son aquellos mensajes que tienen los locales en inbox hace más de 30 días y con estado leído.
class BorrarMensajesAntiguosYLeidos(
    mailSender: MailSender,
    val repositorioLocales: Repositorio<Local>
) : AdministradorCommands(mailSender) { //todo: Estas subclases serian las ConcreteCommand
    override fun doExecute() {
        // REPOSITORIO_LOCALES es el receiver
        repositorioLocales.objetosDeRepositorio().forEach{
            it.borrarMensajesAntiguosYLeidos()
        }
    }
}

//Actualización de Ingredientes: Al ejecutarlo se actualizarán los ingredientes del repositorio, utilizando un ServiceIngredientes.
class ActualizacionIngredientes(
    mailSender: MailSender,
    val mensaje: String,
    val repositorioIng: Repositorio<Ingrediente>
) : AdministradorCommands(mailSender) {

    val ingredientesAct = InstanciaActualizador(mensaje, repositorioIng)

    override fun doExecute() {
        ingredientesAct.actualizarIng()
    }
}

//Borrar Cupones vencidos sin aplicar: es decir, eliminar de la aplicación los cupones que se encuentran vencidos y no se usaron en ningún pedido.
class BorrarCupones(
    mailSender: MailSender,
    val repositorioCupon: Repositorio<Cupon>
) : AdministradorCommands(mailSender) {
    override fun doExecute() {
        val cuponesABorrar = repositorioCupon.objetosDeRepositorio().filter { it.estaVencidoYNoAplicado() }
        cuponesABorrar.forEach { repositorioCupon.eliminarDeColeccion(it.id) }
    }
}

//Agregar Locales: agregar de forma masiva locales, partiendo de una lista dada.
class AgregarLocales(
    val localesNuevos : MutableList<Local>,
    val repositorioLocales: Repositorio<Local>,
    mailSender: MailSender
) : AdministradorCommands(mailSender) {
    override fun doExecute() {
        localesNuevos.forEach {
            repositorioLocales.crear(it)
        }
    }
}


