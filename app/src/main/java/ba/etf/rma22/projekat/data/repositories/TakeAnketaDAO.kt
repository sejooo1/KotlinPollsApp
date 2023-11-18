package ba.etf.rma22.projekat.data.repositories

import androidx.room.*
import ba.etf.rma22.projekat.data.models.AnketaTaken

@Dao
interface TakeAnketaDAO {
    @Query("SELECT * FROM anketataken")
    suspend fun getPoceteAnkete(): List<AnketaTaken>

    @Insert (onConflict=OnConflictStrategy. REPLACE)
    suspend fun insertAll(vararg anketaTaken : AnketaTaken)

    @Delete
    suspend fun delete(anketaTaken : AnketaTaken)
}