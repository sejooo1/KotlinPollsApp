package ba.etf.rma22.projekat.data.models

data class PitanjeAnketa (
    val anketa: String,
    val istrazivanje: String,
    val listaPitanja: List<String>
)