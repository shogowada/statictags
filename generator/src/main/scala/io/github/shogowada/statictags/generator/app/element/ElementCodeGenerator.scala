package io.github.shogowada.statictags.generator.app.element

class ElementCodeGenerator {

  def generate(elementSpecs: Iterable[ElementSpec]): List[String] = {
    elementSpecs.map(generate).toList
  }

  def generate(spec: ElementSpec): String = {
    s"""lazy val ${spec.name} = Element(name = ${spec.name})"""
  }
}
