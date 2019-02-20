package com.mcp.lottery.util.cons;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shiqm on 2018/9/29.
 */
public class ConvertCons {

    public static final class GameCodeCover {
        public static final Map<String, String> map = new HashMap();

        static {
            init();
        }

        public static void init() {
            map.put(Cons.Game.CQSSC,"重庆时时彩");
            map.put(Cons.Game.TXFFC,"腾讯分分彩");

        }

        public static String get(String key) {
            return map.get(key);
        }
    }


}
