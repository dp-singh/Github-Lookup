package com.dpsingh.githublookup.domain.repository

import com.dpsingh.githublookup.data.local.RealmDao
import com.dpsingh.githublookup.data.remote.Api
import com.dpsingh.githublookup.domain.model.User
import com.dpsingh.githublookup.schedulers.BaseScheduler
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.*
import org.mockito.Mockito.*


@RunWith(JUnit4::class)
class UserRepositoryTest {


    @Mock
    private lateinit var api: Api

    @Mock
    private lateinit var realmDao: RealmDao

    @Mock
    private lateinit var mScheduler: BaseScheduler

    @InjectMocks
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(mScheduler.io()).thenReturn(Schedulers.trampoline())
        `when`(mScheduler.ui()).thenReturn(Schedulers.trampoline())
    }


    @Test
    fun test_getUsersWhenInDB() {

        val userTest = getTestUser()
        `when`(realmDao.getUser(userName)).thenReturn(userTest)

        val user = userRepository.getUser(userName)
        user.test().assertResult(getTestUser())

        verify(realmDao).getUser(userName)
        verifyNoMoreInteractions(api)
    }

    @Test
    fun test_getUsersWhenNotInDb() {

        `when`(realmDao.getUser(userName)).thenReturn(null)
        `when`(api.getUserDetails(userName)).thenReturn(getTestUserSingle())
        doNothing().`when`(realmDao).storeOrUpdateUser(getTestUser())


        val user = userRepository.getUser(userName)
        user.test().assertResult(getTestUser())

        verify(realmDao).getUser(userName)
        verify(realmDao).storeOrUpdateUser(getTestUser())
        verify(api).getUserDetails(userName)
        verify(mScheduler).io()
        verify(mScheduler).ui()

        verifyNoMoreInteractions(api, realmDao, mScheduler)
    }


    @Test
    fun test_loadSearchHistory() {
        `when`(realmDao.getAllUser()).thenReturn(getListOfTestUserFlowable())
        val userList = userRepository.loadSearchHistory()
        userList.test().assertResult(getListOfUsers())
        verify(realmDao).getAllUser()
        verifyNoMoreInteractions(api, realmDao, mScheduler)
    }


    companion object {

        val userName = "dp-singh"

         fun getTestUserSingle() = Single.just(getTestUser())
         fun getListOfTestUserFlowable() = Flowable.just(getListOfUsers())

         fun getListOfUsers() = listOf(getTestUser(), getTestUser())
         fun getTestUser(): User {
            return User(
                    id = 2740228,
                    login = userName,
                    name = "dharmendra pratap singh",
                    avatar_url = "https://avatars0.githubusercontent.com/u/2740228?v=4",
                    company = "lifcare",
                    location = "delhi",
                    bio = null,
                    followers = 0,
                    following = 0,
                    public_gists = 0,
                    public_repos = 0
            )
        }
    }

}