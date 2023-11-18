package ba.etf.rma22.projekat.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ba.etf.rma22.projekat.data.models.*
import ba.etf.rma22.projekat.data.repositories.*

@Database(entities = arrayOf(Anketa::class, Pitanje::class, Odgovor::class, Grupa::class,Istrazivanje::class, AnketaTaken::class, Account::class), version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun anketaDao(): AnketaDAO
    abstract fun odgovorDao(): OdgovorDAO
    abstract fun pitanjeAnketaDao(): PitanjeAnketaDAO
    abstract fun istrazivanjeIGrupaDao(): IstrazivanjeIGrupaDAO
    abstract fun takeAnketaDAO(): TakeAnketaDAO

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }
        fun setInstance(appdb:AppDatabase):Unit{
            INSTANCE=appdb
        }
        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "RMA22DB"
            ).fallbackToDestructiveMigration().build()
        fun getInstance(): AppDatabase?{
            return INSTANCE
        }
    }

}