package io.github.shogowada.statictags.generator.app.common

class FieldNameFactory {

  val reservedWords = Set(
    "class",
    "for",
    "object",
    "type",
    "var"
  )

  def create(name: String): String = {
    if (isInvalidName(name)) {
      return s"`$name`"
    }
    name
  }

  private def isInvalidName(name: String): Boolean = {
    if (name.contains("-")) {
      return true
    }
    reservedWords.contains(name)
  }
}
