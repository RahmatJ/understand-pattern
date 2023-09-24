package cell

data class DataCell(val id: String) {
    var northSide: MutableSet<String> = mutableSetOf<String>()
    var eastSide: MutableSet<String> = mutableSetOf<String>()
    var southSide: MutableSet<String> = mutableSetOf<String>()
    var westSide: MutableSet<String> = mutableSetOf<String>()

    private fun sideToString(side: MutableSet<String>, name: String): String {
        var result = "$name: [ "
        side.forEach {
            result += "$it "
        }
        result += "] "
        return result
    }

    override fun toString(): String {
        var result = ""
        result += "id=$id\n"
        result += sideToString(northSide, "N")
        result += sideToString(eastSide, "E")
        result += sideToString(southSide, "S")
        result += sideToString(westSide, "W")
        result += "\n"
        return result
    }
}
