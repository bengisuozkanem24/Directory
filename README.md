
# Directory Projesi

Directory Projesi, Spring Boot tabanlı bir mikroservis uygulamasıdır. Uygulama, kullanıcıların kişi bilgilerini ve iletişim bilgilerini yönetmesine olanak tanır. Ayrıca, kişilerin bulundukları konumlara göre raporlar oluşturabilir.

---

## **Proje Yapısı**

Proje, iki mikroservisten oluşmaktadır:
1. **Person Service**: Kişi ve iletişim bilgilerini yönetir.
2. **Report Service**: Raporlama taleplerini işleyip sonuçları döner.

---

## **Kullanılan Teknolojiler**
- **Java 17**
- **Spring Boot 3.x**
- **PostgreSQL**
- **Kafka** 
- **Maven** 
- **JUnit 5** ve **Mockito**

---

## **Kurulum ve Çalıştırma**

### **Adımlar**

1. **Depoyu Klonlayın**
   ```bash
   git clone https://github.com/username/directory.git
   cd directory
   ```

2. **Kafka'yı Docker ile Çalıştırın**
   Projede yer alan `docker-compose.yml` dosyasını kullanarak gerekli hizmetleri başlatın:
   ```bash
   docker-compose up -d
   ```

3. **Mikroservisleri Build Alın ve Çalıştırın**
   Her iki servisi ayrı ayrı build edip çalıştırabilirsiniz:
    - **Person Service:**
      ```bash
      cd person
      mvn clean install
      mvn spring-boot:run
      ```
    - **Report Service:**
      ```bash
      cd report
      mvn clean install
      mvn spring-boot:run
      ```

4. **Uygulama Testi**
   Uygulama testlerini çalıştırmak için:
   ```bash
   mvn test
   ```

---

## **API Endpointleri**

### **Person Service**
| HTTP Metodu | URL                          | Açıklama                       |
|-------------|------------------------------|--------------------------------|
| `POST`      | `/api/person`               | Yeni kişi ekler               |
| `GET`       | `/api/person`               | Tüm kişileri listeler         |
| `GET`       | `/api/person/{id}`          | Belirli ID ile kişiyi döner   |
| `DELETE`    | `/api/person/{id}`          | Kişiyi siler                  |
| `POST`      | `/api/person/{id}/contact-info`| Kişiye iletişim bilgisi ekler |
| `GET`       | `/api/person/{id}/contact-info`| Kişinin iletişim bilgilerini listeler |
| `DELETE`    | `/api/contact-info/{id}`    | İletişim bilgisini siler      |

### **Report Service**
| HTTP Metodu | URL                 | Açıklama                                 |
|-------------|---------------------|------------------------------------------|
| `POST`      | `/api/report`       | Yeni rapor talebi oluşturur             |
| `GET`       | `/api/report`       | Tüm raporları listeler                  |
| `GET`       | `/api/report/{id}`  | Belirli ID ile raporun detayını döner   |

---

## **Örnek Kullanım**

### **Yeni Bir Kişi Eklemek**
```bash
curl -X POST http://localhost:8080/api/person -H "Content-Type: application/json" -d '{
  "firstName": "Jane",
  "lastName": "Doe",
  "company": "ABC Company"
}'
```

### **Kişiye İletişim Bilgisi Eklemek**
```bash
curl -X POST http://localhost:8080/api/person/{id}/contact-info -H "Content-Type: application/json" -d '{
  "contactType": "PHONE",
  "content": "5301234567"
}'
```


