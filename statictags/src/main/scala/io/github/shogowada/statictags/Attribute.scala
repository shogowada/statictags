package io.github.shogowada.statictags

import io.github.shogowada.statictags.AttributeValueType._

object AttributeValueType {
  trait AttributeValueType

  case object DEFAULT extends AttributeValueType
  case object COMMA_SEPARATED extends AttributeValueType
  case object SPACE_SEPARATED extends AttributeValueType
  case object CSS extends AttributeValueType
  case object TRUE_OR_FALSE extends AttributeValueType
  case object ON_OR_OFF extends AttributeValueType
  case object YES_OR_NO extends AttributeValueType
}

case class Attribute[Value](name: String, value: Value, valueType: AttributeValueType = DEFAULT) {

  implicit class RichBoolean(value: Boolean) {
    def fold[T](trueValue: T, falseValue: T): T = {
      if (value) {
        trueValue
      } else {
        falseValue
      }
    }
  }

  override def toString: String =
    this match {
      case Attribute(_, _: Boolean, DEFAULT) => valueToString
      case _ => name + quoteAndAddEqual(valueToString)
    }

  def valueToString: String =
    this match {
      case Attribute(_, values: Iterable[_], COMMA_SEPARATED) => delimitValues(values, ",")
      case Attribute(_, values: Iterable[_], SPACE_SEPARATED) => delimitValues(values, " ")
      case Attribute(_, valueMap: Map[_, _], CSS) => formatCss(valueMap)
      case Attribute(_, theValue: Boolean, ON_OR_OFF) => theValue.fold("on", "off")
      case Attribute(_, theValue: Boolean, TRUE_OR_FALSE) => theValue.fold("true", "false")
      case Attribute(_, theValue: Boolean, YES_OR_NO) => theValue.fold("yes", "no")
      case Attribute(_, theValue: Boolean, DEFAULT) => theValue.fold(name, "")
      case Attribute(_, _, _) => value.toString
    }

  private def delimitValues(values: Iterable[_], delimiter: String): String =
    values.map(_.toString).mkString(delimiter)

  private def formatCss(valueMap: Map[_, _]): String =
    valueMap.map { (kv: (_, _)) => s"${kv._1}:${kv._2};" }.mkString

  private def quoteAndAddEqual(value: String): String = s"""="$value""""
}
