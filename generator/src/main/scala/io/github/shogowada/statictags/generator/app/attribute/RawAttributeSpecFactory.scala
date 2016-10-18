package io.github.shogowada.statictags.generator.app.attribute

import io.github.shogowada.statictags._

class RawAttributeSpecFactory {

  val nameColumnName = "Attribute"
  val valueColumnName = "Value"

  def createSpec(rawSpec: Map[String, String]): RawAttributeSpec = {
    val get = (columnName: String) => rawSpec.get(columnName).get

    val name = get(nameColumnName)
    RawAttributeSpec(
      name = name,
      specType = createSpecType(name, get(valueColumnName))
    )
  }

  def createSpecType(name: String, value: String): Class[_] = {
    value match {
      case _ if name == "autocomplete" => StringAttributeSpec.getClass
      case _ if value.contains("Boolean attribute") => BooleanAttributeSpec.getClass
      case _ if value.contains("Ordered set of unique space-separated tokens") => OrderedSetOfUniqueSpaceSeparatedStringAttributeSpec.getClass
      case _ if value.contains("Set of comma-separated tokens") => CommaSeparatedStringAttributeSpec.getClass
      case _ if value.contains("Set of space-separated tokens") => SpaceSeparatedStringAttributeSpec.getClass
      case _ if value.contains("Valid non-negative integer") => IntegerAttributeSpec.getClass
      case _ if value.contains("Valid list of integers") => CommaSeparatedIntegerAttributeSpec.getClass
      case _ if value == """"true"; "false"""" => StringBooleanAttributeSpec.getClass
      case _ if value == """"on"; "off"""" => StringBooleanOnOffAttributeSpec.getClass
      case _ => StringAttributeSpec.getClass
    }
  }
}
