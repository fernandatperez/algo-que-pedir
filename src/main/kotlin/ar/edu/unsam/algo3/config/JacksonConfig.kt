package ar.edu.unsam.algo3.config

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.module.SimpleModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ar.edu.unsam.algo3.repositorio.RepositorioLocal
import ar.edu.unsam.algo3.dto.FielesDeserializer
import ar.edu.unsam.algo3.modelo.usuario.Fieles

@Configuration
class JacksonConfig {

    @Bean
    fun fielesModule(localRepo: RepositorioLocal): Module {
        val module = SimpleModule()
        module.addDeserializer(Fieles::class.java, FielesDeserializer(localRepo))
        return module
    }
}