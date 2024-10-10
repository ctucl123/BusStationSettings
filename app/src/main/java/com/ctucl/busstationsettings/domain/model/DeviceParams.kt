package com.ctucl.busstationsettings.domain.model

data class DeviceParams(
    var time_turnstile: String = "0",
    var time_open_actuator: String ="0",
    var time_close_actuator: String ="0",
    var time_special_door: String ="0",
    var time_delay_turnstile: String ="0",
    var time_delay_special: String ="0",
    var place:String = "",
    var uuid:String = "",
    var lat:String = "",
    var lon:String = ""
)

data class DeviceParamsPost(
    val place: String,
    val time_turnstile: Int,
    val time_open_actuator: Int,
    val time_close_actuator: Int,
    val time_special_door: Int,
    val time_delay_turnstile: Int,
    val time_delay_special: Int,
    val date: String,
    val uuid: String,
    val lat: String,
    val lon: String
)
data class MecanismResponse(
    val time_turnstile: String,
    val time_open_actuator: String,
    val time_close_actuator: String,
    val time_special_door: String,
    val time_delay_turnstile: String,
    val time_delay_special: String,
    val place: String,
    val uuid: String,
    val lat: String,
    val lon: String
)

data class DbResponse(
    val date: String,
    val uuid: String,
    val lat: String,
    val lon: String
)
data class TransactionResponse(
    val id: Int,
    val code: String,
    val type: String,
    val date_card: String,
    val time_card: String,
    val place: String,
    val cost: Float,
    val previous: Float,
    val balance: Float,
    val uuid: String,
    val lat: String,
    val lon: String,
    val date:String
)
data class apiPost(
    val operation: String,
)

data class apiActionResponse(
    val result: Any
)

data class apiDbResponse(
    val result: List<TransactionResponse>
)