package tv.turbik.test.client;

import org.junit.Assert;
import org.junit.Test;
import tv.turbik.client.episode.Video;
import tv.turbik.client.episode.VideoMeta;

/**
 * @author Pavel Savinov [swapii@gmail.com]
 * @version 24.11.13 20:19
 */
public class MetaDecoderTest {

	private static final String SAMPLE = "Ptas6vG8R7r5UxZW6jY1fSsGu1JZ67pWlwZIlLY1RTd7zXExujg%2btn%3dg6vax9vc%2btn%3d8ut2L6kr50xrLfjspC1A8uCAglwr70Tr4Rtss0XfsOXnYf7F3p70xpLR1pjlhpxphfjBh07EGfvn5pXGWlwr70Tr4RtspC1A8uCAg9eE%2bOvnYpL0LOwdhOX020LnkfXnifvph0jB3pxEslj0sptfgzxh2P8YKuCAgzkpWRTBjlTf5P8YKuCAg0TpGlvpYPjExOXGW0TpGlvpYP8YKuCAgler50TdN6xs%2bfXfiOtGWler50TdN6xs%2btn%3d8ut2%3dUXs2PCa%3dUXspC1A8PwrNltsxfj0gzxrNltspC1A8PepjU7rZ6jsWzxZDl5yYRTB16xlN6wYIRe0WrwhZn7ZQn7FIlid%3dlva5HSgxfvB7fXA5pX8LpX3YfLlZpXfif7Eyf70G0jRZ07cLpvuI9QJQPCaL0kBZlvs%2btn%3d8ut2L9TNZULspC1A8uCAglwr70Tr4Rts2fX32pXAGfXfgzxdZl7Fi6en%2btn%3d8uCA8Pwh2PjfkfLnYpjc2pjGW9eE%2btn%3d8utGWUxZblTf%2btn%3d8ut240vyQULspC1A8uCAglvs%2bfXGWlvs%2btn%3d8uCA8PeBiPjEgzkBiP8YKuCAgzx2h67RLP8YKuCAgUkr1RwZY6wrLP8YKuCA8ut2Z6jsGPCaZ6jspC1A8uCAgUQc%2bftGWUQc%2btn%3d8uCA8PepWRTBjlTf8zLspC1A8PCaLRvBY9Td4lTf%2btn%3dgzxiWR7ZZP8oo";
	public static final String EXPECTED = "https://cdn.turbofilm.tv/67b8b1c4fedb2e38e2eb26f403b9c2b2642b0a2c/626/8a389442ad6f677b66a7ca22aba01d25/0/2b37c596af0b3f600318455bd0b5f36ae097627a/be23c31c0e852abb26cdcddc871a8275ce7bec92/77497189ab3a63731f7b151acab4cb5db8102363";

	@Test
	public void decode() {
		String decodedString = Video.decodeMeta(SAMPLE);

		Assert.assertNotNull(decodedString);
	}

	@Test
	public void url() {

		VideoMeta meta = new VideoMeta();
		meta.setEid(626);
		meta.setDefaultSource("8a389442ad6f677b66a7ca22aba01d25");

		String url = Video.generateUrl(meta, "2b37c596af0b3f600318455bd0b5f36ae097627a", "ru", false, 0);

		Assert.assertEquals(EXPECTED, url);
	}

}
