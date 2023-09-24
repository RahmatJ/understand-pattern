package pattern

import cell.Cell
import cell.DataCell

class Pattern() {
    companion object {
        fun readPattern(strings: List<Array<String>>) {
//            create window 3x3
            for (i in strings.indices) {
                val data = strings[i]
                for (j in data.indices) {
//                    | x | N | x |
//                    | W | C | E |
//                    | x | S | x |
                    val c: String = strings[i][j]
                    val cellData = DataCell(c)
                    if (data.indices.contains(i - 1)) {
                        val n = strings[i - 1][j]
                        cellData.northSide.add(n)
                    }

                    if (data.indices.contains(j - 1)) {
                        val w = strings[i][j - 1]
                        cellData.westSide.add(w)
                    }

                    if (data.indices.contains(i + 1)) {
                        val s = strings[i + 1][j]
                        cellData.southSide.add(s)
                    }
                    if (data.indices.contains(j + 1)) {
                        val e = strings[i][j + 1]
                        cellData.eastSide.add(e)
                    }

                    Cell.addCellData(cellData)
                }
            }
        }
    }
}