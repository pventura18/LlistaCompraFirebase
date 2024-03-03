package cat.institutmontilivi.llistacomprafirebase.model

data class Producte (
    val id:String = "",
    val nom:String = "",
    val quantitat:Int = 0,
    var comprat:Boolean = false
)