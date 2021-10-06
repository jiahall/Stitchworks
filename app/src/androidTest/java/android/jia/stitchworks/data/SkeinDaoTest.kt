package android.jia.stitchworks.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SkeinDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var skeinDao: SkeinDao

    private val skeinA = Skein("DMC-001", 1, "red-light", "1", "#c78559", 1, false)
    private val skeinB = Skein("DMC-002", 2, "red-medlight", "2", "#c78559", 0, false)
    private val skeinC = Skein("DMC-003", 3, "red-normal", "3", "#c78559", 1, true)
    private val skeinD = Skein("DMC-004", 4, "red-dark", "4", "#c78559", 0, false)
    private val skeinE = Skein("DMC-005", 5, "red-veryDark", "5", "#c78559", 1, false)
    private val skeinF = Skein("DMC-006", 6, "red-ULTdark", "6", "#c78559", 0, true)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        skeinDao = database.skeinDao

        skeinDao.insertAll(listOf(skeinA, skeinC, skeinE, skeinB, skeinD, skeinF))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getSkeins() = runBlocking {
        val skeinList = skeinDao.getAll("").first()
        assertThat(skeinList.size, equalTo(6))


        //making sure they're all in order of skeinNumber
        assertThat(skeinList[0], equalTo(skeinA))
        assertThat(skeinList[1], equalTo(skeinB))
        assertThat(skeinList[2], equalTo(skeinC))
        assertThat(skeinList[3], equalTo(skeinD))
        assertThat(skeinList[4], equalTo(skeinE))
        assertThat(skeinList[5], equalTo(skeinF))

        val skeinListQuery = skeinDao.getAll("normal").first()
        assertThat(skeinListQuery.size, equalTo(1))

        assertThat(skeinListQuery[0], equalTo(skeinC))
    }

    @Test
    fun getOwnedSkeins() = runBlocking {
        val ownedSkeinList = skeinDao.getOwned("").first()

        assertThat(ownedSkeinList.size, equalTo(3))


        //making sure they're all in order of skeinNumber
        assertThat(ownedSkeinList[0], equalTo(skeinA))
        assertThat(ownedSkeinList[1], equalTo(skeinC))
        assertThat(ownedSkeinList[2], equalTo(skeinE))

        val ownedSkeinListQuery = skeinDao.getOwned("light").first()

        assertThat(ownedSkeinListQuery.size, equalTo(1))
    }

    @Test
    fun getUnownedSkeins() = runBlocking {
        val unownedSkeinList = skeinDao.getUnowned("").first()

        assertThat(unownedSkeinList.size, equalTo(3))


        //making sure they're all in order of skeinNumber
        assertThat(unownedSkeinList[0], equalTo(skeinB))
        assertThat(unownedSkeinList[1], equalTo(skeinD))
        assertThat(unownedSkeinList[2], equalTo(skeinF))

        val unownedSkeinListQuery = skeinDao.getUnowned("ULT").first()
        assertThat(unownedSkeinListQuery.size, equalTo(1))
    }

    @Test
    fun addThread() = runBlocking {
        skeinDao.addThread("DMC-002")
        val thread = skeinDao.get("DMC-002")

        assertThat(thread.amountOfSkeins, equalTo(1))
    }

    @Test
    fun removeThread() = runBlocking {
        skeinDao.removeThread("DMC-005")
        val thread = skeinDao.get("DMC-005")

        assertThat(thread.amountOfSkeins, equalTo(0))
    }

    @Test
    fun addThreadRange() = runBlocking {
        skeinDao.addThreadRange(1, 3)
        val addThreadRange = skeinDao.getOwned("").first()

        assertThat(addThreadRange.size, equalTo(4))

        assertThat(addThreadRange[0].amountOfSkeins, equalTo(1))
        assertThat(addThreadRange[1].amountOfSkeins, equalTo(1))
        assertThat(addThreadRange[2].amountOfSkeins, equalTo(1))
    }

    @Test
    fun removeThreadRange() = runBlocking {
        skeinDao.removeThreadRange(4, 6)
        val removeThreadRange = skeinDao.getUnowned("").first()

        assertThat(removeThreadRange.size, equalTo(4))

        assertThat(removeThreadRange[0].amountOfSkeins, equalTo(0))
        assertThat(removeThreadRange[1].amountOfSkeins, equalTo(0))
        assertThat(removeThreadRange[2].amountOfSkeins, equalTo(0))
    }


}