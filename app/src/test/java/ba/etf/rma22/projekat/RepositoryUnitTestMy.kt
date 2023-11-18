package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.data.datum
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.GrupaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasProperty
import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

class RepositoryUnitTestMy {



    @Test
    fun test1_getAllAnketa() = runBlocking{
        val ankete = AnketaRepository.getAll()
        assertEquals(ankete.size, 25)
    }

    @Test
    fun test2_getMyAnketa()= runBlocking{
        val ankete = AnketaRepository.getMyAnkete()
        assertEquals(ankete.size, 5)
    }

    @Test
    fun test3_getFutureAnketa()= runBlocking{
        val ankete = AnketaRepository.getFuture()
        assertEquals(ankete.size, 3)
    }

    @Test
    fun test4_getDoneAnketa()= runBlocking{
        val ankete = AnketaRepository.getDone()
        assertEquals(ankete.size, 1)
    }

    @Test
    fun test5_getNotTakenAnketa()= runBlocking{
        val ankete = AnketaRepository.getDone()
        assertEquals(ankete.size, 1)
    }

    @Test
    fun test6_dodajAnketu()= runBlocking{
        AnketaRepository.dodajAnketu(Anketa(0,"Proba", "Proba", datum(0,0,0), datum(0,0,0), datum(0,0,0), 0, "Proba", 0f))
        val ankete = AnketaRepository.getMyAnkete()
        assertEquals(ankete.size, 6)
    }

    @Test
    fun test7_getAnketaByNazivIstrazivanjaINazivGrupe()= runBlocking{
        val anketa = AnketaRepository.getAnketaByNazivIstrazivanjaINazivGrupe("Istrazivanje broj 0", "Grupa 1")
        assertEquals(anketa.naziv, "Anketa 1")
    }

    @Test
    fun test8_getAllAnketa2()= runBlocking{
        val ankete = AnketaRepository.getAll()
        assertEquals(ankete.size, 25)
        assertThat(ankete, hasItem<Anketa>(hasProperty("naziv", Is("Anketa 10"))))
        assertThat(ankete, not(hasItem<Anketa>(hasProperty("naziv", Is("Probni")))))
    }

    @Test
    fun test1_getAllIstrazivanje()= runBlocking{
        val istrazivanja = IstrazivanjeRepository.getAll()
        assertEquals(istrazivanja.size, 12)
    }

    @Test
    fun test2_getMyIstrazivanje()= runBlocking{
        val istrazivanja = IstrazivanjeRepository.getUpisani()
        assertEquals(istrazivanja.size, 5)
    }

    @Test
    fun test3_getIstrazivanjeByGodina()= runBlocking{
        val istrazivanja = IstrazivanjeRepository.getIstrazivanjeByGodina(1)
        assertEquals(istrazivanja.size, 2)
    }

    @Test
    fun test4_getIstrazivanjeByNaziv()= runBlocking{
        val istrazivanje = IstrazivanjeRepository.getIstrazivanjeByNaziv("Istrazivanje broj 10")
        assertEquals(istrazivanje.naziv, "Istrazivanje broj 10")
    }

    @Test
    fun test5_dodajIstrazivanja()= runBlocking{
        IstrazivanjeRepository.dodajIstrazivanja(Istrazivanje(0,"Istrazivanje Test", 5))
        val istrazivanja = IstrazivanjeRepository.getUpisani()
        assertEquals(istrazivanja.size, 6)
    }

    @Test
    fun test6_getAllIstrazivanje2()= runBlocking{
        val istrazivanja = IstrazivanjeRepository.getAll()
        assertEquals(istrazivanja.size, 12)
        assertThat(istrazivanja, hasItem<Istrazivanje>(hasProperty("naziv", Is("Istrazivanje broj 10"))))
        assertThat(istrazivanja, not(hasItem<Istrazivanje>(hasProperty("godina", Is(6)))))
    }

    @Test
    fun test1_getGroupsByIstrazivanje()= runBlocking{
        val grupe = GrupaRepository.getGroupsByIstrazivanje("Istrazivanje broj 10")
        assertEquals(grupe.size, 3)
    }

    @Test
    fun test2_getGroupsByIstrazivanje2()= runBlocking{
        val grupe = GrupaRepository.getGroupsByIstrazivanje("Istrazivanje broj 10")
        assertEquals(grupe.size, 3)
        assertThat(grupe, hasItem<Grupa>(hasProperty("naziv", Is("Grupa 3"))))
        assertThat(grupe, not(hasItem<Grupa>(hasProperty("naziv", Is("Grupa 235522")))))
    }
}