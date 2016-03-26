package in.srain.cube.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import in.srain.cube.views.inter.RefreshListener;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.R;

/**
 * Created by July on 2016/3/26.
 */
public class PullToRefreshListView extends RelativeLayout {

    private PtrClassicFrameLayout ptr;
    private LoadMoreListViewContainer loadmore;
    private ListView listView;
    private RefreshListener mRefreshListener;

    public PullToRefreshListView(Context context) {
        super(context);
        initView();
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.widget_list_view, this);
        ptr = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_classic_frame_layout);
        loadmore = (LoadMoreListViewContainer) view.findViewById(R.id.load_more_list_view_container);
        listView = (ListView) view.findViewById(R.id.list_view);

        ptr.setLoadingMinTime(800);
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, listView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (mRefreshListener != null) mRefreshListener.onRefresh();
            }
        });
        loadmore.useDefaultFooter();
        loadmore.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                if (mRefreshListener != null) mRefreshListener.onLoadMore();
            }
        });
    }

    /**
     * 自动刷新
     */
    public void autoRefresh() {
        ptr.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptr.autoRefresh();
            }
        }, 150);
    }

    /**
     * 刷新完成
     */
    public void refreshComplete() {
        ptr.refreshComplete();
    }

    /**
     * 加载完成
     *
     * @param emptyResult
     * @param hasMore
     */
    public void loadMoreFinish(boolean emptyResult, boolean hasMore) {
        loadmore.loadMoreFinish(emptyResult, hasMore);
    }

    /**
     * 加载错误
     *
     * @param errorCode
     * @param errorMessage
     */
    public void loadMoreError(int errorCode, String errorMessage) {
        loadmore.loadMoreError(errorCode, errorMessage);
    }

    public void setAdapter(ListAdapter adapter) {
        listView.setAdapter(adapter);
    }

    public void setRefreshListener(RefreshListener listener) {
        this.mRefreshListener = listener;
    }
}
