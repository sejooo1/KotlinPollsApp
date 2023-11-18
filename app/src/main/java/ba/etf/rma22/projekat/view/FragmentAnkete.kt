package ba.etf.rma22.projekat.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.AnketaActivity
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.viewmodel.AnketaListViewModel

class FragmentAnkete : Fragment() {
    private var anketaListViewModel =  AnketaListViewModel()
    private lateinit var listaAnketa: RecyclerView
    private lateinit var anketaAdapter: AnketaListAdapter
    val izbori = arrayOf("Sve moje ankete","Sve ankete","Urađene ankete","Buduće ankete","Prošle ankete")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ankete_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner = view.findViewById<Spinner>(R.id.filterAnketa)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            activity as Context,
            android.R.layout.simple_spinner_item, izbori
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        listaAnketa = view.findViewById(R.id.listaAnketa)
        listaAnketa.layoutManager = GridLayoutManager(
            activity as Context, 2
        )
        anketaAdapter = AnketaListAdapter(listOf()) { anketa -> prikaziAnketu(anketa) }
        listaAnketa.adapter = anketaAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position == 0) anketaAdapter.updateAnkete(anketaListViewModel.dajMoje())
                if(position == 1) anketaAdapter.updateAnkete(anketaListViewModel.dajSve())
                if(position == 2) anketaAdapter.updateAnkete(anketaListViewModel.dajUradjene())
                if(position == 3) anketaAdapter.updateAnkete(anketaListViewModel.dajBuduce())
                if(position == 4) anketaAdapter.updateAnkete(anketaListViewModel.dajProsle())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                anketaAdapter.updateAnkete(anketaListViewModel.dajSve())
            }
        }
    }

    private fun prikaziAnketu(anketa: Anketa) {
        val intent = Intent(activity, AnketaActivity::class.java).apply {
            putExtra("naziv_ankete", anketa.naziv)
            putExtra("naziv_istrazivanja", anketa.nazivIstrazivanja)
        }
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        val main: MainActivity = activity as MainActivity
        main.refreshSecondFragmentText2()
    }
}