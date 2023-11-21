package com.example.lib_common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SpinnerView extends FrameLayout {

    private boolean isOpen = false;

    private View topView;

    private @LayoutRes
    int openItemView = 0;

    private List<Object> data;

    private View showBgView;

    private CommonCallMore<View, Object> itemInitCall;

    private CommonCallMore<View, Object> itemClickCall;

    private CommonCall<View> onDismissCall;

    private CommonCall<View> onOpenCall;

    private PopupWindow popupWindow;

    private boolean backgroundIsDarken;

    private Window window;

    private boolean widthIdEqualTopView = false;

    private SpinnerAdapter spinnerAdapter;

    public SpinnerView(@NonNull Context context) {
        super(context);
    }

    public SpinnerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SpinnerAdapter getSpinnerAdapter() {
        return spinnerAdapter;
    }

    public SpinnerView setWidthIdEqualTopView(boolean widthIdEqualTopView) {
        this.widthIdEqualTopView = widthIdEqualTopView;
        return this;
    }

    public SpinnerView setOnOpenCall(CommonCall<View> onOpenCall) {
        this.onOpenCall = onOpenCall;
        return this;
    }

    public SpinnerView setWindow(Window window) {
        this.window = window;
        return this;
    }

    public SpinnerView setBackgroundIsDarken(boolean backgroundIsDarken) {
        this.backgroundIsDarken = backgroundIsDarken;
        return this;
    }

    public SpinnerView setItemInitCall(CommonCallMore<View, Object> itemInitCall) {
        this.itemInitCall = itemInitCall;
        return this;
    }

    public SpinnerView setItemClickCall(CommonCallMore<View, Object> itemClickCall) {
        this.itemClickCall = itemClickCall;
        return this;
    }

    public SpinnerView setOnDismissCall(CommonCall<View> onDismissCall) {
        this.onDismissCall = onDismissCall;
        return this;
    }

    public SpinnerView setData(List<Object> data) {
        this.data = data;
        return this;
    }

    public View setShowBgView(View showBgView) {
        this.showBgView = showBgView;
        return this;
    }

    public SpinnerView setTopView(View topView) {
        this.topView = topView;
        return this;
    }

    public SpinnerView setOpenItemView(int openItemView) {
        this.openItemView = openItemView;
        return this;
    }

    public void build() {
        if (topView == null || openItemView == 0) {
            Toast.makeText(getContext(), "设置有误！", Toast.LENGTH_SHORT).show();
            return;
        }
        initView();
    }

    private void initView() {
        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        spinnerAdapter = new SpinnerAdapter(data);
        recyclerView.setAdapter(spinnerAdapter);
        addView(topView);

        View openView = showBgView == null ? recyclerView : showBgView;
        if (openView instanceof RecyclerView) {
            popupWindow = new PopupWindow(openView, widthIdEqualTopView ? getLayoutParams().width : GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT);
        }
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        initListener();
    }

    private void initListener() {
        popupWindow.setOnDismissListener(() -> {
            dismiss();
            isOpen = false;
            if (onDismissCall != null) onDismissCall.call(topView);
        });

        topView.setOnClickListener(v -> {
            if (!isOpen) {
                showOpen();
            } else {
                dismiss();
            }
            isOpen = !isOpen;
        });
    }

    private void dismiss() {
        if (popupWindow != null) {
            if (backgroundIsDarken && window != null) {
                window.getAttributes().alpha = 1f;
                window.setAttributes(window.getAttributes());
            }
            popupWindow.dismiss();
        }
    }

    private void showOpen() {
        if (popupWindow != null) {
            popupWindow.showAsDropDown(topView, 0, 0, Gravity.BOTTOM);
        }

        if (backgroundIsDarken && window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.alpha = 0.5f;
            window.setAttributes(attributes);
        }

        if (onOpenCall != null) {
            onOpenCall.call(topView);
        }
    }

    @SuppressLint("RecyclerView")
    public final class SpinnerAdapter extends RecyclerView.Adapter<SpinnerAdapter.SpinnerViewHolder> {
        private final List<Object> list;

        public SpinnerAdapter(List<Object> data) {
            list = data != null ? data : new ArrayList<>();
        }

        @NonNull
        @Override
        public SpinnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SpinnerViewHolder(LayoutInflater.from(parent.getContext()).inflate(openItemView, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull SpinnerViewHolder holder, int position) {
            if (itemInitCall != null) {
                itemInitCall.call(holder.itemView, data.get(position));
            }

            if (itemClickCall != null) {
                holder.itemView.setOnClickListener(v -> {
                    itemClickCall.call(topView, data.get(position));
                    dismiss();
                });
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        private final class SpinnerViewHolder extends RecyclerView.ViewHolder {
            public SpinnerViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }

    public interface CommonCallMore<T, F> {
        void call(T t, F f);
    }

    public interface CommonCall<T> {
        void call(T t);
    }
}
