package io.github.shogowada.statictags

case class Element
(
    name: String,
    attributes: Iterable[Any],
    contents: Seq[Any],
    isSupposedToBeEmpty: Boolean = false
) {
  lazy val flattenedAttributes: Iterable[Attribute[_]] = flatten(attributes)
      .map(_.asInstanceOf[Attribute[_]])

  lazy val flattenedContents: Seq[Any] = flatten(contents)
      .toSeq

  private def flatten(items: Iterable[Any]): Iterable[Any] = items
      .flatMap(item => item match {
        case value: Iterable[_] => value
        case _ => Iterable(item)
      })
      .flatMap(item => item match {
        case None => None
        case Some(value) => Some(value)
        case _ => Some(item)
      })

  override def toString: String = {
    val attributesAsString = if (flattenedAttributes.isEmpty) {
      ""
    } else {
      " " + flattenedAttributes.map(_.toString)
          .filterNot(_.isEmpty)
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
