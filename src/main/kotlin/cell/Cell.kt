package cell

class Cell() {
    companion object {
        @JvmStatic
        private var cellData: MutableSet<DataCell> = mutableSetOf<DataCell>()

        fun addCellData(cell: DataCell) {
            if (cellData.find { it.id == cell.id } == null) {
                cellData.add(cell)
                return
            }
            cellData.filter { it == cell }.forEach { x ->
                x.eastSide.addAll(cell.eastSide)
                x.northSide.addAll(cell.northSide)
                x.southSide.addAll(cell.southSide)
                x.westSide.addAll(cell.westSide)
            }
        }

        fun getCellData(): Set<DataCell> {
            return cellData
        }
    }
}