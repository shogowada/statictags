package io.github.shogowada.statictags.generator.app.attribute

import javax.inject.Inject

import io.github.shogowada.statictags.generator.app.common.FieldNameFactory

class AttributeCodeGenerator @Inject()
(
    fieldNameFactory: FieldNameFactory
) {

  def generate(attributeSpecs: Iterable[AttributeSpec]): List[String] = {
    attributeSpecs.map(generate).toList
  }

  def generate(spec: AttributeSpec): String = {
    s"""lazy val ${fieldNameFactory.create(spec.name)}: AttributeSpec[String] = AttributeSpec[String](name = "${spec.name}")"""
  }
}
