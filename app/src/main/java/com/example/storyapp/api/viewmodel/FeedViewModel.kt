package com.example.storyapp.api.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.R
import com.example.storyapp.api.model.FeedsAPIResponse
import com.example.storyapp.api.repository.FeedRepository
import com.example.storyapp.api.utils.ErrorHandler
import com.example.storyapp.api.utils.Resource
import com.example.storyapp.extras.CheckNetwork
import com.example.storyapp.extras.MyApplication
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response

class FeedViewModel : ViewModel() {
    val feedLiveEvent: MutableLiveData<Resource<Any?>?> = MutableLiveData()


    fun getFeedAsync() {
        viewModelScope.launch {
            feedLiveEvent.postValue(Resource.loading(null))
            try {
                coroutineScope {
                    val checkNetwork = CheckNetwork()
                    if (checkNetwork.isNetworkAvailable) {
                        //Request
                        val request = FeedRepository.getFeedAsync()
                        //Response
                        val response: Response<FeedsAPIResponse> = request.await()
                        //Success
                        if (response.isSuccessful) {
                            val feedsAPIResponse: FeedsAPIResponse? = response.body()
                            feedLiveEvent.postValue(Resource.success(data = feedsAPIResponse))
                        } else {
                            //Error
                            val errorResponse = ErrorHandler.getDetailError(response.errorBody())
                            feedLiveEvent.postValue(errorResponse?.getErrorMessage()?.let {
                                Resource.error(
                                    data = errorResponse,
                                    message = it
                                )
                            })
                        }
                    } else {
                        //No Network Available
                        feedLiveEvent.postValue(
                            Resource.error(
                                data = null, message = MyApplication.getAppContext().getString(
                                    R.string.no_network_available
                                )
                            )
                        )
                    }
                }
            }
            //Exception
            catch (e: Exception) {
                feedLiveEvent.postValue(
                    Resource.error(
                        data = null,
                        message = e.message ?: MyApplication.getAppContext()
                            .getString(R.string.error_occurred)
                    )
                )
            }
        }
    }
}