package ar.edu.unsam.algo3.bootstrap

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.repositorio.Repositorio

@Configuration
class RepoConfig {
    @Bean
    fun repoPlatos(): Repositorio<Plato> = Repositorio()
}
