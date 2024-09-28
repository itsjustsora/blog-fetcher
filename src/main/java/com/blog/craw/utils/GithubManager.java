package com.blog.craw.utils;

import static com.blog.craw.constants.CrawlConstants.*;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class GithubManager {

	/**
	 * Github 객체 생성
	 * @return
	 */
	public GitHub connectGithub() {
		GitHub gitHub = null;

		try {
			String token = System.getenv("GITHUB_TOKEN");
			gitHub = new GitHubBuilder().withOAuthToken(token).build();
			gitHub.checkApiUrlValidity();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return gitHub;
	}

	/**
	 * GHContent 추출 후 Document 반환
	 * @return
	 */
	public Document getDocument() {
		GitHub gitHub = connectGithub();

		Document document = null;
		try {
			GHContent ghContent = gitHub.getRepository(REPO).getReadme();

			String content = new String(ghContent.read().readAllBytes());

			document = Jsoup.parse(content);

		} catch(IOException e) {
			throw new RuntimeException(e);
		}

		return document;
	}

	/**
	 * README.md 수정
	 * @param content
	 * @param commitMessage
	 */
	public void updateReadme(String content, String commitMessage) {
		GitHub gitHub = connectGithub();

		try {
			gitHub.getRepository(REPO)
				.getFileContent(README_FILE)
				.update(content, commitMessage);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
