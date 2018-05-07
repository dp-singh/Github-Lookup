package com.dpsingh.githublookup.ui.repository_listing

import com.dpsingh.githublookup.data.local.RepoDataSource
import com.dpsingh.githublookup.domain.repository.RepoListRepository
import com.dpsingh.githublookup.schedulers.BaseScheduler
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class RepositoryListViewModelTest {


    @Mock
    lateinit var repoListRepository: RepoListRepository

    @Mock
    lateinit var scheduler: BaseScheduler

    @Mock

    lateinit var repoDataSource: RepoDataSource

    @InjectMocks
    private lateinit var repositoryListViewModel: RepositoryListViewModel


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repositoryListViewModel.repoDataSource = repoDataSource
    }


    @Test
    fun test_retry() {
        doNothing().`when`(repoDataSource).retry()
        repositoryListViewModel.retry()
        verify(repoDataSource).retry()
        verifyNoMoreInteractions(repoDataSource, scheduler, repoListRepository)
    }


    @Test
    fun test_setUserName() {
        val test = "hello"
        repositoryListViewModel.setUserName(test)
        assertSame(repoDataSource.userName, test)
        verifyNoMoreInteractions(repoDataSource, scheduler, repoListRepository)
    }
}