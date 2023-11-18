package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.AnketaTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

object TakeAnketaRepository {

    suspend fun zapocniAnketu(idAnkete:Int): AnketaTaken {
        return withContext(Dispatchers.IO){
            val anketa = AnketaRepository.getById(idAnkete)
            val response= ApiAdapter.retrofit.zapocniAnketu(AccountRepository.getHash(), idAnkete, AccountRepository.getHash(), anketa.id, AccountRepository.getHash(), 0, "", anketa.id)
            val responseBody = response.body()
            return@withContext responseBody!!
        }

    }

    private fun vratiAnketaTakenIzAnkete(anketa: JSONObject): AnketaTaken {
        try{
            val datumRada: Date = Calendar.getInstance().time
            val povratna = AnketaTaken(anketa.getInt("id"), anketa.getString("student"), anketa.getInt("progres"), datumRada, anketa.getInt("AnketumId"))
            return povratna
        }catch (e : JSONException){
            throw JSONException(e.message)
        }

    }

    suspend fun getPoceteAnkete(): List<AnketaTaken>? {
            return withContext(Dispatchers.IO) {
                val ankete = ArrayList<AnketaTaken>()
                val adresa =
                    ApiConfig.baseURL + "/student/" + AccountRepository.getHash() + "/anketataken"
                val url = URL(adresa)
                (url.openConnection() as? HttpURLConnection)?.run {
                    val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                    val items = JSONArray(rezultat)
                    for (j in 0 until items.length()) {
                        val anketa = items.getJSONObject(j)
                        ankete.add(vratiAnketu(anketa))
                    }
                }
                if(ankete.isEmpty()) return@withContext null
                return@withContext ankete
            }

    }

    private fun vratiAnketu(anketa: JSONObject): AnketaTaken {
        try{
            var datum = anketa.getString("datumRada")
            val datumRada = Date(
                datum.subSequence(0, 4).toString().toInt() - 1900,
                datum.subSequence(5, 7).toString().toInt() - 1,
                datum.subSequence(8, 10).toString().toInt()
            )

            val povratna = AnketaTaken(anketa.getInt("id"), anketa.getString("student"), anketa.getInt("progres"), datumRada, anketa.getInt("AnketumId"))
            return povratna
        }catch (e : JSONException){
            throw JSONException(e.message)
        }

    }
/*
    suspend fun getPoceteAnkete(context: Context) : List<AnketaTaken> {
        var db = AppDatabase.getInstance(context)
        var ankete = db!!.takeAnketaDAO().getPoceteAnkete()
        return ankete
    }

    suspend fun zapocniAnketu(context: Context,idAnkete : Int) : AnketaTaken?{
        return withContext(Dispatchers.IO) {
            try{
                var db = AppDatabase.getInstance(context)
                val anketa = AnketaRepository.getById(context, idAnkete)
                val datumRada: Date = Calendar.getInstance().time
                val anketaTaken = AnketaTaken(anketa.id, AccountRepository.getHash(), 0, datumRada, anketa.id)
                db!!.takeAnketaDAO().insertAll(anketaTaken)
                return@withContext anketaTaken
            }
            catch(error:Exception){
                return@withContext null
            }
        }
    }*/
}