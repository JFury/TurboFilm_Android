package tv.turbik.screens.main.series;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tv.turbik.R;
import tv.turbik.beans.EventBus;
import tv.turbik.beans.EventBus_;
import tv.turbik.client.Images;
import tv.turbik.dao.Series;
import tv.turbik.events.OpenSeriesEvent;

/**
 * Created by Pavel on 21.10.2014.
 */
public class SeriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

	private static final Logger L = LoggerFactory.getLogger(SeriesViewHolder.class.getSimpleName());

	private final Context context;
	private final EventBus eventBus;

	private final View container;
	private final ImageView logo;
	private final TextView nameEn;
	private final TextView nameRu;

	private Series series;
	private Target picassoTarget;

	public SeriesViewHolder(View view) {
		super(view);
		view.setOnClickListener(this);

		context = view.getContext();
		eventBus = EventBus_.getInstance_(context);

		container = view.findViewById(R.id.container);
		logo = ((ImageView) view.findViewById(R.id.logo));
		nameEn = (TextView) view.findViewById(R.id.name_en_text);
		nameRu = (TextView) view.findViewById(R.id.name_ru_text);


		picassoTarget = new Target() {
			@Override
			public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

				logo.setImageBitmap(bitmap);
				logo.setVisibility(View.VISIBLE);

				Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
					@Override
					public void onGenerated(Palette palette) {
						nameEn.setTextColor(palette.getVibrantColor(android.R.color.white));
						container.setBackgroundColor(palette.getDarkMutedColor(R.color.back_gray));
					}
				});
			}

			@Override
			public void onBitmapFailed(Drawable errorDrawable) {
			}

			@Override
			public void onPrepareLoad(Drawable placeHolderDrawable) {
			}

		};

	}

	public void setSeries(final Series series) {
		this.series = series;

		nameEn.setText(series.getNameEn());
		nameRu.setText(series.getNameRu());

		nameEn.setTextColor(context.getResources().getColor(android.R.color.white));
		container.setBackgroundColor(context.getResources().getColor(R.color.back_gray));
		logo.setVisibility(View.INVISIBLE);

		Picasso.with(context)
				.load(Images.seriesBigPoster(series.getId()))
				.into(picassoTarget);
	}

	@Override
	public void onClick(View v) {
		eventBus.post(new OpenSeriesEvent(series));
	}

}
