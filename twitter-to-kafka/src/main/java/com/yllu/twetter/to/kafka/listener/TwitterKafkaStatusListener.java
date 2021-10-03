package com.yllu.twetter.to.kafka.listener;

import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.StatusAdapter;

@Component
public class TwitterKafkaStatusListener extends StatusAdapter {

    @Override
    public void onStatus(Status status) {
        System.out.println("STATUS -- " + status.getText());
    }
}
