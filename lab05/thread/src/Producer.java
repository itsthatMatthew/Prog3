public class Producer implements Runnable {
    private String str_;
    private Fifo fifo_;
    int n_;
    public Producer(Fifo f, String str, int n) {
        fifo_ = f;
        str_ = str;
        n_ = n;
    }
    public void go() throws InterruptedException {
        int count = 0;
        while(true) {
            fifo_.put(str_ + ' ' + count);
            System.out.println(str_ + " produced " + count++ + ' ' + System.currentTimeMillis());
            Thread.sleep(n_);
        }
    }
    @Override
    public void run() {
        try {
            this.go();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
