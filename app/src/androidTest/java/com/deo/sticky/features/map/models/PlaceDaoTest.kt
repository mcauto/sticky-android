package com.deo.sticky.features.map.models

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.deo.sticky.MainCoroutinesRule
import com.google.common.truth.Truth.assertThat
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
class PlaceDaoTest {
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
    lateinit var placeDao: PlaceDao

    @Test
    fun `장소 저장하기`() = runBlockingTest {
        assertThat(
            placeDao.add("강남 12번 출구 커피빈", 37.4988373, 127.0292686)
        ).isAtLeast(1)
    }

    @Test
    fun `반경 내 장소 리스트 불러오기`() = runBlockingTest {
        assertThat(placeDao.add("강남 12번 출구 커피빈", 37.4988373, 127.0292686))
            .isAtLeast(1)
        assertThat(placeDao.add("강남 11번 출구 할리스커피", 37.4987334, 127.0278077))
            .isAtLeast(1)
        assertThat(placeDao.add("강남 1번 출구 스타벅스", 37.497853, 127.028587))
            .isAtLeast(1)
        // 강남역
        assertThat(placeDao.getPlacesWithinRadius(37.497952, 127.027619, radius = 1_000))
            .hasSize(3)
    }
}
