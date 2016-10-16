package io.github.shogowada.statictags.generator

import com.google.inject.{AbstractModule, Guice}
import io.github.shogowada.statictags.generator.app.{App, CodeGenerator}

object Main {
  def main(args: Array[String]): Unit = {
    val app = Guice
        .createInjector(new GeneratorModule)
        .getInstance(classOf[App])
    app.run()
  }
}

class GeneratorModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[App])

    bind(classOf[CodeGenerator])
  }
}
