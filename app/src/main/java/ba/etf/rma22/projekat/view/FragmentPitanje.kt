package ba.etf.rma22.projekat.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.get
import androidx.core.view.size
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.AnketaActivity
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Pitanje


class FragmentPitanje(private var pitanje: Pitanje) : Fragment() {
    private lateinit var tekstPitanja: TextView
    private lateinit var odgovoriLista : ListView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pitanje_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val anketaActivity : AnketaActivity = activity as AnketaActivity

        tekstPitanja = view.findViewById(R.id.tekstPitanja)
        odgovoriLista = view.findViewById(R.id.odgovoriLista)
        val dugmeZaustavi: Button = view.findViewById(R.id.dugmeZaustavi)


        populateDetails()

        if(odgovoriLista.isClickable == true) {
            odgovoriLista.setOnItemClickListener() { parent, view, position, id ->
                anketaActivity.dodajOdgovor()
                dodajOdgovorNaPitanje(parent.getItemAtPosition(position).toString())
                (view as TextView).setTextColor(Color.parseColor("#0000FF"))
                odgovoriLista.isClickable = false
                odgovoriLista.isEnabled = false
                if(dajOdgovore().contains(parent.getItemAtPosition(position))) ((view as TextView).setTextColor(Color.parseColor("#0000FF")))
            }
        }
        dugmeZaustavi.setOnClickListener(){
            val intent = Intent(activity, MainActivity::class.java).apply {
            }
            startActivity(intent)
        }
    }


    private fun populateDetails() {
            tekstPitanja.text = pitanje.tekstPitanja
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(activity as Context, android.R.layout.simple_list_item_1, pitanje.opcije)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            odgovoriLista.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val anketaActivity : AnketaActivity = activity as AnketaActivity
        anketaActivity.refreshFragment()
    }

    companion object Odgovori{
        private val odgovori: ArrayList<String> = arrayListOf()

        fun dajOdgovore() : ArrayList<String>{
            return odgovori
        }

        fun dodajOdgovorNaPitanje(odgovor: String){
            odgovori.add(odgovor)
        }
    }


}