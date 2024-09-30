package com.blog.craw.service;

import static com.blog.craw.constants.CrawlConstants.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.stream.IntStream;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.blog.craw.constants.CrawlConstants;
import com.blog.craw.utils.GithubManager;
import com.blog.craw.utils.HtmlContentParser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WebContentService {

	private final HtmlContentParser htmlContentParser;
	private final GithubManager githubManager;

	/**
	 * 업데이트 여부 확인 및 블로그 포스팅 조회
	 * @return
	 */
	public Optional<String> generateContents() {
		String date = getRecentDate();
		if (!isUpdated(date)) {
			return Optional.empty();
		}

		return Optional.of(buildContent());
	}

	/**
	 * 최근 날짜 조회
	 * @return
	 */
	private String getRecentDate() {
		Element dateElement = htmlContentParser.parseElements(DATE_SELECTOR).first();
		return (dateElement != null) ? dateElement.text() : "";
	}

	/**
	 * Elements 추출 후 HTML 문자열 생성
	 * @return
	 */
	private String buildContent() {
		Elements titleElements = htmlContentParser.parseElements(TITLE_SELECTOR);
		Elements linksElements = htmlContentParser.parseElements(LINK_SELECTOR);

		return IntStream.range(0, CrawlConstants.POST_SIZE)
			.mapToObj(i -> String.format("<a href=\"%s%s\">%s</a><br>",
				URL, linksElements.get(i).attr(HREF), titleElements.get(i).text()))
			.reduce(new StringBuilder(), StringBuilder::append, StringBuilder::append)
			.toString();
	}

	/**
	 * date > 업데이트 필요 여부 확인
	 * @param date
	 * @return
	 */
	public boolean isUpdated(String date) {
		if (date.isEmpty()) {
			return false;
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		try {
			LocalDate localDate = LocalDate.parse(date, formatter);
			// 스케줄링 시 업데이트 된 포스팅 정보가 있는지 확인 (하루 전 날짜로 검증)
			return localDate.equals(LocalDate.now().minusDays(1));
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	/**
	 * README 내용 업데이트
	 */
	public void updateReadme() {
		Document document = githubManager.getDocument();

		// 업데이트하고자 하는 div 태그 부분 조회
		Element element = document.getElementById(UPDATE_DIV);
		if (element == null) {
			return;
		}

		Optional<String> updateContent = generateContents();
		updateContent.ifPresent(content -> {
			element.html(content);
			String updatedContent = document.body().html();
			githubManager.updateReadme(updatedContent, COMMIT_MESSAGE);
		});
	}
}
