package pattern

import cell.Cell
import cell.DataCell

class Pattern {
    companion object {
        fun readPattern(strings: List<Array<Int>>) {
//            create window 3x3
            for (i in strings.indices) {
                val data = strings[i]
                for (j in data.indices) {
//                    | x | N | x |
//                    | W | C | E |
//                    | x | S | x |
                    val c: String = strings[i][j].toString()
                    val cellData = DataCell(c)
                    var n: String = "x"
                    if (data.indices.contains(i - 1)) {
                        n = strings[i - 1][j].toString()
                        cellData.northSide.add(n)
                    }
                    var w: String = "x"
                    if (data.indices.contains(j - 1)) {
                        w = strings[i][j - 1].toString()
                        cellData.westSide.add(w)
                    }
                    var s: String = "x"
                    if (data.indices.contains(i + 1)) {
                        s = strings[i + 1][j].toString()
                        cellData.southSide.add(s)
                    }
                    var e: String = "x"
                    if (data.indices.contains(j + 1)) {
                        e = strings[i][j + 1].toString()
                        cellData.eastSide.add(e)
                    }

                    Cell.addCellData(cellData)

                    println("[$i, $j]")
                    println(
                        "| x | $n | x |\n" +
                                "| $w | $c | $e |\n" +
                                "| x | $s | x |"
                    )
                }
            }
        }
    }
}