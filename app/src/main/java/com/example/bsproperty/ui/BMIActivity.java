package com.example.bsproperty.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bsproperty.R;
import com.example.bsproperty.bean.UserBean;
import com.example.bsproperty.utils.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BMIActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.et_h)
    EditText etH;
    @BindView(R.id.et_w)
    EditText etW;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_end)
    TextView tvEnd;
    @BindView(R.id.ll_end)
    LinearLayout llEnd;

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitle.setText("BMI测试");
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText("计算");
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_bmi;
    }

    @Override
    protected void loadData() {
        UserBean bean = SpUtils.getUserBean(mContext);
        if (bean != null) {
            showEnd(bean.getDh(), bean.getDw(), bean.getEnd());
            etH.setText(bean.getDh() + "");
            etW.setText(bean.getDw() + "");
        }
    }

    @OnClick({R.id.btn_back, R.id.btn_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_right:
                String hh = etH.getText().toString().trim();
                String ww = etW.getText().toString().trim();
                if (TextUtils.isEmpty(hh) || TextUtils.isEmpty(ww)) {
                    showToast("请输入完整数据");
                    return;
                }
                try {
                    double dh = Double.parseDouble(hh);
                    double dw = Double.parseDouble(ww);
                    double ok = (dh - 80) * 0.7;

                    double ok1 = (dh - 80) * 0.6;
                    double ok2 = (dh - 80) * 0.8;

                    double e1 = (dh - 80) * 0.5;
                    double e2 = (dh - 80) * 0.9;

                    String end = "";
                    if (dw < e1) {
                        end = "体重不足";
                    } else if (dw > e2) {
                        end = "肥胖";
                    } else if (e1 <= dw && dw <= ok) {
                        end = "过轻";
                        if (ok1 <= dw && dw <= ok) {
                            end = "正常";
                        }
                    } else if (ok <= dw && dw <= e2) {
                        end = "过重";
                        if (ok <= dw && dw <= ok2) {
                            end = "正常";
                        }
                    }

                    showEnd(dh, dw, end);

                } catch (Exception e) {
                    showToast("数据格式不正确");
                }
                break;
        }
    }

    private void showEnd(double dh, double dw, String end) {
        UserBean bean = new UserBean();
        bean.setDh(dh);
        bean.setDw(dw);
        bean.setEnd(end);
        SpUtils.setUserBean(mContext, bean);
        llEnd.setVisibility(View.VISIBLE);
        tvResult.setText(end);
        String help = "";
        switch (end) {
            case "体重不足":
                help = "体重过低说明身体的营养不良，可以影响未成年人身体和智力的正常发育;" +
                        "成年人体重过低可出现劳动能力下降、骨量丢失和骨折、" +
                        "胃肠功能紊乱、免疫力低下、女性月经不调和闭经、贫血和抑郁症等。";
                break;
            case "肥胖":
                help = "(骨骼畸形)体重增加，压迫下肢骨骼发育畸形，造成脊柱和椎间软骨损害等症状。\n" +
                        "(影响智力)容易形成“脂肪脑”，致使智力落后于同龄人，影响学习效率，使成绩下降。\n" +
                        "(生殖发育)肥胖会导致性发育提前，男孩易出现隐睾、缩阳等，女孩易出现卵巢、子宫发育不良等;骨骺提前愈合，成年身高远远低于预测身高。\n" +
                        "(成年疾病)“三高”，免疫系统受到抑制，抗病能力较差，如糖尿病、脂肪肝、冠心病等;女孩还易患卵巢囊肿。这些并发症会导致死亡，是肥胖者猝死率越来越高的直接原因。";
                break;
            case "过轻":
                help = "体重过低说明身体的营养不良，可以影响未成年人身体和智力的正常发育;" +
                        "成年人体重过低可出现劳动能力下降、骨量丢失和骨折、" +
                        "胃肠功能紊乱、免疫力低下、女性月经不调和闭经、贫血和抑郁症等。";
                break;
            case "过重":
                help = "(骨骼畸形)体重增加，压迫下肢骨骼发育畸形，造成脊柱和椎间软骨损害等症状。\n" +
                        "(影响智力)容易形成“脂肪脑”，致使智力落后于同龄人，影响学习效率，使成绩下降。\n" +
                        "(生殖发育)肥胖会导致性发育提前，男孩易出现隐睾、缩阳等，女孩易出现卵巢、子宫发育不良等;骨骺提前愈合，成年身高远远低于预测身高。\n" +
                        "(成年疾病)“三高”，免疫系统受到抑制，抗病能力较差，如糖尿病、脂肪肝、冠心病等;女孩还易患卵巢囊肿。这些并发症会导致死亡，是肥胖者猝死率越来越高的直接原因。";
                break;
            case "正常":
                help = "希望继续保持";
                break;
        }
        tvEnd.setText(help);
    }
}
