import java.util.LinkedList;

public class Fifo {
    private LinkedList<String> container_ = new LinkedList<String>();
    synchronized public void put(String str) throws InterruptedException {
        System.out.println("Fifo.put() " + Thread.currentThread().getId());
        while (container_.size() >= 10)
            this.wait();
        container_.add(str);
        notify();
    }
    synchronized public String get() throws InterruptedException {
        System.out.println("Fifo.get() " + Thread.currentThread().getId());
        while (container_.isEmpty())
            this.wait();
        String ret = container_.pop();
        notify();
        return ret;
    }
}
