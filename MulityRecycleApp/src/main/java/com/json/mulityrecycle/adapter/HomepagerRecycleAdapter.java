package com.json.mulityrecycle.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.json.mulityrecycle.R;
import com.json.mulityrecycle.bean.Headerbean;
import com.json.mulityrecycle.bean.HomeCategory;
import com.json.mulityrecycle.bean.WaterFallBean;
import com.json.mulityrecycle.utils.GlideImageLoader;
import com.json.mulityrecycle.weidget.ImageUtils;
import com.json.mulityrecycle.weidget.MyHeaderTitleView;
import com.json.mulityrecycle.weidget.MyStaggerGrildLayoutManger;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JsonQiu on 2017/11/30.
 */
public class HomepagerRecycleAdapter extends RecyclerView.Adapter {

    private final Context mContext;
    private LayoutInflater inflater;
    private RecyclerView recyclerView;
    private MyStaggerGrildLayoutManger mystager;
    private List<Headerbean.DataBean> headerData;
    private List<WaterFallBean.DataBean> refreshbean;
    private List<WaterFallBean.DataBean> centerBean;
    private ArrayList<HomeCategory> mHomeCategories;
    private List<Integer> mHeights = new ArrayList<>();
    private List<Integer> imagesList = new ArrayList<>();

    private int count = 3;

    private int TYPE_TOP = 1;//头部布局
    private int TYPE_CENTER = 2;//
    private int TYPE_CATEGORY_FourCard = 3;//中间的四个快速入口
    private int TYPE_HEADER = 4;//每个分类的head
    private int TYPE_WATERFALL = 5;//最下面的布局

    private int WATERFALL_POSITION = 6;//下部head的位置
    private int CENTER_POSITION = 7;//中间head的位置

