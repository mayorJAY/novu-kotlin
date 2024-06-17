import co.novu.Novu
import co.novu.NovuConfig
import co.novu.dto.request.ChangesRequest
import co.novu.dto.response.ChangesResponse
import co.novu.dto.response.PaginatedResponseWrapper
import co.novu.dto.response.ResponseWrapper
import co.novu.extensions.applyBulkChanges
import co.novu.extensions.applyChange
import co.novu.extensions.changes
import co.novu.extensions.changesCount
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Test
import java.math.BigInteger
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
class ChangesApiTest {
    private val mockWebServer = MockWebServer()
    private val mockNovu =
        Novu(
            NovuConfig(apiKey = "1245", backendUrl = mockWebServer.url("").toString()),
        )

    @Test
    fun testGetChanges() =
        runTest {
            val responseBody =
                PaginatedResponseWrapper(
                    data =
                        listOf(
                            ChangesResponse(
                                id = "_id",
                                creatorId = "_creatorId",
                                environmentId = "_environmentId",
                                organizationId = "_organizationId",
                                entityId = "_entityId",
                                enabled = true,
                                type = "type",
                                change = "change",
                                createdAt = "createdAt",
                                parentId = "_parentId",
                            ),
                        ),
                    totalCount = BigInteger.TEN,
                )
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(Gson().toJson(responseBody)),
            )
            val page = BigInteger.ONE
            val limit = BigInteger.TEN
            val promoted = "promoted"
            val result = mockNovu.changes(page, limit, promoted)
            val request = mockWebServer.takeRequest()

            assert(request.method == "GET")
            assert(request.path == "/changes?page=$page&limit=$limit&promoted=$promoted")
            assert(responseBody == result)
        }

    @Test
    fun testGetChangesCount() =
        runTest {
            val responseBody = ResponseWrapper(data = BigInteger.ONE)
            mockWebServer.enqueue(
                MockResponse().setResponseCode(200)
                    .setBody(Gson().toJson(responseBody)),
            )
            val result = mockNovu.changesCount()
            val request = mockWebServer.takeRequest()

            assert(request.path == "/changes/count")
            assert(request.method == "GET")
            assert(result == responseBody)
        }

    @Test
    fun testApplyChanges() =
        runTest {
            val requestBody = ChangesRequest(listOf("id1", "id2"))
            val responseBody =
                ResponseWrapper(
                    listOf(
                        ChangesResponse(
                            id = "_id",
                            creatorId = "_creatorId",
                            environmentId = "_environmentId",
                            organizationId = "_organizationId",
                            entityId = "_entityId",
                            enabled = true,
                            type = "type",
                            change = "change",
                            createdAt = "createdAt",
                            parentId = "_parentId",
                        ),
                    ),
                )

            mockWebServer.enqueue(
                MockResponse().setResponseCode(201)
                    .setBody(Gson().toJson(responseBody)),
            )
            val result = mockNovu.applyBulkChanges(requestBody)
            val request = mockWebServer.takeRequest()

            assert(request.path == "/changes/bulk/apply")
            assert(request.method == "POST")
            assert(result == responseBody)
        }

    @Test
    fun testApplyChange() =
        runTest {
            val responseBody =
                ResponseWrapper(
                    listOf(
                        ChangesResponse(
                            id = "_id",
                            creatorId = "_creatorId",
                            environmentId = "_environmentId",
                            organizationId = "_organizationId",
                            entityId = "_entityId",
                            enabled = true,
                            type = "type",
                            change = "change",
                            createdAt = "createdAt",
                            parentId = "_parentId",
                        ),
                    ),
                )

            mockWebServer.enqueue(
                MockResponse().setResponseCode(201)
                    .setBody(Gson().toJson(responseBody)),
            )
            val changeId = UUID.randomUUID().toString()
            val result = mockNovu.applyChange(changeId)
            val request = mockWebServer.takeRequest()

            assert(request.method == "POST")
            assert(request.path == "/changes/$changeId/apply")
            assert(responseBody == result)
        }
}
