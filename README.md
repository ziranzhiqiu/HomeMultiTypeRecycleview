![image](https://github.com/ziranzhiqiu/HomeMultiTypeRecycleview/blob/master/MulityRecycleApp/src/main/res/mipmap-xxxhdpi/a201712011045.png)
![image](https://github.com/ziranzhiqiu/HomeMultiTypeRecycleview/blob/master/MulityRecycleApp/src/main/res/mipmap-xxxhdpi/b201712011440.png)

  @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TOP) {
               //头部轮播图
            View viewtop = inflater.inflate(R.layout.adapter_slider, parent, false);
            StaggeredGridLayoutManager.LayoutParams params =
                    (StaggeredGridLayoutManager.LayoutParams) viewtop.getLayoutParams();
            params.setFullSpan(true);//最为重要的一个方法，占满全屏,以下同理
            viewtop.setLayoutParams(params);
            return new TypeTopsliderHolder(viewtop);
        } else if (viewType == TYPE_HEADER) {

            View view2 = inflater.inflate(R.layout.item_homepagertypeheader_type, parent, false);

            StaggeredGridLayoutManager.LayoutParams params =
                    (StaggeredGridLayoutManager.LayoutParams) view2.getLayoutParams();
            params.setFullSpan(true);
            view2.setLayoutParams(params);
            return new TypeheadHolder(view2);
        } else if (viewType == TYPE_CENTER) {
            //中间head下面的布局
            View view = inflater.inflate(R.layout.itam_homepageradapter_rv2, parent, false);
            StaggeredGridLayoutManager.LayoutParams params2 =
                    (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            params2.setFullSpan(true);
            view.setLayoutParams(params2);
            return new TypetypeHolder2(view);

        } else if (viewType == TYPE_CATEGORY) {
//四个快速入口的holder
//这里的TypetypeHolder和上面的TypetypeHolder2 其实可以写成一个holder，这里为了简单，避免引起复用带来的问题，分开了
            View view = inflater.inflate(R.layout.itam_homepageradapter_rv2, parent, false);
            StaggeredGridLayoutManager.LayoutParams params2 =
                    (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            params2.setFullSpan(true);
            view.setLayoutParams(params2);
            return new TypetypeHolder(view);

        } else if (viewType == TYPE_REFRESH) {
            return new TypeRefresh(inflater.inflate(R.layout.item_raiders2, parent, false));
        } else {

            View viewtop = inflater.inflate(R.layout.adapter_slider, parent, false);
            StaggeredGridLayoutManager.LayoutParams params =
                    (StaggeredGridLayoutManager.LayoutParams) viewtop.getLayoutParams();
            params.setFullSpan(true);
            viewtop.setLayoutParams(params);
            return new TypeTopsliderHolder(viewtop);
        }
    }