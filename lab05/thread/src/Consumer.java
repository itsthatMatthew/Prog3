public class Consumer implements Runnable {
    private Fifo fifo_;
    private String str_;
    int n_;
    public Consumer(Fifo f, String s, int n) {
        fifo_ = f;
        str_ = s;
        n_ = n;
    }
    @Override
    public void run() {
        try {
            while(true) {
                String got = fifo_.get();
                System.out.println(str_ + " consumed " + got + ' ' + System.currentTimeMillis() % 100000);
                Thread.sleep(n_);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
