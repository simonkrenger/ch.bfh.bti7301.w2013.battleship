----------------------------------------------------------------------------------
# BRAINSTORMING 2013-09-19
----------------------------------------------------------------------------------

### Fragen:
---------------------------

* Daten, Präsentation zwischenpräsentation.
* methode vorgegeben? relevant für präsentation?
* gehst es ausschliesslich um das Produkt oder auch um das Wie?
* UML ?? was?


----------------------------------------------------------------------------------
### Themen Freitags Meeting:
---------------------------

Was ist gegangen?
Code Walk Trough
Problem Diskussion
Battleship jeder gegen jeden!
Fragen an Dozenten

----------------------------------------------------------------------------------

Grobe Aufteilung der Aufgaben:
---------------------------

Simu AI, Meyer Graphik, Fränzi Kommunikation

----------------------------------------------------------------------------------

Initial Meeting:
---------------------------

Project Setup, Brainstorming, Project Administration, General Task Asignement.


Pretasks:
Diary
Projectlog


Task1:
Simon: GitProject
Meyer: Initial Project
Alle: Scrum Anschauen



Use cases:

Search Oponent
Schiffe setzte
darstellen vom gegner
piu piu
schiff remove
loose/ win
hiscore
history
chat
speed chess /timer
define ships
Themes
Spiel Abspeicher (Checksum)
Find Computer oponent

ToDo
BattleShip Film Schauen.

----------------------------------------------------------------------------------

UML
Context Model
Use Case Diagram
Activity Diagramm
Zustandsdiagramm (Speichern)
Domain Model
Sequence Diagramm
CCRC-Karten
Class Diagramm
Package Diagramm



----------------------------------------------------------------------------------
BRAINSTORMING 2013-09-20
----------------------------------------------------------------------------------

ToDo / UML / Documents / 
---------------------------
Vision (Requirements)(Context Model)
Zeitplan (Excel)
Use Cases
-bis Freitag 27-09-2013

Work
---------------------------

--//---Vision Draft---\\---
--//------------------\\---

Vision
--------------------
Intorduction
-------------------
Im rahmen des Moduls "Projekt 1" wird das bekannte Spiel "Schiffeversenken (engl. Battleship)" implementiert.
Ein genauer Spielbeschrieb findet sich auf Wikipedia.(1)

(1)http://de.wikipedia.org/wiki/Schiffe_versenken


Managament Summary
-------------------

Das vorliegende Dokument beschreibt die Anforderungen an das "Battleship" Spiel und dient weiter zur grundlegenden Definition der Systemarchitektur und der verwendeten Systemmodelle.

Das Projektteam besteht aus drei Personen, namentlich: Simon Krenger, Christian Meyer, Franziska Corradi.

Das Projekt soll gemäss UML umgesetzt werden. 


Spiel Beschreibung:
-----------------------------
CLJ1-HS13-2

Titel: Ship sunk game simulation

Beschreibung: The aim of the project is to implement the ship sunk game on the PC/Notebook. The issues of defining the contents and dimensions of the game area and the visual and acoustical effects are part of the project.

Gruppengrösse: 2-3

Anzuwendende Technologien: UML, any of the following programming languages: C/C++/Java.

Link: ---

Betreuer: Jean-Pierre Caillot (clj1@bfh.ch)

Systemspezifikation
--------------------

Das Projekt besteht hauptsächchli aus drei Teilen. Graphische Darstellung, Spiellogik und Datenkommunikation.

Als Programmiersprache kommt Java zum Einsatzt. Die Kommunikation soll direkt zwischen den Spielpartnern Stattfinden und daher ohne Server auskommen. 

--//------------------\\---
--//------------------\\---




--//---Requirements---\\---
--//------------------\\---

Non Funktional Requiremenst

Portabilität
Stabilität 
Fehlertoleranz Netzwerk 
Flüssig
Graphisch ansprechende Darstellung


Functional Requirements

1. Darstellung Spielfelder
   Es sollen zwei Quadratische Spielfelder (10x10) angezeigt werden. Das eigene und das gegnerische.
	
2. Platzeren von Schiffen gemässe Vorgaben
   Auf dem eigenen Spielfeld müssen die Vorgegebenen Schiffe platzert, verschoben und gedreht werden 	     können. Die Platzierung muss den Regeln entsprechen.

