package com.example.m1.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cqupt.master_helper.R;
import com.cqupt.master_helper.dao.UserReportDao;
import com.cqupt.master_helper.dao.VideoDao;
import com.example.m1.bean.reportData;

import java.util.ArrayList;

public class reportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //声明

    private ItemClickListener itemClickListener;

    private Context context;

    private ArrayList<com.example.m1.bean.reportData> reportData;

    private final int normalType = 1;
    private final int footerType = 0;

    private boolean hasMore = true;
    public boolean fadeTips = false;

    public reportAdapter(ArrayList<reportData> DataSet) {
        this.reportData = DataSet;
    }

    private int retCode;

    //--------------------------------------------------------------------------------------------------
    //视图创建
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        if (viewType == normalType)
            return new reportAdapter.normalHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report, parent, false));
        else
            return new reportAdapter.footerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.video_footer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof reportAdapter.normalHolder) {
            showNormalHolder(holder, position);
        } else {
            showFooterHolder(holder, footerType);
        }

    }

    //----------------------------------------------------------------------------------------------
    //normal holder操作
    private void showNormalHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((reportAdapter.normalHolder) holder).videoName.setText(reportData.get(position).videoName);//视频名
        ((normalHolder) holder).Rid.setText(reportData.get(position).Rid + "");//
        ((normalHolder) holder).reportNum.setText(reportData.get(position).reportNum + "");
        ((normalHolder) holder).btn_reportDeleteVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        retCode = new VideoDao().reportVideoDeleteServer(reportData.get(holder.getBindingAdapterPosition()).Vid, reportData.get(holder.getBindingAdapterPosition()).Rid);
                    }
                };
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                reportData.remove(holder.getBindingAdapterPosition());
                notifyDataSetChanged();
            }
        });
        ((normalHolder) holder).btn_deleteReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        retCode = new UserReportDao().deleteReport(reportData.get(holder.getBindingAdapterPosition()).Rid);
                    }
                };
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                reportData.remove(holder.getBindingAdapterPosition());
                notifyDataSetChanged();
            }
        });
//        //为Spinner设置itemSelectListener
//        ((reportAdapter.normalHolder)holder).moreAction.setSelection(0,true);//spinner初始化
//        ((reportAdapter.normalHolder)holder).moreAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                reportData.remove(holder.getBindingAdapterPosition());
//                notifyDataSetChanged();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        //为normalItem生成监听
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(v -> itemClickListener.onItemClick(holder.getBindingAdapterPosition()));
        }
    }

    //----------------------------------------------------------------------------------------------
    //footer holder操作
    private void showFooterHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((reportAdapter.footerHolder) holder).tips.setVisibility(View.VISIBLE);
        if (hasMore) {
            fadeTips = false;
            if (reportData.size() > 0) {
                ((reportAdapter.footerHolder) holder).tips.setText("正在加载更多...");
            } else {
                // 如果查询数据发现并没有增加时，就显示没有更多数据了
                ((reportAdapter.footerHolder) holder).tips.setText("没有更多数据了");

                // 然后通过延时加载模拟网络请求的时间，在500ms后执行
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 隐藏提示条
                        ((reportAdapter.footerHolder) holder).tips.setVisibility(View.GONE);
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
        private final TextView videoName;
        private final TextView Rid;
        private final TextView reportNum;
        private final Button btn_reportDeleteVideo;
        private final Button btn_deleteReport;

        public normalHolder(@NonNull View itemView) {
            super(itemView);
            Rid = itemView.findViewById(R.id.reportId);
            reportNum = itemView.findViewById(R.id.reportNum);
            videoName = itemView.findViewById(R.id.videoName);
            btn_reportDeleteVideo = itemView.findViewById(R.id.btn_reportDeleteVideo);
            btn_deleteReport = itemView.findViewById(R.id.btn_deleteReport);
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

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }


    //----------------------------------------------------------------------------------------------
    //获取item数量
    @Override
    public int getItemCount() {
        return reportData.size() + 1;
    }

    //获取除footer外的最后一个item位置
    public int getRealLastPosition() {
        return reportData.size();
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
        reportData = new ArrayList<>();
    }

    // 暴露接口，更新数据源，并修改hasMore的值，如果有增加数据，hasMore为true，否则为false
    public void updateList(ArrayList<reportData> newDatas, boolean hasMore) {
        // 在原有的数据之上增加新数据
        if (newDatas != null) {
            reportData.addAll(newDatas);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }
}
