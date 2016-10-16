package io.github.shogowada.statictags.generator.app

import java.io.File

object Utils {
  def getFile(fileName: String): File = {
    val classLoader = getClass.getClassLoader
    new File(classLoader.getResource(fileName).getFile)
  }
}
