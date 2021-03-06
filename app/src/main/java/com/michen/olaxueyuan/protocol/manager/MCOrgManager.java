package com.michen.olaxueyuan.protocol.manager;

import com.michen.olaxueyuan.protocol.result.MCOrgListResult;
import com.michen.olaxueyuan.protocol.result.MCTeacherResult;
import com.michen.olaxueyuan.protocol.service.MCOrgService;
import com.michen.olaxueyuan.protocol.result.MCCheckInfoResult;
import com.michen.olaxueyuan.protocol.result.MCCommonResult;
import com.michen.olaxueyuan.protocol.result.MCEnrollListResult;
import com.michen.olaxueyuan.protocol.result.SystemCourseResult;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tianxiaopeng on 15/12/20.
 */
public class MCOrgManager {

    private static MCOrgManager s_instance;
    private MCOrgService orgService;

    private MCOrgManager() {
        orgService = SERestManager.getInstance().create(MCOrgService.class);
    }

    public static MCOrgManager getInstance() {
        if (s_instance == null) {
            s_instance = new MCOrgManager();
        }
        return s_instance;
    }

    /**
     * 获取机构列表信息
     *
     * @param callback
     */
    public void fetchOrganizationList(String userId, final Callback<MCOrgListResult> callback) {
        orgService.fetchOrganizationList(userId, new Callback<MCOrgListResult>() {
            @Override
            public void success(MCOrgListResult result, Response response) {
                if (callback != null) {
                    callback.success(result, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });

    }

    /**
     * 获取报名列表
     *
     * @param callback
     */
    public void fetchEnrollList(String userPhone, final Callback<MCEnrollListResult> callback) {
        orgService.fetchEnrollList(userPhone, new Callback<MCEnrollListResult>() {
            @Override
            public void success(MCEnrollListResult result, Response response) {
                if (callback != null) {
                    callback.success(result, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });
    }

    /**
     * 获取报名列表
     *
     * @param callback
     */
    public void cancelEnroll(String enrollId, final Callback<MCCommonResult> callback) {
        orgService.removeEnroll(enrollId, new Callback<MCCommonResult>() {
            @Override
            public void success(MCCommonResult result, Response response) {
                if (callback != null) {
                    callback.success(result, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });
    }

    /**
     * 获取报名状态
     *
     * @param callback
     */
    public void getEnrollInfo(String phone, String orgId, final Callback<MCCheckInfoResult> callback) {
        orgService.getEnrollInfo(phone, orgId, new Callback<MCCheckInfoResult>() {
            @Override
            public void success(MCCheckInfoResult result, Response response) {
                if (callback != null) {
                    callback.success(result, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });
    }

    /**
     * 更新报名人数
     *
     * @param callback
     */
    public void updateEnrollCount(String orgId, String type, final Callback<MCCommonResult> callback) {
        orgService.updateEnrollCount(orgId, type, new Callback<MCCommonResult>() {
            @Override
            public void success(MCCommonResult result, Response response) {
                if (callback != null) {
                    callback.success(result, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });
    }

    /**
     * 更新关注人数
     *
     * @param callback
     */
    public void updateAttendCount(String orgId, final Callback<MCCommonResult> callback) {
        orgService.updateAttendCount(orgId, new Callback<MCCommonResult>() {
            @Override
            public void success(MCCommonResult result, Response response) {
                if (callback != null) {
                    callback.success(result, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });

    }

    /**
     * 获取机构教师列表
     *
     * @param callback
     */
    public void fetchTeacherList(String orgId, final Callback<MCTeacherResult> callback) {
        orgService.fetchTeacherList(orgId, new Callback<MCTeacherResult>() {
            @Override
            public void success(MCTeacherResult result, Response response) {
                if (callback != null) {
                    callback.success(result, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });

    }

    /**
     * 获取机构教师列表
     *
     * @param callback
     */
    public void enroll(String orgId, String userPhone, String userLocal, String type, String checkinTime, final Callback<MCCommonResult> callback) {
        orgService.enroll(orgId, userPhone, userLocal, type, checkinTime, new Callback<MCCommonResult>() {
            @Override
            public void success(MCCommonResult result, Response response) {
                if (callback != null) {
                    callback.success(result, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });

    }

    /**
     * 获取整套视频或题库列表
     *
     * @param type 1 视频 2 题库
     * @param cb
     */
    public void getGoodsList(String type, Callback<SystemCourseResult> cb) {
        orgService.getGoodsList(type, cb);
    }
}
