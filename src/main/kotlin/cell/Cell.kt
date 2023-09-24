package cell

class Cell() {

    private var candidateId: MutableSet<String> = mutableSetOf<String>()

    init {
        candidateId = baseCellProbability
    }

    fun removeInvalidCandidate(validCandidate: MutableSet<String>) {
        println("Before: $candidateId")
        println("Removing except: $validCandidate")
        this.candidateId.removeIf { x -> !validCandidate.contains(x) }
        println("Removed ...")
        println("After: $candidateId")
    }

    fun getCandidateId(): MutableSet<String> {
        return candidateId
    }

    fun setCandidateId(newCandidate: MutableSet<String>) {
        this.candidateId = newCandidate
    }

    override fun toString(): String {
        var result = ""
        candidateId.forEach {
            result += "$it "
        }
        return result
    }


    companion object {
        @JvmStatic
        private var cellData: MutableSet<DataCell> = mutableSetOf<DataCell>()

        @JvmStatic
        private var baseCellProbability: MutableSet<String> = mutableSetOf<String>()

        fun addCellData(cell: DataCell) {
            if (cellData.find { it.id == cell.id } == null) {
                baseCellProbability.add(cell.id)
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