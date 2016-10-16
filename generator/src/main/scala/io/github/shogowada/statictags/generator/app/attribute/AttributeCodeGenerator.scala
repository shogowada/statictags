package io.github.shogowada.statictags.generator.app.attribute

class AttributeCodeGenerator {

  def generate(attributeSpecs: Iterable[AttributeSpec]): List[String] = {
    attributeSpecs.map(generate).toList
  }

  def generate(spec: AttributeSpec): String = {
    s"""lazy val ${spec.name} = Attribute(name = "${spec.name}")"""
  }
}
