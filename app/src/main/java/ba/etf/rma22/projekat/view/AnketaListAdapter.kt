package ba.etf.rma22.projekat.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import java.text.SimpleDateFormat
import java.util.*

class AnketaListAdapter(
    private var ankete: List<Anketa>,
    private val onItemClicked: (anketa: Anketa) -> Unit
) : RecyclerView.Adapter<AnketaListAdapter.AnketaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnketaViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_anketa, parent, false)
        return AnketaViewHolder(view)
    }
    override fun getItemCount(): Int = ankete.size

    override fun onBindViewHolder(holder: AnketaViewHolder, position: Int) {
        holder.nazivAnkete.text = ankete[position].naziv
        holder.nazivIstrazivanja.text = ankete[position].nazivIstrazivanja
        holder.progresZavrsetka.min = 0
        holder.progresZavrsetka.max = 100
        holder.progresZavrsetka.progress = srediProgres(ankete[position].progres)
        val context: Context = holder.ikonica.context
        var id = 0
        val date: Date = Calendar.getInstance().time
        if(ankete[position].progres == 1f){
             id = context.resources.getIdentifier("plava", "drawable", context.packageName)
              holder.datumAnkete.text = "Anketa urađena: "+ (ankete[position].datumRada?.dateToString())
        }
        if(ankete[position].progres != 1f && ankete[position].datumKraj.after(date)){
            id = context.resources.getIdentifier("zelena", "drawable", context.packageName)
            holder.datumAnkete.text = "Vrijeme zatvaranja: "+ (ankete[position].datumKraj.dateToString())
        }
        if(ankete[position].progres != 1f && ankete[position].datumKraj.before(date)){
            id = context.resources.getIdentifier("crvena", "drawable", context.packageName)
            holder.datumAnkete.text = "Anketa zatvorena: "+ (ankete[position].datumKraj.dateToString())
        }
        if(ankete[position].progres != 1f && ankete[position].datumPocetak.after(date)){
            id = context.resources.getIdentifier("zuta", "drawable", context.packageName)
            holder.datumAnkete.text = "Vrijeme aktiviranja: "+ (ankete[position].datumPocetak.dateToString())
        }
        holder.ikonica.setImageResource(id)
        if(!(ankete[position].progres != 1f && ankete[position].datumPocetak.after(date))) {
            holder.itemView.setOnClickListener { onItemClicked(ankete[position]) }
        }
    }
    fun updateAnkete(ankete: List<Anketa>) {
        this.ankete = ankete
        notifyDataSetChanged()
    }
    private fun Date.dateToString(): String {

        val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())


        return dateFormatter.format(this)
    }

    private fun srediProgres(progres: Float): Int{
        val povratni: Int = (progres*100).toInt()
        for(i in 100 downTo 0 step 20){
            if(i-povratni<10)
                return i
        }
        return 0
    }

inner class AnketaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val ikonica: ImageView = itemView.findViewById(R.id.ikonica)
    val nazivAnkete: TextView = itemView.findViewById(R.id.nazivAnkete)
    val nazivIstrazivanja: TextView = itemView.findViewById(R.id.nazivIstrazivanja)
    val progresZavrsetka: ProgressBar = itemView.findViewById(R.id.progresZavrsetka)
    val datumAnkete: TextView = itemView.findViewById(R.id.datumAnkete)
}
    }