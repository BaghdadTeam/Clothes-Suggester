package org.baghdad.logic.module.exceptions

open class ClothesSuggestionExceptions(msg: String) : Exception(msg)
class NoClothesSuggestionException (msg: String): ClothesSuggestionExceptions(msg)
class MongoDBConnectionException (msg: String): ClothesSuggestionExceptions(msg)