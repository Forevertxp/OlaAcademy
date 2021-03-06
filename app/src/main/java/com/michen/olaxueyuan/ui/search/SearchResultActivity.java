package com.michen.olaxueyuan.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.michen.olaxueyuan.protocol.manager.SECourseManager;
import com.michen.olaxueyuan.protocol.model.MCVideo;
import com.michen.olaxueyuan.protocol.result.MCSearchResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.course.CourseDetailActivity;
import com.michen.olaxueyuan.R;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchResultActivity extends SEBaseActivity {

    private PullToRefreshListView searchListView;
    private SearchResultAdapter adapter;

    private String key;
    private ArrayList<MCVideo> videoArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        key = getIntent().getStringExtra("keyword");
        setTitleText(key);

        searchListView = (PullToRefreshListView) findViewById(R.id.searchListView);
        searchListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                performRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MCVideo videoInfo = videoArrayList.get(position-1);
                Intent intent = new Intent(SearchResultActivity.this,CourseDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("videoInfo",videoInfo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        performRefresh();
    }


    private void performRefresh() {
        SECourseManager courseManager = SECourseManager.getInstance();
        courseManager.searchVideoList(key, new Callback<MCSearchResult>() {
            @Override
            public void success(MCSearchResult result, Response response) {
                videoArrayList = result.videoArrayList;
                adapter = new SearchResultAdapter(SearchResultActivity.this,result.videoArrayList);
                searchListView.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

}
