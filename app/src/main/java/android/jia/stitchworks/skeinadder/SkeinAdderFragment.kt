package android.jia.stitchworks.skeinadder


import android.jia.stitchworks.R
import android.jia.stitchworks.database.SkeinDatabase
import android.jia.stitchworks.databinding.FragmentSkeinAdderBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

class SkeinAdderFragment : Fragment() {


    private lateinit var skeinAdderViewModel: SkeinAdderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentSkeinAdderBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_skein_adder, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = SkeinDatabase.getInstance(application).skeinDatabaseDao
        val viewModelFactory = SkeinAdderViewModelFactory(dataSource)

        skeinAdderViewModel =
            ViewModelProvider(this, viewModelFactory)
                .get(SkeinAdderViewModel::class.java)



        binding.skeinAdderViewModel = skeinAdderViewModel

        binding.lifecycleOwner = this


        val adapter = SkeinAdderAdapter(SkeinAdderListener { brandNumber ->
            passThreads(binding.skeinSlider.value.toInt(), brandNumber)
        })

        binding.skeinSelectorRecycler.adapter = adapter


        skeinAdderViewModel.threads.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    adapter.submitList(it)
                }
            })






        binding.skeinSlider.addOnChangeListener { slider, value, fromUser ->
            when (value.toInt()) {
                0 -> {
                    binding.skeinSeperator.isGone = false
                    binding.skeinEndInserter.isGone = false
                    skeinAdderViewModel.getUnowned()

                }
                1 -> {
                    binding.skeinSeperator.isGone = true
                    binding.skeinEndInserter.isGone = true
                    skeinAdderViewModel.getUnowned()
                }
                2 -> {
                    binding.skeinSeperator.isGone = true
                    binding.skeinEndInserter.isGone = true
                    skeinAdderViewModel.getOwned()
                }
                3 -> {
                    binding.skeinSeperator.isGone = false
                    binding.skeinEndInserter.isGone = false
                    skeinAdderViewModel.getOwned()
                }
            }
        }





        return binding.root
    }

    private fun passThreads(value: Int, brandNumber: String) {
        when (value) {
            0 -> {
                skeinAdderViewModel.addThread(brandNumber)
                Toast.makeText(context, "just added $brandNumber to database", Toast.LENGTH_LONG)
                    .show()
            }
            1 -> {
                skeinAdderViewModel.addThread(brandNumber)
                Toast.makeText(context, "just added $brandNumber to database", Toast.LENGTH_LONG)
                    .show()
            }
            2 -> {
                skeinAdderViewModel.removeThread(brandNumber)
                Toast.makeText(context, "just removed $brandNumber to database", Toast.LENGTH_LONG)
                    .show()
            }
            3 -> {
                skeinAdderViewModel.removeThread(brandNumber)
                Toast.makeText(context, "just removed $brandNumber to database", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }


}
