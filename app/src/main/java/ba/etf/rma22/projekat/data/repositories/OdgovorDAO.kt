package ba.etf.rma22.projekat.data.repositories

import androidx.room.*
import ba.etf.rma22.projekat.data.models.Odgovor

@Dao
interface OdgovorDAO {
    @Insert (onConflict=OnConflictStrategy. REPLACE)
    suspend fun insertAll(vararg odgovor : Odgovor)

    @Delete
    suspend fun delete(odgovor : Odgovor)
}