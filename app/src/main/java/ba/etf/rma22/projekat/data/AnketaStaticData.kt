package ba.etf.rma22.projekat.data

import ba.etf.rma22.projekat.data.models.Anketa
import java.util.*

fun sveAnkete(): List<Anketa> {
    return listOf(
        Anketa(0,
            "Anketa 1", "Istrazivanje broj 0", datum(1,1,2022),
            datum(20,4,2022), datum(10,4,2022),
            3, "Grupa 1", 1f
        ),
        Anketa(0,
            "Anketa 2", "Istrazivanje broj 0", datum(1,1,2022),
            datum(11,7,2022), datum(10,4,2022),
            3, "Grupa 2", 0f
        ),
        Anketa(0,
            "Anketa 3", "Istrazivanje broj 1", datum(1,1,2022),
            datum(20,4,2022), datum(10,4,2022),
            3, "Grupa 1", 1f
        ),
        Anketa(0,
            "Anketa 4", "Istrazivanje broj 1", datum(1,1,2022),
            datum(2,4,2022), datum(10,4,2022),
            3, "Grupa 2", 0f
        ),
        Anketa(0,
            "Anketa 5", "Istrazivanje broj 2", datum(10,7,2022),
            datum(20,8,2022), datum(10,8,2022),
            3, "Grupa 1", 0f),
        Anketa(0,
            "Anketa 6", "Istrazivanje broj 2", datum(10,7,2022),
            datum(20,8,2022), datum(10,8,2022),
            3, "Grupa 2", 0f),
        Anketa(0,
            "Anketa 7", "Istrazivanje broj 3", datum(10,2,2022),
            datum(20,8,2022), datum(10,8,2022),
            3, "Grupa 1", 0.6f),
        Anketa(0,
            "Anketa 8", "Istrazivanje broj 3", datum(10,2,2022),
            datum(20,8,2022), datum(10,8,2022),
            3, "Grupa 2", 0.5f),
        Anketa(0,
            "Anketa 9", "Istrazivanje broj 4", datum(16,2,2022),
            datum(20,8,2022), datum(10,3,2022),
            3, "Grupa 1", 1f),
        Anketa(0,
            "Anketa 10", "Istrazivanje broj 4", datum(10,7,2022),
            datum(20,8,2022), datum(10,8,2022),
            3, "Grupa 2", 0f),
        Anketa(0,
            "Anketa 11", "Istrazivanje broj 5", datum(10,2,2022),
            datum(20,8,2022), datum(10,4,2022),
            3, "Grupa 1", 1f),
        Anketa(0,
            "Anketa 12", "Istrazivanje broj 5", datum(10,2,2022),
            datum(20,8,2022), datum(10,4,2022),
            3, "Grupa 2", 1f),
        Anketa(0,
            "Anketa 13", "Istrazivanje broj 6", datum(10,2,2022),
            datum(20,8,2022), datum(10,8,2022),
            3, "Grupa 1", 0.2f),
        Anketa(0,
            "Anketa 14", "Istrazivanje broj 6", datum(10,2,2022),
            datum(20,8,2022), datum(10,8,2022),
            3, "Grupa 2", 0.5f),
        Anketa(0,
            "Anketa 15", "Istrazivanje broj 7", datum(10,2,2022),
            datum(20,3,2022), datum(10,8,2022),
            3, "Grupa 1", 0f),
        Anketa(0,
            "Anketa 16", "Istrazivanje broj 7", datum(10,2,2022),
            datum(20,3,2022), datum(10,8,2022),
            3, "Grupa 2", 0f),
        Anketa(0,
            "Anketa 17", "Istrazivanje broj 8", datum(10,7,2022),
            datum(20,8,2022), datum(10,8,2022),
            3, "Grupa 1", 0f),
        Anketa(0,
            "Anketa 18", "Istrazivanje broj 8", datum(10,7,2022),
            datum(20,8,2022), datum(10,8,2022),
            3, "Grupa 2", 0f),
        Anketa(0,
            "Anketa 19", "Istrazivanje broj 9", datum(10,7,2022),
            datum(20,8,2022), datum(10,8,2022),
            3, "Grupa 1", 0f),
        Anketa(0,
            "Anketa 20", "Istrazivanje broj 9", datum(10,7,2022),
            datum(20,8,2022), datum(10,8,2022),
            3, "Grupa 2", 0f),
        Anketa(0,
            "Anketa 21", "Istrazivanje broj 10", datum(10,7,2022),
            datum(20,8,2022), datum(10,8,2022),
            3, "Grupa 1", 0f),
        Anketa(0,
            "Anketa 22", "Istrazivanje broj 10", datum(10,3,2022),
            datum(20,8,2022), datum(10,8,2022),
            3, "Grupa 2", 0.9f),
        Anketa(0,
            "Anketa 23", "Istrazivanje broj 10", datum(10,3,2022),
            datum(20,8,2022), datum(10,4,2022),
            3, "Grupa 3", 1f),
        Anketa(0,
            "Anketa 24", "Istrazivanje broj 11", datum(10,3,2022),
            datum(20,8,2022), datum(10,4,2022),
            3, "Grupa 1", 1f),
        Anketa(0,
            "Anketa 25", "Istrazivanje broj 11", datum(10,3,2022),
            datum(20,8,2022), datum(10,8,2022),
            3, "Grupa 2", 0f)
    )
}


fun datum(d: Int, m: Int, y: Int): Date{
    val cal = Calendar.getInstance()
    cal[Calendar.YEAR] = y
    cal[Calendar.MONTH] = m-1
    cal[Calendar.DAY_OF_MONTH] = d
    val date = cal.getTime()
    return date
}