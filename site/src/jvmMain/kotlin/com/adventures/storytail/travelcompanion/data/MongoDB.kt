package com.adventures.storytail.travelcompanion.data

import com.adventures.storytail.travelcompanion.models.User
import com.adventures.storytail.travelcompanion.util.Constants.DATABASE_NAME
import com.adventures.storytail.travelcompanion.util.Constants.PASSWORD_FIELD
import com.adventures.storytail.travelcompanion.util.Constants.USERNAME_FIELD
import com.adventures.storytail.travelcompanion.util.Constants.USER_COLLECTION
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import kotlinx.coroutines.flow.first

@InitApi
fun initMongoDB(context: InitApiContext) {
    System.setProperty("org.litote.mongo.test.mapping.service", "org.litote.kmongo.jackson.JacksonClassMappingTypeService")
    context.data.add(MongoDB(context))
}

class MongoDB(private val context: InitApiContext): MongoRepository {

    private val client = MongoClient.create()
    private val database = client.getDatabase(DATABASE_NAME)
    private val userCollection = database.getCollection<User>(USER_COLLECTION)

    override suspend fun checkUserExistence(user: User): User? {
        return try {
            userCollection.find(
                and(
                    eq(USERNAME_FIELD, user.username),
                    eq(PASSWORD_FIELD, user.password)
                )
            ).first()
        } catch (e: Exception) {
            context.logger.error(e.message.toString())
            null
        }

    }
}