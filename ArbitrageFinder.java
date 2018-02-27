package My.Awesome.Project.cryptarbitrage30;
import android.graphics.Path;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ArrayList;
/**
 * Created by Alexander on 1/11/2018.
 */

public class ArbitrageFinder {
    private final int NUMBER_OPPORTUNITIES = 15;
    private PriorityQueue<Opportunity> bestOpportunitiesWithinExchanges;
    private PriorityQueue<Opportunity> bestOpportunitiesAcrossExchanges;
    private double goalReturn;

    public ArbitrageFinder(double goalReturn) {
        this.goalReturn = goalReturn;
        bestOpportunitiesWithinExchanges = new PriorityQueue(NUMBER_OPPORTUNITIES, new Comparator<Opportunity>() {
            @Override
            public int compare(Opportunity o1, Opportunity o2) {
                if(o1.getPercentGain() > o2.getPercentGain()){
                    return -1;
                }
                else if (o1.getPercentGain() < o2.getPercentGain()){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        });
        bestOpportunitiesAcrossExchanges = new PriorityQueue(NUMBER_OPPORTUNITIES, new Comparator<Opportunity>() {
            @Override
            public int compare(Opportunity o1, Opportunity o2) {
                if(o1.getPercentGain() > o2.getPercentGain()){
                    return -1;
                }
                else if (o1.getPercentGain() < o2.getPercentGain()){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        });
    }
    public PriorityQueue getBestOpportunitiesWithinExchange() {
        calculateBestOpportunitiesWithinExchange();
        return this.bestOpportunitiesWithinExchanges;
    }
    public PriorityQueue getBestOpportunitiesAcrossExchange(){
        calculateBestOpportunitiesCrossExchange();
        return this.bestOpportunitiesAcrossExchanges;
    }

    private void calculateBestOpportunitiesCrossExchange(){
        if(HomePage.listOfCurrencies.size() == 0){
            //tell user to select more currencies
            return;
        }
        if(HomePage.listOfExchanges.size() <= 1){
            //tell user to select more exchanges - need at least two to tango
            return;
        }
        for(String coin: HomePage.listOfCurrencies){
            typeSevenandEight(coin);
        }
    }

    private void calculateBestOpportunitiesWithinExchange() {
        Opportunity opportunity;
        Coin bitcoin;
        Coin ethereum;
        if(HomePage.listOfExchanges.size() == 0){
            //Tell user to select more Exchanges
            return;
        }
        for (Exchange exchange : HomePage.listOfExchanges) {
            bitcoin = exchange.getCoins().get(0);
            ethereum = exchange.getCoins().get(1);
            //don't want to perform function
            if(exchange.getCoins().size() == 0){
                continue;
            }
            for (Coin coin : exchange.getCoins()) {
                opportunity = typeOne(coin, bitcoin );
                if(opportunity != null){
                    bestOpportunitiesWithinExchanges.add(opportunity);
                }
                opportunity = typeTwo(coin, bitcoin);
                if(opportunity != null){
                    bestOpportunitiesWithinExchanges.add(opportunity);
                }
                opportunity = typeThree(coin, ethereum);
                if(opportunity != null){
                    bestOpportunitiesWithinExchanges.add(opportunity);
                }
                opportunity = typeFour(coin, ethereum);
                if(opportunity != null){
                    bestOpportunitiesWithinExchanges.add(opportunity);
                }
                opportunity = typeFive(coin, ethereum);
                if(opportunity != null){
                    bestOpportunitiesWithinExchanges.add(opportunity);
                }
                opportunity = typeSix(coin, ethereum);
                if(opportunity != null){
                    bestOpportunitiesWithinExchanges.add(opportunity);
                }
            }
        }
    }
    /**
     * Checks if way type one offers above a 'goalReturn' arbitrage opportunitiy
     * @param coin is the coin you're testing
     * @param bitcoin is bitcoin on the exchange your coin is on
     * @return Opportunity if a 'goalReturn' opportunity or higher exists, null if it doesn't
     */
    private Opportunity typeOne(Coin coin, Coin bitcoin) {
        double type1Rate;

        if (coin.getAskPriceUSD() > 0 && coin.getBidPriceBTC() > 0 && bitcoin.getBidPriceUSD() > 0) {
            type1Rate = (1/coin.getAskPriceUSD()) * coin.getBidPriceBTC() * bitcoin.getBidPriceUSD();
            System.out.println("Type 1: "+ type1Rate+ "  " + coin.getName());
            if (type1Rate - 1 > goalReturn / 100) {
                return new Opportunity((type1Rate - 1) * 100, 1, coin, bitcoin);
            }
        }
        return null;
    }
    /**
     * Checks if way type two offers above a 'goalReturn' arbitrage opportunitiy
     * @param coin is the coin you're testing
     * @param bitcoin is bitcoin on the exchange your coin is on
     * @return Opportunity if a 'goalReturn' opportunity or higher exists, null if it doesn't
     */
    private Opportunity typeTwo(Coin coin, Coin bitcoin) {
        double type2Rate;
        if (bitcoin.getAskPriceUSD() > 0 && coin.getAskPriceBTC() > 0 && coin.getBidPriceUSD() > 0) {
            type2Rate = (1/bitcoin.getAskPriceUSD()) * (1/coin.getAskPriceBTC()) * coin.getBidPriceUSD();
            System.out.println("Type 2: "+ type2Rate+ "  " + coin.getName());
            if (type2Rate - 1 > goalReturn / 100) {
                return new Opportunity((type2Rate - 1) * 100, 2, coin, bitcoin);
            }
        }
        return null;
    }
    /**
     * Checks if way type three offers above a 'goalReturn' arbitrage opportunitiy
     * @param coin is the coin you're testing
     * @param ethereum is ethereum on the exchange your coin is on
     * @return Opportunity if a 'goalReturn' opportunity or higher exists, null if it doesn't
     */
    private Opportunity typeThree(Coin coin, Coin ethereum) {
        double type3Rate;
        if (coin.getAskPriceUSD() > 0 && coin.getBidPriceETH() > 0 && ethereum.getBidPriceUSD() > 0) {
            type3Rate = (1/coin.getAskPriceUSD()) * coin.getBidPriceETH() * ethereum.getBidPriceUSD();
            System.out.println("Type 3: "+ type3Rate+ "  " + coin.getName());
            if (type3Rate - 1 > goalReturn / 100) {
                return new Opportunity((type3Rate - 1) * 100, 3, coin, ethereum);
            }
        }
        return null;
    }

    /**
     * Checks if way type four offers above a 'goalReturn' arbitrage opportunitiy
     * @param coin is the coin you're testing
     * @param ethereum is ethereum on the exchange your coin is on
     * @return Opportunity if a 'goalReturn' opportunity or higher exists, null if it doesn't
     */
    private Opportunity typeFour(Coin coin, Coin ethereum) {
        double type4Rate;
        if (ethereum.getAskPriceUSD() > 0 && coin.getAskPriceETH() > 0 && coin.getBidPriceUSD() > 0) {
            type4Rate = (1/ethereum.getAskPriceUSD()) * (1/coin.getAskPriceETH()) * coin.getBidPriceUSD();
            System.out.println("Type 4: "+ type4Rate+ "  " + coin.getName());
            if (type4Rate - 1> goalReturn / 100) {
                return new Opportunity((type4Rate - 1) * 100, 4, coin, ethereum);
            }
        }
        return null;
    }

    /**
     * Checks if way type four offers above a 'goalReturn' arbitrage opportunitiy
     * @param coin is the coin you're testing
     * @param ethereum is ethereum on the exchange you're testing
     * @return Opportunity if a 'goalReturn' opportunity or higher exists, null if it doesn't
     */
    private Opportunity typeFive(Coin coin, Coin ethereum) {
        double type5Rate;
        if(coin.getAskPriceETH() > 0 && coin.getBidPriceBTC() > 0 && ethereum.getAskPriceBTC() > 0){
            type5Rate = (1 / coin.getAskPriceETH()) * coin.getBidPriceBTC() / ethereum.getAskPriceBTC();
            System.out.println("Type 5 " + type5Rate + "  " + coin.getName());
            if(type5Rate - 1 > goalReturn / 100) {
                return new Opportunity((type5Rate - 1) * 100, 5, coin, ethereum);
            }
        }
        return null;
    }
    /**
     * Checks if way type four offers above a 'goalReturn' arbitrage opportunitiy
     * @param coin is the coin you're testing
     * @param ethereum is ethereum on the exchange you're testing
     * @return Opportunity if a 'goalReturn' opportunity or higher exists, null if it doesn't
     */
    private Opportunity typeSix(Coin coin, Coin ethereum) {
        double type6Rate;
        if(coin.getAskPriceBTC() > 0 && coin.getBidPriceETH() > 0 && ethereum.getBidPriceBTC() > 0) {
            type6Rate = (1 / coin.getAskPriceBTC()) * coin.getBidPriceETH() * ethereum.getBidPriceBTC();
            System.out.println("Type 6: "+ type6Rate + "  "+ coin.getName());
            if(type6Rate -1 > goalReturn / 100) {
                return new Opportunity((type6Rate - 1) * 100, 6, coin, ethereum);
            }
        }
        return null;
    }

    /**
     * Checks if way type seven offers above a 'goalReturn' arbitrage opportunitiy
     * @param coinName is the coin you're testing
     */
    private void typeSevenandEight(String coinName){
        ArrayList<Coin> listOfCoins = new ArrayList<>();

        for(Exchange exchange: HomePage.listOfExchanges){
            for(Coin coin: exchange.getCoins()){
                if(coin.getName().equals(coinName)){
                    listOfCoins.add(coin);
                }
            }
        }
        if(listOfCoins.size() == 0){
            return;
        }
        else {
            for (int i = 0; i < listOfCoins.size() - 1; i++) {
                for (int j = i + 1; j < listOfCoins.size(); j++) {
                    if (listOfCoins.get(i).getExchange().getIsUSD() && listOfCoins.get(j).getExchange().getIsUSD()){
                        if (listOfCoins.get(i).getBidPriceUSD() / listOfCoins.get(j).getAskPriceUSD() - 1 >
                                HomePage.minGainsWanted / 100) {
                            bestOpportunitiesAcrossExchanges.add(new Opportunity((listOfCoins.get(i).getBidPriceUSD()
                                    / listOfCoins.get(j).getAskPriceUSD() - 1) * 100, 7, listOfCoins.get(i), listOfCoins.get(j)));
                        }
                        if (listOfCoins.get(j).getBidPriceUSD() / listOfCoins.get(i).getAskPriceUSD() - 1 >
                                HomePage.minGainsWanted / 100) {
                            bestOpportunitiesAcrossExchanges.add(new Opportunity((listOfCoins.get(j).getBidPriceUSD()
                                    / listOfCoins.get(i).getAskPriceUSD() - 1) * 100, 7, listOfCoins.get(j), listOfCoins.get(i)));
                        }
                    }
                    else if (listOfCoins.get(i).getExchange().getIsUSD() == listOfCoins.get(j).getExchange().getIsUSD()){

                        if (listOfCoins.get(i).getBidPriceUSD() / listOfCoins.get(j).getAskPriceUSD() - 1 >
                                HomePage.minGainsWanted / 100) {
                            bestOpportunitiesAcrossExchanges.add(new Opportunity((listOfCoins.get(i).getBidPriceUSD()
                                    / listOfCoins.get(j).getAskPriceUSD() - 1) * 100, 10, listOfCoins.get(i), listOfCoins.get(j)));
                        }
                        if (listOfCoins.get(j).getBidPriceUSD() / listOfCoins.get(i).getAskPriceUSD() - 1 >
                                HomePage.minGainsWanted / 100) {
                            bestOpportunitiesAcrossExchanges.add(new Opportunity((listOfCoins.get(j).getBidPriceUSD()
                                    / listOfCoins.get(i).getAskPriceUSD() - 1) * 100, 10, listOfCoins.get(j), listOfCoins.get(i)));
                        }
                    }
                    if (listOfCoins.get(i).getBidPriceBTC() / listOfCoins.get(j).getAskPriceBTC() - 1 >
                            HomePage.minGainsWanted / 100) {
                        bestOpportunitiesAcrossExchanges.add(new Opportunity((listOfCoins.get(i).getBidPriceBTC()
                                / listOfCoins.get(j).getAskPriceBTC() - 1) * 100, 8, listOfCoins.get(i), listOfCoins.get(j)));
                    }
                    if (listOfCoins.get(j).getBidPriceBTC() / listOfCoins.get(i).getAskPriceBTC() - 1 >
                            HomePage.minGainsWanted / 100) {
                        bestOpportunitiesAcrossExchanges.add(new Opportunity((listOfCoins.get(j).getBidPriceBTC()
                                / listOfCoins.get(i).getAskPriceBTC() - 1) * 100, 8, listOfCoins.get(j), listOfCoins.get(i)));
                    }
                    if (listOfCoins.get(i).getBidPriceETH() / listOfCoins.get(j).getAskPriceETH() - 1 >
                            HomePage.minGainsWanted / 100) {
                        bestOpportunitiesAcrossExchanges.add(new Opportunity((listOfCoins.get(i).getBidPriceETH()
                                / listOfCoins.get(j).getAskPriceETH() - 1) * 100, 9, listOfCoins.get(i), listOfCoins.get(j)));
                    }
                    if (listOfCoins.get(j).getBidPriceETH() / listOfCoins.get(i).getAskPriceETH() - 1 >
                            HomePage.minGainsWanted / 100) {
                        bestOpportunitiesAcrossExchanges.add(new Opportunity((listOfCoins.get(j).getBidPriceETH()
                                / listOfCoins.get(i).getAskPriceETH() - 1) * 100, 9, listOfCoins.get(j), listOfCoins.get(i)));

                    }
                }
            }
        }
    }

    public void getRealVolumeNumbers(){
        for(Exchange exchange: HomePage.listOfExchanges){
            switch (exchange.getName()){
                case "Bittrex":
                    for(Coin coin: exchange.getCoins()){
                        if(coin.getVolumeBTC() > 0) {
                            coin.setVolumeBTC(coin.getVolumeBTC() * exchange.getCoins().get(0).getAskPriceUSD());
                        }
                        if(coin.getVolumeETH() > 0) {
                            coin.setVolumeETH(coin.getVolumeETH() * exchange.getCoins().get(1).getAskPriceUSD());
                        }
                    }
                    break;
                case "HitBTC":
                    for(Coin coin: exchange.getCoins()){
                        if(coin.getVolumeBTC() > 0) {
                            coin.setVolumeBTC(coin.getVolumeBTC() * coin.getBidPriceBTC() * exchange.getCoins().get(0).getAskPriceUSD());
                        }
                        if(coin.getVolumeETH() > 0) {
                            coin.setVolumeETH(coin.getVolumeETH() * coin.getBidPriceETH() * exchange.getCoins().get(1).getAskPriceUSD());
                        }
                        if(coin.getVolumeUSD() > 0) {
                            coin.setVolumeUSD(coin.getVolumeUSD() * coin.getAskPriceUSD());
                        }
                    }
                    break;
                case "Bit-Z":
                    for(Coin coin: exchange.getCoins()){
                        if(coin.getVolumeBTC() > 0) {
                            coin.setVolumeBTC(coin.getVolumeBTC() * coin.getBidPriceBTC() * exchange.getCoins().get(0).getAskPriceUSD());
                        }
                        if(coin.getVolumeETH() > 0) {
                            coin.setVolumeETH(coin.getVolumeETH() * coin.getBidPriceETH() * exchange.getCoins().get(1).getAskPriceUSD());
                        }
                        if(coin.getVolumeUSD() > 0) {
                            coin.setVolumeUSD(coin.getVolumeUSD() * coin.getAskPriceUSD());
                        }
                    }
                    break;
                case "Poloniex":
                    for(Coin coin: exchange.getCoins()){
                        if(coin.getVolumeBTC() > 0) {
                            coin.setVolumeBTC(coin.getVolumeBTC() * exchange.getCoins().get(0).getAskPriceUSD());
                        }
                        if(coin.getVolumeETH() > 0) {
                            coin.setVolumeETH(coin.getVolumeETH() * exchange.getCoins().get(1).getAskPriceUSD());
                        }
                    }
                    break;

                case "OKEX":
                    for(Coin coin: exchange.getCoins()){
                        if(coin.getVolumeBTC() > 0) {
                            coin.setVolumeBTC(coin.getVolumeBTC() * coin.getBidPriceBTC() * exchange.getCoins().get(0).getAskPriceUSD());
                        }
                        if(coin.getVolumeETH() > 0) {
                            coin.setVolumeETH(coin.getVolumeETH() * coin.getBidPriceETH() * exchange.getCoins().get(1).getAskPriceUSD());
                        }
                        if(coin.getVolumeUSD() > 0) {
                            coin.setVolumeUSD(coin.getVolumeUSD() * coin.getAskPriceUSD());
                        }
                    }
                    break;
                case "Huobi":
                    for(Coin coin: exchange.getCoins()){
                        if(coin.getVolumeBTC() > 0) {
                            coin.setVolumeBTC(coin.getVolumeBTC() * coin.getBidPriceBTC() * exchange.getCoins().get(0).getAskPriceUSD());
                        }
                        if(coin.getVolumeETH() > 0) {
                            coin.setVolumeETH(coin.getVolumeETH() * coin.getBidPriceETH() * exchange.getCoins().get(1).getAskPriceUSD());
                        }
                        if(coin.getVolumeUSD() > 0) {
                            coin.setVolumeUSD(coin.getVolumeUSD() * coin.getAskPriceUSD());
                        }
                    }
                    break;
            }
        }

        for(Exchange exchange: HomePage.listOfExchanges){
            System.out.println(exchange.getName());
            for(Coin coin: exchange.getCoins()){
                System.out.println(coin.getName() + ": V usd: " + coin.getVolumeUSD() +
                        "  v btc: " + coin.getVolumeBTC() + "  volume eth: " + coin.getVolumeETH());
            }
        }
    }
}