package com.slack.notifications.slacknotification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/messages")
public class SlackController {


    @Autowired
    private SlackService slackService;

    @PostMapping("/{message}")
    public ResponseEntity<String> sendSlackMessage(@PathVariable(name = "message") String message) throws IOException {
        slackService.sendMessageToSlack(message);
        return ResponseEntity.ok(message);
    }


}
