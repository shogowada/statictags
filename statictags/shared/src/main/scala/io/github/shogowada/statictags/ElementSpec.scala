package io.github.shogowada.statictags

case class ElementSpec(name: String, isSupposedToBeEmpty: Boolean = false) {
  def apply(attributes: Any*)(contents: Any*): Element = {
    Element(
      name = name,
      attributes = attributes,
      contents = contents,
      isSupposedToBeEmpty = isSupposedToBeEmpty
    )
  }

  def empty: Element = {
    apply()()
  }
}
