package tv.turbik.screens.main.series;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tv.turbik.EventBus_;
import tv.turbik.R;
import tv.turbik.client.Images;
import tv.turbik.dao.Series;
import tv.turbik.events.OpenSeriesEvent;

/**
 * Created by Pavel on 21.10.2014.
 */
public class SeriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

	private final Context context;
	private final EventBus_ eventBus;
	ImageView logo;
	TextView nameEn;
	TextView nameRu;
	private Series series;

	public SeriesViewHolder(View view) {
		super(view);
		view.setOnClickListener(this);

		context = view.getContext();
		eventBus = EventBus_.getInstance_(context);

		logo = ((ImageView) view.findViewById(R.id.logo));
		nameEn = (TextView) view.findViewById(R.id.name_en_text);
		nameRu = (TextView) view.findViewById(R.id.name_ru_text);
	}

	public void setSeries(Series series) {
		this.series = series;
		Picasso.with(context).load(Images.seriesBigPoster(series.getId())).into(logo);
		nameEn.setText(series.getNameEn());
		nameRu.setText(series.getNameRu());
	}

	@Override
	public void onClick(View v) {
		eventBus.post(new OpenSeriesEvent(series));
	}

}
