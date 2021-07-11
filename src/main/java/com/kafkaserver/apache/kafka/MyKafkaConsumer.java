package com.kafkaserver.apache.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class MyKafkaConsumer {
    public void consume() {
        Consumer<Long, String> consumer = ConsumerCreator.createConsumer();

        int noMessageFound = 0;

        while (true) {
            ConsumerRecords<Long, String> consumerRecords = consumer.poll(1);
            // 1000 is the time in milliseconds consumer will wait if no record is found at broker.
            if (consumerRecords.count() == 0) {
                noMessageFound++;
                if (noMessageFound > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
                    // If no message found count is reached to threshold exit loop.
                    break;
                else
                    continue;
            }

            //print each record.
            consumerRecords.forEach(record -> {
                System.out.println("Record Key " + record.key());
                System.out.println("Record value " + record.value());
                System.out.println("Record partition " + record.partition());
                System.out.println("Record offset " + record.offset());
                Headers headers = record.headers();
                for (Header header:headers) {
                    System.out.println("Header Key = "+ header.key()+ "Header Value="+new String(header.value(), StandardCharsets.UTF_8).replace("\"",""));
                }
            });

            // commits the offset of record to broker.
            consumer.commitAsync();
        }
        consumer.close();
    }
}
