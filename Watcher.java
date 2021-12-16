import yahoofinance.Stock;
import yahoofinance.YahooFinance;
//now().getSecond()<=ENDTIME
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

import static java.time.LocalTime.now;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Watcher implements Runnable{
    HashMap data = new HashMap(1);
    String stock;
    final int ENDTIME=now().getSecond()+10;
    final int INTERVAL=500;
    Lock getPriceLock;

    public Watcher(String stock){
        this.stock=stock;
        getPriceLock = new ReentrantLock();

    }


    public static void main(String[] args) throws IOException {
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
            while(now().getSecond()!=ENDTIME){
                if(!data.containsKey(this.stock)){
                    data.put(this.stock, new Option(this.stock, now()));
                }else{
                    data.replace(this.stock, new Option(this.stock, now()));
                }
                System.out.println(this.stock+" | "+data.get(this.stock)+" | "+now());
                Thread.sleep(INTERVAL);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            getPriceLock.unlock();
        }

    }
}
