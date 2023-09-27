package cell

class Cell() {

    private var candidateId: MutableSet<String> = mutableSetOf<String>()
    private var entropy: Double = 1.0

    init {
        setCandidateId(baseCellProbability)
    }

    fun removeInvalidCandidate(validCandidate: MutableSet<String>) {
//        instead of removing, try to create new array and set candidate with it
        val newCandidate = this.candidateId.filter { validCandidate.contains(it) }.toMutableSet()
        println(newCandidate)
        setCandidateId(newCandidate)
    }

    fun getCandidateId(): MutableSet<String> {
        return candidateId
    }

    fun setCandidateId(newCandidate: MutableSet<String>) {
        this.candidateId = newCandidate
        this.entropy = calculateEntropy()
    }

    fun resetCandidateId() {
        setCandidateId(baseCellProbability)
    }

    private fun calculateEntropy(): Double {
        return candidateId.size.toDouble() / baseCellProbability.size.toDouble()
    }

    fun getEntropy(): Double {
        return this.entropy
    }

    override fun toString(): String {
        var result = ""
        candidateId.forEach {
            result += "$it "
        }
        if (candidateId.size < baseCellProbability.size) {
            result += "_ ".repeat(baseCellProbability.size - candidateId.size)
        }
        return result
    }


    companion object {
        @JvmStatic
        private var cellData: MutableSet<DataCell> = mutableSetOf<DataCell>()

        @JvmStatic
        private var baseCellProbability: MutableSet<String> = mutableSetOf<String>()

        fun getBaseCellProbability(): MutableSet<String> {
            return baseCellProbability
        }

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