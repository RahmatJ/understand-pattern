package cell

data class DataCell(val id: String) {
    var northSide: MutableList<String> = mutableListOf<String>()
    var eastSide: MutableList<String> = mutableListOf<String>()
    var southSide: MutableList<String> = mutableListOf<String>()
    var westSide: MutableList<String> = mutableListOf<String>()
}
