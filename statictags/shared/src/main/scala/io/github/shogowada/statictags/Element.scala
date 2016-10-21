package io.github.shogowada.statictags

case class Element
(
    name: String,
    attributes: Iterable[Attribute[_]],
    contents: Seq[Any],
    isSupposedToBeEmpty: Boolean = false
) {
  override def toString: String = {
    val attributesAsString = if (attributes.isEmpty) {
      ""
    }
    else {
      " " + attributes.map(_.toString)
          .reduce((lhs, rhs) => lhs + " " + rhs)
    }

    val contentsAsString = contents.map(_.toString)
        .reduceOption((lhs, rhs) => lhs + rhs)
        .getOrElse("")

    if (isSupposedToBeEmpty && contents.isEmpty) {
      s"""<$name$attributesAsString/>"""
    } else {
      s"""<$name$attributesAsString>$contentsAsString</$name>"""
    }
  }
}
