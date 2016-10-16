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
  val templateFilePath = Utils.getFile("StaticTags.scala.template").toPath
  val className = "StaticTags"

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

  def inject(lines: List[String], placeholder: String, replacement: List[String]): List[String] = {
    inject(
      lines,
      placeholder,
      replacement.reduce((lhs, rhs) => lhs + System.lineSeparator() + rhs)
    )
  }

  def inject(lines: List[String], placeholder: String, replacement: String): List[String] = {
    lines.map(inject(_, placeholder, replacement))
  }

  def inject(line: String, placeholder: String, replacement: String): String = {
    line.replaceFirst(placeholder, replacement)
  }

  def generate(baseDirectory: Path, className: String, lines: List[String]): Unit = {
    Files.createDirectories(baseDirectory)

    val fileName = s"$className.scala"
    Files.write(baseDirectory.resolve(fileName), lines.asJava)
    Files.write(baseDirectory.resolve(".gitignore"), fileName.getBytes)
  }
}
