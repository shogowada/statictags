package io.github.shogowada.statictags

import java.util.regex.Pattern

trait AttributeSpec {
  val name: String
}

case class BooleanAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: Boolean): Attribute[Boolean] = {
    Attribute[Boolean](name = name, value = value)
  }
}

case class CommaSeparatedIntegerAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: Int): Attribute[Int] = {
    Attribute[Int](name = name, value = value)
  }

  def :=(value: Iterable[Int]): Attribute[String] = {
    Attribute[String](
      name = name,
      value = value.map(_.toString).reduce((lhs, rhs) => {
        lhs + "," + rhs
      })
    )
  }
}

case class CommaSeparatedStringAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: String): Attribute[String] = {
    Attribute[String](name = name, value = value)
  }

  def :=(value: Iterable[String]): Attribute[String] = {
    this := value.reduce((lhs, rhs) => {
      lhs + "," + rhs
    })
  }
}

case class BigDecimalAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: BigDecimal): Attribute[BigDecimal] = {
    Attribute[BigDecimal](name = name, value = value)
  }
}

case class BigDecimalOrAnyAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: BigDecimal): Attribute[BigDecimal] = {
    Attribute[BigDecimal](name = name, value = value)
  }

  def any: Attribute[String] = {
    Attribute[String](name = name, value = "any")
  }
}

case class IntegerAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: Int): Attribute[Int] = {
    Attribute[Int](name = name, value = value)
  }
}

case class OrderedSetOfUniqueSpaceSeparatedStringAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: String): Attribute[String] = {
    Attribute[String](name = name, value = value)
  }

  def :=(value: Seq[String]): Attribute[String] = {
    this := value.reduce((lhs, rhs) => {
      lhs + " " + rhs
    })
  }
}

case class RegexAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: Pattern): Attribute[Pattern] = {
    Attribute[Pattern](name = name, value = value)
  }
}

case class SetOfUniqueSpaceSeparatedStringAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: Set[String]): Attribute[Set[String]] = {
    Attribute[Set[String]](name = name, value = value)
  }
}

case class SpaceSeparatedStringAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: String): Attribute[String] = {
    Attribute[String](name = name, value = value)
  }

  def :=(value: Iterable[String]): Attribute[String] = {
    this := value.reduce((lhs, rhs) => {
      lhs + " " + rhs
    })
  }
}

case class StringAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: String): Attribute[String] = {
    Attribute[String](name = name, value = value)
  }
}

case class StringBooleanAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: Boolean): Attribute[String] = {
    Attribute[String](
      name = name,
      value match {
        case true => "true"
        case false => "false"
      }
    )
  }
}

case class StringBooleanOnOffAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: Boolean): Attribute[String] = {
    Attribute[String](
      name = name,
      value match {
        case true => "on"
        case false => "off"
      }
    )
  }
}

case class StringBooleanYesNoAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: Boolean): Attribute[Boolean] = {
    Attribute[Boolean](name = name, value = value)
  }
}

case class ValueAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: String): Attribute[String] = {
    Attribute[String](name = name, value = value)
  }

  def :=(value: Int): Attribute[Int] = {
    Attribute[Int](name = name, value = value)
  }

  def :=(value: BigDecimal): Attribute[BigDecimal] = {
    Attribute[BigDecimal](name = name, value = value)
  }
}
