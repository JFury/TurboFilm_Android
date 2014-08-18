package tv.turbik.screens.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
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
import tv.turbik.dao.Series;

/**
* @author Pavel Savinov
* @version 05/04/14 08:56
*/
class SeriesAdapter extends ArrayAdapter<Series> {

	private final LayoutInflater inflater;
	private final ImageLoader imageLoader;
	private final DisplayImageOptions options;

	public SeriesAdapter(Context context, ImageLoader imageLoader, DisplayImageOptions options) {
		super(context, 0);
		this.imageLoader = imageLoader;
		this.options = options;
		inflater = LayoutInflater.from(getContext());
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		final ViewHolder holder;

		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.home_series_item, parent, false);
			view.setTag(holder);
			holder.logo = (ImageView) view.findViewById(R.id.logo);
			holder.nameEn = (TextView) view.findViewById(R.id.nameEnText);
			holder.nameRu = (TextView) view.findViewById(R.id.nameRuText);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		Series series = getItem(position);
		holder.nameEn.setText(series.getNameEn());
		holder.nameRu.setText(series.getNameRu());

		imageLoader.displayImage(Images.seriesBigPoster(series.getId()), holder.logo, options, new SimpleImageLoadingListener(){
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				holder.logo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				holder.logo.setScaleType(ImageView.ScaleType.CENTER_CROP);
			}
		});

		return view;
	}

	class ViewHolder {

		private ImageView logo;
		private TextView nameEn;
		private TextView nameRu;

	}

}
