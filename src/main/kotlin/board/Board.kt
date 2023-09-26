package board

import cell.Cell
import cell.DataCell

class Board(val length: Int, val width: Int) {

    private var board: MutableList<MutableList<Cell>>
    private lateinit var pattern: Set<DataCell>
    private var visited: MutableSet<Coordinate> = mutableSetOf()


    init {
        board = initiateBoard()
    }

    private fun initiateBoard(): MutableList<MutableList<Cell>> {
        val result = mutableListOf<MutableList<Cell>>()
        for (i in 0 until length) {
            val column = mutableListOf<Cell>()
            for (j in 0 until width) {
                column.add(Cell())
            }
            result.add(column)
        }
        return result
    }

    fun setPattern(pattern: Set<DataCell>) {
        this.pattern = pattern
    }

    fun addVisitedCoordinate(coordinate: Coordinate) {
        visited.add(coordinate)
        println(">> Added $coordinate to visited cell")
    }

    fun getVisitedCoordinate(): MutableSet<Coordinate> {
        return this.visited
    }

    fun collapseSurrounding(coordinate: Coordinate, validCandidate: MutableSet<String>) {
//        TODO(Rahmat): fix this logic, should only remove one cell, but now it replace whole board
//        Check coordinate exist
        if (!board.indices.contains(coordinate.x) || !board[1].indices.contains(coordinate.y)) {
            return
        }
        val currentCell = board[coordinate.x][coordinate.y]
        currentCell.removeInvalidCandidate(validCandidate)
        print(this)

//        if (currentCell.getCandidateId().size == 1 && !getVisitedCoordinate().contains(coordinate)) {
//            addPendingCollapse(coordinate)
//        }
    }

    fun getCell(coordinate: Coordinate): Cell? {
        if (board.indices.contains(coordinate.x) && board[0].indices.contains(coordinate.y)) {
            return board[coordinate.x][coordinate.y]
        }
        return null
    }

    fun printRegion(coordinate: Coordinate) {
        println("Printing region around $coordinate")
        val cValue = "[$coordinate] ${getCell(coordinate) ?: "?"}"
        val nValue = "[${coordinate.getNorth()}] ${getCell(coordinate.getNorth()) ?: "?"}"
        val wValue = "[${coordinate.getWest()}] ${getCell(coordinate.getWest()) ?: "?"}"
        val eValue = "[${coordinate.getEast()}] ${getCell(coordinate.getEast()) ?: "?"}"
        val sValue = "[${coordinate.getSouth()}] ${getCell(coordinate.getSouth()) ?: "?"}"
        println(
            "| x | $nValue | x |\n" +
                    "| $wValue | $cValue | $eValue |\n" +
                    "| x | $sValue | x |"
        )
    }

    fun collapse() {
        while (pendingCollapse.isNotEmpty()) {
            val coordinate = popPendingCollapse()
            val cell = board[coordinate.x][coordinate.y]
            val pickSingleValue: String = cell.getCandidateId().random()
            cell.setCandidateId(mutableSetOf(pickSingleValue))
            printRegion(coordinate)

//        Get Valid Pattern
            val dataCell: DataCell = pattern.find { it.id == pickSingleValue } ?: throw Exception("DataCell not found")

            collapseSurrounding(coordinate.getNorth(), dataCell.northSide)
            collapseSurrounding(coordinate.getEast(), dataCell.eastSide)
            collapseSurrounding(coordinate.getSouth(), dataCell.southSide)
            collapseSurrounding(coordinate.getWest(), dataCell.westSide)

            printRegion(coordinate)
//            Add to visited
            addVisitedCoordinate(coordinate)
        }
    }

    fun executeCollapse() {
        var count = 0
        while (count < 20) {
            val x = (0 until length).random()
            val y = (0 until width).random()
            val coordinate = Coordinate(x, y)

            if (pendingCollapse.contains(coordinate)) {
                continue
            }

            addPendingCollapse(coordinate)
            collapse()

            count++
        }
    }

    override fun toString(): String {
        var result = ""
        board.forEachIndexed { i, column ->
            result += "[ "
            column.forEachIndexed { j, cell ->
                result += "$cell | "
            }
            result += "]\n"
        }
        return result
    }

    data class Coordinate(val x: Int, val y: Int) {
        fun getNorth(): Coordinate {
            return Coordinate(x - 1, y)
        }

        fun getEast(): Coordinate {
            return Coordinate(x, y + 1)
        }

        fun getSouth(): Coordinate {
            return Coordinate(x + 1, y)
        }

        fun getWest(): Coordinate {
            return Coordinate(x, y - 1)
        }

        override fun toString(): String {
            return "[$x, $y]"
        }
    }

    companion object {
        @JvmStatic
        private var pendingCollapse: MutableSet<Coordinate> = mutableSetOf()

        fun popPendingCollapse(): Coordinate {
            val result = pendingCollapse.elementAt(0)
            pendingCollapse.remove(result)
            println(">> Collapsing $result from pending collapse ")
            return result
        }

        fun addPendingCollapse(coordinate: Coordinate) {
            pendingCollapse.add(coordinate)
            println(">> Added $coordinate to pending collapse ")
        }
    }
}