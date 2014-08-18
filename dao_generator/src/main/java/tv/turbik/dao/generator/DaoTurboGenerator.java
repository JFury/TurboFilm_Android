package tv.turbik.dao.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

import java.io.File;

/**
 * @author Pavel Savinov
 * @version 16/08/14 22:05
 */
public class DaoTurboGenerator {

	public static void main(String[] args) throws Exception {

		Schema schema = new Schema(1, "tv.turbik.dao");

		Entity series = schema.addEntity("Series");
		series.addIdProperty();
		series.addStringProperty("alias").notNull().unique();
		series.addStringProperty("nameEn").notNull();
		series.addStringProperty("nameRu");
		series.addStringProperty("description");

		Entity episode = schema.addEntity("Episode");
		episode.addIdProperty();
		episode.addByteProperty("season").notNull();
		episode.addByteProperty("episode").notNull();
		episode.addStringProperty("nameEn").notNull();
		episode.addStringProperty("nameRu");
		episode.addStringProperty("smallPosterUrl");
		Property.PropertyBuilder episodeSeriesId = episode.addLongProperty("seriesId");

		series.addToMany(episode, episodeSeriesId.getProperty());

		Entity video = schema.addEntity("Video");
		video.addIdProperty();
		video.addStringProperty("lang").notNull();
		video.addStringProperty("quality").notNull();
		video.addIntProperty("totalLength").notNull();
		Property.PropertyBuilder videoEpisodeId = video.addLongProperty("episodeId");

		episode.addToMany(video, videoEpisodeId.getProperty());

		Entity videoPart = schema.addEntity("VideoPart");
		videoPart.addIdProperty();
		videoPart.addIntProperty("start").notNull();
		videoPart.addIntProperty("length").notNull();
		Property.PropertyBuilder partVideoId = videoPart.addLongProperty("videoId");

		video.addToMany(videoPart, partVideoId.getProperty());

		String outDir = "android/src/dao/java";

		new File(outDir).mkdirs();

		new DaoGenerator().generateAll(schema, outDir);
	}

}