3. Verbinden mit einem Gegenspieler
   Ein Gegenspieler muss über das Netzwerk gefunden werden können. Zur Suche muss auch die eigene IP-Adresse dargestellt werden (Benutzerfreundlichkeit) 

4. Durchführen des Spiles
   Es soll möglich sein, via Eingabe einen schuss auf das gegnerische Spielfeld abzugeben. Es findet eine  Rückmeldung statt, ob ein schiff getroffen oder gar verenkt wurde. Treffer und Fehlschüsse werden Visuell differenziert auf dem Gegnerischen Spieufeld Angezegt. Die Schussabgaben des Gegners werden auf dem Eigenen SPielfeld angezeigt. Wenn eine partei alle gegnerischen schiffe verenkt hat, wird eine nachricht über den Sieg/Verlust angezeigt und das spiel ist beendet.
  

Additional Features
-------------------


1. Zusätzliche Anzeigefeautures
   Eigenes und gegnerisches Schiffs Inventar
   Graphische Hilfestellung für nicht belegbare Felder auf dem Gegnerischen Spielfeld

2. Computer Gegner (AI)ge
   Solle kein Menschlicher Spieler verffügbar sein, kann das Spiel gegen einen "Programmierten" programmierten Gegner gespielt werden. 
   

3. Auto- Search Gegner im eigenen Netz
   Es soll eine Option zur verfügung gestellt werden, um im Eigenen Netzwerk nach verfügbaren gegnern zu   suchen. 



Optional Feautures
---------------------
Features welche zu einem Späteren Zeitpunkt zusätzlich implementiert werden können.

- Chat Funktion 
- Visuelle Themas
- Spiechern es Spielsatatus
- High Score  / Hisotry 
- Eigene Schiffskompositionen
- Speed Battleship (analog Speed chess)
- 3D Varante



1. Kommunikation

Solution Overview
1. Kontext Model
2. Systemarchitektur
3. Programmiersprache

Mayor Feautures

--//------------------\\---
--//------------------\\---


Hausaufgaben
-------------------------------
Simu: Projektplan
Meyer: UseCases
Fränzi: Vision


# ----------------------------------------------------------------------------------
# Ideen 2013-09-20 - Corradi
# ----------------------------------------------------------------------------------

* Ein Ideen und ein Todo Dokument wäre cool, Fas finden Meyer und Krenger?
* Produktbacklog nicht Vergessen.
* Der Highscore könnte ins Diary?

# ----------------------------------------------------------------------------------


# ----------------------------------------------------------------------------------
# Teammeeting 2013-09-27
# ----------------------------------------------------------------------------------

## Verbindungsablauf & Spielstart:

 * Verbindungsdaten eingeben
 * connect klicken
 * Ein Butten mit Label Ready wird aktiviert.
 * Sobald der gegenspieler Ready Clickt welchselt der Button in Play!
 
 
## ToDo bis Mi 03.10.2013 00:00 
  
  * Burn-down chart / Product Backlog(Krenger)
  * Use Case Netzunterbruch (Meyer) 
  * Plannung: wer macht wann was. (Team) 
  * Domain Model / Use Case Diagram / Activity Diagramm (Fränzi)
  * Klassen Diagramm (Fränzi)
  * Sprint 1 planen (Team)
  * CRC-Karten (Meyer)
  

# ----------------------------------------------------------------------------------
# Teammeeting 2013-10-04
# ----------------------------------------------------------------------------------


## Interfaces
  
  * to be defined in detail. Diskussion ist noch im gange.
  
## Notes
 
  * Aktive und Gültge IPv4 Addresse als liste > GUI


## ToDo bis Mi 10.10.2013 00:00 

  * Object Diagramm überarbeiten (+ bei methoden wegnehmen)  
    Ship > direction Attribut. (Wie genau machen wir das Schiff) (Fränzi)
    Missile (Cooridnates mit C)
  * Sequence Diagramm network (Fränzi)
  * Network interruption > Activity Diagram (Fränzi)
  * Grunsätzliche Klassen erstellen, Interfaces andenken. Anfordeungen für Schnittstellen finden. (Alle)


# ----------------------------------------------------------------------------------
# corradi 2013-10-04
# ----------------------------------------------------------------------------------

  * Wie vergleicht man ein Byte...???
  * In welcher form Genau möchte Meyer die IP Adressen?







  
  
  
 
