package tv.turbik.screens.home;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import tv.turbik.R;
import tv.turbik.client.Images;
import tv.turbik.client.model.BasicSeries;

/**
* @author Pavel Savinov
* @version 05/04/14 08:56
*/
class BasicSeriesArrayAdapter extends ArrayAdapter<BasicSeries> {

	private HomeActivity homeActivity;
	private final ImageLoader imageLoader;
	private final DisplayImageOptions options;

	public BasicSeriesArrayAdapter(HomeActivity homeActivity, ImageLoader imageLoader, DisplayImageOptions options) {
		super(homeActivity, 0);
		this.homeActivity = homeActivity;
		this.imageLoader = imageLoader;
		this.options = options;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		BasicSeries series = getItem(position);

		if (view == null) {
			view = homeActivity.getLayoutInflater().inflate(R.layout.home_series_item, parent, false);
		}

		final ImageView imageView = (ImageView) view.findViewById(R.id.logo);

		imageLoader.displayImage(Images.seriesBigPoster(series.getId()), imageView, options, new SimpleImageLoadingListener(){
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			}
		});

		((TextView) view.findViewById(R.id.nameEnText)).setText(series.getNameEn());
		((TextView) view.findViewById(R.id.nameRuText)).setText(series.getNameRu());

		return view;
	}

}
