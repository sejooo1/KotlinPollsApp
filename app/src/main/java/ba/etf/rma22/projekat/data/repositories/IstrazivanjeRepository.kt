package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.istrazivanja


object IstrazivanjeRepository {
    fun getAll() : List<Istrazivanje> {
        return istrazivanja()
    }
    fun getIstrazivanjeByGodina(godina:Int) : List<Istrazivanje>{
        val istrazivanja: ArrayList<Istrazivanje> = arrayListOf()
        val povratni: ArrayList<Istrazivanje> = arrayListOf()
        istrazivanja.addAll(istrazivanja())
        for(istrazivanje in istrazivanja){
            if(istrazivanje.godina == godina){
                povratni.add(istrazivanje)
            }
        }
        return povratni
    }
    fun getIstrazivanjeByNaziv(naziv:String) : Istrazivanje{
        val istrazivanja: ArrayList<Istrazivanje> = arrayListOf()
        istrazivanja.addAll(getAll())
        val istrazivanje = istrazivanja.find { istrazivanje -> naziv.equals(istrazivanje.naziv)}
        return istrazivanje?: Istrazivanje(0,"Test", 0)
    }

        val korisnikovaIstrazivanja: ArrayList<Istrazivanje> = getIstrazivanjeByGodina(3) as ArrayList<Istrazivanje>
        fun dodajIstrazivanja(istrazivanje: Istrazivanje){
            korisnikovaIstrazivanja.add(istrazivanje)
        }
        fun getUpisani() : List<Istrazivanje>{
            return korisnikovaIstrazivanja
    }
}
