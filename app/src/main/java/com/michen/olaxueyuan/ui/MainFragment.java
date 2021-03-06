package com.michen.olaxueyuan.ui;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.SETabBar;
import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.ui.home.data.ChangeIndexEvent;

import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */


public class MainFragment extends Fragment {
    private MainPagerAdapter _viewPagerAdapter;
    private SETabBar _tabBar;
    private static ViewPager _viewPager;

    private static Activity mActivity;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setupActionBar();

        register();

        View fragmentView = inflater.inflate(R.layout.fragment_main, container, false);
        EventBus.getDefault().register(this);

        _viewPager = (ViewPager) fragmentView.findViewById(R.id.MainPager);
        _viewPager.setOffscreenPageLimit(999);
        _viewPagerAdapter = new MainPagerAdapter(getFragmentManager());
//        _viewPagerAdapter = new MainPagerAdapter(getChildFragmentManager());
        _viewPager.setAdapter(_viewPagerAdapter);
        _viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int index) {
                handlePageSelected(index);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        _tabBar = (SETabBar) fragmentView.findViewById(R.id.TabBar);

        _tabBar.getItemViewAt(0).setNormalIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_point_selected));
        _tabBar.getItemViewAt(1).setNormalIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_course_normal));
        _tabBar.getItemViewAt(2).setNormalIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_home_normal));
        _tabBar.getItemViewAt(3).setNormalIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_circle_normal));
        _tabBar.getItemViewAt(4).setNormalIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_user_normal));

        _tabBar.getItemViewAt(0).setTitleText("考点");
        _tabBar.getItemViewAt(1).setTitleText("题库");
        _tabBar.getItemViewAt(2).setTitleText("主页");
        _tabBar.getItemViewAt(3).setTitleText("欧拉圈");
        _tabBar.getItemViewAt(4).setTitleText("我的");

        _tabBar.setOnTabSelectionEventListener(new SETabBar.OnTabSelectionEventListener() {
            @Override
            public boolean onWillSelectTab(int tabIndex) {
                return handleOnWillSelectTab(tabIndex);
            }

            @Override
            public void onDidSelectTab(int tabIndex) {
                handleOnDidSelectTab(tabIndex);
            }
        });

        _tabBar.limitTabNum(5);
        switchToPage(2);

        return fragmentView;
    }

    private void register() {
        IntentFilter filter1 = new IntentFilter("com.swiftacademy.screen.changed");
        getActivity().registerReceiver(screenReceirver, filter1);

        IntentFilter filter = new IntentFilter("com.swiftacademy.tab.change");
        getActivity().registerReceiver(changeTabReceiver, filter);
    }

    private void unregister() {
        getActivity().unregisterReceiver(changeTabReceiver);
        getActivity().unregisterReceiver(screenReceirver);
    }

    private BroadcastReceiver changeTabReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switchToPage(2);
        }
    };

    // 监听视频横竖屏
    private BroadcastReceiver screenReceirver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean enterFullScreen = intent.getBooleanExtra("enterFullScreen", false);
            if (enterFullScreen) {
                _tabBar.setVisibility(View.GONE);
            } else {
                _tabBar.setVisibility(View.VISIBLE);
            }
        }
    };

    private void setupActionBar() {
        ActionBar actionBar = mActivity.getActionBar();
        try {
            actionBar.getClass().getDeclaredMethod("setShowHideAnimationEnabled", boolean.class).invoke(actionBar, false);
        } catch (Exception exception) {
            // Too bad, the animation will be run ;(
        }
    }

    private void handlePageSelected(int index) {
        _tabBar.setSelectedTabIndex(index);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private boolean handleOnWillSelectTab(int tabIndex) {

        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregister();
        EventBus.getDefault().unregister(this);
    }

    private void handleOnDidSelectTab(int tabIndex) {
        switchToPage(tabIndex);
    }

    public void switchToPage(int tabIndex) {
        switch (tabIndex) {
            case 0:
                setActionBarVisible(false);
                _viewPager.setCurrentItem(tabIndex, false);
                ((MainActivity) mActivity).setTitleText("");
                break;
            case 1:
                setActionBarVisible(false);
                _viewPager.setCurrentItem(tabIndex, false);
                ((MainActivity) mActivity).setTitleText("数学");
                break;
            case 2:
                setActionBarVisible(false);
                _viewPager.setCurrentItem(tabIndex, false);
                ((MainActivity) mActivity).setTitleText("数学");
                break;
            case 3:
                setActionBarVisible(false);
                _viewPager.setCurrentItem(tabIndex, false);
                ((MainActivity) mActivity).setTitleText("欧拉圈");
                break;
            case 4:
                setActionBarVisible(false);
                _viewPager.setCurrentItem(tabIndex, false);
                ((MainActivity) mActivity).setTitleText("");
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
//                MWTAuthManager am = MWTAuthManager.getInstance();
//                if (am.isAuthenticated())
//                {
//                    _tabBar.setSelectedTabIndex(4);
//                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void setActionBarVisible(boolean actionBarVisible) {
        ActionBar actionBar = mActivity.getActionBar();
        try {
            actionBar.getClass().getDeclaredMethod("setShowHideAnimationEnabled", boolean.class).invoke(actionBar, false);
        } catch (Exception exception) {
            // Too bad, the animation will be run ;(
        }

        if (actionBarVisible) {
            actionBar.show();
        } else {
            actionBar.hide();
        }
    }

    /**
     * {@link com.michen.olaxueyuan.ui.home.HomeFragment#chageIndex(int)}
     */
    public void onEventMainThread(ChangeIndexEvent changeIndexEvent) {
        if (changeIndexEvent.isChange) {
            _viewPager.setCurrentItem(changeIndexEvent.position, false);
        }
    }

    // EventBus 回调
    public void onEventMainThread(UserLoginNoticeModule module) {
//        _viewPagerAdapter.upDateMainFragment();
        /*if (module.isLogin) {
            Logger.e(""+ SEAuthManager.getInstance().getAccessUser().getIsActive());
//            _viewPagerAdapter.notifyDataSetChanged();
            _viewPagerAdapter.upDateMainFragment();
        } else {
            _viewPagerAdapter.upDateMainFragment();
        }*/
    }

}

