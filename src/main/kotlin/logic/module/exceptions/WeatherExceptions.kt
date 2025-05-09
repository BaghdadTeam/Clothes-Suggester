package org.baghdad.logic.module.exceptions

class ErrorFetchingWeatherData(message: String) : Exception(message)
class NotValidCityNameException(msg: String): Exception(msg)