package com.cybrilla.urlshorten.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.cybrilla.urlshorten.dataaccess.LongUrlRepository;
import com.cybrilla.urlshorten.dataaccess.UrlShortenRepository;
import com.cybrilla.urlshorten.model.UrlLink;
import com.cybrilla.urlshorten.sevice.UrlService;
import com.cybrilla.urlshorten.model.Url;

@Controller
@RequestMapping("/")
public class UrlShortenController {
	
	@Autowired
	private UrlShortenRepository urlShortenRepository;
	@Autowired
	private LongUrlRepository longUrlRepository;
	
	@RequestMapping(method=RequestMethod.GET,value="/urls")
	public String getShortenURL(Model model) {
//		List<UrlLink> urlList=new ArrayList<UrlLink>();
//		UrlLink urlobj=new UrlLink();
//		urlobj.setUrl("www.goole.com");
//		urlobj.setShortUrl("12345");
//		urlList.add(urlobj);
		List<UrlLink> urlList=urlShortenRepository.findAll();
		model.addAttribute("urls",urlList);
		return "shortenUrl";
		
	}
	@RequestMapping(method=RequestMethod.POST, value = "/urls", consumes = "application/json")
	public ResponseEntity<Object> longToShortUrl(@RequestBody Url url) {
		UrlLink urllink=new UrlLink();
		UrlService urlService= new UrlService();
		String converturl=url.getUrl();
		
		Url entity = longUrlRepository.save(url);
		System.out.println("entity.getId()"+entity.getId());
		String shortUrl=urlService.encode(entity.getId());
		urllink.setUrl(converturl);
		urllink.setShortUrl(shortUrl);
		urlShortenRepository.save(urllink);
		return ResponseEntity.ok().build();
		
		
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/urls/{shorturl}")
	public ResponseEntity<Object> shortToLongUrl(@PathVariable("shorturl") String shortUrlReq) {
		System.out.println("shortUrlReq - "+shortUrlReq);
		UrlService urlService= new UrlService();
		long originalId=urlService.decode(shortUrlReq);
		System.out.println("Optional-id"+originalId);
		originalId +=1;
		System.out.println("Optional-id"+originalId);
		
		Optional<UrlLink> urllink=urlShortenRepository.findById(originalId);
		if(urllink.isPresent()) {
			UrlLink getUrlData=urllink.get();
			String urlstr=getUrlData.getUrl();
			System.out.println("Optional-urllink.get()"+getUrlData.getUrl());
			return ResponseEntity.status(HttpStatus.FOUND)
	                .location(URI.create(urlstr))
	                .build();

		}else {
			
			return ResponseEntity.badRequest().build();

		}
//		System.out.println("Optional-urllink"+urllink);
//		System.out.println("optional_1"+urllink.toString());
		
		
	}

	@RequestMapping(method=RequestMethod.POST, value = "/shorturl", consumes = "application/json")
	public ResponseEntity<Object> shortToLongUrl(@RequestBody Url url) {
		
		UrlService urlService= new UrlService();
		String shorturl=url.getUrl();
		long originalId=urlService.decode(shorturl);
		System.out.println("Optional-id"+originalId);
		originalId +=1;
		System.out.println("Optional-id"+originalId);
		
		Optional<UrlLink> urllink=urlShortenRepository.findById(originalId);
		if(urllink.isPresent()) {
			UrlLink getUrlData=urllink.get();
			String urlstr=getUrlData.getUrl();
			System.out.println("Optional-urllink.get()"+getUrlData.getUrl());
			return ResponseEntity.status(HttpStatus.FOUND)
	                .location(URI.create(urlstr))
	                .build();

		}else {
			
			return ResponseEntity.badRequest().build();

		}
//		System.out.println("Optional-urllink"+urllink);
//		System.out.println("optional_1"+urllink.toString());
		
		
	}
}
