package my.awesome.project.cryptarbitrage30;

/**
 * Created by Alexander on 2/1/2018.
 */

public class Opportunity {
    private double percentGain;
    private int type;
    /*type 1 = usd * (coin/USD) * (btc/coin) * (usd/btc), type 2 = usd * (btc/usd) * (coin/btc) * (usd/coin)
      type 3 = usd * (coin/USD) * (eth/coin) * (usd/eth), type 4 = usd * (eth/usd) * (coin/eth) * (usd/coin)
      type 5 = eth * (coin/eth) * (btc/coin) * (eth/btc), type 6 = btc * (coin/btc) * (eth/coin) * (btc/eth)
      type 6 = buy at Exchange1, sell at Exchange two
    */
    private Coin highPriceCoinExchange;
    private Coin lowPriceCoinExchange;
    public Opportunity(double percentGain, int type, Coin highPriceCoinExchange, Coin lowPriceCoinExchange){
        this.percentGain = percentGain;
        this.type = type;
        this.highPriceCoinExchange = highPriceCoinExchange;
        this.lowPriceCoinExchange = lowPriceCoinExchange;
    }

    public double getPercentGain() {
        return percentGain;
    }

    public void setPercentGain(double percentGain) {
        this.percentGain = percentGain;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Coin getHighPriceCoinExchange() {
        return this.highPriceCoinExchange;
    }

    public void setHighPriceCoinExchange(Coin highPriceCoinExchange) {
        this.highPriceCoinExchange = highPriceCoinExchange;
    }

    public Coin getLowPriceCoinExchange() {
        return this.lowPriceCoinExchange;
    }

    public void setLowPriceCoinExchange(Coin lowPriceCoinExchange) {
        this.lowPriceCoinExchange = lowPriceCoinExchange;
    }
}