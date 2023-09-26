package pattern

import board.Board
import cell.Cell
import cell.DataCell

class Pattern() {
    companion object {
        fun readPattern(strings: List<Array<String>>, printRegion: Boolean = false) {
//            create window 3x3
            println("Base Indices: ${strings.indices}")
            for (i in strings.indices) {
                val data = strings[i]
                println("Column Indices: ${data.indices}")
                for (j in data.indices) {
//                    | x | N | x |
//                    | W | C | E |
//                    | x | S | x |
                    val c: String = strings[i][j]
                    val cellData = DataCell(c)

                    var north: String? = null
                    if (strings.indices.contains(i - 1)) {
                        north = strings[i - 1][j]
                        cellData.northSide.add(north)
                    }

                    var west: String? = null
                    if (data.indices.contains(j - 1)) {
                        west = strings[i][j - 1]
                        cellData.westSide.add(west)
                    }

                    var south: String? = null
                    if (strings.indices.contains(i + 1)) {
                        south = strings[i + 1][j]
                        cellData.southSide.add(south)
                    }

                    var east: String? = null
                    if (data.indices.contains(j + 1)) {
                        east = strings[i][j + 1]
                        cellData.eastSide.add(east)
                    }

//                    printing NEWS
                    if (printRegion) {
                        val coordinate = Board.Coordinate(i, j)
                        println("Printing region around $coordinate with ${cellData.id}")
                        val cValue = cellData.id
                        val nValue = north ?: "?"
                        val wValue = west ?: "?"
                        val eValue = east ?: "?"
                        val sValue = south ?: "?"
                        println(
                            "| x | $nValue | x |\n" +
                                    "| $wValue | $cValue | $eValue |\n" +
                                    "| x | $sValue | x |"
                        )
                    }
//                    end

                    Cell.addCellData(cellData)
                }
            }
        }
    }
}