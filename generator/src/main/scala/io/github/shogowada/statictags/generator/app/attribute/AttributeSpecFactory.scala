package io.github.shogowada.statictags.generator.app.attribute

class AttributeSpecFactory {

  val nameColumnName = "Attribute"

  def createSpec(rawSpec: Map[String, String]): AttributeSpec = {
    AttributeSpec(
      name = rawSpec.get(nameColumnName).get
    )
  }
}
