package retrofit

import java.lang.reflect.Proxy
import java.util.*
import kotlin.reflect.KClass

class Retrofit private constructor(val baseUrl: String, val client: Client) {
  fun <T : Any> create(service: Class<T>): T {
//    return create(service.kotlin)   // Use this, if you've provided implementation for KClass in the first place.

    val proxyInstance = Proxy.newProxyInstance(service.classLoader, arrayOf(service)) { _, method, args ->
      println("'${method.name}' was invoked with args: ${Arrays.deepToString(args)}.")
    }
    @Suppress("UNCHECKED_CAST")
    return proxyInstance as T
  }

  fun <T : Any> create(service: KClass<T>): T {
    return create(service.java)
  }

  internal fun validate(): Retrofit {
    println("!!!!!! internal fun validate() was called !!!!!!")
    return this
  }

  class Builder {
    private lateinit var baseUrl: String
    private var client: Client = Client()

    fun baseUrl(url: String) = this.also { it.baseUrl = url }

    fun client(client: Client) = this.also { it.client = client }

    fun build(): Retrofit = Retrofit(baseUrl, client)
  }
}

class Client

typealias RetroBuilder = Retrofit.Builder
