package android.jia.stitchworks.skeinchecker

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.jia.stitchworks.R
import android.jia.stitchworks.database.Skein
import android.jia.stitchworks.database.SkeinDatabase
import android.jia.stitchworks.databinding.FragmentSkeinCheckerBinding
import android.widget.SearchView

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar

class SkeinCheckerFragment : Fragment() {





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        val binding: FragmentSkeinCheckerBinding = DataBindingUtil.inflate(inflater,
        R.layout.fragment_skein_checker, container, false)

        val application = requireNotNull(this.activity).application


        val dataSource = SkeinDatabase.getInstance(application).skeinDatabaseDao
        val viewModelFactory = SkeinCheckerViewModelFactory(dataSource)


        val skeinCheckerViewModel =
            ViewModelProvider(this, viewModelFactory)
                .get(SkeinCheckerViewModel::class.java)

        binding.skeinCheckerViewModel = skeinCheckerViewModel

        binding.lifecycleOwner = this

        val adapter = SkeinAdapter()
        binding.skeinList.adapter = adapter

        skeinCheckerViewModel.threads.observe(viewLifecycleOwner, Observer { it?.let{adapter.data = it} })
        //have a whencase in the viewmodel for this e.g "threads will be one of the filters not just get all"
        //

        binding.searchView.queryHint= "hello type here"



        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null){
                    //I GUESS YOUD ADD AN OBSERVER HERE TOO OR MAYBE TURN THE OBSERVER INTO A WHEN CASE
                    val searchQuery = "%$query%"
                    skeinCheckerViewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner, { list ->
                        list.let {
                            adapter.data = it
                        }
                    })
                }
                return true
            }

        })






        return binding.root
    }





}


