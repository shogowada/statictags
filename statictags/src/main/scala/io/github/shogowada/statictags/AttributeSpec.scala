package io.github.shogowada.statictags

import java.util.regex.Pattern

import io.github.shogowada.statictags.AttributeValueType._

trait AttributeSpec {
  val name: String
}

case class AutoCompleteAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: String) = Attribute(name = name, value = value)

  def :=(value: Boolean): Attribute[Boolean] =
    value match {
      case true => on
      case false => off
    }

  lazy val on = Attribute[Boolean](name = name, value = true, valueType = ON_OR_OFF)
  lazy val off = Attribute[Boolean](name = name, value = false, valueType = ON_OR_OFF)
}

case class BooleanAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: Boolean): Attribute[Boolean] = Attribute[Boolean](name = name, value = value)
}

case class CommaSeparatedIntegerAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: Int): Attribute[Iterable[Int]] = this := Iterable(value)

  def :=(value: Iterable[Int]): Attribute[Iterable[Int]] =
    Attribute[Iterable[Int]](name = name, value = value, valueType = COMMA_SEPARATED)
}

case class CommaSeparatedStringAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: String): Attribute[Iterable[String]] = this := Iterable(value)

  def :=(value: Iterable[String]): Attribute[Iterable[String]] =
    Attribute[Iterable[String]](name = name, value = value, valueType = COMMA_SEPARATED)
}

case class BigDecimalAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: BigDecimal): Attribute[BigDecimal] =
    Attribute[BigDecimal](name = name, value = value)
}

case class BigDecimalOrAnyAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: BigDecimal): Attribute[BigDecimal] =
    Attribute[BigDecimal](name = name, value = value)

  lazy val any = Attribute[String](name = name, value = "any")
}

case class CssAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: Map[String, _]): Attribute[Map[String, _]] =
    Attribute[Map[String, _]](name = name, value = value, valueType = CSS)
}

case class DynamicAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: String) = Attribute[String](name = name, value = value)

  def :=(value: Boolean) = Attribute[Boolean](name = name, value = value)
}

case class ForAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: String) = Attribute[String](name = name, value = value)

  def :=(value: Set[String]) = Attribute[Set[String]](name = name, value = value, valueType = SPACE_SEPARATED)
}

case class InputModeAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: String) = Attribute[String](name, value)

  lazy val email = this := "email"
  lazy val `full-width-latin` = this := "full-width-latin"
  lazy val kana = this := "kana"
  lazy val katakana = this := "katakana"
  lazy val latin = this := "latin"
  lazy val `latin-name` = this := "latin-name"
  lazy val `latin-prose` = this := "latin-prose"
  lazy val numeric = this := "numeric"
  lazy val tel = this := "tel"
  lazy val url = this := "url"
  lazy val verbatim = this := "verbatim"
}

case class IntegerAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: Int): Attribute[Int] = Attribute[Int](name = name, value = value)
}

case class OrderedSetOfUniqueSpaceSeparatedStringAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: String): Attribute[String] = Attribute[String](name = name, value = value)

  def :=(value: Seq[String]): Attribute[Seq[String]] =
    Attribute[Seq[String]](name = name, value = value, valueType = SPACE_SEPARATED)
}

case class RegexAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: String): Attribute[String] = Attribute[String](name = name, value = value)

  def :=(value: Pattern): Attribute[String] = this := value.pattern()
}

case class SetOfUniqueSpaceSeparatedStringAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: String): Attribute[Set[String]] = this := Set(value)
  def :=(value: Set[String]): Attribute[Set[String]] =
    Attribute[Set[String]](name = name, value = value, valueType = SPACE_SEPARATED)
}

case class SpaceSeparatedStringAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: String): Attribute[Iterable[String]] = this := Seq(value)
  def :=(value: Iterable[String]): Attribute[Iterable[String]] =
    Attribute[Iterable[String]](name = name, value = value, valueType = SPACE_SEPARATED)
}

case class StringAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: String): Attribute[String] = Attribute[String](name = name, value = value)
}

case class TrueOrFalseAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: Boolean): Attribute[Boolean] =
    value match {
      case true => this.`true`
      case false => this.`false`
    }

  lazy val `true` = Attribute[Boolean](name = name, value = true, valueType = TRUE_OR_FALSE)
  lazy val `false` = Attribute[Boolean](name = name, value = false, valueType = TRUE_OR_FALSE)
}

case class TypeAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: String) = Attribute[String](name, value)

  lazy val button = this := "button"
  lazy val checkbox = this := "checkbox"
  lazy val color = this := "color"
  lazy val context = this := "context"
  lazy val date = this := "date"
  lazy val `datetime-local` = this := "datetime-local"
  lazy val email = this := "email"
  lazy val file = this := "file"
  lazy val hidden = this := "hidden"
  lazy val image = this := "image"
  lazy val list = this := "list"
  lazy val month = this := "month"
  lazy val number = this := "number"
  lazy val password = this := "password"
  lazy val radio = this := "radio"
  lazy val range = this := "range"
  lazy val reset = this := "reset"
  lazy val search = this := "search"
  lazy val submit = this := "submit"
  lazy val tel = this := "tel"
  lazy val text = this := "text"
  lazy val time = this := "time"
  lazy val toolbar = this := "toolbar"
  lazy val url = this := "url"
  lazy val week = this := "week"
}

case class ValueAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: String): Attribute[String] = Attribute[String](name = name, value = value)

  def :=(value: Int): Attribute[Int] = Attribute[Int](name = name, value = value)

  def :=(value: BigDecimal): Attribute[BigDecimal] = Attribute[BigDecimal](name = name, value = value)
}

case class WModeAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: String) = Attribute[String](name, value)

  lazy val direct = this := "direct"
  lazy val gpu = this := "gpu"
  lazy val opaque = this := "opaque"
  lazy val transparent = this := "transparent"
  lazy val window = this := "window"
}

case class YesOrNoAttributeSpec(name: String) extends AttributeSpec {
  def :=(value: Boolean): Attribute[Boolean] =
    value match {
      case true => yes
      case false => no
    }

  lazy val yes = Attribute[Boolean](name = name, value = true, valueType = YES_OR_NO)
  lazy val no = Attribute[Boolean](name = name, value = false, valueType = YES_OR_NO)
}
