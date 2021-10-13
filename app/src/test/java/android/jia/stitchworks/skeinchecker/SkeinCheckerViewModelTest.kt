package android.jia.stitchworks.skeinchecker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SkeinCheckerViewModelTest {


    private lateinit var skeinCheckerViewModel: SkeinCheckerViewModel

    // Executes each task synchronously using Architecture Components.
    @get: Rule
    private var instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        fun setUpViewModel() {
            skeinCheckerViewModel =
                SkeinCheckerViewModel(ApplicationProvider.getApplicationContext())
        }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun setFilterAllSkeins() {


        // When the filter type is ALL_TASKS
        val filterSkein = MutableStateFlow(FilterSkein.BY_ALL)


        // Then the "Add task" action is visible

    }
}