package com.afiniti.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class LinkExtractor {
	
	public List<String> extract(String url) {
		Document doc;
		
		try {
			doc = Jsoup.connect(url).get();			
		}
		catch (IOException ex) {
			//Return an empty list for currently unreachable links for simplicity.
			return Collections.emptyList();
		}
		
		List<String> links = doc.select("a").eachAttr("abs:href");
				
		
		return unifyLinks(links);
	}

	private List<String> unifyLinks(List<String> links) {
		HashSet<String> linkSet = new HashSet<>();
	
		for (String link : links) {
			linkSet.add(link);
		}
		
		return new ArrayList<>(linkSet);
	}
}
