package org.baghdad.data.source.remote.clothes

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.kotlin.client.coroutine.MongoClient
import io.github.cdimascio.dotenv.dotenv
import org.baghdad.logic.module.exceptions.MongoDBConnectionException

class MongoClientProvider {
   val connectionString = dotenv()["MONGO_CONNECTION_STRING"]
       ?: throw MongoDBConnectionException("No connection string found")

    fun getClient(): MongoClient {
        val serverApi = ServerApi.builder().version(ServerApiVersion.V1).build()
        val settings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(connectionString))
            .serverApi(serverApi)
            .build()
        return MongoClient.create(settings)
    }
}