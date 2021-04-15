package com.test.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

@TestConfiguration
class SqsTestConfiguration {
    @Value("\${cloud.aws.region.static}")
    lateinit var region: String

    @Value("\${cloud.aws.sqs.endpoint}")
    lateinit var serviceEndpoint: String

    @Value("\${cloud.aws.credentials.secretKey}")
    lateinit var secretKey: String

    @Value("\${cloud.aws.credentials.accessKey}")
    lateinit var accessKey: String

    @Bean
    @Primary
    fun amazonSQSAsyncTest(): AmazonSQSAsync {
        val endpointConfiguration = AwsClientBuilder.EndpointConfiguration(serviceEndpoint, region)
        return AmazonSQSAsyncClientBuilder
                .standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKey, secretKey)))
                .build()
    }


}
