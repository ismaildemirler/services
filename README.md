# services

- Proje indirildikten sonra bilgisayarda maven paketi kurulu olmalıdır ve projenin derlenmesi için 
`mvn clean install -Dmaven.test.skip=true` kodu ilgili proje dizininde terminalde çalıştırılmalıdır.
- product-service servisi Redis bağımlılığına sahip olduğu için Redis bilgisayarda kurulu olmalı ve 
`redis-server` komutuyla ayağa kaldırılmalıdır.
- Servisler dokcer kullanılarak ayağa kaldırılmak isteniyorsa `docker-compose up --build` komutu kullanılarak aşağıdaki adreslerde çalışır halde oldukları görülebilir.
```
    product-service için "localhost:8080/swagger-ui/index.html" 
    bill-service için "localhost:8090/swagger-ui/index.html"
```
- Docker ortamında çalışılmak istenmiyorsa herhangi bir IDE kullanılarak da servisler ayağa kaldırılıp gerekli testler Postman veya Swagger yapısı kullanılarak gerçekleştirilebilir. Lokalde çalıştırıldığında Swagger adresleri aşağıdaki şekilde olacaktır:
```
    product-service için "localhost:8080/swagger-ui/index.html" 
    bill-service için "localhost:8090/swagger-ui/index.html"
```
- Halihazırda servisler aşağıdaki adreslerde çalışır durumdadır.
```
    product-service için "http://37.148.213.195:8080/swagger-ui/index.html" 
    bill-service için "http://37.148.213.195:8090/swagger-ui/index.html"
```