import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBAdder{
    public static void main(String[] args) {
        Connection c=null;
        try{
            Class.forName("org.postgresql.Driver");
            c=DriverManager.getConnection("jdbc:postgresql://localhost:5433/testdb", "postgres", "password");
            createTable(c);
            startThreads();
            c.close();
        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(1);
        }
        System.out.println("opened");
        System.out.println("table created");
    }

    public static void createTable(Connection c) throws SQLException {
        Statement s=c.createStatement();
        String createTable="CREATE TABLE IF NOT EXISTS CRYPTO" +
                "(NAME  VARCHAR    NOT NULL," +
                "PRICE  DECIMAL     NOT NULL," +
                "TIME   TIME    ," +
                "ID     VARCHAR    NOT NULL)";
        s.executeUpdate(createTable);
        s.close();
    }

    public static void startThreads() throws SQLException {
        Runnable NEE = new Watcher("#BTC-USD");
        Thread tNEE = new Thread(NEE);
        tNEE.start();
    }
}