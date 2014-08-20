package tv.turbik.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
public class SeasonNumberDialog {

	public interface SeasonListener {

		public void seasonSelected(byte season);

	}

	private final AlertDialog dialog;

	public SeasonNumberDialog(Context context, int seasonsCount, final SeasonListener listener) {

		TableLayout table = new TableLayout(context);
		TableRow row = new TableRow(context);

		for (byte i = 1; i <= seasonsCount; i++) {
			TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.season_dialog_item, null);
			textView.setText(String.valueOf(i));

			final byte finalI = i;
			textView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
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

		dialog = new AlertDialog.Builder(context)
				.setView(table)
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.create();

		dialog.show();

	}

}
