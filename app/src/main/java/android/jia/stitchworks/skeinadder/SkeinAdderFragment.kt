package android.jia.stitchworks.skeinadder


import android.jia.stitchworks.MainActivity
import android.jia.stitchworks.R
import android.jia.stitchworks.database.Skein
import android.jia.stitchworks.database.SkeinDatabase
import android.jia.stitchworks.databinding.FragmentSkeinAdderBinding
import android.jia.stitchworks.onQueryTextChanged
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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SkeinAdderFragment : Fragment() {


    private lateinit var skeinAdderViewModel: SkeinAdderViewModel
    private lateinit var binding: FragmentSkeinAdderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
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


        val adapter = SkeinAdderAdapter(SkeinAdderListener { Skein ->
            check(Skein)
        })

        binding.skeinSelectorRecycler.adapter = adapter
        binding.skeinSelectorRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.skeinSelectorRecycler.setHasFixedSize(true)

        binding.skeinStartInserter.requestFocus()

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                binding.skeinSelectorRecycler.scrollToPosition(0)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                binding.skeinSelectorRecycler.scrollToPosition(0)
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                binding.skeinSelectorRecycler.scrollToPosition(0)
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                binding.skeinSelectorRecycler.scrollToPosition(0)
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                binding.skeinSelectorRecycler.scrollToPosition(0)
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                binding.skeinSelectorRecycler.scrollToPosition(0)
            }
        })


        skeinAdderViewModel.threads.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    adapter.submitList(it)
                }
            })

        skeinAdderViewModel.submitMessage.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError)
                showError()
        })

        skeinAdderViewModel.clearThreads.observe(
            viewLifecycleOwner,
            Observer { hasThreads -> if (hasThreads) clear() })

        skeinAdderViewModel.clearStartQuery.observe(
            viewLifecycleOwner,
            Observer { hasQuery -> if (hasQuery) clearQuery(1) })

        skeinAdderViewModel.clearEndQuery.observe(
            viewLifecycleOwner,
            Observer { hasQuery -> if (hasQuery) clearQuery(2) })

        binding.skeinStartInserter.onQueryTextChanged {
            skeinAdderViewModel.searchQuery.value = it

        }

        binding.skeinEndInserter.onQueryTextChanged {
            skeinAdderViewModel.searchQuery.value = it

        }


        binding.skeinSlider.addOnChangeListener { slider, value, fromUser ->
            //JIA YOU gotta set each of these to make sure everything is correct on change(all empty right stuff is hidden) submit button unclickable
            when (value.toInt()) {
                0 -> {
                    binding.skeinSeperator.isGone = true
                    binding.skeinEndInserter.isGone = true
                    skeinSliderClear()
                    skeinAdderViewModel.filterSkeinOption.value = FilterSkeinOption.ADD_ONE
                }
                1 -> {
                    binding.skeinSeperator.isGone = false
                    binding.skeinEndInserter.isGone = false
                    skeinSliderClear()
                    skeinAdderViewModel.filterSkeinOption.value = FilterSkeinOption.ADD_RANGE
                }
                2 -> {
                    binding.skeinSeperator.isGone = true
                    binding.skeinEndInserter.isGone = true
                    skeinSliderClear()
                    skeinAdderViewModel.filterSkeinOption.value = FilterSkeinOption.REMOVE_ONE
                }
                3 -> {
                    binding.skeinSeperator.isGone = false
                    binding.skeinEndInserter.isGone = false
                    skeinSliderClear()
                    skeinAdderViewModel.filterSkeinOption.value = FilterSkeinOption.REMOVE_RANGE
                }
            }
        }





        return binding.root
    }

    private fun clearQuery(query: Int) {
        if (query == 1) {
            binding.startSkeinTextView.isGone = true
            skeinAdderViewModel.startSkein.value = null
            binding.skeinStartInserter.isGone = false
            binding.skeinStartInserter.setQuery("", false)
            binding.skeinStartInserter.requestFocus()

        } else {

            binding.skeinEndTextView.isGone = true
            skeinAdderViewModel.endSkein.value = null
            binding.skeinEndInserter.isGone = false
            binding.skeinEndInserter.setQuery("", false)
            binding.skeinEndInserter.requestFocus()
        }
    }

    private fun showError() {
        Toast.makeText(
            context, "Error, please fill in both searchbars", Toast.LENGTH_SHORT
        ).show()
        skeinAdderViewModel.resetSubmitMessage()
    }

    private fun skeinSliderClear() {
        //this fires after the slider changes value making sure all values are null and empty
        binding.startSkeinTextView.isGone = true
        binding.skeinEndTextView.isGone = true
        skeinAdderViewModel.startSkein.value = null
        skeinAdderViewModel.endSkein.value = null
        binding.skeinStartInserter.isGone = false
        binding.skeinStartInserter.setQuery("", false)
        binding.skeinEndInserter.setQuery("", false)
    }

    fun clear() {
        //this fires after the submit button is pressed, resetting both searchbars
        binding.skeinStartInserter.isGone = false
        binding.skeinSeperator.isGone = false
        binding.skeinEndInserter.isGone = false
        binding.startSkeinTextView.isGone = true
        binding.skeinEndTextView.isGone = true
        binding.skeinStartInserter.setQuery("", false)
        binding.skeinEndInserter.setQuery("", false)
        skeinAdderViewModel.startSkein.value = null
        skeinAdderViewModel.endSkein.value = null
        skeinAdderViewModel.resetClearThreads()

    }

    fun check(skein: Skein) {
        binding.skeinStartInserter.setQuery("", false)
        when (binding.skeinSlider.value.toInt()) {

            0 -> skeinAdderViewModel.passThreads(skein)

            1 -> if (binding.skeinStartInserter.hasFocus()) {
                skeinAdderViewModel.startSkein.value = skein
                binding.startSkeinTextView.isGone = false
                binding.skeinStartInserter.isGone = true

                skeinAdderViewModel.searchQuery.value = ""
                binding.skeinEndInserter.requestFocus()

            } else {
                skeinAdderViewModel.endSkein.value = skein
                binding.skeinEndTextView.isGone = false
                binding.skeinEndInserter.isGone = true
                skeinAdderViewModel.searchQuery.value = ""
                binding.skeinStartInserter.requestFocus()

            }

            2 -> skeinAdderViewModel.passThreads(skein)

            3 -> if (binding.skeinStartInserter.hasFocus()) {
                skeinAdderViewModel.startSkein.value = skein
                binding.startSkeinTextView.isGone = false
                binding.skeinStartInserter.isGone = true

                skeinAdderViewModel.searchQuery.value = ""
                binding.skeinEndInserter.requestFocus()

            } else {
                skeinAdderViewModel.endSkein.value = skein
                binding.skeinEndTextView.isGone = false
                binding.skeinEndInserter.isGone = true
                skeinAdderViewModel.searchQuery.value = ""
                binding.skeinStartInserter.requestFocus()

            }
        }

    }
}
