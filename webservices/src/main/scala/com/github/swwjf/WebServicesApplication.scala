package com.github.swwjf

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.web.SpringBootServletInitializer
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(Array("com.github.swwjf"))
class WebServicesApplication extends SpringBootServletInitializer {
  override def configure(builder: SpringApplicationBuilder): SpringApplicationBuilder =
    builder.sources(classOf[WebServicesApplication])
}

object WebServicesApplication {
  def main(args: Array[String]) {
    new SpringApplicationBuilder(classOf[WebServicesApplication]).run(args: _*)
  }
}
