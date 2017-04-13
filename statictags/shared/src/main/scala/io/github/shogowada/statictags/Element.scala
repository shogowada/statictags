package io.github.shogowada.statictags

import scala.collection.mutable.ArrayBuffer

object Element {
  def flattenAttributes(attributes: Iterable[Any]): Iterable[Attribute[_]] = flatten(attributes)
      .map(_.asInstanceOf[Attribute[_]])

  def flattenContents(contents: Seq[Any]): Seq[Any] = flatten(contents).toSeq

  def flatten(items: Iterable[Any]): Iterable[Any] = {
    val buffer = ArrayBuffer[Any]()
    items.foreach {
      case items: Iterable[_] => buffer ++= flatten(items)
      case Some(item) => buffer += item
      case None =>
      case item => buffer += item
    }
    buffer
  }
}

case class Element
(
    name: String,
    attributes: Iterable[Any],
    contents: Seq[Any],
    isSupposedToBeEmpty: Boolean = false
) {

  import Element._

  lazy val flattenedAttributes: Iterable[Attribute[_]] = flattenAttributes(attributes)

  lazy val flattenedContents: Seq[Any] = flattenContents(contents)

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
