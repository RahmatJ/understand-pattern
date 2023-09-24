package cell

class Cell() {
    companion object {
        @JvmStatic
        private var cellData: MutableList<DataCell> = mutableListOf<DataCell>()

        fun addCellData(cell: DataCell) {
            cellData.add(cell)
        }

        fun getCellData(): List<DataCell> {
            return cellData
        }
    }
}