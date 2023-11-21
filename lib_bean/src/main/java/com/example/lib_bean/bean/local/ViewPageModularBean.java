package com.example.lib_bean.bean.local;

/**
 * Created by 王鑫哲 on 2021/12/3 下午 06:21
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class ViewPageModularBean {
    public String title;
    public int icon;
    public int flag; //用来区分是哪个条目 跳转使用
    public int showPage; //显示在第几页

    public ViewPageModularBean(String title, int icon, int flag, int showPage) {
        this.title = title;
        this.icon = icon;
        this.flag = flag;
        this.showPage = showPage;
    }

    @Override
    public String toString() {
        return "ViewPageModularBean{" + "\n" +
                "title='" + title + '\'' +
                ", icon=" + icon +
                ", flag=" + flag +
                ", showPage=" + showPage +
                '}' + "\n";
    }
}
