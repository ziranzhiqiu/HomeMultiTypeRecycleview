package com.json.mulityrecycle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.json.mulityrecycle.R;
import com.json.mulityrecycle.bean.HomeCategory;
import com.json.mulityrecycle.weidget.MyPicTextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JsonQiu on 2017/11/29.
 * 首页分类
 */
public class TypeCategoryAdapter extends RecyclerView.Adapter<TypeCategoryAdapter.TypetypeHolder> {

    private Context mContext;

    private List<HomeCategory> mHomeCategory;

    private LayoutInflater inflater;


    public TypeCategoryAdapter(Context mContext, List<HomeCategory> mHomeCategory) {
        this.mContext = mContext;
        this.mHomeCategory = mHomeCategory;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public TypetypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TypetypeHolder(inflater.inflate(R.layout.item_home_ivtv, null));
    }

    @Override
    public void onBindViewHolder(TypetypeHolder holder, int position) {
        HomeCategory homeCategory = mHomeCategory.get(position);
        holder.myPicTextView.setTvImagetext(homeCategory.getTypename());
        holder.myPicTextView.setIvImagetext(homeCategory.getImageid());
    }

    @Override
    public int getItemCount() {
        return mHomeCategory == null ? 0 : mHomeCategory.size();
    }

    //中间的四个type
    public class TypetypeHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.pictv_home_adapter)
        MyPicTextView myPicTextView;

        public TypetypeHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
