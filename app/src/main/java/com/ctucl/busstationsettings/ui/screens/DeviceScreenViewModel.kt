package com.ctucl.busstationsettings.ui.screens

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.ctucl.busstationsettings.domain.RetrofitInstance


import com.ctucl.busstationsettings.domain.model.DeviceParams
import com.ctucl.busstationsettings.domain.model.DeviceParamsPost
import com.ctucl.busstationsettings.domain.model.MecanismResponse
import com.ctucl.busstationsettings.domain.model.TransactionResponse
import com.ctucl.busstationsettings.domain.model.apiActionResponse
import com.ctucl.busstationsettings.domain.model.apiPost
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeviceScreenViewModel:ViewModel(){
    private val _data = MutableStateFlow<MecanismResponse?>(null)
    val data: StateFlow<MecanismResponse?> = _data
    private val _api_data = MutableStateFlow<apiActionResponse?>(null)
    val api_data: StateFlow<apiActionResponse?> = _api_data

    private val _api_db_data = MutableStateFlow<List<TransactionResponse>?>(null)
    val api_db_data: StateFlow<List<TransactionResponse>?> = _api_db_data

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
//    private val _retrofitIns = mutableStateOf(RetrofitInstance)
//    val retrofitIns: StateFlow<RetrofitInstance> = _retrofitIns
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private  val _parameters = mutableStateOf(
        DeviceParams(
            time_turnstile = "0",
            time_special_door = "0",
            time_open_actuator = "0",
            time_close_actuator = "0",
            time_delay_turnstile = "0",
            time_delay_special = "0",
            place = "",
            uuid = "",
            lat = "",
            lon = ""
            )
        )
    val parametersDevice:State<DeviceParams> = _parameters

    fun clearData(){
        _data.value = null
        _api_data.value = null
    }
    fun updateDevice(data: DeviceParams) {
        _parameters.value = data.copy()
    }
    fun fetchData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                //Do the petition
                val response = RetrofitInstance.api.getParams()

                // Manage json response
                Log.d("AppParadas", "Response: ${response}")
                // here you can use the dates of the API
                _data.value = response

            } catch (e: Exception) {
                Log.e("AppParadas", "Error to get Data: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun fetchApiAction(operation:String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                //Do the petition
                val response = RetrofitInstance.api.doSomething(apiPost(operation = operation))

                // Manage json response
                Log.d("AppParadas", "Response: ${response}")
                // here you can use the dates of the API
                _api_data.value = response

            } catch (e: Exception) {
                Log.e("AppParadas", "Error to get Data: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun fetchApiDb(operation:String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.getLastTransactions(operation)

                Log.d("AppParadas", "Response: ${response}")
                _api_db_data.value = response.result

            } catch (e: Exception) {
                Log.e("AppParadas", "Error to get Data: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun updateRetrofitBaseUrl(ip:String){
        RetrofitInstance.setBaseUrl(ip)
        Log.d("AppParadas", "Ip cambiada: ${ip}")
    }
    fun sendParamsDeviceApi(data:DeviceParamsPost) {
        val call = RetrofitInstance.api.postParams(data)
        val auxUpdate = DeviceParams(
            time_turnstile = data.time_turnstile.toString(),
            time_special_door = data.time_special_door.toString(),
            time_open_actuator = data.time_open_actuator.toString(),
            time_close_actuator = data.time_close_actuator.toString(),
            time_delay_turnstile = data.time_delay_turnstile.toString(),
            time_delay_special = data.time_delay_special.toString(),
            place = data.place,
            uuid = data.uuid,
            lat = data.lat,
            lon = data.lon
        )
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {

                    println("Data response: ${response.code()}")
                    _parameters.value = auxUpdate.copy()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("Error to sending data: ${t.message}")
            }
        })
    }

}