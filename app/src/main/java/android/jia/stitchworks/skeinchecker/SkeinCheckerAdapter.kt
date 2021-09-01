package android.jia.stitchworks.skeinchecker

import android.graphics.Color
import android.jia.stitchworks.database.Skein
import android.jia.stitchworks.databinding.ListItemSkeinBinding
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class SkeinCheckerAdapter(val clickCheckerListener: SkeinCheckerListener) :
    ListAdapter<Skein, ViewHolder>(ViewHolder.SkeinDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(getItem(position)!!, clickCheckerListener)
        holder.binding.executePendingBindings()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType:
    Int): ViewHolder {
        return ViewHolder.from(parent)
    }



}

class ViewHolder private constructor(val binding: ListItemSkeinBinding) : RecyclerView.ViewHolder(binding.root){

//Usually this is where the viewholder defines each property because of binding they were inlined and so not needed

    fun bind(item: Skein, clickCheckerListener: SkeinCheckerListener) {

        binding.skein = item

        binding.clickListener = clickCheckerListener

        binding.skeinListBrandNumber.text = item.brandNumber
        binding.skeinListName.text = item.threadName
        try {
            binding.skeinListColourValue.setBackgroundColor(Color.parseColor(item.colourValue))
        } catch (e: IllegalArgumentException) {
             Log.i("SkeinAdapter", "hi jia it was ${binding.skeinListBrandNumber.text}")

         }
         binding.skeinListAmount.text = item.amountOfSkeins.toString()
         if (!item.inUse) {
             binding.skeinListOwnedYes.isInvisible = true
         }
         if (!item.inShoppingCart) {
             binding.inShoppingCart.isInvisible = true
         }

     }

    class SkeinDiffCallback: DiffUtil.ItemCallback<Skein>(){
        override fun areItemsTheSame(oldItem: Skein, newItem: Skein): Boolean {
            return oldItem.brandNumber == newItem.brandNumber
        }

        override fun areContentsTheSame(oldItem: Skein, newItem: Skein): Boolean {
            return oldItem== newItem
        }
    }


    //The from() function needs to be in a companion object so it can be called on the ViewHolder class, not called on a ViewHolder instance.
    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemSkeinBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}

class SkeinCheckerListener(val clickListener: (BrandNumber: String) -> Unit) {


    fun onClick(skein: Skein) = clickListener(skein.brandNumber)


}