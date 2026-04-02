package com.example.urstore.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.urstore.util.BaseResponse
import retrofit2.Response

class GenericPagingSource<T : Any>(
    private val apiCall: suspend (page: Int) -> Response<BaseResponse<List<T>>>
) : PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val page = params.key ?: 1
            val response = apiCall(page).body()?.data ?: emptyList()

            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition
    }
}
