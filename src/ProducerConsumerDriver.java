import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerDriver {
    private static final int MAX_QUEUE_CAPACITY = 5;
    private static volatile boolean running = true;

    public static void demoSingleProducerAndSingleConsumer() {
        DataQueue dataQueue = new DataQueue(MAX_QUEUE_CAPACITY);

        Producer producer = new Producer(dataQueue);
        Thread producerThread = new Thread(() -> {
            while (running) {
                producer.run();
            }
        });

        Consumer consumer = new Consumer(dataQueue);
        Thread consumerThread = new Thread(() -> {
            while (running) {
                consumer.run();
            }
        });

        producerThread.start();
        consumerThread.start();

        List<Thread> threads = new ArrayList<>();
        threads.add(producerThread);
        threads.add(consumerThread);

        // let threads run for two seconds
        MyThread.sleep(2000);

        // stop threads
        running = false;

        MyThread.waitForAllThreadsToComplete(threads);
    }

    public static void demoMultipleProducersAndMultipleConsumers() {
        DataQueue dataQueue = new DataQueue(MAX_QUEUE_CAPACITY);
        int producerCount = 5;
        int consumerCount = 5;
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < producerCount; i++) {
            Producer producer = new Producer(dataQueue);
            Thread producerThread = new Thread(() -> {
                while (running) {
                    producer.run();
                }
            });
            producerThread.start();
            threads.add(producerThread);
        }

        for (int i = 0; i < consumerCount; i++) {
            Consumer consumer = new Consumer(dataQueue);
            Thread consumerThread = new Thread(() -> {
                while (running) {
                    consumer.run();
                }
            });
            consumerThread.start();
            threads.add(consumerThread);
        }

        // let threads run for ten seconds
        MyThread.sleep(10000);

        // stop threads
        running = false;

        MyThread.waitForAllThreadsToComplete(threads);
    }

    public static void main(String[] args) {
        demoSingleProducerAndSingleConsumer();
        demoMultipleProducersAndMultipleConsumers();
    }
}
