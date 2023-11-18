package ba.etf.rma22.projekat.viewmodel


import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import kotlinx.coroutines.runBlocking


class AnketaListViewModel {

    fun dajSve():List<Anketa> = runBlocking{
        return@runBlocking AnketaRepository.getAll()
    }
    fun dajMoje():List<Anketa>{
        return AnketaRepository.getMyAnkete()
    }
    fun dajBuduce():List<Anketa>{
        return AnketaRepository.getFuture()
    }
    fun dajUradjene():List<Anketa>{
        return AnketaRepository.getDone()
    }
    fun dajProsle():List<Anketa>{
        return AnketaRepository.getNotTaken()
    }
    fun dodajAnketu(anketa: Anketa){
        AnketaRepository.dodajAnketu(anketa)
    }
    fun getAnketaByNazivIstrazivanjaINazivGrupe(naziv:String, nazivGrupe:String): Anketa = runBlocking{
        return@runBlocking AnketaRepository.getAnketaByNazivIstrazivanjaINazivGrupe(naziv, nazivGrupe)
    }
    fun dajMojeBezSortiranja():List<Anketa>{
        return AnketaRepository.getMyAnketeBezSortiranja()
    }
    fun getAnketaByNaziv(naziv:String): Anketa = runBlocking {
        return@runBlocking AnketaRepository.getAnketaByNaziv(naziv)
    }
    fun promijeniProgres(broj: Float, naziv: String){
        AnketaRepository.promijeniProgres(broj, naziv)
    }
}