package io.github.shogowada.statictags

import io.github.shogowada.statictags.AttributeValueType._

object AttributeValueType {

  sealed trait AttributeValueType

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
    def map[T](trueValue: T, falseValue: T): T = {
      if (value) {
        trueValue
      } else {
        falseValue
      }
    }
  }

  override def toString: String = {
    this match {
      case Attribute(_, values: Iterable[_], COMMA_SEPARATED) => name + quoteAndAddEqual(delimitValues(values, ","))
      case Attribute(_, values: Iterable[_], SPACE_SEPARATED) => name + quoteAndAddEqual(delimitValues(values, " "))
      case Attribute(_, valueMap: Map[_, _], CSS) => name + quoteAndAddEqual(formatCss(valueMap))
      case Attribute(_, theValue: Boolean, ON_OR_OFF) => name + quoteAndAddEqual(theValue.map("on", "off"))
      case Attribute(_, theValue: Boolean, TRUE_OR_FALSE) => name + quoteAndAddEqual(theValue.map("true", "false"))
      case Attribute(_, theValue: Boolean, YES_OR_NO) => name + quoteAndAddEqual(theValue.map("yes", "no"))
      case Attribute(_, theValue: Boolean, _) => theValue.map(name, "")
      case Attribute(_, _, _) => name + quoteAndAddEqual(value.toString)
    }
  }

  private def delimitValues(values: Iterable[_], delimiter: String): String = {
    values.map(_.toString).reduce((lhs, rhs) => lhs + delimiter + rhs)
  }

  private def formatCss(valueMap: Map[_, _]): String = {
    valueMap.map(pair => pair._1.toString + ":" + pair._2.toString + ";")
        .reduce((lhs, rhs) => lhs + rhs)
  }

  private def quoteAndAddEqual(value: String): String = {
    s"""="$value""""
  }
}
