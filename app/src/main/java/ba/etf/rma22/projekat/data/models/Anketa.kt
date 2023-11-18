package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import com.google.gson.annotations.SerializedName

@Entity
data class Anketa(
    @PrimaryKey @SerializedName("id")val id: Int,
    @ColumnInfo(name = "naziv")@SerializedName("naziv")val naziv: String,
    val nazivIstrazivanja: String,
    @ColumnInfo(name = "datumPocetak")@SerializedName("datumPocetak")val datumPocetak: Date,
    @ColumnInfo(name = "datumKraj")@SerializedName("datumKraj")val datumKraj: Date,
    val datumRada: Date?,
    @ColumnInfo(name = "trajanje")@SerializedName("trajanje")val trajanje: Int,
    val nazivGrupe: String,
    var progres: Float
)
