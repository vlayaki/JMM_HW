import lombok.extern.log4j.Log4j2;

@Log4j2
public class Counter {
    private int counter = 0;
    private boolean increasing = true;

    public void count() {
        while (true){
            synchronized (this) {
                if (counter % 2 == 0 && Thread.currentThread().getName().equals("thread1")){
                    sleep(500);
                    log.info(next());
                }
                if (counter % 2 == 1 && Thread.currentThread().getName().equals("thread2")) {
                    sleep(500);
                    log.info(next());
                }
            }
        }
    }

    private int next() {
        if (counter == 1){
            increasing = true;
        }
        if(counter == 10){
            increasing = false;
        }
        return increasing ? ++counter : --counter;
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread thread1 = new Thread(counter::count);
        thread1.setName("thread1");
        thread1.start();
        Thread thread2 = new Thread(counter::count);
        thread2.setName("thread2");
        thread2.start();
    }

}
