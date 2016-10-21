package io.github.shogowada.statictags

import io.github.shogowada.statictags.StaticTags._

class ElementTest
    extends org.scalatest.path.FunSpec {

  Seq(
    <.div()() ->
        """<div></div>""",

    <.div(^.id := "foo", ^.`class` := Seq("bar", "baz"))() ->
        """<div id="foo" class="bar baz"></div>""",

    <.div(^.id := "foo")(
      <.p()("This is a paragraph."),
      "This is just a text.",
      <.br.empty
    ) ->
        """<div id="foo"><p>This is a paragraph.</p>This is just a text.<br/></div>"""
  ).foreach { case (element: Element, expectedString: String) =>
    describe("given I am using a standard element like " + element) {
      it(s"then it should output HTML $expectedString when converted to string") {
        assert(element.toString == expectedString)
      }
    }
  }
}
