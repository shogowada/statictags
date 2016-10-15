package io.github.shogowada.statictags.generator

import java.io.File
import java.nio.file._
import javax.inject.Inject

import com.github.tototoshi.csv.CSVReader
import com.google.inject.Guice
import io.github.shogowada.statictags.generator.attribute.{AttributeCodeGenerator, AttributeSpecFactory}
import io.github.shogowada.statictags.generator.element.{ElementCodeGenerator, ElementSpecFactory}

object Main {
  def main(args: String*): Int = {
    val appController = Guice
        .createInjector(new GeneratorModule)
        .getInstance(classOf[AppController])

    appController.run() match {
      case true => 0
      case false => -1
    }
  }
}

class AppController @Inject()
(
    attributeSpecFactory: AttributeSpecFactory,
    elementSpecFactory: ElementSpecFactory,
    attributeCodeGenerator: AttributeCodeGenerator,
    elementCodeGenerator: ElementCodeGenerator
) {
  val attributesFile = new File("Attributes.csv")
  val elementsFile = new File("Elements.csv")

  val generatedCodeBaseDirectory = Paths
      .get("src", "main", "scala", "io", "github", "shogowada", "statictags")
      .toAbsolutePath

  def run(): Boolean = {
    val attributesReader = CSVReader.open(attributesFile)
    val elementsReader = CSVReader.open(elementsFile)

    val attributes = attributesReader.allWithHeaders()
    val elements = elementsReader.allWithHeaders()

    val attributeSpecs = attributeSpecFactory.createSpecs(attributes)
    val elementSpecs = elementSpecFactory.createSpecs(elements)

    attributeCodeGenerator.generate(generatedCodeBaseDirectory, attributeSpecs) &&
        elementCodeGenerator.generate(generatedCodeBaseDirectory, elementSpecs)
  }
}
