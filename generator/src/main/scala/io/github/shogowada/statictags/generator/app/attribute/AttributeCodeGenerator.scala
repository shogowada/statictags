package io.github.shogowada.statictags.generator.app.attribute

import javax.inject.Inject

import io.github.shogowada.statictags.generator.app.common.{FieldNameFactory, Utils}

class AttributeCodeGenerator @Inject()
(
    fieldNameFactory: FieldNameFactory
) {

  def generate(attributeSpecs: Iterable[RawAttributeSpec]): List[String] = {
    attributeSpecs.map(generate).toList
  }

  def generate(spec: RawAttributeSpec): String = {
    val specTypeAsString = Utils.getSimpleClassName(spec.specType)
    s"""lazy val ${fieldNameFactory.create(spec.name)} = $specTypeAsString("${spec.name}")"""
  }
}
