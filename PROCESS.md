## Dag 0 (Di 06-06-17)
#### 13:00-17:00 Proposal
Vandaag heb ik de proposal geschreven (zie [README.md](README.md)). Ik heb
opgeschreven voor wie de app bedoeld is, en de kenmerken van de app. Verder heb
ik enkele schetsen gemaakt:
![alt text](/doc/sketch0.png "Schets 1: Beginscherm, Navigatie, Spelersinfo")

![alt text](/doc/sketch1.png "Schets 2: Chat, Ranking, Kalender")


## Dag 1 (Wo 07-06-17)
#### 10:00-11:15 Daily Standup
App besproken met groepje (Gido, Emma, Julia). Ze vonden het een leuk idee.

Nieuwe suggesties:
* Op meerdere manieren spelersinformatie tonen, bijv. ook alfabetisch i.p.v.
alleen op ranking.
* Door op Twitter te klikken bij een speler, kan je misschien de laatste tweets
van de speler als een 'widget' zien (Twitter API lijkt noodzakelijk).

Deze suggesties vind ik zeer interessant, ook al heb ik besloten om ze alleen
toe te voegen indien ik tijd over heb.

Plan voor vandaag: Beginnen met app set-up in Android Studio, en werken aan
design.

#### 11:15-19:00 Design
Vandaag heb ik een betere schets gemaakt:
![alt text](/doc/design_sketch.png "Design schets")

Hierin heb ik met pijltjes aangegeven welke schermen met elkaar verbonden zijn,
welke activities ik nodig heb en wanneer en hoe ik data van buitenaf moet laden.
Verder heb ik een begin gemaakt aan het Design-document (zie [DESIGN.md](DESIGN.md)),
waarin ik dit verder heb uitgewerkt.

## Dag 2 (Do 08-06-17)
#### 10:00-11:00 Daily Standup
We hebben elkaars werk besproken. Ik heb de Design tekening van gisteren laten
zien en uitgelegd. De tekening was erg mooi, goed dat ik alles heb uitgewerkt.
Wel had ik mijn Home Screen beter in het midden van de tekening kunnen plaatsen,
omdat het dan, vooral met de pijltjes, wat overzichtelijker zou zijn.

Suggesties:
* Misschien i.p.v. In-Play alle wedstrijden van vandaag laten zien, aangezien
er bij darts vaak max. 1 wedstrijd tegelijk is en er niet heel veel wedstrijden
op een dag gespeeld worden.

Plan voor vandaag: Design-document af maken, prototype app maken met Android
Studio.

#### 11:00-15:00 Firebase
Firebase toegevoegd aan project, database aangepast (geldig voor Firebase gemaakt
  en aangepast aan de SofaScore API) en afbeeldingen (countryFlights) gemaakt en
  geüpload.
Voorbeelden: <br>
![alt text](https://firebasestorage.googleapis.com/v0/b/dartsworld-e9f85.appspot.com/o/Netherlands.png "Nederland") ![alt text](https://firebasestorage.googleapis.com/v0/b/dartsworld-e9f85.appspot.com/o/South%20Africa.png "Zuid-Afrika")
![alt text](https://firebasestorage.googleapis.com/v0/b/dartsworld-e9f85.appspot.com/o/Wales.png "Wales")

#### 15:00-22:00 Prototype
Vandaag heb ik alle activities die ik nodig denk te hebben aangemaakt in Android
Studio. Ik denk acht Activity classes nodig te hebben. In elke activity heb ik
de juiste buttons in de Action Bar geplaatst. Verder heb ik bij elke activity,
zoals in de schets van gisteren te zien is, het Hamburger-menu toegevoegd.
Helaas was ik erg lang bezig met een methode vinden om één Navigation Drawer te
maken die bruikbaar is voor alle activities. Uiteindelijk is dit me niet gelukt.
Wel heb ik nu één xml-bestand voor de Navigation Drawer, waar alle activities
heen worden geleid. Duplicaten code zijn er echter nog wel (in de classes).
Alle benodigde schermen zijn aanwezig, en de navigatie die ik voor het inladen
van de data had kunnen creëren, is werkend.
Tip van Renske: Inflater (oplossing voor volgende week).

#### 22:00-24:00 Design
Design document afgemaakt.

## Dag 3 (Vr 09-06-17)
Planning:
* Bettercodehub toevoegen aan README.md.
* Kalender maken
* Spelersinfo proberen te laden vanuit Firebase

#### 15:00-17:00 Presentaties

Planning weekend:
* AsyncTask + HttpRequestHelper maken, proberen SofaScore data op te halen en in
te laden.
* Data verwerken.

Planning volgende week:
* Beginnen aan chat (LoginActivity + ChatActivity schrijven + xmls).
