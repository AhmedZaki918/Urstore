package com.example.urstore.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.urstore.data.model.drinks.DrinksDataDto
import com.example.urstore.data.network.APIService
import com.example.urstore.data.network.SafeApiCall
import com.example.urstore.data.pagination.GenericPagingSource
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class SeeAllRepo @Inject constructor(
    private val api: APIService
) : SafeApiCall {

    fun getAllDrinks(): Flow<PagingData<DrinksDataDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = 6,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GenericPagingSource { page ->
                    api.allDrinks(page)
                }
            }
        ).flow
    }
}