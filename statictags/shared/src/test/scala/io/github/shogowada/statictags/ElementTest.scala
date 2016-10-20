package io.github.shogowada.statictags

import io.github.shogowada.statictags.StaticTags._

class ElementTest
    extends org.scalatest.path.FunSpec {

  describe("given I have a single element without attributes") {
    val element = <.div()()

    it("then it should output HTML when converted to string") {
      assert(element.toString == "<div></div>")
    }
  }

  describe("given I have a single element with some attributes") {
    val element = <.div(
      ^.id := "foo",
      ^.`class` := Seq("bar", "baz")
    )()

    it("then it should output HTML when converted to string") {
      assert(element.toString == """<div id="foo" class="bar baz"></div>""")
    }
  }
}
