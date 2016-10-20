package io.github.shogowada.statictags

import io.github.shogowada.statictags.AttributeValueType.{AttributeValueType, DEFAULT, SPACE_SEPARATED}

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
  override def toString: String = {
    val valueAsString: String = this match {
      case Attribute(_, values: Iterable[_], SPACE_SEPARATED) => values.map(_.toString).reduce((lhs, rhs) => lhs + " " + rhs)
      case Attribute(_, _, _) => value.toString
    }
    s"""$name="$valueAsString""""
  }
}
