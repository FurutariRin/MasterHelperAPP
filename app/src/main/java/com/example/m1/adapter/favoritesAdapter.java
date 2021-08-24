package com.example.m1.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cqupt.master_helper.R;
import com.cqupt.master_helper.biz.video.VideoCollectBiz;
import com.example.m1.adapter.adapterUtilities.adapterUtilities;
import com.example.m1.bean.recycleViewData;

import java.util.ArrayList;

public class favoritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //声明

    private ItemClickListener itemClickListener;

    private Context context;

    private ArrayList<recycleViewData> recycleViewData;

    private final int normalType = 1;
    private final int footerType = 0;

    private boolean hasMore = true;
    public boolean fadeTips = false;

    public favoritesAdapter(ArrayList<recycleViewData> DataSet, String uid, Context context) {
        this.recycleViewData = DataSet;
        this.uid = uid;
        this.c = context;
    }

    private Context c;
    private String uid;
    private int retCode;

    //--------------------------------------------------------------------------------------------------
    //视图创建
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        if (viewType == normalType)
            return new normalHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorites, parent, false));
        else
            return new footerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.video_footer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof normalHolder) {
            showNormalHolder(holder, position);
        } else {
            showFooterHolder(holder, footerType);
        }

    }

    //----------------------------------------------------------------------------------------------
    //normal holder操作
    private void showNormalHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((normalHolder) holder).videoName.setText(recycleViewData.get(position).videoName);//视频名
        ((normalHolder) holder).uploaderName.setText((recycleViewData.get(position).uploaderName));//上传者名字
        ((normalHolder) holder).videoDesc.setText(recycleViewData.get(position).videoDesc);//简介
        adapterUtilities.genCover(((normalHolder) holder).cover, recycleViewData.get(position).videoUri, ((normalHolder) holder).cover.getContext());//封面
        //为Spinner设置itemSelectListener
        ((normalHolder) holder).moreAction.setSelection(0, true);//spinner初始化
        ((normalHolder) holder).moreAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        retCode = new VideoCollectBiz().collect(uid, recycleViewData.get(holder.getBindingAdapterPosition()).vid, VideoCollectBiz.EVENT_DISCOLLECT);
                    }
                };
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (retCode == 0) {
                    recycleViewData.remove(holder.getBindingAdapterPosition());
                    notifyDataSetChanged();
                    Toast.makeText(c, "删除收藏成功", Toast.LENGTH_SHORT).show();
                } else if (retCode == 1) {
                    Toast.makeText(c, "删除收藏失败", Toast.LENGTH_SHORT).show();
                } else if (retCode == 2) {
                    Toast.makeText(c, "事件错误", Toast.LENGTH_SHORT).show();
                } else if (retCode == 3) {
                    Toast.makeText(c, "连接错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //为normalItem生成监听
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(v -> itemClickListener.onItemClick(holder.getBindingAdapterPosition()));
        }
    }

    //----------------------------------------------------------------------------------------------
    //footer holder操作
    private void showFooterHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((footerHolder) holder).tips.setVisibility(View.VISIBLE);
        if (hasMore) {
            fadeTips = false;
            if (recycleViewData.size() > 0) {
                ((footerHolder) holder).tips.setText("正在加载更多...");
            } else {
                // 如果查询数据发现并没有增加时，就显示没有更多数据了
                ((footerHolder) holder).tips.setText("没有更多数据了");

                // 然后通过延时加载模拟网络请求的时间，在500ms后执行
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 隐藏提示条
                        ((footerHolder) holder).tips.setVisibility(View.GONE);
                        // 将fadeTips设置true
                        fadeTips = true;
                        // hasMore设为true是为了让再次拉到底时，会先显示正在加载更多
                        hasMore = true;
                    }
                }, 500);
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    //normalType Holder
    static class normalHolder extends RecyclerView.ViewHolder {

        private final ImageView cover;
        private final TextView videoName;
        private final TextView uploaderName;
        private final TextView videoDesc;
        private final Spinner moreAction;

        public normalHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.image_favoriteCover);
            videoName = itemView.findViewById(R.id.text_favoriteVideoName);
            uploaderName = itemView.findViewById(R.id.text_favoriteUploaderName);
            videoDesc = itemView.findViewById(R.id.text_favoriteVideoDesc);
            moreAction = itemView.findViewById(R.id.btn_favoriteItemMoreAction);
        }
    }

    //footerType Holder
    static class footerHolder extends RecyclerView.ViewHolder {
        private final TextView tips;

        public footerHolder(View itemView) {
            super(itemView);
            tips = itemView.findViewById(R.id.tips);
        }
    }

    //----------------------------------------------------------------------------------------------
    //复写接口ItemClickListener
    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(favoritesAdapter.ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }


    //----------------------------------------------------------------------------------------------
    //获取item数量
    @Override
    public int getItemCount() {
        return recycleViewData.size() + 1;
    }

    //获取除footer外的最后一个item位置
    public int getRealLastPosition() {
        return recycleViewData.size();
    }

    //获取item类型
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footerType;
        } else {
            return normalType;
        }
    }

    //----------------------------------------------------------------------------------------------
    // 暴露接口，改变fadeTips的方法
    public boolean isFadeTips() {
        return fadeTips;
    }

    // 暴露接口，下拉刷新时，通过暴露方法将数据源置为空
    public void resetDatas() {
        recycleViewData = new ArrayList<>();
    }

    // 暴露接口，更新数据源，并修改hasMore的值，如果有增加数据，hasMore为true，否则为false
    public void updateList(ArrayList<recycleViewData> newDatas, boolean hasMore) {
        // 在原有的数据之上增加新数据
        if (newDatas != null) {
            recycleViewData.addAll(newDatas);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }
}




