package ba.etf.rma22.projekat.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import ba.etf.rma22.projekat.AnketaActivity
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.viewmodel.AnketaListViewModel


class FragmentPredaj(private var progres: Float, private var anketa: Anketa) : Fragment() {
    private lateinit var progresAnkete: TextView
    private var anketaListViewModel = AnketaListViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.predaj_fragment, container, false)
        progresAnkete = view.findViewById(R.id.progresTekst)
        progresAnkete.text = srediProgres(progres).toString() + "%"
        val button = view.findViewById<Button>(R.id.dugmePredaj)
        button.setOnClickListener(){
            val anketaActivity : AnketaActivity = activity as AnketaActivity
            anketaActivity.refreshFragmentPredaj()
        }
        val anketaActivity : AnketaActivity = activity as AnketaActivity
        anketaListViewModel.promijeniProgres(progres, anketa.naziv)
        return view
    }

    private fun srediProgres(progres: Float): Int{
        val povratni: Int = (progres*100).toInt()
        for(i in 100 downTo 0 step 20){
            if(i-povratni<10)
                return i
        }
        return 0
    }


}