package io.github.magonxesp.godaddyddns

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class GodaddyClient(
    private val domain: String,
    private val apiKey: String,
    private val apiSecret: String
) {
    private val httpClient = OkHttpClient()

    @OptIn(ExperimentalSerializationApi::class)
    private val jsonEncoder = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        encodeDefaults = false
    }

    @Serializable
    data class HostNameResponse(val data: String)

    @Serializable
    data class DNSRecordCreateTypeName(
        val data: String,
        val port: Int? = null,
        val priority: Int? = null,
        val protocol: String? = null,
        val service: String? = null,
        val ttl: Int? = null,
        val weight: Int? = null,
    )

    fun updateGodaddyHostName(hostName: String, ipAddress: String) {
        val dnsRecord = listOf(DNSRecordCreateTypeName(data = ipAddress))
        val json = jsonEncoder.encodeToString(dnsRecord)

        val request = Request.Builder().apply {
            url("https://api.godaddy.com/v1/domains/$domain/records/A/$hostName")
            put(json.toRequestBody("application/json".toMediaType()))
            header("Authorization", "sso-key $apiKey:$apiSecret")
        }.build()

        val response = httpClient.newCall(request).execute()

        if (!response.isSuccessful) {
            error("Failed to update godaddy hostname: ${response.code} (${response.message}): ${response.body.string()}")
        }
    }

    fun getHostNameIpAddress(hostName: String): String? {
        val request = Request.Builder().apply {
            url("https://api.godaddy.com/v1/domains/$domain/records/A/$hostName")
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            header("Authorization", "sso-key $apiKey:$apiSecret")
        }.build()

        val response = httpClient.newCall(request).execute()
        val body = jsonEncoder.decodeFromString<List<HostNameResponse>>(response.body.string())
        return body.firstOrNull()?.data
    }
}