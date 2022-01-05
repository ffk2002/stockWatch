import static java.time.LocalTime.now;

import java.sql.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Watcher implements Runnable{
    Option option;
    final int INTERVAL=100;
    Lock getPriceLock;

    public Watcher(String option){
        this.option=new Option(option, now());
        getPriceLock = new ReentrantLock();

    }



/*    public static void main(String[] args) {
        Runnable NEE = new Watcher("#BTC-USD");
        *//*Runnable RUN = new Watcher("$RUN");
        Runnable UAL = new Watcher("$UAL");
        Runnable ENPH = new Watcher("$ENPH");
        Runnable AMD = new Watcher("$AMD");*//*

        Thread tNEE = new Thread(NEE);
        *//*Thread tRUN = new Thread(RUN);
        Thread tUAL = new Thread(UAL);
        Thread tENPH = new Thread(ENPH);
        Thread tAMD = new Thread(AMD);*//*

        tNEE.start();
        *//*tRUN.start();
        tUAL.start();
        tENPH.start();

        +this.option.getPrice(this.option.getName())
="INSERT INTO crypto (id, name, price, times)" +
                        " VALUES ("+this.option.type(this.option.id)+", "+this.option.getName()+", "+this.option.getPrice(this.option.getName())+");";
        tAMD.start();*//*
    }*/

    @Override
    public void run() {
        getPriceLock.lock();
        Connection c = null;
        Statement s=null;
        try {
            Class.forName("org.postgresql.Driver");
            c=DriverManager.getConnection("jdbc:postgresql://localhost:5433/testdb", "postgres", "password");
            c.setAutoCommit(false);
            s=c.createStatement();

            while(true){
                String insert="INSERT INTO crypto (id, name, price, times)" +
                        " VALUES ('"+this.option.type(this.option.id)+"', '"+this.option.getName()+"', "+this.option.getPrice(this.option.getName())+",now());";
                s.executeUpdate(insert);
                System.out.println("Added: " + this.option.getName() +" | "+this.option+" | "+now());
                Thread.sleep(INTERVAL);
                c.commit();

            }
        } catch (Exception e) {
            try {
                s.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            getPriceLock.unlock();
        }

    }
}