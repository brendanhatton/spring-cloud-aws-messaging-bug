package com.test

import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.model.CreateQueueRequest
import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.messaging.core.QueueMessagingTemplate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.support.GenericMessage

@SpringBootTest(classes = [Application::class])
class TestIntegrationTest {

    @Autowired
    lateinit var queueMessagingTemplate: QueueMessagingTemplate

    @Autowired
    lateinit var amazonSQSAsync: AmazonSQSAsync

    @Autowired
    lateinit var objectMapper: ObjectMapper

    val queueName = "test-queue-name"

    @BeforeEach
    internal fun setUp() {
            amazonSQSAsync.createQueue(CreateQueueRequest(queueName))
    }

    @Test
    fun `should send sqs message`() {
        val event = AccountCreatedEvent(comment = "123", name = "abc")
        queueMessagingTemplate.send("test-queue-name", GenericMessage(objectMapper.writeValueAsString(event)))

        val result = this.queueMessagingTemplate.receiveAndConvert(queueName, AccountCreatedEvent::class.java)
        assertThat(result).isEqualTo(event)
    }

}
