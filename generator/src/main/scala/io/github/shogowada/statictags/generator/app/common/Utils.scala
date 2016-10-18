package io.github.shogowada.statictags.generator.app.common

import java.io.File

object Utils {
  def getFile(fileName: String): File = {
    val classLoader = getClass.getClassLoader
    new File(classLoader.getResource(fileName).getFile)
  }

  def getSimpleClassName(clazz: Class[_]): String = {
    clazz.getSimpleName.replaceFirst("""\$$""", "")
  }
}
