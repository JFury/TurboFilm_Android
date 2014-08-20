package tv.turbik.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import tv.turbik.R;

/**
 * @author Pavel Savinov
 * @version 16/01/14 13:37
 */
public class SeasonSelector extends TextView {

	public SeasonSelector(final Context context) {
		super(context);
		init(context);
	}

	public SeasonSelector(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void setSeasonListener(final int seasonsCount, int currentSeason, final SeasonNumberDialog.SeasonListener listener) {
		updateView(currentSeason);
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new SeasonNumberDialog(getContext(), seasonsCount, new SeasonNumberDialog.SeasonListener() {
					@Override
					public void seasonSelected(byte season) {
						updateView(season);
						if (listener != null) {
							listener.seasonSelected(season);
						}

					}
				});
			}
		});
	}

	private void init(final Context context) {
		setTextSize(context.getResources().getDimension(R.dimen.text_size_large));
	}

	private void updateView(int season) {
		setText(getContext().getString(R.string.season_selector_text, season));
	}

}
