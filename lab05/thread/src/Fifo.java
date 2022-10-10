import java.util.LinkedList;

public class Fifo {
    private LinkedList<String> container_;
    public Fifo() {
        container_ = new LinkedList<String>();
    }
    synchronized void put(String str) throws InterruptedException {
        System.out.println("Fifo.put() " + Thread.currentThread().getId());
        while (container_.size() >= 10)
            this.wait();
        container_.add(str);
        notify();
    }
    synchronized String get() throws InterruptedException {
        System.out.println("Fifo.get() " + Thread.currentThread().getId());
        String ret;
        while (container_.isEmpty())
            this.wait();
        ret = container_.getFirst();
        container_.pop();
        notify();
        return ret;
    }
}
