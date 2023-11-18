package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.PitanjeAnketa
import ba.etf.rma22.projekat.data.pitanjaAnkete
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object PitanjeAnketaRepository {

    suspend fun getPitanja(idAnkete:Int):List<Pitanje>{
        return withContext(Dispatchers.IO){
            val pitanja = ArrayList<Pitanje>()
            val adresa = ApiConfig.baseURL + "/anketa/$idAnkete/pitanja"
            val url = URL(adresa)
            (url.openConnection() as? HttpURLConnection)?.run {
            val rezultat = this.inputStream.bufferedReader().use { it.readText() }
            val items = JSONArray(rezultat)
            for (j in 0 until items.length()) {
                val pitanje = items.getJSONObject(j)
                pitanja.add(vratiPitanje(pitanje))
            }
        }
            val db = AppDatabase.getInstance()
            for(pitanje in pitanja){
                db?.pitanjeAnketaDao()?.insertAll(pitanje)
            }
            return@withContext pitanja
        }
    }

    private fun vratiPitanje(pitanje: JSONObject): Pitanje {
        try{
            val opcije = pitanje.getJSONArray("opcije")
            println(opcije)
            val povratna = Pitanje(pitanje.getInt("id"), pitanje.getString("naziv"), pitanje.getString("tekstPitanja"), 0)
            return povratna
        }catch (e : JSONException){
            throw JSONException(e.message)
        }

    }

    fun getPitanja(nazivAnkete: String, nazivIstrazivanja: String): List<Pitanje>{
        val pitanjaAnkete: ArrayList<PitanjeAnketa> = arrayListOf()
        pitanjaAnkete.addAll(getAll())
        val pitanjeAnketa= pitanjaAnkete.find { pitanjeAnketa -> nazivAnkete.equals(pitanjeAnketa.anketa) && nazivIstrazivanja.equals(pitanjeAnketa.istrazivanje)}
        val pitanja: ArrayList<Pitanje> = arrayListOf()
        if (pitanjeAnketa != null) {
            for(p in pitanjeAnketa.listaPitanja){
                pitanja.add(PitanjeRepository.getByName(p))
            }
        }
        return pitanja
    }

    fun getAll() : List<PitanjeAnketa>{
        return pitanjaAnkete()
    }
}