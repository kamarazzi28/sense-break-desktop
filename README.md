# Sense Break (Vision & Hearing Trainer Microservices Application)

## Projekt pro předmět B6B36NSS – Návrh softwarových systémů

---

## 📌 Cíl projektu

Cílem projektu je vytvořit desktopovou aplikaci pro trénink zraku a sluchu pro vývojáře a uživatele, kteří tráví dlouhý čas u počítače. Aplikace bude obsahovat cvičení jako např. sledování pohybující se tečky po různých trajektoriích a rozlišování zvukových signálů.

Projekt demonstruje použití mikroservisní architektury, základních návrhových vzorů a správného návrhu systému včetně dokumentace.

Projekt bude klást důraz na:
- návrh architektury s oddělenými službami
- jednoduché použití designových vzorů (např. komunikace mezi službami, autentizace)
- verzování projektu v GitLabu
- dokumentaci pomocí UML a specifikací požadavků

---

## 👤 Tým

- Jednotlivec: Kamilla Ishmukhammedova (@ishmukam)

---

## 🛠️ Technologie

- **Java 17**, **Spring Boot** (REST služby)
- **PostgreSQL** – pro ukládání uživatelů a výsledků cvičení
- **JavaFX** nebo jiné jednoduché GUI (front-end desktop aplikace)
- **RabbitMQ** – pro notifikace a asynchronní zpracování 
- **GitLab CI/CD** – základní pipeline pro build/test

---

## 🏛️ Architektura

- **Typ architektury:** Mikroservisní (oddělené backendové služby)
- **Komunikace:** REST API mezi desktopovou aplikací a službami
- **Komponenty:**
  1. **User Service** – správa uživatelů, registrace, přihlášení
  2. **Training Service** – logika zrakových a sluchových cvičení, ukládání výsledků
  3. **Result Viewer (Desktop GUI)** – JavaFX aplikace pro vizuální a sluchové testy
  4. **Notification Service** – připomenutí tréninku pomocí RabbitMQ

---

## ✨ Funkcionality

- Registrace a přihlášení uživatelů
- Spuštění tréninkových cvičení (sledování bodu, poslouchání tónů)
- Vyhodnocení a uložení výsledků
- Přehled historie a pokroku uživatele
- Notifikace připomínající trénink

---

## 📝 Dokumentace

- Funkční požadavky
- Nefunkční požadavky (rychlost odezvy, jednoduchost použití)
- Use-case scénáře
- UML diagramy:
  - Class Diagram (User, Session, Training)
  - Component Diagram
  - Sequence Diagram (registrace, spuštění cvičení)
- Architektura systému a interakce mezi službami

---

## ⚙️ Setup projektu

1. **Naklonuj repozitář:**
   ```bash
   git clone git@gitlab.fel.cvut.cz:ishmukam/sensebreak.git
   cd sensebreak
   ```
2. **Spusť backendové služby:**
   ```bash
   cd user-service
   mvn spring-boot:run
   ```
   ```bash
   cd training-service
   mvn spring-boot:run
   ```
3. **Spusť desktopovou aplikaci:**
   ```bash
   cd desktop-gui
   mvn javafx:run
   ```

---

## ✅ TODO seznam

### Krátkodobé úkoly
- [x] Zvolit název projektu a nastavit GitLab
- [x] Definovat cíle projektu
- [x] Sepsat základní funkční a nefunkční požadavky
- [ ] Navrhnout hlavní Use Cases
- [ ] Vytvořit první verze UML diagramů
- [ ] Připravit prezentaci pro Milník 1
- [ ] Vytvořit adresářovou strukturu a inicializační soubory pro každou službu
- [ ] Navrhnout základní API pro User Service a Training Service
- [ ] Spustit PostgreSQL databázi a připravit schéma tabulek

### Dlouhodobé úkoly
- [ ] Implementovat User Service
- [ ] Implementovat Training Service
- [ ] Navrhnout a vytvořit JavaFX GUI
- [ ] Integrovat REST komunikaci mezi GUI a službami
- [ ] Otestovat a ladit aplikaci
- [ ] Dokončit dokumentaci a diagramy
- [ ] Připravit finální prezentaci a odevzdání

---

## 📂 Struktura repozitáře

```plaintext
sensebreak/
├── user-service/
├── training-service/
├── desktop-gui/
├── docs/
└── README.md
```

---

## 📜 Licence

Projekt je určen pouze pro studijní účely v rámci kurzu B6B36NSS na FEL ČVUT.

---

