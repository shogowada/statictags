package io.github.shogowada.statictags.generator.app.element

import org.scalatest.Matchers

class RawElementSpecFactoryTest
    extends org.scalatest.path.FunSpec
        with Matchers {
  val target = new RawElementSpecFactory

  describe("given the raw spec contains single element") {
    val name = "foo"
    val rawSpec = Map(
      "Element" -> name
    )

    it("then it should create single element") {
      target.createSpecs(rawSpec).toList should equal(Seq(
        RawElementSpec(name = name)
      ))
    }
  }

  describe("given the raw spec contains multiple elements") {
    val name1 = "foo"
    val name2 = "bar"
    val rawSpec = Map(
      "Element" -> s"$name1, $name2"
    )

    it("then it should create multiple elements") {
      target.createSpecs(rawSpec).toList should equal(Seq(
        RawElementSpec(name = name1),
        RawElementSpec(name = name2)
      ))
    }
  }
}