package com.yllu.twetter.to.kafka.runner;

import com.yllu.twetter.to.kafka.configuration.TwitterToKafkaServiceConfigData;
import com.yllu.twetter.to.kafka.listener.TwitterKafkaStatusListener;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import javax.annotation.PreDestroy;

@Component
public class TwitterKafkaStreamRunner implements StreamRunner {

    private final TwitterKafkaStatusListener twitterKafkaStatusListener;
    private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;
    private TwitterStream twitter;

    public TwitterKafkaStreamRunner(TwitterKafkaStatusListener twitterKafkaStatusListener,
                                    TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData) {
        this.twitterKafkaStatusListener = twitterKafkaStatusListener;
        this.twitterToKafkaServiceConfigData = twitterToKafkaServiceConfigData;

    }

    @Override
    public void start() throws TwitterException {
        twitter = new TwitterStreamFactory().getInstance();
        twitter.addListener(twitterKafkaStatusListener);
        String[] keywords = twitterToKafkaServiceConfigData.getTwitterKeywords().toArray(new String[0]);
        System.out.println("KEY words" + keywords[0]);
        FilterQuery filterQuery = new FilterQuery(keywords);
        twitter.filter(filterQuery);

    }

    @PreDestroy
    public void shutDown() {
        if (twitter != null) {
            twitter.shutdown();
        }
    }
}
