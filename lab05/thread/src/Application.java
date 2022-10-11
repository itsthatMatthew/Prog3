import java.util.Random;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        Random rand = new Random();
        // közös Fifo tároló
        Fifo common = new Fifo();
        // Producerek
        new Thread(new Producer(common, "1. producer", 1000 + (500 - rand.nextInt(1000)))).start();
        new Thread(new Producer(common, "2. producer", 1000 + (500 - rand.nextInt(1000)))).start();
        new Thread(new Producer(common, "3. producer", 1000 + (500 - rand.nextInt(1000)))).start();
        // Consumerek
        new Thread(new Consumer(common, "1. consumer", 100 + (50 - rand.nextInt(100)))).start();
        new Thread(new Consumer(common, "2. consumer", 100 + (50 - rand.nextInt(100)))).start();
        new Thread(new Consumer(common, "3. consumer", 100 + (50 - rand.nextInt(100)))).start();
        new Thread(new Consumer(common, "4. consumer", 100 + (50 - rand.nextInt(100)))).start();
    }
}
