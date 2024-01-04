# services

- Proje indirildikten sonra bilgisayarda maven paketi kurulu olmalıdır ve projenin derlenmesi için `mvn clean install -Dmaven.test.skip=true` kodu ilgili proje dizininde terminalde çalıştırılmalıdır.
- product-service servisi Redis bağımlılığına sahip olduğu için Redis bilgisayarda kurulu olmalı ve `redis-server` komutuyla ayağa kaldırılmalıdır.
- servisler dokcer kullanılarak ayağa kaldırılmak isteniyorsa `docker-compose up --build` komutu kullanılarak 
```
    product-service için `localhost:8080`
    bill-service için `localhost:8090`
```
adresinde çalışır halde olduğu görülmelidir.
