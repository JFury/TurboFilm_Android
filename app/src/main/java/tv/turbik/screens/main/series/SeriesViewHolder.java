package tv.turbik.screens.main.series;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import tv.turbik.ui.PosterTarget;

/**
 * Created by Pavel on 21.10.2014.
 */
public class SeriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

	private static final Logger L = LoggerFactory.getLogger(SeriesViewHolder.class.getSimpleName());

	private final Context context;
	private final EventBus eventBus;
	private final TextView nameEn;
	private final TextView nameRu;
	private final Target picassoTarget;

	private Series series;

	public SeriesViewHolder(ViewGroup parentView) {
		super(LayoutInflater.from(parentView.getContext()).inflate(R.layout.main_series_card, parentView, false));

		nameEn = (TextView) itemView.findViewById(R.id.name_en_text);
		nameRu = (TextView) itemView.findViewById(R.id.name_ru_text);

		View container = itemView.findViewById(R.id.container);
		ImageView logo = ((ImageView) itemView.findViewById(R.id.logo));

		itemView.setOnClickListener(this);

		context = itemView.getContext();
		eventBus = EventBus_.getInstance_(context);

		int defaultTextColor = context.getResources().getColor(R.color.blue);
		int defaultBackColor = context.getResources().getColor(R.color.back_gray);

		picassoTarget = new PosterTarget(container, logo, nameEn, defaultTextColor, defaultBackColor);
	}

	public void setSeries(final Series series) {
		this.series = series;

		nameEn.setText(series.getNameEn());
		nameRu.setText(series.getNameRu());

		Picasso.with(context)
				.load(Images.seriesBigPoster(series.getId()))
				.into(picassoTarget);
	}

	@Override
	public void onClick(View v) {
		eventBus.post(new OpenSeriesEvent(series));
	}

}
