package br.com.kafka_ses;

import java.util.concurrent.ExecutionException;

import br.com.kafka_ses.services_kafka.KafkaService;

public class App {
    public static void main( String[] args ) throws InterruptedException, ExecutionException {
        
        System.out.println("Reading messages...");

        String grupoId = System.getenv("KAFKA_GROUP_ID_READER");
        
        KafkaService.readMessage(grupoId);
       
    }
}
