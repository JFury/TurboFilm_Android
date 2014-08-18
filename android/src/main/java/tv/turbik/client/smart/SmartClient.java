package tv.turbik.client.smart;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import tv.turbik.client.TurbikClient;
import tv.turbik.client.container.HomePage;
import tv.turbik.client.exception.TurboException;
import tv.turbik.client.model.BasicSeries;
import tv.turbik.dao.Series;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Savinov
 * @version 18/08/14 21:31
 */
@EBean
public class SmartClient {

	@Bean TurbikClient turbikClient;

	public List<Series> getSeries() throws TurboException{

		HomePage homePage = turbikClient.getHomePage();

		List<Series> seriesList = new ArrayList<Series>();

		for (BasicSeries basicSeries : homePage.getTopSeries().getMySeries()) {
			Series series = new Series();
			series.setId((long) basicSeries.getId());
			series.setAlias(basicSeries.getAlias());
			series.setNameEn(basicSeries.getNameEn());
			series.setNameRu(basicSeries.getNameRu());
			seriesList.add(series);
		}

		for (BasicSeries basicSeries : homePage.getTopSeries().getMySeries()) {
			Series series = new Series();
			series.setId((long) basicSeries.getId());
			series.setAlias(basicSeries.getAlias());
			series.setNameEn(basicSeries.getNameEn());
			series.setNameRu(basicSeries.getNameRu());
			seriesList.add(series);
		}

		return seriesList;
	}

}
