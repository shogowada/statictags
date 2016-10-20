package io.github.shogowada.statictags

case class Element
(
    name: String,
    attributes: Iterable[Attribute[_]],
    contents: Seq[Any]
) {
  override def toString: String = {
    val attributesAsString = if (attributes.isEmpty) {
      ""
    }
    else {
      " " + attributes.map(_.toString).reduce((lhs, rhs) => lhs + " " + rhs)
    }

    s"""<$name$attributesAsString></$name>"""
  }
}
