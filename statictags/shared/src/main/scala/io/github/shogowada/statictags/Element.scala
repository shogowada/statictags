package io.github.shogowada.statictags

case class Element
(
    name: String,
    attributes: Iterable[Attribute[_]],
    contents: Seq[Any],
    isSupposedToBeEmpty: Boolean = false
) {
  val flattenedContents = contents
      .flatMap(content => content match {
        case value: Seq[_] => value
        case _ => Seq(content)
      })
      .flatMap(content => content match {
        case None => None
        case Some(value) => Some(value)
        case _ => Some(content)
      })

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
    for (content <- flattenedContents) {
      if (!content.isInstanceOf[Element] && maybePreviousContent.exists(!_.isInstanceOf[Element])) {
        contentStringBuilder.append(" ")
      }
      contentStringBuilder.append(content.toString)

      maybePreviousContent = Some(content)
    }

    if (isSupposedToBeEmpty && flattenedContents.isEmpty) {
      s"""<$name$attributesAsString/>"""
    } else {
      s"""<$name$attributesAsString>${contentStringBuilder.toString}</$name>"""
    }
  }
}
