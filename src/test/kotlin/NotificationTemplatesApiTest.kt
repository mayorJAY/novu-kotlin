import co.novu.Novu
import co.novu.NovuConfig
import co.novu.dto.Children
import co.novu.dto.Filters
import co.novu.dto.Metadata
import co.novu.dto.NotificationGroup
import co.novu.dto.PreferenceSettings
import co.novu.dto.Step
import co.novu.dto.Trigger
import co.novu.dto.Variables
import co.novu.dto.request.UpdateNotificationTemplateStatusRequest
import co.novu.dto.response.NotificationTemplates
import co.novu.dto.response.PaginatedResponseWrapper
import co.novu.dto.response.ResponseWrapper
import co.novu.extensions.createNotificationTemplates
import co.novu.extensions.deleteNotificationTemplate
import co.novu.extensions.notificationTemplate
import co.novu.extensions.notificationTemplates
import co.novu.extensions.updateNotificationTemplateStatus
import co.novu.extensions.updateNotificationTemplates
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Test
import java.math.BigInteger

@OptIn(ExperimentalCoroutinesApi::class)
class NotificationTemplatesApiTest {
    private val mockWebServer = MockWebServer()
    private val mockNovu = Novu(
        NovuConfig(apiKey = "1245", backendUrl = mockWebServer.url("").toString())
    )

    @Test
    fun testGetNotificationTemplates() = runTest {
        val responseBody = PaginatedResponseWrapper(
            data = listOf(
                NotificationTemplates(
                    id = "_id",
                    name = "name",
                    description = "description",
                    active = true,
                    draft = true,
                    preferenceSettings = PreferenceSettings(
                        email = true,
                        sms = true,
                        push = true,
                        inApp = true,
                        chat = true
                    ),
                    critical = true,
                    tags = listOf("tag1", "tag2"),
                    steps = listOf(
                        Step(
                            id = "_id",
                            templateId = "_templateId",
                            active = true,
                            shouldStopOnFail = true,
                            template = "template",
                            filters = listOf(
                                Filters(
                                    isNegated = true,
                                    type = "type",
                                    value = "value",
                                    children = listOf(
                                        Children(
                                            field = "field",
                                            value = "value",
                                            operator = "operator",
                                            on = "subscriber"
                                        )
                                    )
                                )
                            ),
                            metadata = Metadata(
                                amount = BigInteger.valueOf(10),
                                unit = "unit",
                                digestKey = "digestKey",
                                delayPath = "delayPath",
                                type = "type",
                                backoffUnit = "backoffUnit",
                                backoffAmount = BigInteger.valueOf(10),
                                updateMode = true
                            ),
                            parentId = "_parentId",
                            replyCallback = "replyCallback"
                        )
                    ),
                    organizationId = "_organizationId",
                    creatorId = "_creatorId",
                    environmentId = "_environmentId",
                    triggers = listOf(
                        Trigger(
                            type = "type",
                            identifier = "identifier",
                            variables = listOf(
                                Variables(
                                    name = "name"
                                )
                            ),
                            subscriberVariables = listOf(
                                Variables(
                                    name = "name"
                                )
                            )
                        )
                    ),
                    notificationGroupId = "_notificationGroupId",
                    deleted = true,
                    deletedBy = "deletedBy",
                    notificationGroup = NotificationGroup(
                        id = "_id",
                        name = "name",
                        environmentId = "_environmentId",
                        organizationId = "_organizationId",
                        parentId = "_parentId"
                    )
                )
            )

        )

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(Gson().toJson(responseBody)))
        val page = BigInteger.valueOf(1)
        val limit = BigInteger.valueOf(10)
        val result = mockNovu.notificationTemplates(page, limit)
        val request = mockWebServer.takeRequest()

