package com.example.urstore.presentation.see_all

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.urstore.data.model.drinks_dto.DrinksDataDto
import com.example.urstore.data.repository.SeeAllRepo
import com.example.urstore.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class SeeAllViewModel @Inject constructor(
    private val seeAllRepo: SeeAllRepo
) : BaseViewModel<SeeAllIntent>() {

    private var _drinks: Flow<PagingData<DrinksDataDto>> = flowOf(PagingData.empty())
    val drinks: Flow<PagingData<DrinksDataDto>> get() = _drinks

    init {
        displaySeeAll()
    }

    override fun onIntent(intent: SeeAllIntent) {
        if (intent is SeeAllIntent.AddToCart) {
            addToCart(intent.item)
        }
    }


    private fun displaySeeAll() {
        _drinks = seeAllRepo.getAllDrinks()
            .cachedIn(viewModelScope)
    }

    private fun addToCart(item: DrinksDataDto) {
    }
}
