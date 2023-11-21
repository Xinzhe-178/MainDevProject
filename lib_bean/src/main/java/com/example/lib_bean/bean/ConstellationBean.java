package com.example.lib_bean.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2023/7/6 10:58 上午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class ConstellationBean {
    public boolean success;
    public DataDTO data;

    public static class DataDTO {
        public String title;
        public String type;
        public String time;
        public TodoDTO todo;
        public FortuneDTO fortune;
        public IndexDTO index;
        public String shortcomment;
        public FortunetextDTO fortunetext;
        public String luckynumber;
        public String luckycolor;
        public String luckyconstellation;

        public static class TodoDTO {
            public String yi;
            public String ji;
        }

        public static class FortuneDTO {
            public int all;
            public int love;
            public int work;
            public int money;
            public int health;
        }

        public static class IndexDTO {
            public String all;
            public String love;
            public String work;
            public String money;
            public String health;
        }

        public static class FortunetextDTO {
            public String all;
            public String love;
            public String work;
            public String money;
            public String health;
        }
    }

    public static class ConstellationItem {
        public String type;
        public String ConstellationName;

        public int position; //当前Item索引
        public int differ;   //当前item和居中位置的差值

        public ConstellationItem(int position, int differ) {
            this.position = position;
            this.differ = differ;
        }

        public ConstellationItem(String type, String constellationName) {
            this.type = type;
            ConstellationName = constellationName;
        }

        public static List<ConstellationItem> getData() {
            ArrayList<ConstellationItem> list = new ArrayList<>();
            list.add(new ConstellationItem("aries", "白羊"));
            list.add(new ConstellationItem("taurus", "金牛"));
            list.add(new ConstellationItem("gemini", "双子"));
            list.add(new ConstellationItem("cancer", "巨蟹"));
            list.add(new ConstellationItem("leo", "狮子"));
            list.add(new ConstellationItem("virgo", "处女"));
            list.add(new ConstellationItem("libra", "天秤"));
            list.add(new ConstellationItem("scorpio", "天蝎"));
            list.add(new ConstellationItem("sagittarius", "射手"));
            list.add(new ConstellationItem("capricorn", "摩羯"));
            list.add(new ConstellationItem("aquarius", "水瓶"));
            list.add(new ConstellationItem("pisces", "双鱼"));
            return list;
        }
    }
}
