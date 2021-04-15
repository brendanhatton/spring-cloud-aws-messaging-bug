package com.test

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.awspring.cloud.messaging.core.QueueMessagingTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.converter.MappingJackson2MessageConverter


@Configuration
class MessageListenerConfiguration {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Bean
    fun amazonSQSAsync(@Value("\${cloud.aws.sqs.endpoint}") sqsEndpoint: String,
                       @Value("\${cloud.aws.region.static}") region: String): AmazonSQSAsync {
        return AmazonSQSAsyncClientBuilder
                .standard()
                .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(sqsEndpoint, region))
                .build()
    }

    @Bean
    fun queueMessagingTemplate(amazonSqs: AmazonSQSAsync): QueueMessagingTemplate {
        val queueMessagingTemplate = QueueMessagingTemplate(amazonSqs)

        //FIXME the lines below this should not be required. Comment them to see the test fail, uncomment them to see it pass
//        val messageConverter = MappingJackson2MessageConverter()
//        messageConverter.objectMapper = objectMapper
//        queueMessagingTemplate.messageConverter = messageConverter
        //FIXME The lines above this should not be required

        return queueMessagingTemplate
    }

}
