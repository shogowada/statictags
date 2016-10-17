package io.github.shogowada.statictags

case class AttributeSpec[Value](name: String) {
  def :=(value: Value): Attribute[Value] = {
    Attribute(name = name, value = value)
  }

  def empty: Attribute[Unit] = {
    Attribute(name = name, value = Unit)
  }
}
