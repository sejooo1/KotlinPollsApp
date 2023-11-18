package ba.etf.rma22.projekat.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.viewmodel.AnketaListViewModel

class FragmentPoruka(private var kod: String, private var anketaUradjena: Anketa) : Fragment() {
    private lateinit var poruka : TextView
    private var anketaListViewModel = AnketaListViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.poruka_fragment, container, false)
        poruka = view.findViewById(R.id.tvPoruka)
        var anketa : Anketa = anketaListViewModel.getAnketaByNazivIstrazivanjaINazivGrupe("test", "test")
        for (e in anketaListViewModel.dajMojeBezSortiranja()){
            anketa = e
        }
        if(kod.equals("a")){
            poruka.text = "Uspješno ste upisani u grupu ${anketa.nazivGrupe} istraživanja ${anketa.nazivIstrazivanja}!"
        }
        if(kod.equals("b")){
            poruka.text = "Završili ste anketu ${anketaUradjena.naziv} u okviru istraživanja ${anketaUradjena.nazivIstrazivanja}"
        }


        return view
    }

    override fun onStop() {
        super.onStop()
        val intent = Intent(activity, MainActivity::class.java).apply {
        }
        startActivity(intent)
    }


}
