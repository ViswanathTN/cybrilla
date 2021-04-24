package com.cybrilla.urlshorten.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cybrilla.urlshorten.model.UrlLink;

@Controller
@RequestMapping("/")
public class UrlShortenController {
	@RequestMapping(method=RequestMethod.GET,value="/urls")
	public String getShortenURL(Model model) {
		List<UrlLink> urlList=new ArrayList<UrlLink>();
		UrlLink urlobj=new UrlLink();
		urlobj.setUrl("www.goole.com");
		urlobj.setShortUrl("12345");
		urlList.add(urlobj);
		model.addAttribute("urls",urlList);
		return "shortenUrl";
		
	}

}
