package com.example.m1.adapter;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cqupt.master_helper.R;
import com.example.m1.adapter.adapterUtilities.adapterUtilities;
import com.example.m1.bean.recycleViewData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class rankVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ItemClickListener mItemClickListener ;

    private ArrayList<recycleViewData> recycleViewData;

    private final int normalType =1;
    private final int footerType =0 ;

    private boolean hasMore =true;
    private boolean fadeTips=false;


    public rankVideoAdapter(ArrayList<recycleViewData> Dataset){
        this.recycleViewData=Dataset;
    }



    @NonNull
    @Override
    public @NotNull RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
       if(viewType ==normalType)
       {
           View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_rank,parent,false);
           return new normalHolder(view);
       }
       else {
           View view =LayoutInflater.from((parent.getContext())).inflate(R.layout.video_footer,parent,false);
           return new footerHolder(view);
       }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof normalHolder){
            ((normalHolder) holder).videoNameView.setText(recycleViewData.get(position).videoName);
            ((normalHolder) holder).hotPointView.setText(String.valueOf(recycleViewData.get(position).hot));
            ((normalHolder) holder).uploaderNameView.setText(recycleViewData.get(position).uploaderName);
            ((normalHolder) holder).rankLevel.setText("NO. "+position);
           adapterUtilities.genCover(((normalHolder) holder).videoCoverView,recycleViewData.get(position).videoUri,((normalHolder) holder).videoCoverView.getContext());
            //为item设置点击事件
            if(mItemClickListener!=null)
            {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.onItemClick(holder.getBindingAdapterPosition());
                    }
                });
            }
            else {
                if (recycleViewData.size() > 0) {
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




    }

    public int getItemCount() { return recycleViewData.size()+1; }

    public int getRealLastPosition() {
        return recycleViewData.size();
    }

    public int getItemViewType(int position){
        if (position==getItemCount()-1){
            return footerType;
        }
        else {
            return normalType;
        }
    }

    class normalHolder extends RecyclerView.ViewHolder {

        private final TextView videoNameView;
        private final TextView uploaderNameView;
        private final TextView hotPointView;
        private final ImageView videoCoverView;
        private final TextView rankLevel;

        public normalHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.videoNameView = itemView.findViewById(R.id.rankVideoName);
            this.uploaderNameView = itemView.findViewById(R.id.rankVideoUploaderName);
            this.hotPointView = itemView.findViewById(R.id.rankHotPoint);
            this.videoCoverView = itemView.findViewById(R.id.rankVideoCover);
            this.rankLevel=itemView.findViewById(R.id.rankLevel);
        }
    }

    class footerHolder extends RecyclerView.ViewHolder{
        private final TextView tips;

        public  footerHolder (View itemView){
            super(itemView);
            tips=itemView.findViewById(R.id.tips);
        }

    }

    // 暴露接口，改变fadeTips的方法
    public boolean isFadeTips() {
        return fadeTips;
    }

    // 暴露接口，下拉刷新时，通过暴露方法将数据源置为空
    public void resetDatas() {
        recycleViewData = new ArrayList<>();
    }

    // 暴露接口，更新数据源，并修改hasMore的值，如果有增加数据，hasMore为true，否则为false
    public void updateList( ArrayList<recycleViewData> newDatas, boolean hasMore) {
        // 在原有的数据之上增加新数据
        if (newDatas != null) {
            recycleViewData.addAll(newDatas);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }




    public interface ItemClickListener{
        public void onItemClick(int position) ;
    }
    public void setOnItemClickListener(rankVideoAdapter.ItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener ;

    }
}
