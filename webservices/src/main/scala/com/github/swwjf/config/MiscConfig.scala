package com.github.swwjf.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.springframework.context.annotation.{Configuration, Bean}

@Configuration
class MiscConfig {
  @Bean
  def scalaObjectMapper(): ObjectMapper = {
    val om = new ObjectMapper()
    om.registerModule(DefaultScalaModule)
    om
  }
}
