public class Producer implements Runnable {
    private Fifo fifo_;
    private String str_;
    int n_;
    public Producer(Fifo f, String s, int n) {
        fifo_ = f;
        str_ = s;
        n_ = n;
    }
    @Override
    public void run() {
        try {
            int count = 0;
            while(true) {
                fifo_.put(str_ + ' ' + count);
                System.out.println(str_ + " produced " + count++ + ' ' + System.currentTimeMillis() % 100000);
                Thread.sleep(n_);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
