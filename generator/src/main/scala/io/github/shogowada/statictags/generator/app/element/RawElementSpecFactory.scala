package io.github.shogowada.statictags.generator.app.element

class RawElementSpecFactory {

  val nameColumnName = "Element"

  def createSpecs(rawSpec: Map[String, String]): Iterable[RawElementSpec] = {
    namesOf(rawSpec)
        .map(name => RawElementSpec(name = name))
  }

  def namesOf(rawSpec: Map[String, String]): Iterable[String] = {
    rawSpec.get(nameColumnName).get.split("""\s*,\s*""")
  }
}
