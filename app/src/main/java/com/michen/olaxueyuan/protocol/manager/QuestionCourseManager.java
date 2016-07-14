package com.michen.olaxueyuan.protocol.manager;

import com.michen.olaxueyuan.protocol.result.CommentModule;
import com.michen.olaxueyuan.protocol.result.CommentSucessResult;
import com.michen.olaxueyuan.protocol.result.ExamModule;
import com.michen.olaxueyuan.protocol.result.MCQuestionListResult;
import com.michen.olaxueyuan.protocol.result.OLaCircleModule;
import com.michen.olaxueyuan.protocol.result.OLaCircleOldModule;
import com.michen.olaxueyuan.protocol.result.QuestionCourseModule;
import com.michen.olaxueyuan.protocol.service.QuestionService;

import retrofit.Callback;

/**
 * Created by mingge on 16/4/28.
 */
public class QuestionCourseManager {
    private static QuestionCourseManager q_instance;
    private QuestionService questionService;

    private QuestionCourseManager() {
        questionService = SERestManager.getInstance().create(QuestionService.class);
    }

    public static QuestionCourseManager getInstance() {
        if (q_instance == null) {
            q_instance = new QuestionCourseManager();
        }
        return q_instance;
    }

    public QuestionService getQuestionService() {
        return questionService;
    }

    /**
     * 首页课程列表
     *
     * @param callback
     */
    public void fetchHomeCourseList(String userid, String pid, String type, final Callback<QuestionCourseModule> callback) {
        getQuestionService().fetchHomeCourseList(userid, pid, type, callback);
    }

    /**
     * 题库首页
     *
     * @param courseId
     * @param type
     * @param callback
     */
    public void getExamList(String userId, String courseId, String type, final Callback<ExamModule> callback) {
        getQuestionService().getExamList(userId, courseId, type, callback);
    }

    /**
     * 获取题目列表
     *
     * @param callback
     */
    public void fetchExamQuestionList(String examId, final Callback<MCQuestionListResult> callback) {
        getQuestionService().getExamQuestionList(examId, callback);
    }

    /**
     * 欧拉圈，获取视频观看历史记录列表(old)
     * <p>
     * {@link #getCircleList(String, String, Callback)} ())}
     *
     * @param callback
     */
    @Deprecated
    public void getHistotyList(String videoId, String pageSize, final Callback<OLaCircleOldModule> callback) {
        getQuestionService().getHistotyList(videoId, pageSize, callback);
    }

    /**
     * 欧拉圈，获取视频观看历史记录列表
     *
     * @param circleId
     * @param pageSize
     * @param callback
     */
    public void getCircleList(String circleId, String pageSize, final Callback<OLaCircleModule> callback) {
        getQuestionService().getCircleList(circleId, pageSize, callback);
    }

    /**
     * 评论列表
     *
     * @param postId   couserId或circle中的帖子Id
     * @param type     1 postId为课程 2 postId 为帖子
     * @param callback
     */
    public void getCommentList(String postId, String type, final Callback<CommentModule> callback) {
        getQuestionService().getCommentList(postId, type, callback);
    }

    /**
     * 发表评论
     *
     * @param userId
     * @param postId   课程Id 或 帖子Id
     * @param toUserId
     * @param content
     * @param location
     * @param type     1 课程评论 2 帖子评论
     * @param callback
     */
    public void addComment(String userId, String postId, String toUserId, String content, String location, String type, final Callback<CommentSucessResult> callback) {
        getQuestionService().addComment(userId, postId, toUserId, content, location, type, callback);
    }

}
