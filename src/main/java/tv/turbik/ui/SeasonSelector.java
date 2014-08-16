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

	private SeasonNumberDialog.SeasonListener listener;

	public SeasonSelector(final Context context) {
		super(context);
		init(context);
	}

	public SeasonSelector(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void setSeasonListener(SeasonNumberDialog.SeasonListener listener) {
		this.listener = listener;
	}

	private void init(final Context context) {

		setText("-----");

		setTextSize(context.getResources().getDimension(R.dimen.text_size_large));

		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new SeasonNumberDialog(context, new SeasonNumberDialog.SeasonListener() {
					@Override
					public void seasonSelected(int season) {

						setText("Сезон " + season);

						if (listener != null) listener.seasonSelected(season);

					}
				});
			}
		});
	}
}
