/*
 *  Copyright (c) 2016.  Project TestProject
 *  Source MainActivity
 *  Author 沈煜
 *  此源码及相关文档等附件由 沈煜 编写，作者保留所有权利
 *  使用必须注明出处。
 *  The code and documents is write by the author. All rights are reserved.
 *  Use must indicate the source.
 *
 */

package com.jiuzhou.porter.testproject;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private SimpleRecycleView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (SimpleRecycleView) findViewById(R.id.recycler1);
        List<AppBean> mList = getAllApk();
        AppBeanAdapter adapter = new AppBeanAdapter(this, mList);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));

        int right = (int) getResources().getDimension(R.dimen.px_positive_35);
        int bottom = (int) getResources().getDimension(R.dimen.px_positive_35);
        RecyclerView.ItemDecoration spacingInPixel = new SpaceItemDecoration(right, bottom);
        recyclerView.addItemDecoration(spacingInPixel);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemSelectedListener(new AppBeanAdapter.OnItemSelectedListener() {
            @Override
            public void OnItemSelected(View view, int position) {
                recyclerView.smoothHorizontalScrollToNext(position);
            }
        });
    }

    public List<AppBean> getAllApk() {
        List<AppBean> appBeanList = new ArrayList<>();
        AppBean bean;
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> list = packageManager.getInstalledPackages(0);
        for (PackageInfo p : list) {
            bean = new AppBean();
            bean.setAppIcon(p.applicationInfo.loadIcon(packageManager));
            bean.setAppName(packageManager.getApplicationLabel(p.applicationInfo).toString());
            bean.setAppPackageName(p.applicationInfo.packageName);
            bean.setApkPath(p.applicationInfo.sourceDir);
            File file = new File(p.applicationInfo.sourceDir);
            bean.setAppSize((int) file.length());
            int flags = p.applicationInfo.flags;
            //判断是否是属于系统的apk
            if ((flags & ApplicationInfo.FLAG_SYSTEM) != 0){
                bean.setSystem(true);
            }else {
                bean.setSd(true);
            }
            appBeanList.add(bean);
        }
        return appBeanList;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                Log.d(TAG, "遥控器左键按下");

                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                Log.d(TAG, "遥控器右键按下");
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                Log.d(TAG, "遥控器上键按下");
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                Log.d(TAG, "遥控器下键按下");
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
