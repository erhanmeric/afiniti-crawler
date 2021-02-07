package com.afiniti.crawler;

import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;


public class WebCrawler {
	private PrintStream outputStream;
	private LinkExtractor linkExtractor;
	
	public WebCrawler(PrintStream outputStream) {
		this.outputStream = outputStream;
		this.linkExtractor = new LinkExtractor();
	}

	public void crawl(String rootUrl) throws URISyntaxException {
		Set<String> urlSet = new HashSet<>();
		Queue<String> urlQueue = new LinkedList<>();
		
		urlSet.add(strip(rootUrl));
		urlQueue.add(rootUrl);
		
		String rootDomain = getDomainName(rootUrl);
		
		while (!urlQueue.isEmpty()) {
			String url = urlQueue.poll();
			List<String> linkList = linkExtractor.extract(url);
			
			printLinks(url, linkList);
			
			List<String> domainLinkList = filterForDomain(rootDomain, linkList);
			
			for (String domainLink : domainLinkList) {
				if (urlSet.add(strip(domainLink))) {
					urlQueue.add(domainLink);
				}
			}
		}
	}

	private String strip(String link) {
		if (link.startsWith("http://")) {
			link = link.substring(7);
		}
		
		if (link.startsWith("https://")) {
			link = link.substring(8);
		}

		if (link.startsWith("www.")) {
			link = link.substring(4);
		}

		return link;
	}

	private List<String> filterForDomain(String rootDomain, List<String> linkList) {
		List<String> domainLinkList = linkList.stream().filter(p -> checkDomain(p, rootDomain)).collect(toList());
		return domainLinkList;
	}

	private boolean checkDomain(String url, String rootDomain) {
		String domain;
		
		try {
		    URI uri = new URI(url);
		    String scheme = uri.getScheme();
		    
		    if (!scheme.equals("http") && !scheme.equals("https")) {
		    	return false;
		    }
		    
		    domain = uri.getHost();
		    domain = domain.startsWith("www.") ? domain.substring(4) : domain;
		} catch (URISyntaxException e) {
			return false;
		} 
		
		return domain.endsWith(rootDomain);
	}

	private void printLinks(String url, List<String> linkList) {
		final String line = linkList.stream()
	            .collect(joining(" , ", url + " -> [", "]"));
		
		outputStream.println(line);
	}

	private static String getDomainName(String url) throws URISyntaxException {
	    URI uri = new URI(url);
	    String domain = uri.getHost();
	    return domain.startsWith("www.") ? domain.substring(4) : domain;
	}
}