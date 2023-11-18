package ba.etf.rma22.projekat.data.repositories

import androidx.room.*
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje

@Dao
interface IstrazivanjeIGrupaDAO {
    @Query("SELECT * FROM istrazivanje")
    suspend fun getIstrazivanja(): List<Istrazivanje>

    @Query("SELECT * FROM grupa")
    suspend fun getGrupe(): List<Grupa>

    @Query("SELECT * FROM grupa WHERE id=:idGrupe")
    suspend fun getGrupaById(idGrupe: Int) : Grupa

    @Insert (onConflict=OnConflictStrategy. REPLACE)
    suspend fun insertAll(vararg istrazivanje : Istrazivanje)

    @Delete
    suspend fun delete(istrazivanje : Istrazivanje)

    @Insert (onConflict=OnConflictStrategy. REPLACE)
    suspend fun insertAll(vararg grupa : Grupa)

    @Delete
    suspend fun delete(grupa : Grupa)
}