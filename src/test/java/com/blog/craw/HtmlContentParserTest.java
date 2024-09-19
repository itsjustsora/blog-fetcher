package com.blog.craw;

import static org.junit.jupiter.api.Assertions.*;

import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HtmlContentParserTest {

	private static final HtmlContentParser htmlContentParser = new HtmlContentParser();

	@Test
	@DisplayName("유효한 CSS Selector가 제공되었을 때 Elements가 반환된다.")
	void when_validSelectorProvided_expect_returnElements() {
		// given
		String cssSelector = "#content .title";

		// when
		Elements titles = htmlContentParser.parseElements(cssSelector);

		// then
		assertNotNull(titles);
		assertFalse(titles.isEmpty());
	}
}