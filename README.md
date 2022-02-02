# Haushaltsbuch
Uebungsprojekt für einen Weiterbildungskurs
Projektdauer 17.01.2022 - 02.02.2022

Das Programm "Haushaltsbuch" ist dazu gedacht die Ausgaben von Wocheneinkäufen zu überprüfen.
Es ist eine REST-Schnittstelle vorhanden, welche Anfragen über GET und POST verarbeitet.
Man kann über die Registration neue Nutzer anlegen, wobei das Passwort verschlüsselt in einer DB gespeichert wird.
Jeder Nutzer kann Produkte und Kategorien hinzufügen. Diese gelten nur für den jeweiligen Account und werden dem Nutzer in einer Tabelle angezeigt.
Man kann sich die Ausgaben der aktuellen Woche in einem Tortendiagramm anzeigen lassen.

Die angeforderten Webseiten, bestehend aus HTML und JavaScript, werden vom Server generiert und an den Client geleitet.
Anfragen des Clients an den Server werden über das HTML Form-Konstrukt oder Json realisiert.

Die Validierung der Eingaben erfolgt Client- und Server-Seitig
Es wurden zu verschiedenen Methoden Unit-Tests geschrieben.

Genutzte Sprachen:

Java
HTML
JavaScript

Genutzte Frameworks:

SpringBoot - für die REST Schnittstelle
SpringSecurity - um die Seitenaufrufe auf eingeloggte Nutzer zu beschränken, ohne Login ist nur die Registrationsseite erreichbar
Hibernate - ORM Framework, einfacher Aufbau der Datenbank und zugehöriger Query-Anfragen
javax - Framework um die Serverseitige Validierung von Attributen von zu vereinfachen
Thymeleaf - Template Engine, vereinfacht die Erzeugung von Webseiten
Plotly.js - erlaubt das einfach erstellen von Diagrammen in Webseiten  


zeitlich nicht geschaffte Features:
- Anzeige der Ausgaben zu verschiedenen Zeiten (vorige Woche, ganzer Monat, voriger Monat, ...)
- Reduzierung der Menge vorhandener Produkte um ein Warenbestandssystem zu ermöglichen
- entfernen von Produkten und Kategorien durch den Nutzer
