# Sense Break (Vision & Hearing Trainer Microservices Application)

## Projekt pro předmět B6B36NSS – Návrh softwarových systémů

---

## 📌 Cíl projektu

Cílem projektu je vytvořit desktopovou aplikaci pro trénink zraku a sluchu pro vývojáře a uživatele, kteří tráví dlouhý čas u počítače. Aplikace nabízí jednoduchá cvičení, jako je sledování pohybující se tečky nebo rozlišování zvukových tónů, s cílem předejít přetížení smyslového vnímání.

Projekt je postaven na architektuře modulárního monolitu, který zahrnuje oddělené moduly pro správu uživatelů, tréninkovou logiku a autentizaci. K tomu je připojena samostatná mikroslužba pro notifikace, která zpracovává asynchronní události pomocí Kafka.
Projekt demonstruje správné oddělení zodpovědností, základní návrhové vzory, dokumentaci pomocí UML a důraz na rozšiřitelnost a čistý návrh systému.


Projekt bude klást důraz na:
- návrh architektury s oddělenými službami
- jednoduché použití designových vzorů (např. komunikace mezi službami, autentizace)
- verzování projektu v GitLabu
- dokumentaci pomocí UML a specifikací požadavků

---

## 👤 Autorka

- Kamilla Ishmukhammedova (@ishmukam)

---

## 🛠️ Technologie

- **Java 17**, **Spring Boot** (REST služby)
- **PostgreSQL** – pro ukládání uživatelů a výsledků cvičení
- **JavaFX** GUI (front-end desktop aplikace)
- **Kafka** – pro notifikace a asynchronní zpracování
- **Redis** – cache vrstva pro urychlení přístupu k často čteným datům
- **GitLab CI/CD** – základní pipeline pro build/test

---

## 🏛️ Architektura

- **Typ architektury:** Modulární monolit + 1 samostatná mikroslužba
- **Komunikace:**
    - Desktopová aplikace ↔ Backend Service: REST API
    - Backend Service ↔ Notification Service: asynchronně přes Apache Kafka

- **Komponenty:**
    1. **Backend Service** – modulární aplikace obsahující:
        - **User modul** – registrace, přihlášení, profil
        - **Training modul** – spuštění a uložení tréninků
        - **Auth Middleware** – kontrola oprávnění a validace tokenů
    2. **Notification Service** – samostatná mikroslužba zpracovávající zprávy z Kafka a odesílající notifikace
    3. **Desktop GUI (JavaFX)** – desktopová aplikace pro uživatele

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
2. **Spusť hlavní backend (modulární monolit):**
   ```bash
   cd backend service
   mvn spring-boot:run
   ```
3. **Spusť notifikační mikroslužbu:**
   ```bash
   cd notification-service
   mvn spring-boot:run
   ```
4. **Spusť desktopovou aplikaci (JavaFX):**
   ```bash
   cd desktop-gui
   mvn javafx:run
   ```
---

## ✅ TODO seznam

### 🟢 Krátkodobé úkoly
- [x] Zvolit název projektu a nastavit GitLab
- [x] Definovat cíle projektu a architekturu 
- [x] Sepsat funkční a nefunkční požadavky
- [x] Navrhnout hlavní Use Case scénáře
- [x] Vytvořit první verze UML diagramů
- [x] Připravit prezentaci pro Milník 1
- [x] Vytvořit adresářovou strukturu a inicializační soubory pro backend, GUI a notifikace
- [x] Navrhnout základní REST API pro User modul a Training modul
- [x] Spustit PostgreSQL

### 🧩 Dlouhodobé úkoly
- [x] Implementovat User modul (registrace, přihlášení, profil)
- [x] Implementovat Training modul (spuštění tréninku, uložení výsledků)
- [x] Integrovat Auth Middleware (ověření tokenu, role)
- [ ] Implementovat Notification Service (Kafka listener, odesílání notifikací)
- [ ] Navrhnout a vytvořit JavaFX GUI (volba cvičení, zobrazení výsledků)
- [ ] Propojit REST komunikaci mezi GUI a backendem
- [ ] Pokrýt API testy (unit + integrační)
- [ ] Dokončit dokumentaci, aktualizovat diagramy a README
- [ ] Připravit finální prezentaci a odevzdání projektu


---

## 📂 Struktura repozitáře

```plaintext
sensebreak/
├── backend-service/         # Modulární backend: User modul, Training modul, Auth Middleware
│   ├── src/
│   ├── pom.xml
│   └── ...
├── notification-service/    # Samostatná mikroslužba pro notifikace (Kafka listener)
│   ├── src/
│   ├── pom.xml
│   └── ...
├── desktop-gui/             # JavaFX desktopová aplikace
│   ├── src/
│   ├── pom.xml
│   └── ...
├── docs/                    # Dokumentace, UML diagramy, specifikace
│   ├── diagrams/
│   └── use-cases.md
└── README.md                # Hlavní popis projektu
```
---

## 📜 Licence

Projekt je určen pouze pro studijní účely v rámci kurzu B6B36NSS na FEL ČVUT.

---
