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
            splitted.forEach { it ->
                println(it)
                val splitByChar = it.split("").filter { it != "" }.toTypedArray()
                splitByChar.forEachIndexed { i, data ->
                    print("$i $data ")
                }
                println("Length: ${splitByChar.size}")
                result.add(splitByChar)
            }
            return result
        }
    }
}