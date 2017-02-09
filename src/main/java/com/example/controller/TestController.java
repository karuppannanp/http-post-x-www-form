/**
 * 
 */
package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.http.HttpConnection;

/**
 * @author KXP8101
 *
 */
@RestController
public class TestController {
	
	@Autowired
	private HttpConnection httpConection;
	
	@RequestMapping(value = "getData", method = RequestMethod.GET)
	public String getData(
			@RequestParam("host") String host,
			@RequestParam("url") String url,
			@RequestParam("prgCode") String prgCode,
			@RequestParam("numberOfCards") String numberOfCards,
			@RequestParam("barCodeType") String barCodeType) throws Exception {
		return httpConection.postRequest(host, url, prgCode, numberOfCards, barCodeType);
	}
}
