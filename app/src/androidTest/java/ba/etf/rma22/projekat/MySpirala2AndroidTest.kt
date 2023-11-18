package ba.etf.rma22.projekat

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MySpirala2AndroidTest {

    @get:Rule
    val intentsTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun ucitavaSePoruka(){
        Espresso.onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToLast())
        Espresso.onView(withId(R.id.odabirGodina)).perform(ViewActions.click())
        Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`("1"))).perform(ViewActions.click())
        Espresso.onView(withId(R.id.odabirIstrazivanja)).perform(ViewActions.click())
        Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`("Istrazivanje broj 0"))).perform(ViewActions.click())
        Espresso.onView(withId(R.id.odabirGrupa)).perform(ViewActions.click())
        Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`("Grupa 1"))).perform(ViewActions.click())
        Espresso.onView(withId(R.id.dodajIstrazivanjeDugme)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withSubstring("Uspješno ste upisani u grupu")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun postojeSvaPitanja(){
        Espresso.onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToFirst())
        Espresso.onView(withId(R.id.filterAnketa)).perform(ViewActions.click())
        Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`("Sve moje ankete"))).perform(
            ViewActions.click()
        )
        val ankete = AnketaRepository.getMyAnkete()
        Espresso.onView(withId(R.id.listaAnketa)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(CoreMatchers.allOf(
                ViewMatchers.hasDescendant(ViewMatchers.withText(ankete[0].naziv)),
                ViewMatchers.hasDescendant(ViewMatchers.withText(ankete[0].nazivIstrazivanja))
            ), ViewActions.click()
            ))
        val pitanjeAnketa = PitanjeAnketaRepository.getPitanja(ankete[0].naziv, ankete[0].nazivIstrazivanja)
        Espresso.onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(pitanjeAnketa.size))
        Espresso.onView(withId(R.id.dugmePredaj)).check(ViewAssertions.matches(isCompletelyDisplayed()))
    }
}