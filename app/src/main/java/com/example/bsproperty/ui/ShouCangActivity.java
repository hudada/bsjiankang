package com.example.bsproperty.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.adapter.BaseAdapter;
import com.example.bsproperty.bean.CiBean;
import com.example.bsproperty.bean.CiListBean;
import com.example.bsproperty.bean.CiPaiBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShouCangActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.sl_list)
    SwipeRefreshLayout slList;
    private ArrayList<CiBean> mData = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        rvList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShouCangActivity.MyAdapter(mContext, R.layout.item_shoucang, mData);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Object item, int position) {
                Intent intent = new Intent(ShouCangActivity.this, CiInfoActivity.class);
                intent.putExtra("data", mData.get(position));
                intent.putExtra("islike", true);
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
        return R.layout.activity_shou_cang;
    }

    @Override
    protected void onResume() {
        super.onResume();
        OkHttpTools.sendPost(mContext, ApiManager.LIKE_LIST)
                .addParams("uid", MyApplication.getInstance().getUserBean().getId() + "")
                .build()
                .execute(new BaseCallBack<CiListBean>(mContext, CiListBean.class) {
                    @Override
                    public void onResponse(CiListBean ciListBean) {
                        mData = ciListBean.getData();
                        adapter.notifyDataSetChanged(mData);
                    }
                });
    }

    @Override
    protected void loadData() {

    }

    private class MyAdapter extends BaseAdapter<CiBean> {

        public MyAdapter(Context context, int layoutId, ArrayList<CiBean> data) {
            super(context, layoutId, data);
        }

        @Override
        public void initItemView(BaseViewHolder holder, CiBean ciBean, int position) {
            holder.setText(R.id.tv01, ciBean.getTname() + "." + ciBean.getName());
            holder.setText(R.id.tv02, TextUtils.isEmpty(ciBean.getPeople()) ? "佚名" : ciBean.getPeople());
        }
    }
}
