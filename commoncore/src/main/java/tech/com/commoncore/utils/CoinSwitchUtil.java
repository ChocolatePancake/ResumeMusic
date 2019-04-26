package tech.com.commoncore.utils;

import tech.com.commoncore.R;

/**
 * Anthor:HeChuan
 * Time:2019/1/4
 * Desc:
 */
public class CoinSwitchUtil {

    public static int getCoinIconId(String coinName) {
        if(coinName!=null&&!coinName.equals("")){
            coinName = coinName.toUpperCase();
        }
        if(coinName.contains("ada")){
            return R.mipmap.icon_coin_ada;
        }
        if(coinName.contains("dash")){
            return R.mipmap.icon_coin_dash;
        }
        if(coinName.contains("etc")){
            return R.mipmap.icon_coin_etc;
        }
        if(coinName.contains("miota")){
            return R.mipmap.icon_coin_miota;
        }
        if(coinName.contains("qtum")){
            return R.mipmap.icon_coin_qtum;
        }
        if(coinName.contains("trx")){
            return R.mipmap.icon_coin_trx;
        }
        if(coinName.contains("usdt")){
            return R.mipmap.icon_coin_usdt;
        }
        if(coinName.contains("ven")){
            return R.mipmap.icon_coin_ven;
        }
        if(coinName.contains("xem")){
            return R.mipmap.icon_coin_xem;
        }
        if(coinName.contains("bch")){
            return R.mipmap.icon_coin_bch;
        }
        if(coinName.contains("xmr")){
            return R.mipmap.icon_coin_xmr;
        }
        if(coinName.contains("btc")){
            return R.mipmap.icon_coin_btc;
        }
        if(coinName.contains("eos")){
            return R.mipmap.icon_coin_eos;
        }
        if(coinName.contains("eth")){
            return R.mipmap.icon_coin_eth;
        }
        if(coinName.contains("ltc")){
            return R.mipmap.icon_coin_ltc;
        }
        if(coinName.contains("xlm")){
            return R.mipmap.icon_coin_xlm;
        }
        if(coinName.contains("xrp")){
            return R.mipmap.icon_coin_xrp;
        }
        return R.mipmap.icon_coin_btc;
    }

}
