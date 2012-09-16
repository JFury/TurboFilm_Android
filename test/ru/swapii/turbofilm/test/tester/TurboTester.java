package ru.swapii.turbofilm.test.tester;

import ru.swapii.turbofilm.TurboFilm;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel
 * Date: 13.08.12
 * Time: 21:44
 */
public class TurboTester {

	private TurboFilm turbo;

	public TurboTester(String login, String password) {
		turbo = new TurboFilm();
		turbo.setLogin(login);
		turbo.setPassword(password);
	}

	public static void main(String[] args) {

		new TurboTester(System.getProperty("login"), System.getProperty("password"));



	}

}
