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

    val contentStringBuilder = new StringBuilder
    var maybePreviousContent: Option[Any] = None
    for (content <- contents) {
      if (!content.isInstanceOf[Element] && maybePreviousContent.exists(!_.isInstanceOf[Element])) {
        contentStringBuilder.append(" ")
      }
      contentStringBuilder.append(content.toString)

      maybePreviousContent = Some(content)
    }

    if (isSupposedToBeEmpty && contents.isEmpty) {
      s"""<$name$attributesAsString/>"""
    } else {
      s"""<$name$attributesAsString>${contentStringBuilder.toString}</$name>"""
    }
  }
}
