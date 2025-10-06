# 🏢 ms-restaurant

**Application Name:** `ms-restaurant`  
**Description:** Restaurant Management Microservice

---

## 📘 Ümumi Məlumat

Bu layihə **Restaurant Management Microservice** kimi fəaliyyət göstərir və restoran məlumatlarının idarə olunmasını təmin edir.  
Layihə **microservice arxitekturası**na əsaslanır və digər servislərlə — məsələn, **ms-menu** və **ms-notification** — inteqrasiya olunur.

---

## ⚙️ Əsas Məntiq

### 🧩 Restaurant
- Restoranların **yaradılması**, **yenilənməsi**, **silinməsi** və **siyahılanması** (CRUD əməliyyatları).
- Yeni restoran yaradıldıqda məlumat həm **PostgreSQL** bazasına yazılır, həm də **Kafka** üzərindən event şəklində yayımlanır.
- **Soft delete** tətbiq olunur — restoran silindikdə `status = DELETED`, amma məlumat bazadan tam silinmir.

### 🚀 Kafka Integration
- Yeni restoran yaradıldıqda `restaurant.created` adlı **Kafka topic**-ə event göndərilir.
- Bu event digər servislər, xüsusən də **ms-notification** tərəfindən dinlənilir və məsələn, email göndərilməsi kimi hadisələr baş verir.

### ⚡ Redis Caching
- `findAll()` və `findById()` əməliyyatları **Redis cache** üzərindən işləyir.
- Məlumat dəyişdikdə (create/update/delete) cache avtomatik yenilənir.
- Bu, performansı artırır və DB-yə olan sorğuların sayını azaldır.

### 🧱 Liquibase Integration
- DB struktur dəyişiklikləri üçün `changelog-master.yml` vasitəsilə **Liquibase** istifadə olunur.
- Versiyalaşdırma və bazada dəyişikliklərin izlənməsi asanlaşdırılır.

---

## 🛠️ İstifadə Olunan Texnologiyalar

| Texnologiya | Məqsəd |
|--------------|--------|
| **Spring Boot 3** | Framework və tətbiq əsas infrastrukturu |
| **Spring Data JPA** | ORM vasitəsilə DB əməliyyatları |
| **PostgreSQL** | Əsas verilənlər bazası |
| **Kafka** | Event-driven arxitektura və microservice əlaqəsi |
| **Redis** | Caching mexanizmi |
| **Liquibase** | Database migration və changelog idarəetməsi |
| **Lombok** | Kodun sadələşdirilməsi (Getter, Setter, Builder və s.) |
| **Docker** | PostgreSQL, Kafka və Redis konteynerlərinin idarəsi |

---

## 🔁 İş Axını (Workflow)

1. **Yeni restoran yaradılır**  
   → Request gəlir → DB-yə yazılır → Kafka topic-ə event göndərilir.

2. **ms-notification** servisi həmin event-i dinləyir  
   → Məlumatı oxuyur → Gmail SMTP vasitəsilə bildiriş göndərir.

3. **Redis Cache** aktiv olur  
   → Restoran məlumatları cache-ə əlavə edilir → `findAll` və `findById` sorğuları daha sürətli cavablanır.

---

## 🧠 Niyə belə dizayn seçilib?

- **Performans:** Redis cache sayəsində çox sorğulanan məlumatlar tez cavablanır.
- **Asinxron əlaqə:** Kafka event sistemi ilə servislər bir-birindən asılı olmadan işləyə bilir.
- **Yüksək etibarlılıq:** Liquibase DB versiyalarını təhlükəsiz idarə etməyə imkan verir.
- **Məlumat itkisinə qarşı:** Soft delete sayəsində tarixçə itmir.

---

## ✅ Nəticə

`ms-restaurant` servisi restoran məlumatlarını idarə edir, Kafka ilə eventləri göndərir və Redis cache vasitəsilə performansı optimallaşdırır.  
Bu layihə **event-driven**, **cached** və **scalable microservice** yanaşmasının praktiki nümunəsidir.
