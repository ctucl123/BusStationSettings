package com.ctucl.busstationsettings.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ctucl.busstationsettings.data.local.DeviceEntity
import com.ctucl.busstationsettings.data.local.DeviceRepositoryImpl

import kotlinx.coroutines.launch

class HomeScreenViewModel(private val repository: DeviceRepositoryImpl):ViewModel(){
    val allDevices = repository.getAllDevices()
    fun insert(device: DeviceEntity) = viewModelScope.launch {
        repository.insertDevice(device)
    }
    fun deleteDevice(device: DeviceEntity) = viewModelScope.launch {
        repository.deleteDevice(device)
    }

}

class DevicesViewModelFactory(private val repository: DeviceRepositoryImpl):ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>):T{
        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeScreenViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}