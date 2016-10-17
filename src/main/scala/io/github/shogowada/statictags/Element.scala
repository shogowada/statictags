package io.github.shogowada.statictags

case class Element
(
    name: String,
    attributes: Iterable[Attribute[_]],
    contents: Seq[Any]
)
