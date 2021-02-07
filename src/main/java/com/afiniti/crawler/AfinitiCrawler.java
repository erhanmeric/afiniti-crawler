package com.afiniti.crawler;

public class AfinitiCrawler {
	public static final String AFINITI_URL = "https://www.afiniti.com";
	
	public static void main(String[] args) throws Exception {
		WebCrawler crawler = new WebCrawler(System.out);
		crawler.crawl(AFINITI_URL);
	}
}

