package com.prep.android.restaurantapp

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.prep.android.restaurantapp.model.RestaurantBrief
import com.prep.android.restaurantapp.model.RestaurantViewModel
import com.prep.android.restaurantapp.repository.RestaurantRepository
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class RestaurantViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    lateinit var restaurantViewModel: RestaurantViewModel
    private val restaurantOne: RestaurantBrief = mock()
    private val restaurantTwo: RestaurantBrief = mock()

    @Mock
    lateinit var application: Application

    @Mock
    lateinit var observer: Observer<List<RestaurantBrief>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        restaurantViewModel = RestaurantViewModel(application)
        restaurantViewModel.getRestaurantBriefs().observeForever(observer)
        mockkObject(RestaurantRepository)
        val fakeMutableLiveData = MutableLiveData<List<RestaurantBrief>>()
        val fakeCache = arrayListOf<RestaurantBrief>()
        every { RestaurantRepository.getRestaurantLiveData() } returns fakeMutableLiveData
        every { RestaurantRepository.fetchMoreRestaurants(any(), any()) } answers {
            RestaurantRepository.getRestaurantLiveData().postValue(listOf(restaurantOne, restaurantTwo))
        }
        every { RestaurantRepository.resetCache() } answers {
            fakeCache.clear()
            RestaurantRepository.getRestaurantLiveData().postValue(listOf())
        }
    }

    @Test
    fun fetchMoreRestaurantCallRepoTest() {
        restaurantViewModel.fetchMoreRestaurant()
        verify(1) {
            RestaurantRepository.fetchMoreRestaurants(0.0, 0.0)
        }
    }

    @Test
    fun fetchMoreRestaurantLiveDataUpdateTest() {
        val target = restaurantViewModel.getRestaurantBriefs()
        restaurantViewModel.fetchMoreRestaurant()
        assertEquals(2, restaurantViewModel.getRestaurantBriefs().value!!.size)
    }

    @Test
    fun clearDataTest() {
        val target = restaurantViewModel.getRestaurantBriefs()
        restaurantViewModel.fetchMoreRestaurant()
        restaurantViewModel.clearData()
        assertEquals(0, restaurantViewModel.getRestaurantBriefs().value!!.size)
    }

    @After
    fun afterTests() {
        unmockkAll()
    }
}