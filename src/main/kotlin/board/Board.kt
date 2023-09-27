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
        for (x in 0 until length) {
            val column = mutableListOf<Cell>()
            for (y in 0 until width) {
                column.add(Cell())
                addLowestEntropyCoordinate(Coordinate(x, y))
            }
            result.add(column)
        }
        return result
    }

    private fun resetBoard() {
        resetLowestEntropyCoordinate()
        setLowestEntropy(99.0)
        board = initiateBoard()
        resetVisitedCoordinate()
    }

    fun setPattern(pattern: Set<DataCell>) {
        this.pattern = pattern
    }

    fun addVisitedCoordinate(coordinate: Coordinate) {
        visited.add(coordinate)
        println(">> Added $coordinate to visited cell")
    }

    fun resetVisitedCoordinate() {
        visited = mutableSetOf()
    }

    fun getVisitedCoordinate(): MutableSet<Coordinate> {
        return this.visited
    }

    fun collapseSurrounding(coordinate: Coordinate, validCandidate: MutableSet<String>): Boolean {
//        Check coordinate exist
        if (!board.indices.contains(coordinate.x) || !board[1].indices.contains(coordinate.y)) {
            return false
        }

        val currentCell = board[coordinate.x][coordinate.y]

        currentCell.removeInvalidCandidate(validCandidate)
//        TODO(Rahmat): Think about how to do backtrack for this case
        if (currentCell.getCandidateId().isEmpty()) {
//                let's reset board when we found empty element
            return true
        }

//        if (currentCell.getCandidateId().size == 1 && !getVisitedCoordinate().contains(coordinate)) {
//            addPendingCollapse(coordinate)
//        }

        println(this)
        return false
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

    fun recalculateEntropy() {
        println("Recalculating Entropy...")
        setLowestEntropy(1.0)
        board.forEachIndexed { x, data ->
            data.forEachIndexed innerLoop@{ y, cell ->
//                if (cell.getCandidateId().size == Cell.getBaseCellProbability().size) {
//                    return@innerLoop
//                }
                val coordinate = Coordinate(x, y)
                if (visited.contains(coordinate)) {
                    return@innerLoop
                }
                val cellEntropy = cell.getEntropy()
                if (cellEntropy < lowestEntropy) {
                    setLowestEntropy(cellEntropy)
                    setLowestEntropyCoordinate(mutableSetOf(coordinate))
                    return@innerLoop
                }
                if (cellEntropy == lowestEntropy) {
                    addLowestEntropyCoordinate(coordinate)
                }
            }
        }
        println("LowestEntropy: $lowestEntropy")
        println("LowestEntropyCell: $arrayOfLowestEntropy")
    }

    fun collapse() {
        var shouldRecalculate = true
        while (pendingCollapse.isNotEmpty()) {
            val coordinate = popPendingCollapse()
            val cell = board[coordinate.x][coordinate.y]
            /*            TODO(Rahmat): Should we store random in somewhere else as baseline of backtrack, might be using stack
                        store current coordinate, possible solution, visited value
                        if all visited value,visited, then pop new level of stack
                        if pop new, should delete also from visited array
            */
            val pickSingleValue: String = cell.getCandidateId().random()
            /* in here we should push new level of stack or update the top
               if top stack == coordinate, that means we reach contradict, and should pick other data
            */
            cell.setCandidateId(mutableSetOf(pickSingleValue))

//        Get Valid Pattern
            val dataCell: DataCell = pattern.find { it.id == pickSingleValue } ?: throw Exception("DataCell not found")
            println("Center: ${cell.getCandidateId().elementAt(0)}")
            var shouldReset: Boolean = false
            println("Collapsing North... ")
            shouldReset = collapseSurrounding(coordinate.getNorth(), dataCell.northSide)
            if (shouldReset) {
                println("Resetting the board !!! ")
                resetBoard()
                shouldRecalculate = false
                return
            }
            println("Collapsing North... Done !!!")
            println("Collapsing East...")
            shouldReset = collapseSurrounding(coordinate.getEast(), dataCell.eastSide)
            if (shouldReset) {
                println("Resetting the board !!! ")
                resetBoard()
                shouldRecalculate = false
                return
            }
            println("Collapsing East... Done !!!")
            println("Collapsing South...")
            shouldReset = collapseSurrounding(coordinate.getSouth(), dataCell.southSide)
            if (shouldReset) {
                println("Resetting the board !!! ")
                resetBoard()
                shouldRecalculate = false
                return
            }
            println("Collapsing South... Done !!!")
            println("Collapsing West...")
            shouldReset = collapseSurrounding(coordinate.getWest(), dataCell.westSide)
            if (shouldReset) {
                println("Resetting the board !!! ")
                resetBoard()
                shouldRecalculate = false
                return
            }
            println("Collapsing West... Done !!!")


//            Add to visited
            addVisitedCoordinate(coordinate)
        }
        if (shouldRecalculate) {
            recalculateEntropy()
        }

    }

    fun executeCollapse() {
        var count = 0

        while (arrayOfLowestEntropy.size > 0) {
            println("Count: $count")
            val coordinate = arrayOfLowestEntropy.random()
            arrayOfLowestEntropy.remove(coordinate)

            if (pendingCollapse.contains(coordinate)) {
                continue
            }

            addPendingCollapse(coordinate)
            collapse()

            count++
        }
    }

    fun getResult() {
        var result = ""
        board.forEach { data ->
            data.forEach { cell ->
                result += cell.getCandidateId().elementAt(0)
            }
            result += "\n"
        }

        print(result)
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

        @JvmStatic
        private var lowestEntropy: Double = 1.0

        fun setLowestEntropy(entropy: Double) {
            lowestEntropy = entropy
        }

        fun getLowestEntropy(): Double {
            return lowestEntropy
        }

        @JvmStatic
        private var arrayOfLowestEntropy: MutableSet<Coordinate> = mutableSetOf()

        fun removeLowestEntropyCoordinate(coordinate: Coordinate) {
            arrayOfLowestEntropy.remove(coordinate)
        }

        fun resetLowestEntropyCoordinate() {
            arrayOfLowestEntropy = mutableSetOf()
        }

        fun addLowestEntropyCoordinate(coordinate: Coordinate) {
            arrayOfLowestEntropy.add(coordinate)
        }

        fun setLowestEntropyCoordinate(coordinates: MutableSet<Coordinate>) {
            arrayOfLowestEntropy = coordinates
        }
    }
}