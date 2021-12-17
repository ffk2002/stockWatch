import static java.time.LocalTime.now;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Watcher implements Runnable{
    Option option;
    final int ENDTIME=now().getSecond()+10;
    final int INTERVAL=500;
    Lock getPriceLock;

    public Watcher(String option){
        this.option=new Option(option, now());
        getPriceLock = new ReentrantLock();

    }



    public static void main(String[] args) {
        Runnable NEE = new Watcher("#BTC-USD");
        Runnable RUN = new Watcher("$RUN");
        Runnable UAL = new Watcher("$UAL");
        Runnable ENPH = new Watcher("$ENPH");
        Runnable AMD = new Watcher("$AMD");

        Thread tNEE = new Thread(NEE);
        Thread tRUN = new Thread(RUN);
        Thread tUAL = new Thread(UAL);
        Thread tENPH = new Thread(ENPH);
        Thread tAMD = new Thread(AMD);

        tNEE.start();
        tRUN.start();
        tUAL.start();
        tENPH.start();
        tAMD.start();
    }

    @Override
    public void run() {
        getPriceLock.lock();
        try {
            while(now().getSecond()<=ENDTIME){
                System.out.println(this.option.getName() +" | "+this.option+" | "+now());
                Thread.sleep(INTERVAL);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            getPriceLock.unlock();
        }

    }
}