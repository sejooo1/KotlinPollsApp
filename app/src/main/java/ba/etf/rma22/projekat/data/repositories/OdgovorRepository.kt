package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Odgovor
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
import java.util.*
import kotlin.collections.ArrayList

object OdgovorRepository {



    private fun vratiOdgovor(odgovor: JSONObject): Odgovor {
        try{
            val povratna = Odgovor(1, odgovor.getInt("PitanjeId"), odgovor.getInt("odgovoreno"))
            return povratna
        }catch (e : JSONException){
            throw JSONException(e.message)
        }
    }


    suspend fun postaviOdgovorAnketa(idAnketaTaken:Int,idPitanje:Int,odgovor:Int):Int{
        return withContext(Dispatchers.IO){
            val adresa = ApiConfig.baseURL + "/student/" + AccountRepository.getHash() + "/anketataken/$idAnketaTaken/odgovor"
            val url = URL(adresa)
            val con = (url.openConnection() as HttpURLConnection)
            con.requestMethod = "POST"
            con.setRequestProperty("Content-Type", "application/json")
            con.setRequestProperty("Accept", "application/json")
            con.doOutput = true
            val ankete = TakeAnketaRepository.getPoceteAnkete()
            var idAnkete = 0
            if(ankete != null)
                for(x in ankete){
                    if(idAnketaTaken == x.id) idAnkete = x.AnketumId
                }
            val brojPitanja = PitanjeAnketaRepository.getPitanja(idAnkete).size
            val progres = ((getOdgovoriAnketa(idAnketaTaken).size.toDouble() + 1.0)/brojPitanja)
            var povratni = srediProgres(progres)
            val jsonInputString = "{\"odgovor\": \"$odgovor\", \"pitanje\": \"$idPitanje\", \"progres\": \"$progres\"}"
            con.outputStream.use{ os ->
                val input = jsonInputString.toByteArray(charset("utf-8"))
                os.write(input, 0, input.size)
            }
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
            val odgovori = getOdgovoriAnketa(idAnkete)
            val db = AppDatabase.getInstance()
            for(odgovor in odgovori){
                db?.odgovorDao()?.insertAll(odgovor)
            }
            return@withContext povratni
        }
    }

    suspend fun getOdgovoriAnketa(idAnkete:Int):List<Odgovor>{
        return withContext(Dispatchers.IO){
            val odgovori = ArrayList<Odgovor>()
            val ankete = TakeAnketaRepository.getPoceteAnkete()
            val datumRada: Date = Calendar.getInstance().time
            var pomocna : AnketaTaken = AnketaTaken(0, "", 0, datumRada, 0)
            if (ankete != null) {
                for(anketa in ankete){
                    if(anketa.AnketumId == idAnkete) pomocna = anketa
                }
            }
            val adresa = ApiConfig.baseURL + "/student/" + AccountRepository.getHash() + "/anketataken/${pomocna.id}/odgovori"
            val url = URL(adresa)
            (url.openConnection() as? HttpURLConnection)?.run {
                val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                val items = JSONArray(rezultat)
                for (j in 0 until items.length()) {
                    val odgovor = items.getJSONObject(j)
                    odgovori.add(vratiOdgovor(odgovor))
                }
            }
            val db = AppDatabase.getInstance()
            for(odgovor in odgovori){
                db?.odgovorDao()?.insertAll(odgovor)
            }
            return@withContext odgovori
        }
    }

    private fun srediProgres(progres: Double): Int {
        if(progres < 0.1) return 0
        if(progres >= 0.1 && progres < 0.3) return 20
        if(progres >= 0.3 && progres < 0.5) return 40
        if(progres >= 0.5 && progres < 0.7) return 60
        if(progres >= 0.7 && progres < 1.0) return 80
        return 100
    }
}