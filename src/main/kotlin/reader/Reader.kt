package reader

import java.io.File
import java.io.InputStream

class Reader {
    companion object {
        fun readFile(filePath: String): MutableList<Array<String>> {
            val resourceBaseURI = "src/main/resources"
            val inputFile: InputStream = File("$resourceBaseURI/$filePath").inputStream()
            val inputString = inputFile.bufferedReader().use { it.readText() }
            val splitted = inputString.split("\n")
            val result = mutableListOf<Array<String>>()
            splitted.forEach {
                println(it)
                val splitByChar = it.split("").toTypedArray()
                result.add(splitByChar)
            }
            return result
        }
    }
}