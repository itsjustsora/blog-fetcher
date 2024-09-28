package com.blog.craw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.blog.craw.service.WebContentService;

@SpringBootApplication
public class CrawApplication {

	public static void main(String[] args) {
		ApplicationContext context =
			SpringApplication.run(CrawApplication.class, args);

		// WebContentService 인스턴스를 가져와서 updateReadme() 메소드 호출
		WebContentService webContentService = context.getBean(WebContentService.class);
		webContentService.updateReadme();
	}
}
