# Static Tags
master: [![Build Status](https://travis-ci.org/shogowada/statictags.svg?branch=master)](https://travis-ci.org/shogowada/statictags)
develop: [![Build Status](https://travis-ci.org/shogowada/statictags.svg?branch=develop)](https://travis-ci.org/shogowada/statictags)

|Target|Artifact|
|---|---|
|Scala|```"io.github.shogowada" %% "statictags" % "1.0.0-SNAPSHOT"```|
|Scala JS|```"io.github.shogowada" %%% "statictags" % "1.0.0-SNAPSHOT"```|

Static Tags makes it easy for you to write HTML in Scala.

All Static Tags element can be converted to HTML via ```toString``` method.

```scala
class ToUpperCase(text: String) {
  override def toString: String = text.toUpperCase
}

val example = <.div(^.id := "example")(
  <.p(^.`class` := Seq("main-paragraph", "main-paragraph-bold"))("This is a paragraph."),
  "This is a text.",
  new ToUpperCase("Anything other than Static Tags element is converted to string.")
)

println(example)
```

The above code will output the minified version of the following HTML.

```html
<div id="example">
  <p class="main-paragraph main-paragraph-bold">This is a paragraph.</p>
  This is a text.
  ANYTHING OTHER THAN STATIC TAGS ELEMENT IS CONVERTED TO STRING.
</div>
```

Note that when you use Static Tags, for example, you don't need to worry if the ```class``` attribute value was space delimited or comma delimited. You can just give it a collection of strings, and Static Tags takes care the rest for you. This is one of many advantages of using Static Tags!

## Extending Static Tags

You can add your own elements and attributes, as well as your own deserializer. And it's super easy!

```scala
class MyElements extends StaticTags.Elements {
  lazy val myCustomElement = ElementSpec("myCustomElement")
}

class MyAttributes extends StaticTags.Attributes {
  case class MyCustomAttribute(name: String) extends AttributeSpec {
    def :=(value: MyCustomClass) = Attribute[String](name = name, value = value.toString)
  }

  lazy val myCustomStringAttribute = StringAttributeSpec("myCustomStringAttribute")
}

object MyStaticTags extends StaticTags {
  override val < = new MyElements()
  override val ^ = new MyAttributes()
  
  implicit def(element: Element): MyElement = {
    // Deserialize Static Tags Element to your own MyElement here
  }
}
```

If you had code like above, you can use it like below.

```scala
import MyStaticTags._

class MyRenderer {
  def render: MyElement = {
    <.myCustomElement(
      ^.myCustomAttribute := MyCustomClass(),
      ^.myCustomStringAttribute := "My custom string attribute value"
    )("Oh my, it's so easy!")
  }
}
```

## Development

### How to generate statictags code

1. Run ```sbt generator/run``` from the project root.
