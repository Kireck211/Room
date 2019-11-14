package iteso.mx.room.adapters

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import iteso.mx.room.R
import iteso.mx.room.Word

class AdapterName(private var words: List<Word>) : RecyclerView.Adapter<NameViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_name, parent, false)

        return NameViewHolder(view)
    }

    override fun getItemCount(): Int = words.size

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        holder.bind(words[position])
    }

    fun updateData (newWords: List<Word>) {
        words = newWords
        notifyDataSetChanged()
    }
}

class NameViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    private val textView : TextView = view.findViewById(R.id.word)

    fun bind(word: Word) {
        textView.text = word.word
    }
}
