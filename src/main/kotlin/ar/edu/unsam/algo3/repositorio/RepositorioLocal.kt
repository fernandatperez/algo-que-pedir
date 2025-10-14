package ar.edu.unsam.algo3.repositorio

import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import org.springframework.stereotype.Repository


@Repository
class RepositorioLocal : Repositorio<Local>()