package android.jia.stitchworks.skeinadder

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.jia.stitchworks.R
import android.jia.stitchworks.database.SkeinDatabase
import android.jia.stitchworks.databinding.FragmentSkeinAdderBinding


import androidx.databinding.DataBindingUtil

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

        return binding.root
    }


}