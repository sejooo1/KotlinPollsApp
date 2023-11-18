package ba.etf.rma22.projekat.data.repositories

import android.app.Application
import android.content.Context
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import kotlin.collections.ArrayList

object IstrazivanjeIGrupaRepository {

    suspend fun getIstrazivanja(offset:Int = 0):List<Istrazivanje>{
        return withContext(Dispatchers.IO) {
            val istrazivanja = ArrayList<Istrazivanje>()
            if (offset == 0) {
                for (i in 1..5) {
                    val adresa = ApiConfig.baseURL + "/istrazivanje?offset=$i"
                    val url = URL(adresa)
                    (url.openConnection() as? HttpURLConnection)?.run {
                        val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                        val items = JSONArray(rezultat)
                        for (j in 0 until items.length()) {
                            val istrazivanje = items.getJSONObject(j)
                            istrazivanja.add(vratiIstrazivanje(istrazivanje))
                        }
                    }
                }
            } else {
                val adresa = ApiConfig.baseURL + "/istrazivanje?offset=$offset"
                val url = URL(adresa)
                (url.openConnection() as? HttpURLConnection)?.run {
                    val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                    val items = JSONArray(rezultat)
                    for (j in 0 until items.length()) {
                        val istrazivanje = items.getJSONObject(j)
                        istrazivanja.add(vratiIstrazivanje(istrazivanje))
                    }
                }
            }
            val db = AppDatabase.getInstance()
            for(istrazivanje in istrazivanja){
                db?.istrazivanjeIGrupaDao()?.insertAll(istrazivanje)
            }
            return@withContext istrazivanja
        }
    }

    private fun vratiIstrazivanje(istrazivanje: JSONObject): Istrazivanje {
        try{
            val povratna = Istrazivanje(istrazivanje.getInt("id"), istrazivanje.getString("naziv"), istrazivanje.getInt("godina"))
            return povratna
        }catch (e : JSONException){
            throw JSONException(e.message)
        }


    }

    suspend fun getGrupe():List<Grupa>{
        return withContext(Dispatchers.IO){
            val grupe = ArrayList<Grupa>()
            val adresa = ApiConfig.baseURL + "/grupa"
            val url = URL(adresa)
            (url.openConnection() as? HttpURLConnection)?.run {
                val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                val items = JSONArray(rezultat)
                for (j in 0 until items.length()) {
                    val grupa = items.getJSONObject(j)
                    grupe.add(vratiGrupu(grupa))
                }
            }
            val db = AppDatabase.getInstance()
            for(grupa in grupe){
                db?.istrazivanjeIGrupaDao()?.insertAll(grupa)
            }
            return@withContext grupe
        }
    }

    suspend fun getGrupeZaIstrazivanje(idIstrazivanja:Int):List<Grupa>{
        return withContext(Dispatchers.IO){
            var response = ApiAdapter.retrofit.getGrupeZaIstrazivanje(idIstrazivanja)
            val responseBody = response.body()
            return@withContext responseBody!!
        }
    }

    suspend fun upisiUGrupu(idGrupa:Int):Boolean{
        return withContext(Dispatchers.IO){
            val adresa = ApiConfig.baseURL + "/grupa/$idGrupa/student/" + AccountRepository.getHash()
            val url = URL(adresa)
            val con = (url.openConnection() as HttpURLConnection)
            con.requestMethod = "POST"
            con.setRequestProperty("Content-Type", "application/json")
            con.setRequestProperty("Accept", "application/json")
            con.doOutput = true
            BufferedReader(
                InputStreamReader(con.inputStream, "utf-8")
            ).use { br->
                val response = StringBuilder()
                var responseLine : String? = null
                while(br.readLine().also( { responseLine = it }) != null){
                    response.append(responseLine!!.trim { it <= ' ' })
                }
                println(response.toString())
            }
            return@withContext true
        }
    }

    suspend fun getUpisaneGrupe():List<Grupa>{
        return withContext(Dispatchers.IO){
            val grupe = ArrayList<Grupa>()
            val adresa = ApiConfig.baseURL + "/student/" + AccountRepository.getHash() + "/grupa"
            val url = URL(adresa)
            (url.openConnection() as? HttpURLConnection)?.run {
                val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                val items = JSONArray(rezultat)
                for (j in 0 until items.length()) {
                    val grupa = items.getJSONObject(j)
                    grupe.add(vratiGrupu(grupa))
                }
            }
            return@withContext grupe
        }
    }

    suspend fun getGrupaById(idGrupe: Int) : Grupa{
        return withContext(Dispatchers.IO){
            lateinit var povratna : Grupa
            val adresa = ApiConfig.baseURL + "/grupa/$idGrupe"
            val url = URL(adresa)
            (url.openConnection() as? HttpURLConnection)?.run {
                val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                val items = JSONArray(rezultat)
                for (j in 0 until items.length()) {
                    val grupa = items.getJSONObject(j)
                    povratna = vratiGrupu(grupa)
                }
            }
            return@withContext povratna
        }
    }

    private fun vratiGrupu(grupa: JSONObject): Grupa {
        try{
            val povratna = Grupa(grupa.getInt("id"), grupa.getString("naziv"), "")
            return povratna
        }catch (e : JSONException){
            throw JSONException(e.message)
        }

    }

    /*suspend fun getIstrazivanja(context: Context) : List<Istrazivanje> {
        var db = AppDatabase.getInstance(context)
        var istrazivanja = db!!.istrazivanjeIGrupaDao().getIstrazivanja()
        return istrazivanja
    }

    suspend fun getGrupe(context: Context) : List<Grupa> {
        var db = AppDatabase.getInstance(context)
        var grupe = db!!.istrazivanjeIGrupaDao().getGrupe()
        return grupe
    }

    suspend fun getGrupaById(context: Context, idGrupe: Int) : Grupa {
        var db = AppDatabase.getInstance(context)
        var grupa = db!!.istrazivanjeIGrupaDao().getGrupaById(idGrupe)
        return grupa
    }*/

}
