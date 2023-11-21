package com.example.networkpro.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.lib_common.Interfac.AbsTextWatcher;
import com.example.networkpro.R;

/**
 * Created by 王鑫哲 on 2021/7/29 上午 11:20
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class UpdateDataView extends FrameLayout {
    /**
     * 用来控制是TextView还是EditText  1:textview 2:EditText
     */
    private int select_control = 1;
    /**
     * 布局View
     */
    private View view;
    public OnControlClick controlClick;
    private TextView tv_hint_left_name;
    private EditText et_name_center;
    private TextView tv_name_center;
    private ConstraintLayout cl_group;

    public EditText getEt_name_center() {
        return et_name_center;
    }

    public void OnControlClick(OnControlClick onControlClick) {
        this.controlClick = onControlClick;
    }

    public UpdateDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.UpdateDataView);
        select_control = typedArray.getInteger(R.styleable.UpdateDataView_select_control, 1);
        typedArray.recycle();

        view = LayoutInflater.from(getContext()).inflate(R.layout.update_data_view_layout, this);
        tv_hint_left_name = view.findViewById(R.id.tv_hint_left_name);
        et_name_center = view.findViewById(R.id.et_name_center);
        tv_name_center = view.findViewById(R.id.tv_name_center);
        cl_group = view.findViewById(R.id.cl_group);
        ifShow();
        listener();
    }

    private void listener() {
        tv_name_center.setOnClickListener(v -> {
            if (controlClick != null && select_control == 1) {
                controlClick.onClick(tv_name_center.getText().toString().trim());
                return;
            }
        });
        //此处点击事件根据业务需要 点击当前整个条目设置监听
        if (select_control == 1) {
            cl_group.setOnClickListener(v -> {
                if (controlClick != null && tv_name_center != null) {
                    controlClick.onClick(tv_name_center.getText().toString().trim());
                }
            });
        }
        /**
         * 如果是EditText的话  监听文字内容变化 走接口
         */
        et_name_center.addTextChangedListener(new AbsTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                super.onTextChanged(charSequence, start, before, count);
                if (controlClick != null && select_control == 2) {
                    controlClick.onClick(charSequence.length() == 0 ? "false" : charSequence.toString().trim());
                }
            }
        });
    }

    /**
     * 设置左边提示文字
     *
     * @param s
     * @return
     */
    public UpdateDataView setLeftHintText(String s) {
        tv_hint_left_name.setText(s);
        return this;
    }

    /**
     * 设置中间内容文字
     *
     * @param s
     * @return
     */
    public UpdateDataView setCenterText(String s) {
        et_name_center.setText(s);
        tv_name_center.setText(s);
        return this;
    }

    /**
     * 判断中间部分是用TextView还是EditText
     */
    public void ifShow() {
        et_name_center.setVisibility(select_control == 1 ? GONE : VISIBLE);
        tv_name_center.setVisibility(select_control == 1 ? VISIBLE : GONE);
    }

    /**
     * 点击接口回调
     */
    public interface OnControlClick {
        void onClick(String s);
    }

    /**
     * 获取中间文字内容
     *
     * @return
     */
    public String getCenterText() {
        return select_control == 1 ? tv_name_center.getText().toString() : et_name_center.getText().toString().trim();
    }

    /**
     * 是否可点击
     *
     * @param isClick
     * @return
     */
    public UpdateDataView isClick(boolean isClick) {
        if (cl_group != null) {
            cl_group.setEnabled(isClick);
            cl_group.setClickable(isClick);
        }
        return this;
    }
}
