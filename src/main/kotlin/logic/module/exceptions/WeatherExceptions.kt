package org.baghdad.logic.module.exceptions

class InvalidWeatherApiKey(message: String) : Exception(message)
class ErrorFetchingWeatherData(message: String) : Exception(message)
class NotValidCityNameException(msg: String): Exception(msg)