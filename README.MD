# Spring cloud AWS possible bug demo app

This is a simple app demonstrating what I think is a bug in spring cloud aws. 
The issue is when receiving SQS messages and deserialising them, the object mapper being used is a default one which is 
separate to the app - and in the case of kotlin, does not include the kotlin module.

It seems very similar to https://github.com/spring-cloud/spring-cloud-aws/issues/533 

## How to run
1. `./gradlew up` will start a local SQS (via localstack docker)

2. Run the test `com.test.TestIntegrationTest`

3. Inspect the `com.test.MessageListenerConfiguration` - there are a few lines which seem to act as a workaround for this 
   issue.
