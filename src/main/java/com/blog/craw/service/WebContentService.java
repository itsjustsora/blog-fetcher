package com.blog.craw.service;

import static com.blog.craw.constants.CrawlConstants.*;

import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.blog.craw.constants.CrawlConstants;
import com.blog.craw.utils.HtmlContentParser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WebContentService {

	private final HtmlContentParser htmlContentParser;

	public String generateContents() {
		Elements titles = htmlContentParser.parseElements(TITLE_SELECTOR);
		Elements links = htmlContentParser.parseElements(LINK_SELECTOR);

		StringBuilder content = new StringBuilder();
		for (int i = 0; i < CrawlConstants.POST_SIZE; i++) {
			content.append("<a href=\"")
				.append(URL)
				.append(links.get(i).attr(HREF)).append("\">")
				.append(titles.get(i).text())
				.append("<br/>");
		}

		return content.toString();
	}
}
