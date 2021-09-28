package android.jia.stitchworks.skeinchecker

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.jia.stitchworks.R
import android.jia.stitchworks.database.SkeinDatabase
import android.jia.stitchworks.databinding.FragmentSkeinCheckerBinding
import android.jia.stitchworks.onQueryTextChanged
import android.view.*
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SkeinCheckerFragment : Fragment() {

    private val skeinCheckerViewModel by activityViewModels<SkeinCheckerViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding: FragmentSkeinCheckerBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_skein_checker, container, false
        )

        binding.skeinCheckerViewModel = skeinCheckerViewModel


        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        val adapter = SkeinCheckerAdapter(SkeinCheckerListener { brandNumber ->
            Toast.makeText(context, "${brandNumber}", Toast.LENGTH_SHORT).show()
        })
        binding.skeinList.adapter = adapter
        binding.skeinList.layoutManager = LinearLayoutManager(requireContext())
        binding.skeinList.setHasFixedSize(true)


        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                binding.skeinList.scrollToPosition(0)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                binding.skeinList.scrollToPosition(0)
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                binding.skeinList.scrollToPosition(0)
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                binding.skeinList.scrollToPosition(0)
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                binding.skeinList.scrollToPosition(0)
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                binding.skeinList.scrollToPosition(0)
            }
        })

        skeinCheckerViewModel.threads.observe(
            viewLifecycleOwner, { it?.let { adapter.submitList(it) } })


        binding.searchView.queryHint = "hello, type here"






        binding.searchView.onQueryTextChanged {
            skeinCheckerViewModel.searchQuery.value = it

        }


        //it refers to the view jia
        binding.searchFilter.setOnClickListener{openMenu(it)
        }
        return binding.root
    }

    private fun openMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val inflater: MenuInflater = popupMenu.menuInflater
        inflater.inflate(R.menu.thread_filter_menu, popupMenu.menu)
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener {


            when (it.itemId) {
                R.id.show_owned -> {
                    skeinCheckerViewModel.filterSkein.value = FilterSkein.BY_OWNED


                }
                R.id.show_all -> {
                    skeinCheckerViewModel.filterSkein.value = FilterSkein.BY_ALL
                }

                R.id.show_unowned -> {
                    skeinCheckerViewModel.filterSkein.value = FilterSkein.BY_UNOWNED
                }

                R.id.show_in_use ->{}

                R.id.show_shopping_cart ->{}
            }
            true
        }

    }
}


