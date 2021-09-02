package android.jia.stitchworks

import android.jia.stitchworks.skeinchecker.SkeinCheckerAdapter
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener

inline fun SearchView.onQueryTextChanged(crossinline listener: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(query: String?): Boolean {
            listener(query.orEmpty())

            return true
        }

    })
}