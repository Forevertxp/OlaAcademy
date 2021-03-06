package com.michen.olaxueyuan.protocol.manager;

import com.michen.olaxueyuan.protocol.SECallBack;
import com.michen.olaxueyuan.protocol.model.SEStory;
import com.michen.olaxueyuan.protocol.model.SEUser;
import com.michen.olaxueyuan.protocol.result.ServiceError;
import com.michen.olaxueyuan.protocol.service.SEStoryService;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tianxiaopeng on 15-1-17.
 */
public class SEStoryManger {


    private static SEStoryManger s_instance;
    private ArrayList<SEStory> storyList = new ArrayList<SEStory>();
    private SEStoryService storyService;

    private SEStoryManger() {
        storyService = SERestManager.getInstance().create(SEStoryService.class);
    }

    public static SEStoryManger getInstance() {
        if (s_instance == null) {
            s_instance = new SEStoryManger();
        }
        return s_instance;
    }

    public ArrayList<SEStory> getStoryList() {
        return storyList;
    }

    public SEStoryService getStotyService() {
        return storyService;
    }


    public void refreshStory(int page, int limit, final SECallBack callback) {
        SEUser u = SEAuthManager.getInstance().getAccessUser();
        int uid = 0;
        if (u != null && !u.getId().equals("")) {
            uid = Integer.parseInt(u.getId());
        }
        getStotyService().fetchStory(page, limit, uid, new Callback<ArrayList<SEStory>>() {
            @Override
            public void success(ArrayList<SEStory> data, Response response) {
                storyList = data;
                if (callback != null) {
                    callback.success();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(new ServiceError(error));
                }

            }
        });
    }

    public void loadMoreInformation(int page, int limit, final SECallBack callback) {
        SEUser u = SEAuthManager.getInstance().getAccessUser();
        int uid = 0;
        if (u != null && !u.getId().equals("")) {
            uid = Integer.parseInt(u.getId());
        }
        getStotyService().fetchStory(page, limit, uid, new Callback<ArrayList<SEStory>>() {
            @Override
            public void success(ArrayList<SEStory> data, Response response) {
                storyList = data;
                if (callback != null) {
                    callback.success();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(new ServiceError(error));
                }

            }
        });
    }


}


