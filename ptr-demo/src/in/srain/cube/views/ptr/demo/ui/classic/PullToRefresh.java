package in.srain.cube.views.ptr.demo.ui.classic;

import android.widget.TextView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.demo.R;

public class PullToRefresh extends WithTextViewBaseFragment {

    @Override
    protected void setupViews(PtrClassicFrameLayout ptrFrame, TextView textView) {
        setHeaderTitle(R.string.ptr_demo_title_pull_to_refresh);
        ptrFrame.setPullToRefresh(true);
    }
}