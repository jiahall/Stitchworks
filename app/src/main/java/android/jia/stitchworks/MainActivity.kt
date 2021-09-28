package android.jia.stitchworks

import android.jia.stitchworks.skeinadder.SkeinAdderFragment
import android.jia.stitchworks.skeinchecker.SkeinCheckerFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val skeinCheckerFragment = SkeinCheckerFragment()
    private val skeinAdderFragment = SkeinAdderFragment()
    lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_nav)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.skeinCheckerFragment -> replaceFragment(skeinCheckerFragment)
                R.id.skeinAdderFragment -> replaceFragment(skeinAdderFragment)
            }
            true
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.commit()

        }

    }
}