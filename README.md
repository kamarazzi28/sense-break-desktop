# Sense Break (Vision & Hearing Trainer)

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
   mvn 

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


