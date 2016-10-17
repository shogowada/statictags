package io.github.shogowada.statictags.generator.app.element

import javax.inject.Inject

import io.github.shogowada.statictags.generator.app.common.FieldNameFactory

class ElementCodeGenerator @Inject()
(
    fieldNameFactory: FieldNameFactory
) {

  def generate(elementSpecs: Iterable[ElementSpec]): List[String] = {
    elementSpecs.map(generate).toList
  }

  def generate(spec: ElementSpec): String = {
    s"""lazy val ${fieldNameFactory.create(spec.name)} = ElementSpec(name = "${spec.name}")"""
  }
}
