package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.gson.annotations.SerializedName

data class Opcije (
    @PrimaryKey @SerializedName("id")val id: Int,
    @Relation(
        parentColumn = "opcije",
        entityColumn = "id",
        associateBy = Junction(value = Pitanje::class,
            parentColumn = "opcije",
            entityColumn = "id")
    )
    @ColumnInfo(name = "opcija")@SerializedName("naziv")val opcija: String

        )