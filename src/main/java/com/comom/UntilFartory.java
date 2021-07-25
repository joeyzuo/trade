package com.comom;

public  class UntilFartory {
   public static Double MA(Double[] c,Integer n){
        Double total=0.0;
        for (Double aDouble : c) {
            total=total+aDouble;
        }
        return total/n;
    }
    public  static Double fee(String symbol,Double price,Integer qty){
        Double fee=0.0;
        switch (symbol){
            case "BTC-SWAP-USDT":
                fee=price/10000*0.05/100*qty;
                break;
            default:
                break;
        };
        return  fee;
    }
    public  static Double profit(String symbol,String side,Double startP,Double overP,Integer qty,Double fee){
        Double profit=0.0;
        switch (symbol){
            case "BTC-SWAP-USDT":
                if(side.equals("BUY_OPEN")){
                    profit=  (overP-startP)/10000*qty-fee;
                }else {
                    profit= (startP-overP)/10000*qty-fee;
                }
                break;
            default:
                break;
        };
        return  profit;
    }

    public  static Double profitRate(String symbol,String side,Double startP,Double overP,Integer qty,Double origfee){
        Double profitRate=0.0;
        Double fee=0.0;
        fee=fee+origfee+fee(symbol,overP,qty);
        Double p=profit(symbol,side,startP,overP,qty,fee);
        profitRate=p/(startP/10000*qty)*100;
        return  profitRate;
    }

    public static void main(String[] args) {
        Double buy = UntilFartory.profit("BTC-SWAP-USDT", "SELL_OPEN", 31379.0, 31312.8, 1, 0.0);
        Double buy_open = UntilFartory.profitRate("BTC-SWAP-USDT", "SELL_OPEN", 31379.0, 31312.8, 1, 0.0);
        System.out.println(buy_open);
        System.out.println(buy);
        System.out.println(UntilFartory.fee("BTC-SWAP-USDT",31322.0,1));
    }

}
