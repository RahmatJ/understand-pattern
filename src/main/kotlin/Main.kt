import board.Board
import cell.Cell
import pattern.Pattern
import reader.Reader

fun main(args: Array<String>) {
    val filePath = "resources/data/pattern.txt"
    println(filePath)
    val readFile = Reader.readFile("data/pattern.txt")

//    TODO(Rahmat): Need to create readable pattern from input file

    val pattern = mutableListOf<Array<String>>()
    pattern.add(arrayOf("4", "4", "4", "4", "4", "4", "2", "2", "3", "3"))
    pattern.add(arrayOf("0", "0", "0", "0", "0", "0", "0", "0", "1", "3"))
    pattern.add(arrayOf("0", "0", "0", "0", "0", "0", "0", "0", "1", "3"))
    pattern.add(arrayOf("4", "4", "4", "4", "4", "4", "4", "2", "2", "3"))
    pattern.add(arrayOf("4", "4", "4", "4", "4", "4", "4", "2", "2", "3"))
    pattern.add(arrayOf("4", "10", "5", "5", "5", "5", "4", "2", "2", "3"))
    pattern.add(arrayOf("4", "6", "4", "4", "4", "4", "4", "2", "2", "3"))
    pattern.add(arrayOf("4", "6", "4", "4", "4", "4", "4", "2", "3", "3"))
    pattern.add(arrayOf("4", "9", "5", "5", "5", "5", "4", "2", "3", "3"))
    pattern.add(arrayOf("4", "4", "4", "4", "4", "4", "4", "2", "2", "3"))
    pattern.add(arrayOf("4", "4", "5", "5", "5", "7", "4", "2", "3", "3"))
    pattern.add(arrayOf("4", "4", "4", "4", "4", "6", "4", "2", "3", "3"))

    Pattern.readPattern(pattern)
    println(Cell.getCellData())

    val board: Board = Board(length = 5, width = 5)
    board.setPattern(Cell.getCellData())
    board.executeCollapse()

    println(board)
}