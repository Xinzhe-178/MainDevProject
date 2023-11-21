package com.example.lib_bean.bean;

import java.util.List;

/**
 * Created by 王鑫哲 on 2022/4/2 下午 03:07
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class SeekBean {
    public int total;
    public boolean end;
    public String sid;
    public int ran;
    public int ras;
    public int cuben;
    public int manun;
    public int pornn;
    public int kn;
    public int cn;
    public int gn;
    public int ps;
    public int pc;
    public int adstar;
    public Object lastindex;
    public int ceg;
    public List<ListDTO> list;
    public Object boxresult;
    public Object wordguess;
    public int prevsn;

    public static class ListDTO {
        public String id;
        public boolean qqface_down_url;
        public boolean downurl;
        public String downurl_true;
        public boolean grpmd5;
        public int type;
        public String src;
        public int color;
        public int index;
        public String title;
        public String litetitle;
        public String width;
        public String height;
        public String imgsize;
        public String imgtype;
        public String key;
        public String dspurl;
        public String link;
        public int source;
        public String img;
        public String thumb_bak;
        public String thumb;
        public String _thumb_bak;
        public String _thumb;
        public String imgkey;
        public int thumbWidth;
        public String dsptime;
        public int thumbHeight;
        public boolean grpcnt;
        public boolean fixedSize;
        public String fnum;
        public String comm_purl;

        public ListDTO(String thumb) {
            this.thumb = thumb;
        }
    }
}
