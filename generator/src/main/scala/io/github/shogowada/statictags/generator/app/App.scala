package io.github.shogowada.statictags.generator.app

import java.nio.file.Paths
import javax.inject.Inject

import com.github.tototoshi.csv.CSVReader
import io.github.shogowada.statictags.generator.app.attribute.AttributeSpecFactory
import io.github.shogowada.statictags.generator.app.element.ElementSpecFactory

class App @Inject()
(
    attributeSpecFactory: AttributeSpecFactory,
    elementSpecFactory: ElementSpecFactory,
    codeGenerator: CodeGenerator
) {
  val attributesFileName = "Attributes.csv"
  val elementsFileName = "Elements.csv"

  val packageNameAsPath = Paths.get("io", "github", "shogowada", "statictags")
  val packageName = Range(0, packageNameAsPath.getNameCount)
      .map(packageNameAsPath.getName(_).toString)
      .reduce((lhs, rhs) => lhs + "." + rhs)

  val generatedCodeBaseDirectory = Paths
      .get("src", "main", "scala")
      .toAbsolutePath
      .resolve(packageNameAsPath)

  def run(): Unit = {
    val attributeRawSpecs = loadRawSpecs(attributesFileName)
    val elementRawSpecs = loadRawSpecs(elementsFileName)

    val attributeSpecs = attributeRawSpecs.map(attributeSpecFactory.createSpecs)
    val elementSpecs = elementRawSpecs.map(elementSpecFactory.createSpecs)

    codeGenerator.generate(
      generatedCodeBaseDirectory,
      packageName,
      attributeSpecs,
      elementSpecs
    )
  }

  def loadRawSpecs(fileName: String): Iterable[Map[String, String]] = {
    CSVReader.open(Utils.getFile(fileName)).allWithHeaders()
  }
}
