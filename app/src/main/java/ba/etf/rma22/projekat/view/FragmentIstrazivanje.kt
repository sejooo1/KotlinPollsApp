package ba.etf.rma22.projekat.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.viewmodel.AnketaListViewModel
import ba.etf.rma22.projekat.viewmodel.GrupaListViewModel
import ba.etf.rma22.projekat.viewmodel.IstrazivanjeListViewModel


class FragmentIstrazivanje : Fragment() {
    private val izbori = arrayOf("", "1", "2", "3", "4", "5")
    private var istrazivanjeListViewModel = IstrazivanjeListViewModel()
    private var grupaListViewModel = GrupaListViewModel()
    private var anketaListViewModel = AnketaListViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.upis_istrazivanje_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context: Context = activity as Context
        val main: MainActivity = activity as MainActivity
        val spinner1 = view.findViewById<Spinner>(R.id.odabirGodina)
        val spinner2 = view.findViewById<Spinner>(R.id.odabirIstrazivanja)
        val spinner3 = view.findViewById<Spinner>(R.id.odabirGrupa)
        val button = view.findViewById<Button>(R.id.dodajIstrazivanjeDugme)
        val adapter1: ArrayAdapter<String> = ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_item, izbori
        )
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adapter1
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position == 0) button.isEnabled = false
                val pomocna: List<Istrazivanje> =
                    istrazivanjeListViewModel.dajPoGodini(position)
                val pomocna2: ArrayList<Istrazivanje> =
                    istrazivanjeListViewModel.dajUpisane() as ArrayList<Istrazivanje>
                val izborIstrazivanja: ArrayList<String> = arrayListOf("")
                for (istrazivanje in pomocna) {
                    if (!(pomocna2.contains(istrazivanje))) {
                        izborIstrazivanja.add(istrazivanje.naziv)
                    }
                }

                val adapter2: ArrayAdapter<String> = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, izborIstrazivanja)
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner2.adapter = adapter2
                spinner2.onItemSelectedListener = object:

                    AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                        if(pos == 0) button.isEnabled = false
                        val istrazivanjeNaziv: String = p0?.getItemAtPosition(pos).toString()
                        val pomocna: List<Grupa> = grupaListViewModel.dajPoIstrazivanju(
                            istrazivanjeNaziv)
                        val izborGrupa: ArrayList<String> = arrayListOf()
                        val istrazivanje: Istrazivanje = istrazivanjeListViewModel.getIstrazivanjeByNaziv(istrazivanjeNaziv)
                        for(grupa in pomocna){
                            izborGrupa.add(grupa.naziv)
                        }
                        val adapter3: ArrayAdapter<String> = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, izborGrupa)
                        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinner3.adapter = adapter3
                        spinner3.onItemSelectedListener = object :
                            AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                val grupaNaziv = p0?.getItemAtPosition(p2).toString()
                                val anketa : Anketa = anketaListViewModel.getAnketaByNazivIstrazivanjaINazivGrupe(istrazivanje.naziv, grupaNaziv)
                                button.isEnabled = true
                                button.setOnClickListener(){
                                    anketaListViewModel.dodajAnketu(anketa)
                                    istrazivanjeListViewModel.dodajIstrazivanja(istrazivanje)
                                    main.refreshSecondFragmentText()
                                }

                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {
                                button.isEnabled = false
                            }

                        }
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        button.isEnabled = false
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                button.isEnabled = false
            }
        }
    }

}