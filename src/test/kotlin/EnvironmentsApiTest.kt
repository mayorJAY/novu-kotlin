import co.novu.Novu
import co.novu.NovuConfig
import co.novu.dto.ApiKeys
import co.novu.dto.DNS
import co.novu.dto.Widget
import co.novu.dto.request.CreateEnvironmentRequest
import co.novu.dto.request.UpdateEnvironmentRequest
import co.novu.dto.response.GetEnvironmentResponse
import co.novu.dto.response.ResponseWrapper
import co.novu.extensions.apiKeys
import co.novu.extensions.createEnvironment
import co.novu.extensions.currentEnvironment
import co.novu.extensions.environments
import co.novu.extensions.regenrateApiKey
import co.novu.extensions.updateEnvironment
import co.novu.extensions.updateWidgetSettings
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EnvironmentsApiTest {
    private val mockWebServer = MockWebServer()
    private val mockNovu =
        Novu(
            NovuConfig(apiKey = "1245", backendUrl = mockWebServer.url("").toString()),
        )

    @Test
    fun testGetEnvironments() =
        runTest {
            val responseBody =
                ResponseWrapper(
                    GetEnvironmentResponse(
                        id = "1234",
                        name = "name",
                        organizationId = "orgId",
                        identifier = "identifier",
                        apiKeys =
                            listOf(
                                ApiKeys(
                                    key = "key",
                                    userId = "userId",
                                ),
                            ),
                        widget =
                            Widget(
                                notificationCenterEncryption = true,
                            ),
                        parentId = "parentId",
                    ),
                )

            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(Gson().toJson(responseBody)))
            val result = mockNovu.currentEnvironment()
            val request = mockWebServer.takeRequest()
            assert(request.path == "/environments/me")
            assert(request.method == "GET")
            assert(result == responseBody)
        }

    @Test
    fun testCreateEnvironment() =
        runTest {
            val responseBody =
                ResponseWrapper(
                    GetEnvironmentResponse(
                        id = "1234",
                        name = "name",
                        organizationId = "orgId",
                        identifier = "identifier",
                        apiKeys =
                            listOf(
                                ApiKeys(
                                    key = "key",
                                    userId = "userId",
                                ),
                            ),
                        widget =
                            Widget(
                                notificationCenterEncryption = true,
                            ),
                        parentId = "parentId",
                    ),
                )
            mockWebServer.enqueue(MockResponse().setResponseCode(201).setBody(Gson().toJson(responseBody)))
            val requestBody =
                CreateEnvironmentRequest(
                    name = "name",
                    parentId = "parentId",
                )
            val result = mockNovu.createEnvironment(requestBody)
            val request = mockWebServer.takeRequest()

            assert(request.path == "/environments")
            assert(request.method == "POST")
            assert(request.body.readUtf8() == Gson().toJson(requestBody))
            assert(result == responseBody)
        }

    @Test
    fun testGetEnvironment() =
        runTest {
            val responseBody =
                ResponseWrapper(
                    listOf(
                        GetEnvironmentResponse(
                            id = "1234",
                            name = "name",
                            organizationId = "orgId",
                            identifier = "identifier",
                            apiKeys =
                                listOf(
                                    ApiKeys(
                                        key = "key",
                                        userId = "userId",
                                    ),
                                ),
                            widget =
                                Widget(
                                    notificationCenterEncryption = true,
                                ),
                            parentId = "parentId",
                        ),
                    ),
                )
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(Gson().toJson(responseBody)))
            val result = mockNovu.environments()
            val request = mockWebServer.takeRequest()

            assert(request.path == "/environments")
            assert(request.method == "GET")
            assert(result == responseBody)
        }

    @Test
    fun testUpdateEnvironment() =
        runTest {
            val responseBody =
                ResponseWrapper(
                    GetEnvironmentResponse(
                        id = "1234",
                        name = "name",
                        organizationId = "orgId",
                        identifier = "identifier",
                        apiKeys =
                            listOf(
                                ApiKeys(
                                    key = "key",
                                    userId = "userId",
                                ),
                            ),
                        widget =
                            Widget(
                                notificationCenterEncryption = true,
                            ),
                        parentId = "parentId",
                    ),
                )
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(Gson().toJson(responseBody)))
            val environmentId = "environmentId"
            val requestBody =
                UpdateEnvironmentRequest(
                    name = "name",
                    parentId = "parentId",
                    identifier = "identifier",
                    dns =
                        DNS(
                            inboundParseDomain = "inboundParseDomain",
                        ),
                )
            mockWebServer.enqueue(MockResponse().setResponseCode(200))
            val result = mockNovu.updateEnvironment(environmentId, requestBody)
            val request = mockWebServer.takeRequest()

            assert(request.path == "/environments/$environmentId")
            assert(request.method == "PUT")
            assert(request.body.readUtf8() == Gson().toJson(requestBody))
            assert(result == responseBody)
        }

    @Test
    fun testGetApiKeys() =
        runTest {
            val responseBody =
                ResponseWrapper(
                    listOf(
                        ApiKeys(
                            key = "key",
                            userId = "userId",
                        ),
                    ),
                )
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(Gson().toJson(responseBody)))
            val result = mockNovu.apiKeys()
            val request = mockWebServer.takeRequest()

            assert(request.path == "/environments/api-keys")
            assert(request.method == "GET")
            assert(result == responseBody)
        }

    @Test
    fun testRegenerateApiKey() =
        runTest {
            val responseBody =
                ResponseWrapper(
                    listOf(
                        ApiKeys(
                            key = "key",
                            userId = "userId",
                        ),
                    ),
                )
            mockWebServer.enqueue(MockResponse().setResponseCode(201).setBody(Gson().toJson(responseBody)))
            val result = mockNovu.regenrateApiKey()
            val request = mockWebServer.takeRequest()

            assert(request.path == "/environments/api-keys/regenerate")
            assert(request.method == "POST")
            assert(result == responseBody)
        }

    @Test
    fun testUpdateWidgetSettings() =
        runTest {
            val responseBody =
                ResponseWrapper(
                    GetEnvironmentResponse(
                        id = "1234",
                        name = "name",
                        organizationId = "orgId",
                        identifier = "identifier",
                        apiKeys =
                            listOf(
                                ApiKeys(
                                    key = "key",
                                    userId = "userId",
                                ),
                            ),
                        widget =
                            Widget(
                                notificationCenterEncryption = true,
                            ),
                        parentId = "parentId",
                    ),
                )
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(Gson().toJson(responseBody)))
            val requestBody =
                Widget(
                    notificationCenterEncryption = true,
                )
            val result = mockNovu.updateWidgetSettings(requestBody)
            val request = mockWebServer.takeRequest()

            assert(request.path == "/environments/api-keys/widget/settings")
            assert(request.method == "PUT")
            assert(request.body.readUtf8() == Gson().toJson(requestBody))
            assert(result == responseBody)
        }
}
