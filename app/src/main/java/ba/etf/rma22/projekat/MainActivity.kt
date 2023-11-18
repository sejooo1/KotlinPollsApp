package ba.etf.rma22.projekat

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.repositories.AccountRepository
import ba.etf.rma22.projekat.view.FragmentAnkete
import ba.etf.rma22.projekat.view.FragmentIstrazivanje
import ba.etf.rma22.projekat.view.FragmentPoruka
import ba.etf.rma22.projekat.view.ViewPagerAdapter
import ba.etf.rma22.projekat.viewmodel.AnketaListViewModel
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity(){
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.pager)
        val fragments =
            mutableListOf(
                FragmentAnkete(),
                FragmentIstrazivanje(),
            )

        viewPager.offscreenPageLimit = 3
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, fragments, lifecycle)
        viewPager.adapter = viewPagerAdapter

        val db = AppDatabase.getInstance(this)
        runBlocking { db.anketaDao().getAll() }
        val newString : String?
        val extras = intent.extras
        if (extras == null) {
            newString = null
        } else {
            newString = extras.getString("payload")
        }

        if (newString != null) {
            AccountRepository.postaviHash(newString)
            println(newString)
        }
    }


    fun refreshSecondFragmentText() {
        Handler(Looper.getMainLooper()).postDelayed({
            viewPagerAdapter.refreshFragment(1, FragmentPoruka("a", AnketaListViewModel().getAnketaByNaziv("Anketa 1")))
            viewPagerAdapter.refreshFragment(0, FragmentAnkete())
        }, 100)
    }

    fun refreshSecondFragmentText2() {
        Handler(Looper.getMainLooper()).postDelayed({
            viewPagerAdapter.refreshFragment(1, FragmentIstrazivanje())
        }, 100)
    }




}