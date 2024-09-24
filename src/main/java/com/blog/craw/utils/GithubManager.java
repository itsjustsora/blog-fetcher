package com.blog.craw.utils;

import java.io.IOException;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GithubManager {

	public void connectGithub() {
		try {
			String token = System.getenv("GITHUB_TOKEN");
			GitHub gitHub = new GitHubBuilder().withOAuthToken(token).build();
			gitHub.checkApiUrlValidity();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
