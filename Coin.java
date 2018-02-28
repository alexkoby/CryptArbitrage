package My.Awesome.Project.cryptarbitrage30;


import android.support.annotation.NonNull;

/**
 * Created by Alexander on 1/9/2018.
 */

public class Coin implements Comparable<Coin>{
    //all converted to usd figures
    private Double bidQtyUSD;
    private Double askQtyUSD;
    private Double bidQtyBTC;
    private Double askQtyBTC;
    private Double bidQtyETH;
    private Double askQtyETH;



    private Double volumeUSD;
    private Double volumeBTC;
    private Double volumeETH;


    private String name;
    private String abbreviation;
    private Exchange exchange;
    private Double askPriceUSD;
    private Double bidPriceUSD;
    private Double askPriceBTC;
    private Double bidPriceBTC;
    private Double askPriceETH;
    private Double bidPriceETH;
    private String USDPairBitcoinPairEthPair;


    /**
     * Constructor
     * @param name is the name of the coin
     */
    public Coin(String name, String abbreviation, Exchange exchange, String USDPairBitcoinPairEthPair){
        this.name = name;
        this.abbreviation = abbreviation;
        this.exchange = exchange;
        this.USDPairBitcoinPairEthPair = USDPairBitcoinPairEthPair;
    }
    public Coin(String name, String abbreviation, Exchange exchange){
        this.name = name;
        this.abbreviation = abbreviation;
        this.exchange = exchange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public Double getAskPriceUSD() {
        return askPriceUSD;
    }

    public void setAskPriceUSD(Double askPriceUSD) {
        this.askPriceUSD = askPriceUSD;
    }

    public Double getBidPriceUSD() {
        return bidPriceUSD;
    }

    public void setBidPriceUSD(Double bidPriceUSD) {
        this.bidPriceUSD = bidPriceUSD;
    }

    public Double getAskPriceBTC() {
        return askPriceBTC;
    }

    public void setAskPriceBTC(Double askPriceBTC) {
        this.askPriceBTC = askPriceBTC;
    }

    public Double getBidPriceBTC() {
        return bidPriceBTC;
    }

    public void setBidPriceBTC(Double bidPriceBTC) {
        this.bidPriceBTC = bidPriceBTC;
    }

    public Double getAskPriceETH() {
        return askPriceETH;
    }

    public void setAskPriceETH(Double askPriceETH) {
        this.askPriceETH = askPriceETH;
    }

    public Double getBidPriceETH() {
        return bidPriceETH;
    }

    public void setBidPriceETH(Double bidPriceETH) {
        this.bidPriceETH = bidPriceETH;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getUSDBTCETHPairs(){
        return this.USDPairBitcoinPairEthPair;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Double getVolumeUSD(){
        return this.volumeUSD;
    }
    public void setVolumeUSD(Double volume){
        this.volumeUSD = volume;
    }
    public Double getVolumeBTC(){
        return this.volumeBTC;
    }
    public void setVolumeBTC(Double volume){
        this.volumeBTC = volume;
    }
    public Double getVolumeETH(){
        return this.volumeETH;
    }
    public void setVolumeETH(Double volume){
        this.volumeETH = volume;
    }
    public Double getBidQtyUSD() {
        return bidQtyUSD;
    }

    public void setBidQtyUSD(Double bidQtyUSD) {
        this.bidQtyUSD = bidQtyUSD;
    }

    public Double getAskQtyUSD() {
        return askQtyUSD;
    }

    public void setAskQtyUSD(Double askQtyUSD) {
        this.askQtyUSD = askQtyUSD;
    }

    public Double getBidQtyBTC() {
        return bidQtyBTC;
    }

    public void setBidQtyBTC(Double bidQtyBTC) {
        this.bidQtyBTC = bidQtyBTC;
    }

    public Double getAskQtyBTC() {
        return askQtyBTC;
    }

    public void setAskQtyBTC(Double askQtyBTC) {
        this.askQtyBTC = askQtyBTC;
    }

    public Double getBidQtyETH() {
        return bidQtyETH;
    }

    public void setBidQtyETH(Double bidQtyETH) {
        this.bidQtyETH = bidQtyETH;
    }

    public Double getAskQtyETH() {
        return askQtyETH;
    }

    public void setAskQtyETH(Double askQtyETH) {
        this.askQtyETH = askQtyETH;
    }


    @Override
    //sorts coins alphabetically
    public int compareTo(@NonNull Coin coin) {
        return this.getName().compareTo(coin.getName());
    }
}