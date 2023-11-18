package ba.etf.rma22.projekat.data.repositories

import androidx.room.*
import ba.etf.rma22.projekat.data.models.Anketa

@Dao
interface AnketaDAO {
    @Query("SELECT * FROM anketa")
    suspend fun getAll(): List<Anketa>

    @Query("SELECT * FROM anketa WHERE id=:id")
    suspend fun getById(id:Int):Anketa

    @Insert (onConflict=OnConflictStrategy. REPLACE)
    suspend fun insertAll(vararg anketa : Anketa)

    @Delete
    suspend fun delete(anketa : Anketa)
}