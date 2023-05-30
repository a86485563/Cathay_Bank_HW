package com.example.cathay_bank_hw.model

sealed class AttractionStatus<out T : Any>{
    class Success(val data: AttractionResponse) : AttractionStatus<AttractionResponse>()
    object NoContent : AttractionStatus<Nothing>()
    object Loading : AttractionStatus<Nothing>()
    object Error : AttractionStatus<Nothing>()
}
