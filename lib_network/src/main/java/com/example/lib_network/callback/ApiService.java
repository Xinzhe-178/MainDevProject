package com.example.lib_network.callback;

/**
 * Created by 王鑫哲 on 2021/8/24 上午 11:41
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public interface ApiService {
//    /**
//     * 封装列表接口调用接口多数据 返回Obj
//     */
//    @GET("/article/list/{page}/json")
//    Observable<BaseArrBean<RecyclerBean>> getHomeListData(@Path("page") int page, @Header("Cookie") String userName, @Header("Cookie") String passWord);
//
//    /**
//     * 获得当前时间 苏宁api
//     */
//    @GET("/api/ct.do")
//    Observable<CurrentTimeBean> getCurrentTime();
//
//    /**
//     * 搜索api
//     *
//     * @param content
//     * @param size
//     * @param page
//     * @return
//     */
//    @GET("/j?")
//    Observable<SeekBean> getSeekListData(@Query("q") String content, @Query("sn") int size, @Query("page") int page);
//
//    /**
//     * 随机美女照片
//     *
//     * @return
//     */
//    @GET("/api/wallpaper/mobileGirl?type=json")
//    Observable<BeautyBean> getRandomBeautyImage();
//
//    /**
//     * 免费开放api
//     * 获取一句名言
//     *
//     * @return
//     */
//    @GET("/api/sentences")
//    Observable<AphorismsBean> getAphorismsViewData();
//
//    /**
//     * 韩小韩 热门搜索
//     *
//     * @return
//     */
//    @GET("/api/hotlist/baiduRD")
//    Observable<SeekHotListBean> getSeekHotListData();
//
//    /**
//     * 韩小韩 每日星座运势
//     *
//     * @return
//     */
//    @GET("/api/horoscope?")
//    Observable<ConstellationBean> getConstellationData(@Query("type") String type, @Query("time") String time);
//
//    /**
//     -----------------------------玩安卓start----------------------------
//     */
//
//    /**
//     * 登录api
//     *
//     * @param map 传入 username password
//     * @return
//     */
//    @POST("/user/login")
//    @FormUrlEncoded
//    Observable<BaseBean<LoginBean>> getLoginApi(@FieldMap Map<String, String> map);
//
//    /**
//     * 注册api
//     *
//     * @param map 传入 username password repassword
//     * @return
//     */
//    @POST("/user/register")
//    @FormUrlEncoded
//    Observable<BaseBean<RegisterBean>> getRegisterApi(@FieldMap Map<String, String> map);
//
//    /**
//     * 收藏文章列表
//     *
//     * @param page     页码 从0开始
//     * @param userName 用户名称
//     * @param passWord 用户密码
//     * @return
//     */
//    @GET("/lg/collect/list/{page}/json")
//    Observable<BaseArrBean<RecyclerBean>> getFavListData(@Path("page") int page, @Header("Cookie") String userName, @Header("Cookie") String passWord);
//
//    /**
//     * 收藏该文章
//     *
//     * @param id       该文章的ID
//     * @param userName 用户名称
//     * @param passWord 用户密码
//     * @return
//     */
//    @POST("/lg/collect/{id}/json")
//    Observable<BaseBean<Object>> addFavArticle(@Path("id") int id, @Header("Cookie") String userName, @Header("Cookie") String passWord);
//
//    /**
//     * 取消收藏该文章
//     *
//     * @param id       该文章的ID
//     * @param userName 用户昵称
//     * @param passWord 用户密码
//     * @return
//     */
//    @POST("/lg/uncollect_originId/{id}/json")
//    Observable<BaseBean<Object>> removeFavArticle(@Path("id") int id, @Header("Cookie") String userName, @Header("Cookie") String passWord);
//
//    /**
//     -----------------------------玩安卓end------------------------------
//     */

}
