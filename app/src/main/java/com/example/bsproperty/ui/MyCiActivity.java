package com.example.bsproperty.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bsproperty.MyApplication;
import com.example.bsproperty.R;
import com.example.bsproperty.adapter.BaseAdapter;
import com.example.bsproperty.bean.CiUserBean;
import com.example.bsproperty.bean.CiUserListBean;
import com.example.bsproperty.net.ApiManager;
import com.example.bsproperty.net.BaseCallBack;
import com.example.bsproperty.net.OkHttpTools;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCiActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.sl_list)
    SwipeRefreshLayout slList;
    private ArrayList<CiUserBean> mData = new ArrayList<>();
    private MyCiActivity.MyAdapter adapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        rvList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyCiActivity.MyAdapter(mContext, R.layout.item_myci, mData);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Object item, int position) {
                Intent intent = new Intent(MyCiActivity.this, CiInfoActivity.class);
                intent.putExtra("data", mData.get(position));
                intent.putExtra("ismy", true);
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
        return R.layout.activity_my_ci;
    }

    @Override
    protected void loadData() {
        OkHttpTools.sendGet(mContext, ApiManager.CI_USER_LIKE)
                .addParams("uid", MyApplication.getInstance().getUserBean().getId() + "")
                .build()
                .execute(new BaseCallBack<CiUserListBean>(mContext, CiUserListBean.class) {
                    @Override
                    public void onResponse(CiUserListBean ciUserListBean) {
                        mData = ciUserListBean.getData();
                        adapter.notifyDataSetChanged(mData);
                    }
                });
    }

    private class MyAdapter extends BaseAdapter<CiUserBean> {

        public MyAdapter(Context context, int layoutId, ArrayList<CiUserBean> data) {
            super(context, layoutId, data);
        }

        @Override
        public void initItemView(BaseViewHolder holder, CiUserBean ciUserBean, int position) {
            holder.setText(R.id.tv01, ciUserBean.getName());
        }
    }
}
