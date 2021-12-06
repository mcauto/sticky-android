package com.deo.sticky.features.checkout.models

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.deo.sticky.MainCoroutinesRule
import com.deo.sticky.features.checkin.category.models.CategoryDao
import com.deo.sticky.features.checkin.place.models.PlaceDao
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AccumulateDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutinesRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Inject
    lateinit var accumulateDao: AccumulateDao

    @Inject
    lateinit var placeDao: PlaceDao

    @Inject
    lateinit var categoryDao: CategoryDao

    @Test
    fun `처음 방문하는 장소 체크인 하기`() = runBlockingTest {
        // 장소 입력, 카테고리 입력
    }
}
