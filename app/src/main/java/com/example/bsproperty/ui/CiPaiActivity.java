package com.example.bsproperty.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.adapter.BaseAdapter;
import com.example.bsproperty.bean.CiPaiBean;
import com.example.bsproperty.bean.CiPaiListBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;

import java.util.ArrayList;


import butterknife.BindView;

public class CiPaiActivity extends BaseActivity {

    @BindView(R.id.sl_list)
    SwipeRefreshLayout slList;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private ArrayList<CiPaiBean> mData = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        rvList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(mContext, R.layout.item_cipai, mData);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Object item, int position) {
                Intent intent = new Intent(CiPaiActivity.this, CiPaiInfoActivity.class);
                intent.putExtra("data", mData.get(position));
                startActivity(intent);
            }
        });
        rvList.setAdapter(adapter);
        slList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                slList.setRefreshing(false);
            }
        });
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_cipai;
    }

    @Override
    protected void loadData() {
        OkHttpTools.sendGet(mContext, ApiManager.NAME_LIST)
                .build()
                .execute(new BaseCallBack<CiPaiListBean>(mContext, CiPaiListBean.class) {
                    @Override
                    public void onResponse(CiPaiListBean ciPaiListBean) {
                        mData = ciPaiListBean.getData();
                        adapter.notifyDataSetChanged(mData);
                    }
                });
    }

    private class MyAdapter extends BaseAdapter<CiPaiBean> {

        public MyAdapter(Context context, int layoutId, ArrayList<CiPaiBean> data) {
            super(context, layoutId, data);
        }

        @Override
        public void initItemView(BaseAdapter.BaseViewHolder holder, CiPaiBean ciPaiBean, int position) {
            holder.setText(R.id.tv01, ciPaiBean.getName());
        }
    }
}
