package ru.swapii.turbofilm.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import ru.swapii.turbofilm.android.TurboApp;
import ru.swapii.turbofilm.TurboFilm;
import ru.swapii.turbofilm.android.R;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 03.05.12
 * Time: 23:15
 */
public class LoginActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final SharedPreferences settings = getSharedPreferences("settings", 0);

		final TurboFilm turbo = ((TurboApp) getApplication()).getTurbo();
		turbo.setLogin(settings.getString("login", "login"));
		turbo.setPassword(settings.getString("password", "password"));

		new Thread(new Runnable() {
			@Override
			public void run() {
				if (turbo.login()) {
					startActivity(new Intent(LoginActivity.this, SeriesActivity.class));
				} else {
					showUI(turbo, settings);
				}
			}
		}).start();
	}

	private void showUI(final TurboFilm turbo, final SharedPreferences settings) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				setContentView(R.layout.login);
				findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {

						String login = ((EditText) findViewById(R.id.loginField)).getText().toString();
						String password = ((EditText) findViewById(R.id.passwordField)).getText().toString();

						turbo.setLogin(login);
						turbo.setPassword(password);

						SharedPreferences.Editor editor = settings.edit();
						editor.putString("login", login);
						editor.putString("password", password);
						editor.commit();

						new Thread(new Runnable() {
							@Override
							public void run() {
								if (turbo.login()) {
									startActivity(new Intent(LoginActivity.this, SeriesActivity.class));
								} else {
									wrongLogin();
								}
							}
						}).start();
					}
				});
			}
		});
	}

	private void wrongLogin() {
		((TextView) findViewById(R.id.errorLogin)).setText("Ошибка логина");
	}

}