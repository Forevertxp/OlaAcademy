package com.michen.olaxueyuan.protocol.service;

import com.michen.olaxueyuan.protocol.result.HomeModule;
import com.michen.olaxueyuan.protocol.result.MaterialListResult;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by mingge on 16/4/28.
 */
public interface HomeListService {
    /**
     * 首页列表
     *
     * @param cb
     */
    @POST("/ola/home/getHomeList")
    void getHomeList(Callback<HomeModule> cb);

    /**
     * 资料列表
     *
     * @param userId     字符串
     * @param materailId 字符串	当前页最后一条的id
     * @param pageSize   字符串	每页条数
     * @param type       字符串	1 数学 2 英语 3 逻辑 4 写作
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/material/getMaterialList")
    void getMaterailList(
            @Field("userId") String userId,
            @Field("materailId") String materailId,
            @Field("pageSize") String pageSize,
            @Field("type") String type,
            Callback<MaterialListResult> cb);
}
