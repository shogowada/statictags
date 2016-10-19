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
      case _ if name == "for" => SetOfUniqueSpaceSeparatedStringAttributeSpec.getClass
      case _ if Set("max", "min").contains(name) => BigDecimalAttributeSpec.getClass
      case _ if name == "step" => BigDecimalOrAnyAttributeSpec.getClass
      case _ if name == "value" => ValueAttributeSpec.getClass
      case _ if value.contains("Boolean attribute") => BooleanAttributeSpec.getClass
      case _ if value.contains("CSS declarations") => CssAttributeSpec.getClass
      case _ if value.contains("Ordered set of unique space-separated tokens") => OrderedSetOfUniqueSpaceSeparatedStringAttributeSpec.getClass
      case _ if value.contains("Regular expression matching the JavaScript Pattern production") => RegexAttributeSpec.getClass
      case _ if value.contains("Unordered set of unique space-separated tokens") => SetOfUniqueSpaceSeparatedStringAttributeSpec.getClass
      case _ if value.contains("Set of comma-separated tokens") => CommaSeparatedStringAttributeSpec.getClass
      case _ if value.contains("Set of space-separated tokens") => SpaceSeparatedStringAttributeSpec.getClass
      case _ if value.contains("Valid floating-point number") => BigDecimalAttributeSpec.getClass
      case _ if value.contains("Valid integer") => IntegerAttributeSpec.getClass
      case _ if value.contains("Valid non-negative integer") => IntegerAttributeSpec.getClass
      case _ if value.contains("Valid list of integers") => CommaSeparatedIntegerAttributeSpec.getClass
      case _ if value == """"true"; "false"""" => TrueOrFalseAttributeSpec.getClass
      case _ if value == """"on"; "off"""" => OnOrOffAttributeSpec.getClass
      case _ if value == """"yes"; "no"""" => YesOrNoAttributeSpec.getClass
      case _ => StringAttributeSpec.getClass
    }
  }
}
