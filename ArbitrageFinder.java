import com.example.alexander.cryptarbitrage2.Coin;
import com.example.alexander.cryptarbitrage2.Exchange;
import java.lang.reflect.Array;
import java.util.ArrayList;
/**
 * Created by Alexander on 1/11/2018.
 */

public class ArbitrageFinder {
    private ArrayList<Exchange> allExchanges;
    private final int NUMBER_OPPROTUNITIES = 10;
    private MaxHeap bestOpprotunitiesWithinExchanges;


    public ArbitrageFinder(){
        bestOpprotunitiesWithinExchanges = new MaxHeap(NUMBER_OPPROTUNITIES);
    }

    public void addExchange(Exchange e){
        allExchanges.add(e);
    }
    public ArrayList<Exchange> getAllExchanges(){
        return this.allExchanges;
    }

    public MaxHeap getBestOpprotunitiesWithinExchange(){
        calculateBestOpprotunitiesWithinExchange();
        return this.bestOpprotunitiesWithinExchanges;
    }
    private void calculateBestOpprotunitiesWithinExchange(){
        for(Exchange exchange: allExchanges){
            for(Coin coin: exchange.getCoins()){ //120$/1Btc * 1LTC/10$ * xBTC/LTC  -- sell btc, buy ltc, exchange ltc for bitcoin
                //if coin can be converted to btc and converted to usd
                if(coin.getBidPriceBTC()>0 && coin.getBidPriceUSD()>0){
                    //sell price of bitcoin / buy price usd of coin * convert coin to btc (starded with 1 btc, now hopefully 1.03)
                    //buy price of bitcoin / convert btc to coin * sell price of other coin - (
                        // gotta divide by buy price of btc for percentage gain)
                    double betterDeal = Double.max((exchange.getCoins().get(0).getBidPriceUSD()/
                        coin.getAskPriceUSD()*coin.getBidPriceBTC()),exchange.getCoins().get(0).getAskPriceUSD()*
                            coin.getAskPriceBTC()*coin.getBidPriceUSD()/exchange.getCoins().get(0).getAskPriceUSD());
                    //if this can yield a 1% gain
                    if (betterDeal>1.01){
                        bestOpprotunitiesWithinExchanges.insert(betterDeal);
                    }

            }
        }
    }

}
