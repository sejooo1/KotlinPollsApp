package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeRepository

class IstrazivanjeListViewModel {
    fun dajSva(): List<Istrazivanje> {
        return IstrazivanjeRepository.getAll()
    }
    fun dajPoGodini(godina: Int): List<Istrazivanje> {
        return IstrazivanjeRepository.getIstrazivanjeByGodina(godina)
    }
    fun dajUpisane(): List<Istrazivanje>{
        return IstrazivanjeRepository.getUpisani()
    }
    fun dodajIstrazivanja(istrazivanje: Istrazivanje){
        IstrazivanjeRepository.dodajIstrazivanja(istrazivanje)
    }
    fun getIstrazivanjeByNaziv(naziv:String) : Istrazivanje{
        return IstrazivanjeRepository.getIstrazivanjeByNaziv(naziv)
    }
}