package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.pitanja

object PitanjeRepository {

    fun getAll() : List<Pitanje>{
        return pitanja()
    }

    fun getByName(naziv: String) : Pitanje{
        val pitanja: ArrayList<Pitanje> = arrayListOf()
        pitanja.addAll(getAll())
        val pitanje= pitanja.find { pitanje -> naziv.equals(pitanje.naziv)}
        return pitanje?: Pitanje(0,"test", "test", 0)
    }

}