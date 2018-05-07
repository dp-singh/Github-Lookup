package com.dpsingh.githublookup.ui.home

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.dpsingh.githublookup.data.remote.response.Response
import com.dpsingh.githublookup.domain.model.User
import com.dpsingh.githublookup.domain.repository.UserRepository
import com.dpsingh.githublookup.domain.repository.UserRepositoryTest
import com.dpsingh.githublookup.utils.ViewState
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class UserViewModelTest {


    @Rule
    @JvmField
    val instantExecutor = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    lateinit var observer: Observer<Response<User>>


    @Mock
    lateinit var listObserver: Observer<List<User>>


    @InjectMocks
    private lateinit var userViewModel: UserViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }


    @Test
    fun testSuccess_loadUser() {

        `when`(userRepository.getUser(anyString()))
                .thenReturn(UserRepositoryTest.getTestUserSingle())

        userViewModel.response.observeForever(observer)
        userViewModel.loadUser(UserRepositoryTest.userName)


        verify(userRepository).getUser(UserRepositoryTest.userName)
        verify(observer).onChanged(Response(ViewState.LOADING))
        verify(observer).onChanged(Response(ViewState.SUCCESS, data = UserRepositoryTest.getTestUser()))

        assert(userViewModel.response.value?.status == ViewState.SUCCESS)
        assert(userViewModel.response.value?.data == UserRepositoryTest.getTestUser())

        Mockito.verifyNoMoreInteractions(userRepository, observer)
    }


    @Test
    fun testError_loadUser() {

        val throwable = Throwable("Data not found")
        `when`(userRepository.getUser(anyString()))
                .thenReturn(Single.error(throwable))


        userViewModel.response.observeForever(observer)
        userViewModel.loadUser(UserRepositoryTest.userName)


        verify(userRepository).getUser(UserRepositoryTest.userName)
        verify(observer).onChanged(Response(ViewState.LOADING))
        verify(observer).onChanged(Response(ViewState.ERROR, error = throwable))

        assert(userViewModel.response.value?.status == ViewState.ERROR)
        assert(userViewModel.response.value?.error == throwable)

        Mockito.verifyNoMoreInteractions(userRepository, observer)
    }

    @Test
    fun test_loadSearches() {
        `when`(userRepository.loadSearchHistory())
                .thenReturn(UserRepositoryTest.getListOfTestUserFlowable())

        userViewModel.listUser.observeForever(listObserver)
        userViewModel.loadSearchHistory()

        verify(userRepository).loadSearchHistory()
        verify(listObserver).onChanged(UserRepositoryTest.getListOfUsers())

        assert(userViewModel.listUser.value == UserRepositoryTest.getListOfUsers())

        Mockito.verifyNoMoreInteractions(userRepository, listObserver)
    }
}