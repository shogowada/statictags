package io.github.shogowada.statictags

import io.github.shogowada.statictags.StaticTags._
import org.scalatest.funspec.PathAnyFunSpec

import scala.language.implicitConversions

class ElementTest extends PathAnyFunSpec {

  override def newInstance = new ElementTest

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
        """<div id="foo"><p>This is a paragraph.</p>This is just a text.<br/></div>""",

    <.div()(
      "This is a text.",
      "This is another text."
    ) ->
        """<div>This is a text. This is another text.</div>""",

    <.div()(
      "When the element is a sequence,",
      Seq("it will be", "flattened.")
    ) ->
        """<div>When the element is a sequence, it will be flattened.</div>""",

    <.div()(
      "When the element is an option,",
      None,
      Some("it will be flattened.")
    ) ->
        """<div>When the element is an option, it will be flattened.</div>""",

    <.input(
      ^.`type`.text,
      Seq(^.placeholder := "foo", Option(^.disabled := true), Seq(^.value := "bar"))
    )() ->
        """<input type="text" placeholder="foo" disabled value="bar"/>""",

    <.div(^.id := "foo", Seq(^.width := 100, ^.height := 200))() ->
        """<div id="foo" width="100" height="200"></div>""",

    <.div(^.id := "foo", None, Option(^.width := 100))() ->
        """<div id="foo" width="100"></div>""",

    <.div(^("foo") := "bar", ^("bar") := true, ^("baz") := false)() ->
        """<div foo="bar" bar></div>""",

    <("foo")(^("bar") := "bar")("baz") ->
        """<foo bar="bar">baz</foo>"""
  ).foreach { case (element: Element, expectedString: String) =>
    describe("given I am using a standard element like " + element) {
      it(s"then it should output HTML $expectedString when converted to string") {
        assert(element.toString == expectedString)
      }
    }
  }

  describe("given I have a custom StaticTags") {

    case class MyElementWrapper(element: Element)

    object MyStaticTags extends StaticTags {

      class MyElements extends Elements {
        lazy val myElement = ElementSpec(name = "myElement")
      }

      class MyAttributes extends Attributes {

        case class MyAttributeSpec(name: String) extends AttributeSpec {
          def :=(value: Int) = {
            // Create an attribute with := operator
            Attribute[Int](name = name, value = value)
          }

          lazy val one = this := 1 // Or have an attribute as constant

          def sumOf(lhs: Int, rhs: Int) = {
            // Or create an attribute with custom function
            this := (lhs + rhs)
          }
        }

        lazy val myAttribute = MyAttributeSpec("myAttribute")
      }

      override val < = new MyElements
      override val ^ = new MyAttributes

      implicit def asMyElementWrapper(element: Element): MyElementWrapper = {
        // You can implicitly convert it into whatever you want!
        MyElementWrapper(element)
      }
    }

    describe("when I create elements with it") {
      import MyStaticTags._
      // This imports all of your custom code, including implicit conversion

      val element = <.div(
        ^.myAttribute.one
      )(
        <.myElement(
          ^.`class` := Seq("my-element"),
          ^.myAttribute := 2
        )(
          <.p(
            ^.myAttribute.sumOf(1, 2)
          )("How easy it is to extend the StaticTags!")
        )
      )

      it("then it can be converted to HTML") {
        assert(element.toString == """<div myAttribute="1"><myElement class="my-element" myAttribute="2"><p myAttribute="3">How easy it is to extend the StaticTags!</p></myElement></div>""")
      }

      it("then it can implicitly convert to my custom element") {
        val myElementWrapper: MyElementWrapper = element
        assert(myElementWrapper == MyElementWrapper(element))
      }
    }
  }
}
