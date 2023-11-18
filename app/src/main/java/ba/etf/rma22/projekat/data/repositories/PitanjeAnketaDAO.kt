package ba.etf.rma22.projekat.data.repositories

import androidx.room.*
import ba.etf.rma22.projekat.data.models.Pitanje

@Dao
interface PitanjeAnketaDAO {
    @Insert (onConflict=OnConflictStrategy. REPLACE)
    suspend fun insertAll(vararg pitanje : Pitanje)

    @Delete
    suspend fun delete(pitanje : Pitanje)
}