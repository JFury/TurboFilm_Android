package tv.turbik.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import tv.turbik.R;

/**
 * @author Pavel Savinov
 * @version 16/01/14 13:59
 */
public class SeasonNumberDialog extends AlertDialog {

	public interface SeasonListener {

		public void seasonSelected(byte season);

	}

	public SeasonNumberDialog(Context context, byte seasonCount, final SeasonListener listener) {
		super(context);

		LayoutInflater inflater = LayoutInflater.from(context);

		TableLayout table = new TableLayout(context);
		TableRow row = new TableRow(context);

		for (byte i = 1; i <= seasonCount; i++) {
			TextView textView = (TextView) inflater.inflate(R.layout.season_dialog_item, null);
			textView.setText(String.valueOf(i));

			final byte finalI = i;
			textView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dismiss();
					if (listener != null) listener.seasonSelected(finalI);
				}
			});

			row.addView(textView);

			if (i % 5 == 0) {
				table.addView(row);
				row = new TableRow(context);
			}
		}

		table.addView(row);

		setView(table);
	}

}
