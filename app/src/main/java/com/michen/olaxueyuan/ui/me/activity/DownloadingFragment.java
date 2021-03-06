package com.michen.olaxueyuan.ui.me.activity;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.database.CourseDB;
import com.michen.olaxueyuan.ui.me.adapter.DownloadingAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadingFragment extends BaseFragment {

    private View view;

    private ListView downloadingLV;
    private List<CourseDB> courseList = new ArrayList<CourseDB>();
    private List<Boolean> boolList = new ArrayList<Boolean>();

    private boolean isChooseAll = false; //标记是否已全选
    private boolean isDownloading = false;  //是否存在正在下载的视频
    private int downloadingID; //正在下载的视频ID
    private int downloadingProgress;//正在下载的视频的下载进度

    public DownloadingAdapter adapter;
    private DownloadingAdapter.ViewHolder viewHolder;

    private HttpHandler handler;
    private DbUtils db;

    public DownloadingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_downloading, container, false);
        db = DbUtils.create(getActivity());
        downloadingLV = (ListView) view.findViewById(R.id.downloadingLV);
        initDownloadingData();
        downloadingLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                viewHolder = (DownloadingAdapter.ViewHolder) view.getTag();
                viewHolder.tv_progress.setTag(courseList.get(position).getId());
                if (DownloadingAdapter.CHECKBOS_VISIBLE) {
                    viewHolder.cb.toggle();
                    if (viewHolder.cb.isChecked()) {
                        boolList.set(position, true);
                    } else {
                        boolList.set(position, false);
                    }
                } else {
                    if (!isDownloading) {
                        doDownload(courseList.get(position).getId(), courseList.get(position).getVideo(), DownloadActivity.SDPATH + "/snailvideo" + courseList.get(position).getId() + ".mp4");
                    } else {
                        // 点击的正在下载的视频
                        if (courseList.get(position).getId() == downloadingID) {
                            doStopDownload(courseList.get(position).getId());
                            // 遍历剩下的列表，检查是否有等待下载的
                            try {
                                CourseDB course = db.findFirst(Selector.from(CourseDB.class)
                                        .where("isInQueue", "=", 1).and(WhereBuilder.b("id", "<>", courseList.get(position).getId())));
                                if (course != null) {
                                    doDownload(course.getId(), course.getVideo(), "/sdcard/snailvideo" + course.getId() + ".mp4");
                                }

                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // 点击的未在下载的视频
                            try {
                                CourseDB c = db.findById(CourseDB.class, courseList.get(position).getId());
                                if (c == null)
                                    return;
                                if (c.getIsInQueue() == 0) {
                                    // 添加到下载队列
                                    addToQueue(courseList.get(position).getId());
                                } else {
                                    // 从下载队列取消
                                    removeFromQueue(courseList.get(position).getId());
                                }
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
        return view;
    }

    private void initDownloadingData() {
        DbUtils db = DbUtils.create(getActivity());
        try {
            if (db.findAll(CourseDB.class) != null) {
                courseList = db.findAll(Selector.from(CourseDB.class)
                        .where("isdone", "<>", 1)
                        .orderBy("id"));
                // checkbox 初始为未选中状态
                for (int i = 0; i < courseList.size(); i++) {
                    boolList.add(false);
                }
                adapter = new DownloadingAdapter(getActivity(), courseList, boolList);
                downloadingLV.setAdapter(adapter);
            }

        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    public void doDownload(final int id, String sourceUrl, String targetUrl) {

        final TextView tempTV = (TextView) view.findViewWithTag(id);

        // 1.创建下载器
        HttpUtils http = new HttpUtils();
        // 2.最大开启线程数量
        http.configRequestThreadPoolSize(1);
        handler = http.download(sourceUrl, targetUrl,
                true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
                true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                new RequestCallBack<File>() {

                    @Override
                    public void onStart() {
                        Toast.makeText(getActivity(), "开始下载", Toast.LENGTH_SHORT).show();
                        if (tempTV.getText().toString().equals("暂停"))
                            tempTV.setText("0%");
                        isDownloading = true;
                        downloadingID = id;
                        try {
                            CourseDB c = db.findById(CourseDB.class, id);
                            if (c != null) {
                                c.setIsdone(2);
                                db.update(c, "isdone");
                            }
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        tempTV.setText(current + "/" + total);
                        downloadingProgress = (int) (current * 100 / total);
                        if (!isDownloading) {
                            isDownloading = true;
                            downloadingID = id;
                            try {
                                CourseDB c = db.findById(CourseDB.class, id);
                                if (c != null) {
                                    c.setIsdone(2);
                                    db.update(c, "isdone");
                                }
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        tempTV.setText("已完成");
                        // 更新为已下载
                        try {
                            CourseDB c = db.findById(CourseDB.class, id);
                            if (c != null) {
                                c.setIsdone(1);
                                c.setProgress(100);
                                db.update(c, "isdone", "progress");
                            }
                            isDownloading = false;
                            downloadingID = 0;

                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        // 遍历剩下的列表，检查是否有等待下载的
                        try {
                            CourseDB course = db.findFirst(Selector.from(CourseDB.class)
                                    .where("isInQueue", "=", 1).and(WhereBuilder.b("id", "<>", id)));
                            if (course != null) {
                                doDownload(course.getId(), course.getVideo(), DownloadActivity.SDPATH + "/snailvideo" + course.getId() + ".mp4");
                            }

                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        isDownloading = false;
                        downloadingID = 0;
                        try {
                            CourseDB c = db.findById(CourseDB.class, id);
                            if (c != null) {
                                c.setIsdone(0);
                                db.update(c, "isdone");
                            }
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void addToQueue(int id) {
        final TextView tempTV = (TextView) view.findViewWithTag(id);
        try {
            CourseDB c = db.findById(CourseDB.class, id);
            if (c != null) {
                c.setIsInQueue(1);
                db.update(c, "isInQueue");
            }
            tempTV.setText("等待中");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void removeFromQueue(int id) {
        final TextView tempTV = (TextView) view.findViewWithTag(id);
        try {
            CourseDB c = db.findById(CourseDB.class, id);
            if (c != null) {
                c.setIsInQueue(0);
                db.update(c, "isInQueue");
            }
            tempTV.setText("取消下载");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void doStopDownload(int id) {
        //调用cancel()方法停止下载
        handler.cancel();
        isDownloading = false;
        final TextView tempTV = (TextView) view.findViewWithTag(id);
        try {
            CourseDB c = db.findById(CourseDB.class, id);
            if (c != null) {
                c.setIsInQueue(0);
                c.setProgress(downloadingProgress);
                c.setIsdone(0);
                db.update(c, "isInQueue", "progress", "isdone");
            }
            tempTV.setText("暂停");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void chooseOrDeChoose() {
        if (isChooseAll) {
            for (int i = 0; i < courseList.size(); i++) {
                boolList.set(i, false);
            }
            isChooseAll = false;
        } else {
            for (int i = 0; i < courseList.size(); i++) {
                boolList.set(i, true);
            }
            isChooseAll = true;
        }
        adapter.notifyDataSetChanged();
    }

    public void deleteCourse() {
        boolean existDownloadingCourse = false;
        for (int i = 0; i < courseList.size(); i++) {
            if (boolList.get(i)) {
                if (courseList.get(i).getIsdone() == 2) {
                    doStopDownload(courseList.get(i).getId());
                    existDownloadingCourse = true;
                }
                File file = new File(DownloadActivity.SDPATH + "/snailvideo" + courseList.get(i).getId() + ".mp4");
                if (file.exists()) {
                    file.delete();
                }
                try {
                    db.deleteById(CourseDB.class, courseList.get(i).getId());
                    courseList.remove(courseList.get(i));
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }
        if (existDownloadingCourse)
            try {
                CourseDB course = db.findFirst(Selector.from(CourseDB.class)
                        .where("isInQueue", "=", 1));
                if (course != null) {
                    doDownload(course.getId(), course.getVideo(), "/sdcard/snailvideo" + course.getId() + ".mp4");
                }

            } catch (DbException e) {
                e.printStackTrace();
            }
        adapter.notifyDataSetChanged();
    }
}
