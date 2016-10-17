package io.github.shogowada.statictags.generator.app

import java.nio.file.{Files, Path}
import javax.inject.Inject

import io.github.shogowada.statictags.generator.app.attribute.{AttributeCodeGenerator, AttributeSpec}
import io.github.shogowada.statictags.generator.app.element.{ElementCodeGenerator, ElementSpec}

import scala.collection.JavaConverters._

class CodeGenerator @Inject()
(
    attributeCodeGenerator: AttributeCodeGenerator,
    elementCodeGenerator: ElementCodeGenerator
) {
  val className = "StaticTags"
  val fileName = s"$className.scala"
  val templateFilePath = Utils.getFile(s"$fileName.template").toPath

  val packageNamePlaceholder = "@package"
  val classNamePlaceholder = "@class"
  val attributesPlaceholder = "@attributes"
  val elementsPlaceholder = "@elements"

  def generate(baseDirectory: Path,
               packageName: String,
               attributeSpecs: Iterable[AttributeSpec],
               elementSpecs: Iterable[ElementSpec]): Unit = {
    var lines = Files.readAllLines(templateFilePath).asScala.toList

    lines = inject(lines, packageNamePlaceholder, packageName)
    lines = inject(lines, classNamePlaceholder, className)

    val attributeLines = attributeCodeGenerator.generate(attributeSpecs)
    lines = inject(lines, attributesPlaceholder, attributeLines)

    val elementLines = elementCodeGenerator.generate(elementSpecs)
    lines = inject(lines, elementsPlaceholder, elementLines)

    generate(baseDirectory, className, lines)
  }

  private def inject(lines: List[String], placeholder: String, replacementLines: List[String]): List[String] = {
    val indentedReplacementLines = indentReplacementLines(lines, placeholder, replacementLines)
    inject(
      lines,
      placeholder,
      indentedReplacementLines.reduce((lhs, rhs) => lhs + System.lineSeparator() + rhs)
    )
  }

  private def indentReplacementLines(lines: List[String], placeholder: String, replacementLines: List[String]): List[String] = {
    val indention = lines.map((line: String) => line.indexOf(placeholder))
        .filter(index => index >= 0)
        .head
    replacementLines.zipWithIndex.map {
      case (element, index) =>
        if (index == 0) {
          element
        } else {
          (" " * indention) + element
        }
    }
  }

  private def inject(lines: List[String], placeholder: String, replacement: String): List[String] = {
    lines.map(inject(_, placeholder, replacement))
  }

  private def inject(line: String, placeholder: String, replacement: String): String = {
    line.replaceFirst(placeholder, replacement)
  }

  private def generate(baseDirectory: Path, className: String, lines: List[String]): Unit = {
    Files.createDirectories(baseDirectory)
    Files.write(baseDirectory.resolve(fileName), lines.asJava)
  }
}
