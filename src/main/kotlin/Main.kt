import board.Board
import cell.Cell
import pattern.Pattern

fun main(args: Array<String>) {
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

    val board: Board = Board(length = 10, width = 20)
    board.setPattern(Cell.getCellData())
    board.executeCollapse()
}