package android.jia.stitchworks.skeinchecker

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.jia.stitchworks.R
import android.jia.stitchworks.database.SkeinDatabase
import android.jia.stitchworks.databinding.FragmentSkeinCheckerBinding
import android.view.*
import android.widget.PopupMenu
import android.widget.SearchView

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

class SkeinCheckerFragment : Fragment() {



//these are here so i can talk to adapter/vm outside of onCreate
private lateinit var skeinCheckerViewModel: SkeinCheckerViewModel
private lateinit var adapter: SkeinAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        val binding: FragmentSkeinCheckerBinding = DataBindingUtil.inflate(inflater,
        R.layout.fragment_skein_checker, container, false)

        val application = requireNotNull(this.activity).application


        val dataSource = SkeinDatabase.getInstance(application).skeinDatabaseDao
        val viewModelFactory = SkeinCheckerViewModelFactory(dataSource)


         skeinCheckerViewModel =
            ViewModelProvider(this, viewModelFactory)
                .get(SkeinCheckerViewModel::class.java)

        binding.skeinCheckerViewModel = skeinCheckerViewModel

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

         adapter = SkeinAdapter()
        binding.skeinList.adapter = adapter

        skeinCheckerViewModel.threads.observe(viewLifecycleOwner, Observer { it?.let{adapter.data = it} })

        binding.searchView.queryHint= "hello, type here"




        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                return true
            }

            //you gotta change this to filter the threads observable
            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null){//add and for null case
                    //I GUESS YOUD ADD AN OBSERVER HERE TOO OR MAYBE TURN THE OBSERVER INTO A WHEN CASE

                    skeinCheckerViewModel.searchDatabase(query).observe(viewLifecycleOwner, { list ->
                        list.let {
                            adapter.data = it
                        }
                    })
                    //need to order the big datalist to actually get here
                }else if(query == ""){
                    skeinCheckerViewModel.threads.observe(viewLifecycleOwner, Observer { it?.let{adapter.data = it} })
                }
                return true
            }

        })


        //it refers to the view jia
        binding.searchFilter.setOnClickListener{openMenu(it)}
        return binding.root
    }

    private fun openMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val inflater: MenuInflater = popupMenu.menuInflater
        inflater.inflate(R.menu.thread_filter_menu, popupMenu.menu)
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener {


            //we gotta figure this out
            when (it.itemId) {
                R.id.show_owned -> {
                    skeinCheckerViewModel.getOwned()
                }
                R.id.show_all -> {
                    skeinCheckerViewModel.updateGetAll()
                }

                R.id.show_unowned -> {
                    skeinCheckerViewModel.getUnowned()
                }

                R.id.show_in_use ->{}

                R.id.show_shopping_cart ->{}
            }
            true
        }

    }
}
