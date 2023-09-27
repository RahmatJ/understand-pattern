import board.Board
import cell.Cell
import pattern.Pattern
import reader.Reader

fun main(args: Array<String>) {
//    val filePath = "data/pattern.txt"
//    val filePath = "data/simple-pattern.txt"
    val filePath = "data/very-simple-pattern.txt"
    println(filePath)
    val pattern = Reader.readFile(filePath)

    Pattern.readPattern(pattern)
    println(Cell.getCellData())

    val board: Board = Board(length = 20, width = 20)
    board.setPattern(Cell.getCellData())
    board.executeCollapse()
    board.getResult()
}