package io.github.shogowada.statictags.generator.app.element

class ElementSpecFactory {

  val nameColumnName = "Element"

  def createSpecs(rawSpec: Map[String, String]): Iterable[ElementSpec] = {
    namesOf(rawSpec)
        .map(name => ElementSpec(name = name))
  }

  def namesOf(rawSpec: Map[String, String]): Iterable[String] = {
    rawSpec.get(nameColumnName).get.split("""\s*,\s*""")
  }
}
