package com.example.urstore.util

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.model.ItemDetails
import com.example.urstore.data.model.drinks_dto.DrinksDataDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSharedViewModel @Inject constructor(
) : BaseViewModel<ProductIntent>() {

    private val _itemDetails = MutableStateFlow(ItemDetails())
    val itemDetails: StateFlow<ItemDetails> = _itemDetails.asStateFlow()

    override fun onIntent(intent: ProductIntent) {
        if (intent is ProductIntent.OnProductClicked) {
            savePopularItem(intent.item)
        }
    }

    private fun savePopularItem(item: DrinksDataDto) {
        viewModelScope.launch {
            _itemDetails.update {
                it.copy(
                    description = item.description,
                    imageName = item.imageName,
                    id = item.id,
                    rate = item.rate,
                    price = item.price,
                    title = item.title
                )
            }
        }
    }
}