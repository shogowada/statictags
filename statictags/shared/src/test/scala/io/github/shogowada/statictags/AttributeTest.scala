package io.github.shogowada.statictags

import io.github.shogowada.statictags.StaticTags._

class AttributeTest extends org.scalatest.path.FunSpec {
  Seq(
    (^.id := "foo") -> """id="foo"""",
    (^.`class` := Seq("foo", "bar")) -> """class="foo bar"""",
    (^.accept := Seq("foo/bar", "bar/baz")) -> """accept="foo/bar,bar/baz"""",
    (^.style := Map(
      "foo" -> "bar",
      "bar" -> "baz",
      "baz" -> 123
    )) ->
        """style="foo:bar;bar:baz;baz:123;"""",
    (^.checked := true, """checked"""),
    (^.checked := false, """"""),
    (^.contenteditable := true, """contenteditable="true""""),
    (^.contenteditable := false, """contenteditable="false""""),
    (^.translate := true, """translate="yes""""),
    (^.translate := false, """translate="no""""),
    (^.autocomplete := true, """autocomplete="on""""),
    (^.autocomplete := false, """autocomplete="off"""")
  ).foreach { case (attribute, expectedString) =>
    describe("given I have an attribute " + attribute) {
      describe("when I convert it to string") {
        val actualString = attribute.toString
        it("then it convert to " + expectedString) {
          assert(actualString == expectedString)
        }
      }
    }
  }
}
