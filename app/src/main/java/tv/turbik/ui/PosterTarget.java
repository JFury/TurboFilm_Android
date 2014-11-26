package tv.turbik.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import tv.turbik.R;

/**
* Created by pavel on 27/11/14.
*/
public class PosterTarget implements Target {

	private final View container;
	private final ImageView logo;
	private final TextView text;
	private final int defaultTextColor;
	private final int defaultBackColor;

	public PosterTarget(View container, ImageView logo, TextView text, int defaultTextColor, int defaultBackColor) {
		this.container = container;
		this.logo = logo;
		this.text = text;
		this.defaultTextColor = defaultTextColor;
		this.defaultBackColor = defaultBackColor;
	}

	@Override
	public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

		logo.setImageBitmap(bitmap);
		logo.setVisibility(View.VISIBLE);

		Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
			@Override
			public void onGenerated(Palette palette) {
				text.setTextColor(palette.getVibrantColor(defaultTextColor));
				container.setBackgroundColor(palette.getDarkMutedColor(defaultBackColor));
			}
		});

	}

	@Override
	public void onBitmapFailed(Drawable errorDrawable) {
	}

	@Override
	public void onPrepareLoad(Drawable placeHolderDrawable) {
		logo.setImageBitmap(null);
		container.setBackgroundColor(defaultBackColor);
		text.setTextColor(defaultTextColor);
	}

}
