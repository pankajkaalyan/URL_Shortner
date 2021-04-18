# URL_Shortner

# This project is being created for generating tiny url from long url string.

1) For generating tiny links

POST REQUEST ->  http://[hostname]:[port]/urlShortner/v1/app

REQUEST BODY ->  {
					"url" : "somevalue",
					"expiryDate" : "somevalue"
				 }
				 
RESPONSE BODY ->  {
				    "originalUrl": "somevalue",
				    "shortUrl": "somevalue",
				    "expiryDate": "somevalue"
				  }

example: 

http://localhost:8080/urlShortner/v1/app

Request :

{
	"url" : "https://www.youtube.com/watch?v=KSX4cwWRzis",
	"expiryDate" : ""
}

Response :

{
    "originalUrl": "https://www.youtube.com/watch?v=KSX4cwWRzis",
    "shortUrl": "e76fdacc",
    "expiryDate": "2021-04-19T01:16:42.16"
}

2) Using tiny links

GET REQUEST -> http://[hostname]:[port]/urlShortner/v1/app/{shortUrl}

example:

localhost:8080/urlShortner/v1/app/e76fdacc

