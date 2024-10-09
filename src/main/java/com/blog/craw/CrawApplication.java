package com.blog.craw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.blog.craw.service.WebContentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class CrawApplication {

	public static void main(String[] args) {
		ApplicationContext context =
			SpringApplication.run(CrawApplication.class, args);

		// WebContentService 인스턴스를 가져와서 updateReadme() 메소드 호출
		WebContentService webContentService = context.getBean(WebContentService.class);

		try {
			webContentService.updateReadme();
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			// 작업 완료 후 Spring의 Bean과 자원 정리 및 애플리케이션 종료
			SpringApplication.exit(context, () -> 0);
		}
	}
}
