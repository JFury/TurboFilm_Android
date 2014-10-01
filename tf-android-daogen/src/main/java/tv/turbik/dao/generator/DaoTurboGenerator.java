package tv.turbik.dao.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
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
		series.addShortProperty("id");
		series.addStringProperty("alias").notNull().unique();
		series.addStringProperty("nameEn").notNull();
		series.addStringProperty("nameRu");
		series.addStringProperty("description");
		series.addByteProperty("seasonsCount");

		Entity episode = schema.addEntity("Episode");
		episode.addStringProperty("seriesAlias");
		episode.addByteProperty("season").notNull();
		episode.addByteProperty("episode").notNull();
		episode.addStringProperty("nameEn").notNull();
		episode.addStringProperty("nameRu");
		episode.addStringProperty("smallPosterUrl");
		episode.addStringProperty("hash");
		episode.addStringProperty("metaData");

		Entity video = schema.addEntity("Video");
		video.addIdProperty();
		video.addStringProperty("lang").notNull();
		video.addStringProperty("quality").notNull();
		video.addIntProperty("totalLength").notNull();

		Entity videoPart = schema.addEntity("VideoPart");
		videoPart.addIdProperty();
		videoPart.addIntProperty("start").notNull();
		videoPart.addIntProperty("length").notNull();

		String outDir = "android/src/dao/java";

		new File(outDir).mkdirs();

		new DaoGenerator().generateAll(schema, outDir);
	}

}
