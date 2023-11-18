package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.datum
import ba.etf.rma22.projekat.data.models.Anketa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

object AnketaRepository{
    val korisnikoveAnkete: ArrayList<Anketa> = arrayListOf(/*getAnketaByNazivIstrazivanjaINazivGrupe("Istrazivanje broj 5", "Grupa 1"),
        getAnketaByNazivIstrazivanjaINazivGrupe("Istrazivanje broj 6", "Grupa 1"),
        getAnketaByNazivIstrazivanjaINazivGrupe("Istrazivanje broj 7", "Grupa 1"),
        getAnketaByNazivIstrazivanjaINazivGrupe("Istrazivanje broj 8", "Grupa 1"),
        getAnketaByNazivIstrazivanjaINazivGrupe("Istrazivanje broj 9", "Grupa 1")*/
    )
    fun dodajAnketu(anketa: Anketa){
        korisnikoveAnkete.add(anketa)
    }
    fun promijeniProgres(broj: Float, naziv: String){
        korisnikoveAnkete.find { anketa -> naziv.equals(anketa.naziv) }?.progres = broj
    }
    suspend fun getAnketaByNazivIstrazivanjaINazivGrupe(naziv:String, nazivGrupe:String): Anketa {
        val ankete: ArrayList<Anketa> = arrayListOf()
        ankete.addAll(getAll())
        val anketa= ankete.find { anketa -> naziv.equals(anketa.nazivIstrazivanja) && nazivGrupe.equals(anketa.nazivGrupe)}
        return anketa?: Anketa(0,"anketa","Test", datum(0,0,0),datum(0,0,0),datum(0,0,0),0, "grupa", 0f)
    }

    suspend fun getAnketaByNaziv(naziv:String): Anketa {
        val ankete: ArrayList<Anketa> = arrayListOf()
        ankete.addAll(getAll())
        val anketa= ankete.find { anketa -> naziv.equals(anketa.naziv) }
        return anketa?: Anketa(0,"anketa","Test", datum(0,0,0),datum(0,0,0),datum(0,0,0),0, "grupa", 0f)
    }

    fun getMyAnketeBezSortiranja() : List<Anketa>{
        return korisnikoveAnkete
    }

    fun getMyAnkete() : List<Anketa>{
        return korisnikoveAnkete.sortedBy { it.datumPocetak }
    }
    /*fun getAll() : List<Anketa>{
        return sveAnkete().sortedBy { it.datumPocetak }
    }*/
    fun getDone() : List<Anketa>{
        val pomocna: ArrayList<Anketa> = arrayListOf()
        val povratna: ArrayList<Anketa> = arrayListOf()
        pomocna.addAll(getMyAnkete())
        for(anketa in pomocna){
            if(anketa.progres == 1f){
                povratna.add(anketa)
            }
        }
        return povratna
    }
    fun getFuture() : List<Anketa>{
        val pomocna: ArrayList<Anketa> = arrayListOf()
        val povratna: ArrayList<Anketa> = arrayListOf()
        val date: Date = Calendar.getInstance().time
        pomocna.addAll(getMyAnkete())
        for(anketa in pomocna){
            if((anketa.progres != 1f && anketa.datumKraj.after(date)) || (anketa.progres != 1f && anketa.datumPocetak.after(date))){
                povratna.add(anketa)
            }
        }
        return povratna
    }
    fun getNotTaken() : List<Anketa>{
        val pomocna: ArrayList<Anketa> = arrayListOf()
        val povratna: ArrayList<Anketa> = arrayListOf()
        val date: Date = Calendar.getInstance().time
        pomocna.addAll(getMyAnkete())
        for(anketa in pomocna){
            if(anketa.progres != 1f && anketa.datumKraj.before(date)){
                povratna.add(anketa)
            }
        }
        return povratna
    }

    suspend fun getAll(offset:Int = 0):List<Anketa> {
        return withContext(Dispatchers.IO) {
            val ankete = ArrayList<Anketa>()
            if (offset == 0) {
                for (i in 1..5) {
                    val adresa = ApiConfig.baseURL + "/anketa?offset=$i"
                    val url = URL(adresa)
                    (url.openConnection() as? HttpURLConnection)?.run {
                        val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                        val items = JSONArray(rezultat)
                        for (j in 0 until items.length()) {
                            val anketa = items.getJSONObject(j)
                            ankete.add(vratiAnketu(anketa))
                        }
                    }
                }
            } else {
                val adresa = ApiConfig.baseURL + "/anketa?offset=$offset"
                val url = URL(adresa)
                (url.openConnection() as? HttpURLConnection)?.run {
                    val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                    val items = JSONArray(rezultat)
                    for (j in 0 until items.length()) {
                        val anketa = items.getJSONObject(j)
                        ankete.add(vratiAnketu(anketa))
                    }
                }
            }
            val db = AppDatabase.getInstance()
            for(anketa in ankete){
                db?.anketaDao()?.insertAll(anketa)
            }
            return@withContext ankete
        }
    }

    private fun vratiAnketu(anketa: JSONObject): Anketa {
        try{
            var datum = anketa.getString("datumPocetak")
            val datumPocetak = Date(
                datum.subSequence(0, 4).toString().toInt() - 1900,
                datum.subSequence(5, 7).toString().toInt() - 1,
                datum.subSequence(8, 10).toString().toInt()
            )
            datum = anketa.get("datumKraj").toString()
            var datumKraj = Date(122, 12, 31)
            if(datum != "null")
                datumKraj = Date(
                    datum.subSequence(0, 4).toString().toInt() - 1900,
                    datum.subSequence(5, 7).toString().toInt() - 1,
                    datum.subSequence(8, 10).toString().toInt()
                )
            val povratna = Anketa(anketa.getInt("id"), anketa.getString("naziv"), "", datumPocetak, datumKraj, null, anketa.getInt("trajanje"), "", 0.0f)
            return povratna
        }catch (e : JSONException){
            throw JSONException(e.message)
        }

    }

    suspend fun getById(id:Int):Anketa{
        return withContext(Dispatchers.IO){
            var response = ApiAdapter.retrofit.getById(id)
            val responseBody = response.body()
            return@withContext responseBody!!
        }
    }

    suspend fun getUpisane():List<Anketa>{
        return withContext(Dispatchers.IO){
            val ankete = ArrayList<Anketa>()
            val grupe = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
                for (i in 0 until grupe.size) {
                    val adresa = ApiConfig.baseURL + "/grupa/" + grupe[i].id + "/ankete"
                    val url = URL(adresa)
                    (url.openConnection() as? HttpURLConnection)?.run {
                        val rezultat = this.inputStream.bufferedReader().use { it.readText() }
                        val items = JSONArray(rezultat)
                        for (j in 0 until items.length()) {
                            val anketa = items.getJSONObject(j)
                            ankete.add(vratiAnketu(anketa))
                        }
                    }
                }

            return@withContext ankete
        }
    }

    /*suspend fun getAll(context: Context) : List<Anketa> {
        var db = AppDatabase.getInstance(context)
        var ankete = db!!.anketaDao().getAll()
        return ankete
    }

    suspend fun getById(context: Context, id: Int) : Anketa {
        var db = AppDatabase.getInstance(context)
        var anketa = db!!.anketaDao().getById(id)
        return anketa
    }*/

}
