package com.example.lib_common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

import com.example.lib_common.R;
import com.example.lib_common.databinding.CustomHtmlWebviewLayoutBinding;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by 王鑫哲 on 2021/9/27 下午 09:32
 * E-mail: User_wang_178@163.com
 * Ps: 加载HTMLView
 */
public class CustomHTMLWebView extends BaseFrameLayout<CustomHtmlWebviewLayoutBinding> {
    public CustomHTMLWebView(@NonNull @NotNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomHTMLWebView(@NonNull @NotNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getLayoutId() {
        return R.layout.custom_html_webview_layout;
    }

    @Override
    public void initView() {
        //WebSettings方法里面是否执行JavaScript脚本&开启javascript 渲染
        mBinding.myWebView.getSettings().setJavaScriptEnabled(true);
        //隐藏水平滚动条
        mBinding.myWebView.setHorizontalScrollBarEnabled(false);
        //隐藏竖直滚动条
        mBinding.myWebView.setVerticalScrollBarEnabled(false);
        //不打开手机内部浏览器展示
        mBinding.myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //修改字体颜色
                view.loadUrl("javascript:function modifyTextColor(){" + "document.getElementsByTagName('body')[0].style.webkitTextFillColor='#E6E6E6'" + "};modifyTextColor();");
            }
        });

        /**
         * WebView默认用系统自带浏览器处理页面跳转。
         * 为了让页面跳转在当前WebView中进行，重写WebViewClient。
         * 但是按BACK键时，不会返回跳转前的页面，而是退出本Activity。重写onKeyDown()方法来解决此问题。
         */
        mBinding.myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    public void setUrl(String url) {
        //就是有时候web view不会自动换行，它会把连在一起的字母或者表情当作是一个单词，
        // 直接显示在后面，不自动换行就造成了在webView中显示不全的现象。
        String pre = "<body style=\"word-wrap:break-word;\"> </body>";
        //loadDataWithBaseURL加载数据的方法  changeImageWidth是写的方法
        mBinding.myWebView.loadDataWithBaseURL(null, pre + changeImageWidth(url), "text/html", "utf-8", null);
    }

    /**
     * 借用Jsoup来修改图片的宽度为100%自适应,防止图片过大造成WebView横向滚动的情况
     * 解决图片大小适配问题
     */
    private String changeImageWidth(String htmlText) {
        Document document = Jsoup.parse(htmlText);
        Elements elementImages = document.getElementsByTag("img");
        if (elementImages.size() > 0) {
            for (Element elementImage : elementImages) {
                elementImage.attr("style", "width: 100%");
            }
        }
        return document.toString();
    }
}
