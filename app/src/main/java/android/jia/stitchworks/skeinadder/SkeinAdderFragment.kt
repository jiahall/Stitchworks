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
import android.jia.stitchworks.skeinchecker.SkeinCheckerAdapter
import android.jia.stitchworks.skeinchecker.SkeinListener
import android.widget.Toast
import androidx.core.view.isGone


import androidx.databinding.DataBindingUtil
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider

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







        binding.skeinSlider.addOnChangeListener { slider, value, fromUser ->
            when (value.toInt()) {
                0 -> {
                    binding.skeinSeperator.isGone = false
                    binding.skeinEndInserter.isGone = false
                }
                1 -> {
                    binding.skeinSeperator.isGone = true
                    binding.skeinEndInserter.isGone = true
                }
                2 -> {
                    binding.skeinSeperator.isGone = true
                    binding.skeinEndInserter.isGone = true
                }
                3 -> {
                    binding.skeinSeperator.isGone = false
                    binding.skeinEndInserter.isGone = false
                }
            }
        }

        return binding.root
    }

}
