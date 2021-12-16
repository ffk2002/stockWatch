import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;


public class Option {
    String name;
    char id;
    BigDecimal price;
    LocalTime now;


    public Option(String ticker, LocalTime now){
        this.name=ticker.substring(1);
        this.id = ticker.charAt(0);
        this.now=now;
        this.price=getPrice(this.name);
    }

    public BigDecimal getPrice(String name)  {

            Stock stock = null;
            try {
                stock = YahooFinance.get(name);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return stock.getQuote().getPrice();
    }

    public BigDecimal getPercentChange(String stockName)  {

        Stock stock = null;
        try {
            stock = YahooFinance.get(stockName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BigDecimal change = stock.getQuote().getChangeInPercent();


        return change;
    }
    public BigDecimal getPeg(String stockName)  {

        Stock stock = null;
        try {
            stock = YahooFinance.get(stockName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BigDecimal peg = stock.getStats().getPeg();

        return peg;
    }

    public void setPrice(BigDecimal price) {
        this.price = getPrice(this.name);
    }

    public String type(char id){
        id = this.id;
        if(id=='#') return "CRYPTO";
        else if(id=='$') return "STOCK";
        else return "X";
    }

    @Override
    public String toString(){
        return type(this.id) + " | " + this.price;
    }
}
