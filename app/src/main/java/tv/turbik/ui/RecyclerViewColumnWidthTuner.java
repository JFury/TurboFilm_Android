package tv.turbik.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

/**
* Created by pavel on 27/11/14.
*/
public class RecyclerViewColumnWidthTuner {

	public RecyclerViewColumnWidthTuner(final RecyclerView view, final int columnWidth) {

		RecyclerView.LayoutManager layoutManager = view.getLayoutManager();

		final GridLayoutManager gridLayoutManager;

		if ((layoutManager instanceof GridLayoutManager)) {
			gridLayoutManager = (GridLayoutManager) view.getLayoutManager();
		} else {
			throw new RuntimeException("RecyclerView must contain GridLayoutManager as layout manager");
		}

		DisplayMetrics metrics = view.getContext().getResources().getDisplayMetrics();
		final float density = metrics.density;

		view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
			@Override
			public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
				float width = view.getWidth() / density;
				gridLayoutManager.setSpanCount((int) (width / columnWidth));
			}
		});

	}

}
