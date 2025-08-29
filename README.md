# Sense Break (Vision & Hearing Trainer) (EN)

## Project Overview
Sense Break is a desktop application designed to train vision and hearing for users who spend long hours at the computer.  
It provides simple exercises such as tracking a moving dot or distinguishing sound tones to help prevent sensory overload.  

The project is built on a modular monolith architecture with a separate notification microservice (Kafka).  
Key principles include:
- clean design and separation of concerns  
- use of design patterns (authentication, service communication)  
- documentation with UML and requirements specifications  
- scalability and maintainability  

## Technologies
- Java 17, Spring Boot (REST services)  
- PostgreSQL – storing users and training results  
- JavaFX – desktop GUI  
- Kafka – notifications and asynchronous messaging  
- Redis – cache layer  

## Architecture
**Architecture type:** Modular monolith + one microservice  

**Communication:**  
- Desktop application ↔ Backend: REST API  
- Backend ↔ Notification Service: Kafka  

**Components:**  
- Backend Service – User module, Training module, Auth Middleware  
- Notification Service – Kafka listener microservice  
- Desktop GUI – JavaFX desktop application  

## Features
- User registration and login  
- Launching training exercises  
- Evaluation and saving of results  
- User history and progress overview  
- Notifications reminding users about training  

## Documentation
- Functional and non-functional requirements  
- Use-case scenarios  
- UML diagrams:  
  - Class diagram (User, Session, Training)  
  - Component diagram  
  - Sequence diagram  

## Project Setup

1. **Clone the repository:**
   ```bash
   git clone 
   cd sensebreak
2. **Run the backend (modular monolith):**
   ```bash
   cd notification-service
   mvn spring-boot:run
3. **Run the notification microservice:**
   ```bash
   cd desktop-gui
   mvn javafx:run
4. **Run the desktop application (JavaFX):**
   ```bash
   cd desktop-gui
   mvn javafx:run

## Repository Structure
   
```plaintext
sensebreak/
├── backend-service/         # Modular backend: User module, Training module, Auth Middleware
│   ├── src/
│   ├── pom.xml
│   └── ...
├── notification-service/    # Standalone microservice for notifications (Kafka listener)
│   ├── src/
│   ├── pom.xml
│   └── ...
├── desktop-gui/             # JavaFX desktop application
│   ├── src/
│   ├── pom.xml
│   └── ...
├── docs/                    # Documentation, UML diagrams, specifications
│   ├── diagrams/
│   └── use-cases.md
└── README.md                # Main project description
```
## Licence

This project is intended for educational purposes only.

----------------------------------------------------------------------
# Sense Break (Vision & Hearing Trainer) (CZ)

## Cíl projektu
Aplikace je určena pro trénink zraku a sluchu uživatelů, kteří tráví dlouhý čas u počítače. Obsahuje cvičení jako sledování pohybující se tečky nebo rozlišování zvukových tónů, aby se předešlo přetížení smyslového vnímání.  

Projekt je postaven na architektuře modulárního monolitu se samostatnou mikroslužbou pro notifikace (Kafka).  
Důraz je kladen na:
- čistý návrh a oddělení zodpovědností  
- použití návrhových vzorů (autentizace, komunikace mezi službami)  
- dokumentaci pomocí UML a specifikací  
- rozšiřitelnost a udržovatelnost systému  

## Technologie
- Java 17, Spring Boot (REST služby)  
- PostgreSQL – ukládání uživatelů a výsledků  
- JavaFX – desktop GUI  
- Kafka – notifikace a asynchronní zprávy  
- Redis – cache vrstva  

## Architektura
**Typ architektury:** Modulární monolit + jedna mikroslužba  

**Komunikace:**  
- Desktopová aplikace ↔ Backend: REST API  
- Backend ↔ Notification Service: Kafka  

**Komponenty:**  
- Backend Service – User modul, Training modul, Auth Middleware  
- Notification Service – mikroslužba pro zpracování zpráv (Kafka listener)  
- Desktop GUI – JavaFX aplikace  

## Funkcionality
- Registrace a přihlášení uživatelů  
- Spuštění tréninkových cvičení  
- Vyhodnocení a ukládání výsledků  
- Historie a přehled pokroku  
- Notifikace připomínající trénink  

## Dokumentace
- Funkční a nefunkční požadavky  
- Use-case scénáře  
- UML diagramy:  
  - Class diagram (User, Session, Training)  
  - Component diagram  
  - Sequence diagram  

## Setup projektu

1. **Naklonujte repozitář:**
   ```bash
   git clone 
   cd sensebreak  
   ```
2. **Spusťte hlavní backend (modulární monolit):**
   ```bash
   cd backend-service
   mvn spring-boot:run
   ```
3. **Spusťte notifikační mikroslužbu:**
   ```bash
   cd notification-service
   mvn spring-boot:run
   ```
4. **Spusťte desktopovou aplikaci (JavaFX):**
   ```bash
   cd desktop-gui
   mvn javafx:run

## Struktura repozitáře

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

## Licence

Projekt je určen pouze pro studijní účely v rámci kurzu B6B36NSS na FEL ČVUT.

---


