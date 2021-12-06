package com.deo.sticky.features.checkin.category.models

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.deo.sticky.MainCoroutinesRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class CategoryDaoTest {
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
    lateinit var categoryDao: CategoryDao

    @Test
    fun `카테고리 불러오기`() = runBlocking {
//        assertThat(categoryDao.getAll())
    }
}
