package org.baghdad.logic.module.exceptions

class ErrorFetchingWeatherData(msg: String): Exception(msg)
class NotValidCityNameException(msg: String): Exception(msg)