        assert(request.method == "GET")
        assert(request.path == "/notification-templates?page=$page&limit=$limit")
        assert(Gson().toJson(responseBody) == Gson().toJson(result))
    }

    @Test
    fun testCreateNotification() = runTest {
        val responseBody = ResponseWrapper(
            NotificationTemplates(
                id = "_id",
                name = "name",
                description = "description",
                active = true,
                draft = true,
                preferenceSettings = PreferenceSettings(
                    email = true,
                    sms = true,
                    push = true,
                    inApp = true,
                    chat = true
                ),
                critical = true,
                tags = listOf("tag1", "tag2"),
                steps = listOf(
                    Step(
                        id = "_id",
                        templateId = "_templateId",
                        active = true,
                        shouldStopOnFail = true,
                        template = "template",
                        filters = listOf(
                            Filters(
                                isNegated = true,
                                type = "type",
                                value = "value",
                                children = listOf(
                                    Children(
                                        field = "field",
                                        value = "value",
                                        operator = "operator",
                                        on = "subscriber"
                                    )
                                )
                            )
                        ),
                        metadata = Metadata(
                            amount = BigInteger.valueOf(10),
                            unit = "unit",
                            digestKey = "digestKey",
                            delayPath = "delayPath",
                            type = "type",
                            backoffUnit = "backoffUnit",
                            backoffAmount = BigInteger.valueOf(10),
                            updateMode = true
                        ),
                        parentId = "_parentId",
                        replyCallback = "replyCallback"
                    )
                ),
                organizationId = "_organizationId",
                creatorId = "_creatorId",
                environmentId = "_environmentId",
                triggers = listOf(
                    Trigger(
                        type = "type",
                        identifier = "identifier",
                        variables = listOf(
                            Variables(
                                name = "name"
                            )
                        ),
                        subscriberVariables = listOf(
                            Variables(
                                name = "name"
                            )
                        )
                    )
                ),
                notificationGroupId = "_notificationGroupId",
                deleted = true,
                deletedBy = "deletedBy",
                notificationGroup = NotificationGroup(
                    id = "_id",
                    name = "name",
                    environmentId = "_environmentId",
                    organizationId = "_organizationId",
                    parentId = "_parentId"
                )
            )
        )

        mockWebServer.enqueue(MockResponse().setResponseCode(201).setBody(Gson().toJson(responseBody)))
        val requestBody = NotificationTemplates(
            name = "name",
            description = "description",
            active = true,
            draft = true,
            preferenceSettings = PreferenceSettings(
                email = true,
                sms = true,
                push = true,
                inApp = true,
                chat = true
            ),
            critical = true,
            tags = listOf("tag1", "tag2"),
            steps = listOf(
                Step(
                    active = true,
                    template = "template",
                    filters = listOf(
                        Filters(
                            isNegated = true,
                            type = "type",
                            value = "value",
                            children = listOf(
                                Children(
                                    field = "field",
                                    value = "value",
                                    operator = "operator",
                                    on = "subscriber"
                                )
                            )
                        )
                    )
                )
            )
        )
        val result = mockNovu.createNotificationTemplates(requestBody)
        val request = mockWebServer.takeRequest()

        assert(request.method == "POST")
        assert(request.path == "/notification-templates")
        assert(request.body.readUtf8() == Gson().toJson(requestBody))
        assert(Gson().toJson(responseBody) == Gson().toJson(result))
    }

    @Test
    fun testUpdateNotificationTemplates() = runTest {
        val responseBody = ResponseWrapper(
            NotificationTemplates(
                id = "_id",
                name = "name",
                description = "description",
                active = true,
                draft = true,
                preferenceSettings = PreferenceSettings(
                    email = true,
                    sms = true,
                    push = true,
                    inApp = true,
                    chat = true
                ),
                critical = true,
                tags = listOf("tag1", "tag2"),
                steps = listOf(
                    Step(
                        id = "_id",
                        templateId = "_templateId",
                        active = true,
                        shouldStopOnFail = true,
                        template = "template",
                        filters = listOf(
                            Filters(
                                isNegated = true,
                                type = "type",
                                value = "value",
                                children = listOf(
                                    Children(
                                        field = "field",
                                        value = "value",
                                        operator = "operator",
                                        on = "subscriber"
                                    )
                                )
                            )
                        ),
                        metadata = Metadata(
                            amount = BigInteger.valueOf(10),
                            unit = "unit",
                            digestKey = "digestKey",
                            delayPath = "delayPath",
                            type = "type",
                            backoffUnit = "backoffUnit",
                            backoffAmount = BigInteger.valueOf(10),
                            updateMode = true
                        ),
                        parentId = "_parentId",
                        replyCallback = "replyCallback"
                    )
                ),
                organizationId = "_organizationId",
                creatorId = "_creatorId",
                environmentId = "_environmentId",
                triggers = listOf(
                    Trigger(
                        type = "type",
                        identifier = "identifier",
                        variables = listOf(
                            Variables(
                                name = "name"
                            )
                        ),
                        subscriberVariables = listOf(
                            Variables(
                                name = "name"
                            )
                        )
                    )
                ),
                notificationGroupId = "_notificationGroupId",
                deleted = true,
                deletedBy = "deletedBy",
                notificationGroup = NotificationGroup(
                    id = "_id",
                    name = "name",
                    environmentId = "_environmentId",
                    organizationId = "_organizationId",
                    parentId = "_parentId"
                )
            )
        )

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(Gson().toJson(responseBody)))
        val requestBody = NotificationTemplates(
            name = "name",
            description = "description",
            active = true,
            preferenceSettings = PreferenceSettings(
                email = true,
                sms = true,
                push = true,
                inApp = true,
                chat = true
            ),
            critical = true,
            tags = listOf("tag1", "tag2"),
            steps = listOf(
                Step(
                    id = "_id",
                    templateId = "_templateId",
                    active = true,
                    template = "template",
                    filters = listOf(
                        Filters(
                            isNegated = true,
                            type = "type",
                            value = "value",
                            children = listOf(
                                Children(
                                    field = "field",
                                    value = "value",
                                    operator = "operator",
                                    on = "subscriber"
                                )
                            )
                        )
                    ),
                    metadata = Metadata(
                        amount = BigInteger.valueOf(10),
                        unit = "unit",
                        digestKey = "digestKey",
                        delayPath = "delayPath",
                        type = "type",
                        backoffUnit = "backoffUnit",
                        backoffAmount = BigInteger.valueOf(10),
                        updateMode = true
                    ),
                    parentId = "_parentId",
                    replyCallback = "replyCallback"
                )
            ),
            notificationGroupId = "_notificationGroupId"
        )
        val templateId = "_id"
        val result = mockNovu.updateNotificationTemplates(templateId, requestBody)
        val request = mockWebServer.takeRequest()

        assert(request.method == "PUT")
        assert(request.path == "/notification-templates/$templateId")
        assert(request.body.readUtf8() == Gson().toJson(requestBody))
        assert(Gson().toJson(responseBody) == Gson().toJson(result))
    }

    @Test
    fun testDeleteNotificationTemplate() = runTest {
        val responseBody = ResponseWrapper(true)
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(Gson().toJson(responseBody)))

        val templateId = "_id"
        val result = mockNovu.deleteNotificationTemplate(templateId)
        val request = mockWebServer.takeRequest()

        assert(request.method == "DELETE")
        assert(request.path == "/notification-templates/$templateId")
        assert(Gson().toJson(responseBody) == Gson().toJson(result))
    }

    @Test
    fun testGetNotificationTemplate() = runTest {
        val responseBody = ResponseWrapper(
            NotificationTemplates(
                id = "_id",
                name = "name",
                description = "description",
                active = true,
                draft = true,
                preferenceSettings = PreferenceSettings(
                    email = true,
                    sms = true,
                    push = true,
                    inApp = true,
                    chat = true
                ),
                critical = true,
                tags = listOf("tag1", "tag2"),
                steps = listOf(
                    Step(
                        id = "_id",
                        templateId = "_templateId",
                        active = true,
                        shouldStopOnFail = true,
                        template = "template",
                        filters = listOf(
                            Filters(
                                isNegated = true,
                                type = "type",
                                value = "value",
                                children = listOf(
                                    Children(
                                        field = "field",
                                        value = "value",
                                        operator = "operator",
                                        on = "subscriber"
                                    )
                                )
                            )
                        ),
                        metadata = Metadata(
                            amount = BigInteger.valueOf(10),
                            unit = "unit",
                            digestKey = "digestKey",
                            delayPath = "delayPath",
                            type = "type",
                            backoffUnit = "backoffUnit",
                            backoffAmount = BigInteger.valueOf(10),
                            updateMode = true
                        ),
                        parentId = "_parentId",
                        replyCallback = "replyCallback"
                    )
                ),
                organizationId = "_organizationId",
                creatorId = "_creatorId",
                environmentId = "_environmentId",
                triggers = listOf(
                    Trigger(
                        type = "type",
                        identifier = "identifier",
                        variables = listOf(
                            Variables(
                                name = "name"
                            )
                        ),
                        subscriberVariables = listOf(
                            Variables(
                                name = "name"
                            )
                        )
                    )
                ),
                notificationGroupId = "_notificationGroupId",
                deleted = true,
                deletedBy = "deletedBy",
                notificationGroup = NotificationGroup(
                    id = "_id",
                    name = "name",
                    environmentId = "_environmentId",
                    organizationId = "_organizationId",
                    parentId = "_parentId"
                )
            )
        )
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(Gson().toJson(responseBody)))

        val templateId = "_id"
        val result = mockNovu.notificationTemplate(templateId)
        val request = mockWebServer.takeRequest()

        assert(request.method == "GET")
        assert(request.path == "/notification-templates/$templateId")
        assert(Gson().toJson(responseBody) == Gson().toJson(result))
    }

    @Test
    fun testUpdateNotificationTemplateStatus() = runTest {
        val responseBody = ResponseWrapper(
            NotificationTemplates(
                id = "_id",
                name = "name",
                description = "description",
                active = true,
                draft = true,
                preferenceSettings = PreferenceSettings(
                    email = true,
                    sms = true,
                    push = true,
                    inApp = true,
                    chat = true
                ),
                critical = true,
                tags = listOf("tag1", "tag2"),
                steps = listOf(
                    Step(
                        id = "_id",
                        templateId = "_templateId",
                        active = true,
                        shouldStopOnFail = true,
                        template = "template",
                        filters = listOf(
                            Filters(
                                isNegated = true,
                                type = "type",
                                value = "value",
                                children = listOf(
                                    Children(
                                        field = "field",
                                        value = "value",
                                        operator = "operator",
                                        on = "subscriber"
                                    )
                                )
                            )
                        ),
                        metadata = Metadata(
                            amount = BigInteger.valueOf(10),
                            unit = "unit",
                            digestKey = "digestKey",
                            delayPath = "delayPath",
                            type = "type",
                            backoffUnit = "backoffUnit",
                            backoffAmount = BigInteger.valueOf(10),
                            updateMode = true
                        ),
                        parentId = "_parentId",
                        replyCallback = "replyCallback"
                    )
                ),
                organizationId = "_organizationId",
                creatorId = "_creatorId",
                environmentId = "_environmentId",
                triggers = listOf(
                    Trigger(
                        type = "type",
                        identifier = "identifier",
                        variables = listOf(
                            Variables(
                                name = "name"
                            )
                        ),
                        subscriberVariables = listOf(
                            Variables(
                                name = "name"
                            )
                        )
                    )
                ),
                notificationGroupId = "_notificationGroupId",
                deleted = true,
                deletedBy = "deletedBy",
                notificationGroup = NotificationGroup(
                    id = "_id",
                    name = "name",
                    environmentId = "_environmentId",
                    organizationId = "_organizationId",
                    parentId = "_parentId"
                )
            )
        )

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(Gson().toJson(responseBody)))

        val templateId = "_id"
        val requestBody = UpdateNotificationTemplateStatusRequest(
            active = true
        )

        val result = mockNovu.updateNotificationTemplateStatus(templateId, requestBody)
        val request = mockWebServer.takeRequest()

        assert(request.method == "PUT")
        assert(request.path == "/notification-templates/$templateId/status")
        assert(request.body.readUtf8() == Gson().toJson(requestBody))
        assert(Gson().toJson(responseBody) == Gson().toJson(result))
    }
}
