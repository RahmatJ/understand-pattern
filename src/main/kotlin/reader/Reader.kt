package reader

import java.io.File
import java.io.InputStream

class Reader {
    companion object {
        fun readFile(filePath: String) {
            val resourceBaseURI = "src/main/resources"
            val inputFile: InputStream = File("$resourceBaseURI/$filePath").inputStream()
//            val inputFile: InputStream = Reader::class.java.getResourceAsStream(filePath)
//                ?: throw Exception("Unable tor read resources")
            val inputString = inputFile.bufferedReader().use { it.readText() }
            println(inputString)
        }
    }
}