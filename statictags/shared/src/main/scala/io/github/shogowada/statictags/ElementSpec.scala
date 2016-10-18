package io.github.shogowada.statictags

case class ElementSpec(name: String) {
  def apply(attributes: Attribute[_]*)(contents: Any*): Element = {
    Element(
      name = name,
      attributes = attributes,
      contents = contents
    )
  }

  def empty: Element = {
    apply()()
  }
}
