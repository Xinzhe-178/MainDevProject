package com.example.lib_statistics;

import android.util.Log;

/**
 * Created by 王鑫哲 on 2023/2/8 2:23 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class Statistics extends BaseStatistics {

    private static final class Holder {
        static Statistics H = new Statistics();
    }

    public static Statistics getInstance() {
        return Holder.H;
    }

    @Override
    public void addRecord(String record) {
        super.addRecord(record);

        String concat = record.concat(getTime());

        Log.e(TAG.concat("addRecord--> "), concat);
    }
}
