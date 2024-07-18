## ABOUT

This project implements a multithreaded proxy server with caching capabilities using Java. The server handles multiple client requests concurrently and caches responses to improve performance for subsequent requests.

## Features
### Multithreading: 
Handles multiple client requests simultaneously using a fixed thread pool.
### LRU Cache: 
Caches responses with a Least Recently Used (LRU) eviction policy.
### HTTP Support: 
Supports basic HTTP GET and POST requests.



===============================================================

Sample HTTP GET request:

```
    GET /path/to/resource HTTP/1.1
    Host: www.example.com
    User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64)
    Accept: text/html,application/xhtml+xml
```

Sample HTTP POST request:

```
    POST /path/to/resource HTTP/1.1
    Host: www.example.com
    User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64)
    Accept: text/html,application/xhtml+xml
    Content-Type: application/x-www-form-urlencoded
    Content-Length: 13

    name=John+Doe
```

Sample HTTP Response:

```
    HTTP/1.1 200 OK
    Date: Sun, 07 Jul 2024 12:00:00 GMT
    Server: Apache/2.4.41 (Ubuntu)
    Content-Type: text/html; charset=UTF-8
    Content-Length: 1234

    <!DOCTYPE html>
    <html>
    <head>
        <title>Sample Response</title>
    </head>
    <body>
        <h1>Hello, World!</h1>
        <p>This is the content of the response...</p>
    </body>
    </html>
```
