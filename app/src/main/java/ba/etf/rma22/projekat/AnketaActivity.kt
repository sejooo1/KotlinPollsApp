package ba.etf.rma22.projekat

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.view.*
import ba.etf.rma22.projekat.viewmodel.AnketaListViewModel
import ba.etf.rma22.projekat.viewmodel.PitanjeAnketaListViewModel

class AnketaActivity  : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var pitanjeAnketa: List<Pitanje>
    private lateinit var anketa: Anketa
    private var pitanjeAnketaListViewModel = PitanjeAnketaListViewModel()
    private var anketaListViewModel = AnketaListViewModel()
    private var anketaBrojac: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val extras = intent.extras
        if (extras != null) {
            pitanjeAnketa = pitanjeAnketaListViewModel.getPitanja(extras.getString("naziv_ankete", ""), extras.getString("naziv_istrazivanja", ""))
            anketa = anketaListViewModel.getAnketaByNaziv(extras.getString("naziv_ankete", ""))
        }
        else{
            finish()
        }
        viewPager = findViewById(R.id.pager)
        val fragments =
            mutableListOf(
                FragmentPitanje(pitanjeAnketa[0]),
                FragmentPredaj((anketaBrojac/pitanjeAnketa.size).toFloat(), anketa)
            )

        viewPager.offscreenPageLimit = 3
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, fragments, lifecycle)
        viewPagerAdapter.remove(0)
        viewPagerAdapter.remove(0)
        for(i in 0 until  pitanjeAnketa.size step 1){
            viewPagerAdapter.add(i, FragmentPitanje(pitanjeAnketa[i]))
        }
        viewPagerAdapter.add(pitanjeAnketa.size, FragmentPredaj((anketaBrojac.toFloat()/pitanjeAnketa.size.toFloat()), anketa))
        viewPager.adapter = viewPagerAdapter
    }

    fun dodajOdgovor(){
        anketaBrojac +=1
    }

    fun refreshFragment(){
        Handler(Looper.getMainLooper()).postDelayed({
            viewPagerAdapter.refreshFragment(pitanjeAnketa.size, FragmentPredaj((anketaBrojac.toFloat()/pitanjeAnketa.size.toFloat()).toFloat(), anketa))
        }, 100)
    }

    fun refreshFragmentPredaj(){
        Handler(Looper.getMainLooper()).postDelayed({
            viewPagerAdapter.refreshFragment(pitanjeAnketa.size, FragmentPoruka("b", anketa))
        }, 100)
    }

}