    public HomepagerRecycleAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        //初始化各数据源
        headerData = new ArrayList<>();
        refreshbean = new ArrayList<>();
        centerBean = new ArrayList<>();
    }


    //抽取出来的方法
    private View getTypeViewHolder(ViewGroup parent, int layoutId) {
        View viewtop = inflater.inflate(layoutId, parent, false);
        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) viewtop.getLayoutParams();
        //最为重要的一个方法，占满全屏,以下同理
        params.setFullSpan(true);
        viewtop.setLayoutParams(params);
        return viewtop;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TOP) {
            return new Type1TopHolder(getTypeViewHolder(parent, R.layout.type_1_top_slider));
        } else if (viewType == TYPE_HEADER) {
            return new Type2HeadHolder(getTypeViewHolder(parent, R.layout.type_2_head_item));
        } else if (viewType == TYPE_CENTER) {
            return new Type3CenterHolder(getTypeViewHolder(parent, R.layout.type_3_center));
        } else if (viewType == TYPE_CATEGORY_FourCard) {
            //四个快速入口的holder
            //这里的TypetypeHolder和上面的Type3CenterHolder 其实可以写成一个holder，这里为了简单，避免引起复用带来的问题，分开了
            return new Type4CategoryHolder(getTypeViewHolder(parent, R.layout.type_3_center));
        } else if (viewType == TYPE_WATERFALL) {
            return new Type5Waterfall(inflater.inflate(R.layout.type_5_waterfall, parent, false));
        } else {
            return new Type1TopHolder(getTypeViewHolder(parent, R.layout.type_1_top_slider));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Type1TopHolder && headerData.size() != 0 && ((Type1TopHolder) holder).linearLayout.getChildCount() == 0) {
            //如果是Type1TopHolder， 并且header有数据，并且Type1TopHolder的linearLayout没有子view
            // （因为这个布局只出现一次，如果他没有子view， 也就是他是第一次加载，才加载数据）
            //加载头部数据源
            init1TopSlider(((Type1TopHolder) holder), headerData);
        } else if (holder instanceof Type2HeadHolder) {
            //加载heade数据源（其实这里可以每个head单独设置，因为有的需求head去各式各样）
            init2Head(((Type2HeadHolder) holder), position);
        } else if (holder instanceof Type3CenterHolder && centerBean.size() != 0) {
            //加载中间head下面的数据源
            init3CenterBean(((Type3CenterHolder) holder));
        } else if (holder instanceof Type4CategoryHolder && centerBean.size() != 0) {
            //加载四个category数据源
            init4Category(((Type4CategoryHolder) holder));
        } else if (holder instanceof Type5Waterfall && refreshbean.size() != 0) {
            //加载瀑布流数据源  waterfall
            init5WaterFall(((Type5Waterfall) holder), position - WATERFALL_POSITION - 1);
        }
    }

    private void init5WaterFall(Type5Waterfall holder, int position) {
        Log.e("position", "init5WaterFall: " + position);
        if (mHeights.size() <= getItemCount() + 2) {
            //这里只是随机数模拟瀑布流， 实际过程中， 应该根据图片高度来实现瀑布流
            mHeights.add((int) (500 + Math.random() * 400));
        }

        ViewGroup.LayoutParams layoutParams = holder.homeReadPivIv.getLayoutParams();
        if (mHeights.size() > position)
            //此处取得随机数，如果mheight里面有数则取， 没有则邹走else
            layoutParams.height = mHeights.get(position);
        else layoutParams.height = 589;
        holder.homeReadPivIv.setLayoutParams(layoutParams);

        holder.homeReadPivIv.setScaleType(ImageView.ScaleType.FIT_XY);
        if (refreshbean.size() > position) {
            ImageUtils.load(mContext, refreshbean.get(position).getCpOne().getImgUrl(), holder.homeReadPivIv);
        } else {
            ImageUtils.load(mContext, refreshbean.get(0).getCpTwo().getImgUrl(), holder.homeReadPivIv);
        }
    }

    private void init4Category(Type4CategoryHolder holder) {
        holder.rvtype.setLayoutManager(new GridLayoutManager(mContext, mHomeCategories.size()));

        TypeCategoryAdapter categoryAdapter = new TypeCategoryAdapter(mContext, mHomeCategories);

        holder.rvtype.setAdapter(categoryAdapter);
    }

    private void init3CenterBean(Type3CenterHolder holder) {
        holder.rvtype.setLayoutManager(new GridLayoutManager(mContext, 2));

        TypeHotpointAdapter centerAdapter = new TypeHotpointAdapter(mContext, centerBean);

        holder.rvtype.setAdapter(centerAdapter);
    }

    private void init2Head(Type2HeadHolder holder, int position) {
        if (position == CENTER_POSITION) {
            holder.myHeaderTitleView.setTypeName("中间head");

        } else if (position == WATERFALL_POSITION) {
            holder.myHeaderTitleView.setTypeName("下部head");
        }
    }

    private void init1TopSlider(Type1TopHolder holder, List<Headerbean.DataBean> data) {
        //轮播图
        imagesList.add(R.mipmap.app_log);
        imagesList.add(R.mipmap.ic_launcher);
        imagesList.add(R.mipmap.app_log);
        imagesList.add(R.mipmap.ic_launcher);
        imagesList.add(R.mipmap.app_log);

        holder.mBanner.setImages(imagesList)
                .setImageLoader(new GlideImageLoader())
                .setDelayTime(2000)
                .setIndicatorGravity(BannerConfig.CENTER)
                .start();
        holder.mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(mContext, " 点击位置 = " + position, Toast.LENGTH_SHORT).show();
            }
        });

        //横向滑动控件展示
        LinearLayout linearLayout = holder.linearLayout;
        for (int i = 0; i < data.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.rightMargin = 40;
            imageView.setLayoutParams(layoutParams);
            ImageUtils.load(mContext, data.get(i).getImgUrl(), imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            linearLayout.addView(imageView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        //此处是根据数据源有无数据来判定分类条的位置；可自行拓展，自由发挥
        CENTER_POSITION = mHomeCategories.size() == 0 ? 1 : 2;
        WATERFALL_POSITION = centerBean.size() == 0 ? 3 : 4;

        Log.e("getItemViewType", "getItemViewType: " + CENTER_POSITION + ",:" + WATERFALL_POSITION);

        if (position == 0) return TYPE_TOP;
        else if (position == CENTER_POSITION || position == WATERFALL_POSITION) return TYPE_HEADER;
        else if (position == 1) return TYPE_CATEGORY_FourCard;
        else if (position == CENTER_POSITION + 1) return TYPE_CENTER;
        else return TYPE_WATERFALL;
    }

    @Override
    public int getItemCount() {
        return count;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        this.recyclerView = recyclerView;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof MyStaggerGrildLayoutManger) {
            mystager = ((MyStaggerGrildLayoutManger) layoutManager);

        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                //如果快速滑动， 不加载图片
                if (newState == 2) {
                    Glide.with(mContext).pauseRequests();
                } else {
                    Glide.with(mContext).resumeRequests();

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });
    }

    public void setheaderbean(Headerbean headerbean) {
        headerData = headerbean.getData();
        notifyDataSetChanged();
    }

    public void setRefreshBean(WaterFallBean waterFallBean, boolean flagFirst) {
        refreshbean.addAll(waterFallBean.getData());
        int count1 = this.count;
        this.count += waterFallBean.getData().size();
        notifyDataSetChanged();
        if (!flagFirst) {
            recyclerView.smoothScrollToPosition(count1 + 2);//加载完以后向上滚动3个条目
        }


    }

    public void setCenterBean(WaterFallBean waterFallBean) {
        centerBean = waterFallBean.getData();
        count++;
        notifyDataSetChanged();
    }

    public void setCategoryBean(ArrayList<HomeCategory> homeCategories) {
        mHomeCategories = homeCategories;
        count++;
        notifyDataSetChanged();

    }

    //头部Viewpager viewholder
    public class Type1TopHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ll_slider)
        LinearLayout linearLayout;
        @Bind(R.id.home_banner)
        Banner mBanner;

        public Type1TopHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class Type2HeadHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ashv_homepager)
        MyHeaderTitleView myHeaderTitleView;

        public Type2HeadHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            myHeaderTitleView.setMoreclicklistenser(new MyHeaderTitleView.MoreclickListenser() {
                @Override
                public void setmoreclicklistenser() {
                }
            });
        }
    }

    //中间的四个type
    public class Type4CategoryHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.rv_home_artist)
        RecyclerView rvtype;

        public Type4CategoryHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class Type3CenterHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.rv_home_artist)
        RecyclerView rvtype;


        public Type3CenterHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    static class Type5Waterfall extends RecyclerView.ViewHolder {
        @Bind(R.id.home_read_piv_iv)
        ImageView homeReadPivIv;

        Type5Waterfall(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
