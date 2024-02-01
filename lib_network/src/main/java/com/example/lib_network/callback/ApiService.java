package com.example.lib_network.callback;

import com.example.lib_bean.BaseArrBean;
import com.example.lib_bean.BaseObjBean;
import com.example.lib_bean.bean.AphorismsBean;
import com.example.lib_bean.bean.BeautyBean;
import com.example.lib_bean.bean.ConstellationBean;
import com.example.lib_bean.bean.CurrentTimeBean;
import com.example.lib_bean.bean.LoginBean;
import com.example.lib_bean.bean.RecyclerBean;
import com.example.lib_bean.bean.RegisterBean;
import com.example.lib_bean.bean.SeekBean;
import com.example.lib_bean.bean.SeekHotListBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by 王鑫哲 on 2021/8/24 上午 11:41
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public interface ApiService {
    /**
     * 封装列表接口调用接口多数据 返回Obj
     */
    @GET("/article/list/{page}/json")
    Observable<BaseArrBean<RecyclerBean>> getHomeListData(@Path("page") int page);

    /**
     * 获得当前时间 苏宁api
     */
    @GET("/api/ct.do")
    Observable<CurrentTimeBean> getCurrentTime();

    /**
     * 搜索api
     *
     * @param content
     * @param size
     * @param page
     * @return
     */
    @GET("/j?")
    Observable<SeekBean> getSeekListData(@Query("q") String content, @Query("sn") int size, @Query("page") int page);

    /**
     * 随机美女照片
     *
     * @return
     */
    @GET("/api/mobil.girl?type=json")
    Observable<BeautyBean> getRandomBeautyImage();

    /**
     * 免费开放api
     * 获取一句名言
     *
     * @return
     */
    @GET("/api/sentences")
    Observable<AphorismsBean> getAphorismsViewData();

    /**
     * 韩小韩 热门搜索
     *
     * @return
     */
    @GET("/api/hotlist?type=")
    Observable<SeekHotListBean> getSeekHotListData(@Query("type") String type);

    /**
     * 韩小韩 每日星座运势
     *
     * @return
     */
    @GET("/api/horoscope?")
    Observable<ConstellationBean> getConstellationData(@Query("type") String type, @Query("time") String time);

    /**
     -----------------------------玩安卓start----------------------------
     */

    /**
     * 登录api
     *
     * @param map 传入 username password
     * @return
     */
    @POST("/user/login")
    @FormUrlEncoded
    Observable<BaseObjBean<LoginBean>> getLoginApi(@FieldMap Map<String, String> map);

    /**
     * 注册api
     *
     * @param map 传入 username password repassword
     * @return
     */
    @POST("/user/register")
    @FormUrlEncoded
    Observable<BaseObjBean<RegisterBean>> getRegisterApi(@FieldMap Map<String, String> map);

    /**
     * 收藏文章列表
     *
     * @param page     页码 从0开始
     * @param userName 用户名称
     * @param passWord 用户密码
     * @return
     */
    @GET("/lg/collect/list/{page}/json")
    Observable<BaseArrBean<RecyclerBean>> getFavListData(@Path("page") int page, @Header("Cookie") String userName, @Header("Cookie") String passWord);

    /**
     * 收藏该文章
     *
     * @param id       该文章的ID
     * @param userName 用户名称
     * @param passWord 用户密码
     * @return
     */
    @POST("/lg/collect/{id}/json")
    Observable<BaseObjBean<Object>> addFavArticle(@Path("id") int id, @Header("Cookie") String userName, @Header("Cookie") String passWord);

    /**
     * 取消收藏该文章
     *
     * @param id       该文章的ID
     * @param userName 用户昵称
     * @param passWord 用户密码
     * @return
     */
    @POST("/lg/uncollect_originId/{id}/json")
    Observable<BaseObjBean<Object>> removeFavArticle(@Path("id") int id, @Header("Cookie") String userName, @Header("Cookie") String passWord);

    /**
     -----------------------------玩安卓end------------------------------
     */

}
