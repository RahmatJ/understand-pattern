import board.Board
import cell.Cell
import pattern.Pattern
import reader.Reader

fun main(args: Array<String>) {
    val filePath = "resources/data/pattern.txt"
    println(filePath)
    val pattern = Reader.readFile("data/pattern.txt")

    Pattern.readPattern(pattern)
    println(Cell.getCellData())

//    TODO(Rahmat): Add better pattern

    val board: Board = Board(length = 20, width = 20)
    board.setPattern(Cell.getCellData())
    board.executeCollapse()
    board.getResult()
}