
import java.util.LinkedList;

class PC {
    LinkedList<Integer> list = new LinkedList<>();
    int capacity = 2;
    int value = 0;

    public void produce() throws InterruptedException {
        while (true) {
            synchronized (this) {
                while (list.size() == capacity)
                    wait();

                list.add(value);
                System.out.println("Producer produced-" + value++);
                notifyAll();
            }
            Thread.sleep(1000);
        }
    }

    public void consume() throws InterruptedException {
        while (true) {
            synchronized (this) {
                while (list.isEmpty())
                    wait();

                int val = list.removeFirst();
                System.out.println("Consumer consumed-" + val);
                notifyAll();
            }
            Thread.sleep(1000);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        final PC pc = new PC();

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.produce();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.consume();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producer.start();
        consumer.start();
    }
}