package io.github.shogowada.statictags

import io.github.shogowada.statictags.StaticTags._

class AttributeTest extends org.scalatest.path.FunSpec {

  override def newInstance = new AttributeTest

  Seq(
    (^.id := "foo") ->
        """id="foo"""",

    (^.`class` := Seq("foo", "bar")) ->
        """class="foo bar"""",

    (^.accept := Seq("foo/bar", "bar/baz")) ->
        """accept="foo/bar,bar/baz"""",

    (^.`for` := "foo") ->
        """for="foo"""",

    (^.`for` := Set("foo", "bar")) ->
        Set("""for="foo bar"""", """for="bar foo""""),

    (^.style := Map(
      "foo" -> "bar",
      "bar" -> "baz",
      "baz" -> 123
    )) ->
        """style="foo:bar;bar:baz;baz:123;"""",

    (^.checked := true) ->
        """checked""",

    (^.checked := false) ->
        """""",

    (^.contenteditable := true) ->
        """contenteditable="true"""",
    (^.contenteditable := false) ->
        """contenteditable="false"""",

    (^.translate := true) ->
        """translate="yes"""",
    (^.translate := false) ->
        """translate="no"""",

    (^.autocomplete := true) ->
        """autocomplete="on"""",
    (^.autocomplete := false) ->
        """autocomplete="off""""
  ).foreach {
    case (attribute, expected) =>
      describe("given I have an attribute " + attribute) {
        describe("when I convert it to string") {
          val actualString = attribute.toString
          expected match {
            case _ if expected.isInstanceOf[Set[_]] => it("then it should convert to any of " + expected) {
              val expectedValues = expected.asInstanceOf[Set[_]]
                  .map(_.toString)
              assert(expectedValues.contains(actualString))
            }
            case _ => it("then it should convert to " + expected) {
              assert(actualString == expected.toString)
            }
          }
        }
      }
  }
}
