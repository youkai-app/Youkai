package app.youkai.ui.feature.media.characters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import app.youkai.R
import app.youkai.data.models.Casting
import app.youkai.util.ext.getLayoutInflater
import kotlinx.android.synthetic.main.item_character.view.*

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {
    val dataset = mutableListOf<Casting>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = parent.context.getLayoutInflater().inflate(R.layout.item_character, parent, false)
        v.setOnClickListener {
            // TODO: Start character activity
        }
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val v = holder.itemView
        val item = dataset[position]

        v.name.text = item.character?.name ?: "?"
        v.image.setImageURI(item.character?.image)
        v.castingName.text = item.person?.name ?: "?"
        v.castingImage.setImageURI(item.person?.image)
    }

    override fun getItemCount(): Int = dataset.size

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v)
}
