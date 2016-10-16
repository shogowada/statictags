package io.github.shogowada.statictags.generator.app.element

class ElementSpecFactory {

  val nameColumnName = "Element"

  def createSpecs(rawSpec: Map[String, String]): ElementSpec = {
    ElementSpec(
      name = rawSpec.get(nameColumnName).get
    )
  }
}
