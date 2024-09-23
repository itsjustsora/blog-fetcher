package com.blog.craw;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HtmlContentParser {

	public Elements parseElements(String selector) {
		Document document = null;

		try {
			document = Jsoup.connect(Constants.URL).get();
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		Elements elements = document.select(selector);
		if (elements.isEmpty()) {
			return new Elements();
		}

		return elements;
	}
}
