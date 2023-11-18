package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.grupe

object GrupaRepository {
    fun getGroupsByIstrazivanje(nazivIstrazivanja:String) : List<Grupa>{
        val grupe: ArrayList<Grupa> = arrayListOf()
        val povratni: ArrayList<Grupa> = arrayListOf()
        grupe.addAll(grupe())
        for(grupa in grupe){
            if(grupa.nazivIstrazivanja.equals(nazivIstrazivanja)){
                povratni.add(grupa)
            }
        }
        return povratni
    }
}