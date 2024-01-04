# services

- Proje indirildikten sonra bilgisayarda maven paketi kurulu olmalıdır ve projenin derlenmesi için 
`mvn clean install -Dmaven.test.skip=true` kodu ilgili proje dizininde terminalde çalıştırılmalıdır.
- product-service servisi Redis bağımlılığına sahip olduğu için Redis bilgisayarda kurulu olmalı ve 
`redis-server` komutuyla ayağa kaldırılmalıdır.
- Servisler `PostgreSQL` veritabanını kullandığı için bu veritabanı sunucusu da lokal bilgisayarda kurulu olmalıdır.
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

# product service curl komutları
- Servisin çalışır durumda olup olmadığının test edilmesi için `api/v1/products/isUp` endpointi için aşağıdaki 'curl' komutu çalıştırılabilir. 
Sonuç `true` döndüğü takdirde servisin çalışıyor olduğu anlaşılabilir.
```
    curl http://37.148.213.195:8080/api/v1/products/isUp
```
- Product tablosuna kayıt eklemek için `api/v1/products` endpointine POST isteği gönderen bir 'curl' komutu çalıştırılabilir. 
```
    curl -i -X POST 37.148.213.195:8080/api/v1/products \
        -H 'Content-Type: application/json' \
        -d '{"productId": "91f802e5-1b44-411a-953e-32e541bd5de7", "name": "TV", "price": 35000, "status": true}'
```
istek başarılı olduğu takdirde aşağıdaki sonuç dönecektir.
```
    HTTP/1.1 201 
    Location: http://37.148.213.195:8080/api/v1/products/91f802e5-1b44-411a-953e-32e541bd5de7
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date
    {
        "success": true,
        "message": "The Product Has Been Successfully Added!",
        "error": null, 
        "data": {
            "productId": "91f802e5-1b44-411a-953e-32e541bd5de7", 
            "name": "TV",
            "price": 35000,
            "status": true
        }
    }
```
- ID bilgisine göre product bilgisi alabilmek için `api/v1/products/{productId}` endpointine GET isteği gönderen bir 'curl' komutu çalıştırılmalıdır. 
```
    curl -i 37.148.213.195:8080/api/v1/products/91f802e5-1b44-411a-953e-32e541bd5de7
```
istek başarılı olduğunda aşağıdaki sonuç dönecektir.
```
    HTTP/1.1 200 
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: 
    {
        "success": true,
        "message": "Product Has Been Successfully Retrieved!",
        "error": null, 
        "data": {
            "productId": "91f802e5-1b44-411a-953e-32e541bd5de7", 
            "name": "TV",
            "price": 35000,
            "status": true
        }
    }
```
- `api/v1/products` endpointine GET isteği gönderen bir 'curl' komutu aşağıdaki şekilde olmalıdır ve tüm productları başarılı bir şekilde listelediği görülmeldir. 
```
    curl -i 37.148.213.195:8080/api/v1/products
```
- `api/v1/products` endpointine DELETE isteği gönderen bir 'curl' komutu aşağıdaki şekilde gönderilebilir ve başarılı olduğu takdirde aşağıdaki gibi sonuç döndürür. 
```
    curl -i -X DELETE 37.148.213.195:8080/api/v1/products/91f802e5-1b44-411a-953e-32e541bd5de7
```
```
    HTTP/1.1 200 
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: 
    {
        "success": true,
        "message": "The Product Has Been Deleted Successfully!",
        "error": null, 
        "data": {
            "productId": "91f802e5-1b44-411a-953e-32e541bd5de7", 
            "name": null,
            "price": 0,
            "status": false
        }
    }
```
# bill service curl komutları
- Servisin çalışır durumda olup olmadığının test edilmesi için `api/v1/auth/isUp` endpointi için aşağıdaki 'curl' komutu çalıştırılabilir. 
Sonuç `true` döndüğü takdirde servisin çalışıyor olduğu anlaşılabilir.
```
    curl http://37.148.213.195:8090/api/v1/auth/isUp
```
- Sisteme kullanıcı kaydı için `api/v1/auth/register` endpointine POST isteği gönderen aşağıda örnek verilen 'curl' komutu çalıştırılmalıdır. 
```
    curl -i -X POST 37.148.213.195:8090/api/v1/auth/register \
        -H 'Content-Type: application/json' \
        -d '{"userId": "f126c53b-1752-4030-9656-77af00373369", "firstName": "John", "lastName":"Doe", "email":"user@domain.com", "password":"123"}'
```
istek başarılı olduğu takdirde aşağıdaki sonuç dönecektir.
```
    HTTP/1.1 201 
    Vary: Origin
    Vary: Access-Control-Request-Method
    Vary: Access-Control-Request-Headers
    X-Content-Type-Options: nosniff
    X-XSS-Protection: 0
    Cache-Control: no-cache, no-store, max-age=0, must-revalidate
    Pragma: no-cache
    Expires: 0
    X-Frame-Options: DENY
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date:
    {
        "success": true,
        "message": "The User Has Been Successfully Registered!",
        "error": null, 
        "data": {
            "fullName": "John Doe", 
            "email": "user@domain.com"
        }
    }
```
- Kullanıcı kaydı sonrası sisteme giriş yapabilmek için `api/v1/auth` endpointine POST isteği gönderen 'curl' komutu çalıştırılmalıdır. 
```
    curl -i -X POST 37.148.213.195:8090/api/v1/auth \
        -H 'Content-Type: application/json' \
        -d '{"email":"user@domain.com", "password":"123"}'
```
istek başarılı olduğu takdirde sonuç aşağıdaki gibi olacaktır. Data bölümünde dönen AuthToken secure endpointlere istek yaparken kullanılacaktır. RefreshToken ise AuthToken expire olduğunda kullanıcının yeniden login işlemi yapmasına gerek olmadan yeni bir AuthToken alınmasına olanak sağlayacaktır.
```
    HTTP/1.1 200 
    Vary: Origin
    Vary: Access-Control-Request-Method
    Vary: Access-Control-Request-Headers
    X-Content-Type-Options: nosniff
    X-XSS-Protection: 0
    Cache-Control: no-cache, no-store, max-age=0, must-revalidate
    Pragma: no-cache
    Expires: 0
    X-Frame-Options: DENY
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date:
    {
        "success": true,
        "message": "Login Succeeded!",
        "error": null, 
        "data": {
            "authToken": "eyJ....", 
            "refreshToken": "eyJ...."
        }
    }
```
- Kullanıcı sisteme giriş yaptıktan sonra fatura ekleme işlemi için `api/v1/bills` endpointine POST isteği gönderen 'curl' komutu çalıştırılmalıdır. 
```
    curl -i -X POST 37.148.213.195:8090/api/v1/bills \
        -H 'Content-Type: application/json' \
        -H 'Authorization: Bearer eyJ...'
        -d '{"billId":"2b7b59ad-fd07-4cd5-bb2f-d538fbb9fe8e", "firstName": "John", "lastName": "Doe", "email": "user@domain.com", "amount": 500, "productName": "TV"}'
```
istek başarılı olduğu takdirde sonuç aşağıdaki gibi olacaktır. Data bölümünde dönen AuthToken secure endpointlere istek yaparken kullanılacaktır. RefreshToken ise AuthToken expire olduğunda kullanıcının yeniden login işlemi yapmasına gerek olmadan yeni bir AuthToken alınmasına olanak sağlayacaktır.
```
    HTTP/1.1 200 
    Vary: Origin
    Vary: Access-Control-Request-Method
    Vary: Access-Control-Request-Headers
    X-Content-Type-Options: nosniff
    X-XSS-Protection: 0
    Cache-Control: no-cache, no-store, max-age=0, must-revalidate
    Pragma: no-cache
    Expires: 0
    X-Frame-Options: DENY
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date:
    {
        "success": true,
        "message": "The Bill Has Been Successfully Created!",
        "error": null, 
        "data": {
            "billId": "2b7b59ad-fd07-4cd5-bb2f-d538fbb9fe8e", 
            "firstName": "John", 
            "lastName": "Doe", 
            "email": "user@domain.com", 
            "amount": 500, 
            "productName": "TV",
            "valid": true
        }
    }
```
- Tüm faturaları görüntüleyebilmek için `api/v1/bills` endpointine istek yapacak olan 'curl' komutu aşağıdaki gibi çalıştırılabilir. 
```
    curl -i -H 'Authorization: Bearer eyJ...' 37.148.213.195:8090/api/v1/bills         
```
