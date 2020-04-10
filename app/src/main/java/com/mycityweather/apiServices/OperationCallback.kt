package com.mycityweather.apiServices

interface OperationCallback<T> {
    fun onSuccess(data:List<T>?)
    fun onError(error:String?)
}