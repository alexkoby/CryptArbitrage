package my.awesome.project.cryptarbitrage30;


import android.support.annotation.NonNull;

/**
 * Created by Alexander on 1/9/2018.
 */

public class Coin implements Comparable<Coin>{
    //all converted to usd figures
    /*private Double bidQtyUSD;
    private Double askQtyUSD;
    private Double bidQtyBTC;
    private Double askQtyBTC;
    private Double bidQtyETH;
    private Double askQtyETH;*/



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
    double withdrawalFee;


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
    protected Coin(String name, String abbreviation, Exchange exchange){
        this.name = name;
        this.abbreviation = abbreviation;
        this.exchange = exchange;
    }
    public Coin(String name, String abbreviation, Exchange exchange, String USDPairBitcoinPairEthPair, double withdrawalFree){
        this.name = name;
        this.abbreviation = abbreviation;
        this.exchange = exchange;
        this.USDPairBitcoinPairEthPair = USDPairBitcoinPairEthPair;
        this.withdrawalFee = withdrawalFree;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected Exchange getExchange() {
        return exchange;
    }

    protected void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    protected Double getAskPriceUSD() {
        return askPriceUSD;
    }

    protected void setAskPriceUSD(Double askPriceUSD) {
        this.askPriceUSD = askPriceUSD;
    }

    protected Double getBidPriceUSD() {
        return bidPriceUSD;
    }

    protected void setBidPriceUSD(Double bidPriceUSD) {
        this.bidPriceUSD = bidPriceUSD;
    }

    protected Double getAskPriceBTC() {
        return askPriceBTC;
    }

    protected void setAskPriceBTC(Double askPriceBTC) {
        this.askPriceBTC = askPriceBTC;
    }

    protected Double getBidPriceBTC() {
        return bidPriceBTC;
    }

    protected void setBidPriceBTC(Double bidPriceBTC) {
        this.bidPriceBTC = bidPriceBTC;
    }

    protected Double getAskPriceETH() {
        return askPriceETH;
    }

    protected void setAskPriceETH(Double askPriceETH) {
        this.askPriceETH = askPriceETH;
    }

    protected Double getBidPriceETH() {
        return bidPriceETH;
    }

    protected void setBidPriceETH(Double bidPriceETH) {
        this.bidPriceETH = bidPriceETH;
    }

    protected String getAbbreviation() {
        return abbreviation;
    }

    protected String getUSDBTCETHPairs(){
        return this.USDPairBitcoinPairEthPair;
    }

    protected void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    protected Double getVolumeUSD(){
        return this.volumeUSD;
    }
    protected void setVolumeUSD(Double volume){
        this.volumeUSD = volume;
    }
    protected Double getVolumeBTC(){
        return this.volumeBTC;
    }
    protected void setVolumeBTC(Double volume){
        this.volumeBTC = volume;
    }
    protected Double getVolumeETH(){
        return this.volumeETH;
    }
    protected void setVolumeETH(Double volume){
        this.volumeETH = volume;
    }
    /*public Double getBidQtyUSD() {
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
    */

    public double getWithdrawalFee(){
        return this.withdrawalFee;
    }

    @Override
    //sorts coins alphabetically
    public int compareTo(@NonNull Coin coin) {
        return this.getName().compareTo(coin.getName());
    }
}