package com.cybrilla.urlshorten.sevice;


public class UrlService {

	private static final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private char[] allowedCharacters = allowedString.toCharArray();
    private int base = allowedCharacters.length;

    public String encode(long input){
    	StringBuilder encodedString = new StringBuilder();

        if(input == 0) {
            return String.valueOf(allowedCharacters[0]);
        }

        while (input > 0) {
            encodedString.append(allowedCharacters[(int) (input % base)]);
            input = input / base;
        }

        return encodedString.reverse().toString();
    }

    public long decode(String shortURL) {
    	int id = 0; // initialize result
    	
		// A simple base conversion logic
		for (int i = 0; i < shortURL.length(); i++)
		{
			if ('a' <= shortURL.charAt(i) &&
					shortURL.charAt(i) <= 'z')
			id = id * 62 + shortURL.charAt(i) - 'a';
			if ('A' <= shortURL.charAt(i) &&
					shortURL.charAt(i) <= 'Z')
			id = id * 62 + shortURL.charAt(i) - 'A' + 26;
			if ('0' <= shortURL.charAt(i) &&
					shortURL.charAt(i) <= '9')
			id = id * 62 + shortURL.charAt(i) - '0' + 52;
		}
		return id;
    }
}
