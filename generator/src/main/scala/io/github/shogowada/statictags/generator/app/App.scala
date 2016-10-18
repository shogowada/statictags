package io.github.shogowada.statictags.generator.app

import java.nio.file.Paths
import javax.inject.Inject

import com.github.tototoshi.csv.CSVReader
import io.github.shogowada.statictags.generator.app.attribute.{RawAttributeSpec, RawAttributeSpecFactory}
import io.github.shogowada.statictags.generator.app.common.Utils
import io.github.shogowada.statictags.generator.app.element.RawElementSpecFactory

class App @Inject()
(
    rawAttributeSpecFactory: RawAttributeSpecFactory,
    rawElementSpecFactory: RawElementSpecFactory,
    codeGenerator: CodeGenerator
) {
  val attributesFileName = "Attributes.csv"
  val elementsFileName = "Elements.csv"

  val packageNameAsPath = Paths.get("io", "github", "shogowada", "statictags")
  val packageName = Range(0, packageNameAsPath.getNameCount)
      .map(packageNameAsPath.getName(_).toString)
      .reduce((lhs, rhs) => lhs + "." + rhs)

  val generatedCodeBaseDirectory = Paths
      .get("statictags", "shared", "src", "main", "scala")
      .toAbsolutePath
      .resolve(packageNameAsPath)

  def run(): Unit = {
    val attributeSpecs = loadSpecs(attributesFileName)
    val elementSpecs = loadSpecs(elementsFileName)

    val rawAttributeSpecs = attributeSpecs.map(rawAttributeSpecFactory.createSpec)
    val rawElementSpecs = elementSpecs.flatMap(rawElementSpecFactory.createSpecs)

    codeGenerator.generate(
      generatedCodeBaseDirectory,
      packageName,
      rawAttributeSpecs.toSet.toSeq.sortBy((spec: RawAttributeSpec) => spec.name),
      rawElementSpecs
    )
  }

  def loadSpecs(fileName: String): Iterable[Map[String, String]] = {
    CSVReader.open(Utils.getFile(fileName)).allWithHeaders()
  }
}
