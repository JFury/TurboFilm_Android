package tv.turbik.screens;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tv.turbik.R;
import tv.turbik.Settings_;
import tv.turbik.client.TurboFilmClient;
import tv.turbik.client.exception.TurboException;
import tv.turbik.client.exception.server.NotLoggedInException;

/**
 * @author Pavel Savinov aka swap_i
 * @since 1.0.0
 */
@WindowFeature(Window.FEATURE_NO_TITLE)
@EActivity(R.layout.auth)
public class AuthActivity extends Activity {

	private static final Logger L = LoggerFactory.getLogger(AuthActivity.class.getSimpleName());

	@ViewById View authForm;
	@ViewById EditText loginField;
	@ViewById EditText passwordField;
	@ViewById Button signinButton;

	@ViewById(R.id.part_progress) View progressView;
	@ViewById(R.id.part_progress_message) TextView progressText;

	@ViewById(R.id.part_message) View messageView;
	@ViewById(R.id.part_message_text) TextView messageText;

	@Bean TurboFilmClient client;
	@Pref Settings_ settings;

	@AfterViews
	void afterViews() {
		progressText.setText(getString(R.string.auth_progress_text));
		authForm.setVisibility(View.VISIBLE);
		progressView.setVisibility(View.INVISIBLE);
		messageView.setVisibility(View.INVISIBLE);
	}

	@Click
	void signinButton() {
		startSignin();
	}

	@Click(R.id.part_message_button)
	void onMessageOk() {
		messageView.setVisibility(View.INVISIBLE);
		progressView.setVisibility(View.INVISIBLE);
		authForm.setVisibility(View.VISIBLE);
	}

	@UiThread
	void startSignin() {
		messageView.setVisibility(View.INVISIBLE);
		authForm.setVisibility(View.INVISIBLE);
		progressView.setVisibility(View.VISIBLE);
		signinProcess();
	}

	@Background
	void signinProcess() {
		try {

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			client.singin(loginField.getText().toString(), passwordField.getText().toString());
			client.saveIdCookie(client.getCookie());

			setResult(RESULT_OK);
			finish();

		} catch (NotLoggedInException e) {
			L.error("Error signing in", e);
			showError(R.string.auth_error_wrongCredentials);

		} catch (TurboException e) {
			L.error("Error signing in", e);
			showError(R.string.auth_error_other);

		}
	}

	@UiThread
	void showError(int textResId) {
		progressView.setVisibility(View.INVISIBLE);
		authForm.setVisibility(View.INVISIBLE);
		messageView.setVisibility(View.VISIBLE);
		messageText.setText(textResId);
	}

}
