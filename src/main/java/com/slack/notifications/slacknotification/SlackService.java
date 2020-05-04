package com.slack.notifications.slacknotification;


import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


//https://dev.to/silviobuss/send-slack-messages-with-java-in-5-minutes-2lio

@Service
public class SlackService {


    private static final Logger LOGGER = LoggerFactory.getLogger(SlackService.class);
    private static HttpURLConnection httpConn;

    @Value("${slack.webhook}")
    private String urlSlackWebHook;

    public void sendMessageToSlack(String message) throws IOException {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("My message:---- " + message);

        RestAssured.baseURI = "http://localhost:8080/actuator/health";
        RequestSpecification request = RestAssured.given();
        String actuatorEndPoint = "/health";

        Response response = request.get(RestAssured.baseURI + actuatorEndPoint);

        LOGGER.info("Actuator API response code ->" + response.getStatusCode());
        LOGGER.info("Actuator API response message ->" + response.getBody().asString());

       /* URL url = null;
        try {
            url = new URL("http://localhost:8080/actuator/health");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {

            httpConn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("Actuator API response code ->" + httpConn.getResponseCode());
        LOGGER.info("Actuator API response message ->" + httpConn.getResponseMessage());*/

        process(messageBuilder.toString());

    }

    private void process(String message) {
        Payload payload = Payload.builder().
                channel("#general").username("Bot").iconEmoji(":rocket").text(message).build();
        WebhookResponse webhookResponse = null;
        try {
            webhookResponse = Slack.getInstance().send(urlSlackWebHook, payload);
            LOGGER.info("code ->" + webhookResponse.getCode());
            LOGGER.info("body ->" + webhookResponse.getBody());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private String exampleMessage() {
        return "This is example message";
    }
}
