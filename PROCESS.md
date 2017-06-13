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
<center>

| Nederland      | Wales    | Zuid-Afrika          |
| :------------: | :------------: | :------------: |
| ![alt text](https://firebasestorage.googleapis.com/v0/b/dartsworld-e9f85.appspot.com/o/Netherlands.png?alt=media "Nederland") | ![alt text](https://firebasestorage.googleapis.com/v0/b/dartsworld-e9f85.appspot.com/o/Wales.png?alt=media "Wales") | ![alt text](https://firebasestorage.googleapis.com/v0/b/dartsworld-e9f85.appspot.com/o/South%20Africa.png?alt=media "Zuid-Afrika")|

</center>

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

#### 11:00-12:00 Bettercodehub
Bettercodehub toegevoegd aan README (8/10), kleine wijzigingen in logboek.

#### 15:00-17:00 Presentaties
Presentatie gegeven, enkele vragen beantwoord maar geen negatieve feedback
ontvangen.
Tip van Jaap: Livescore bijv. eens per 10 sec refreshen.

Verder heb ik het volgende uitgevoerd:
* Ik heb een CalendarView in het Calendar-scherm geplaatst.
* Ik heb een HttpRequestHelper en AsyncTask gemaakt voor de SofaScore API.
De data kan nu binnengehaald worden (de data moet nog wel, eenmaal binnen,
  verwerkt worden).
* Ik heb een Player class aangemaakt, met alle informatie over een speler zoals
in de Firebase database.
* De zelfgemaakte data kan nu in de app bereikt worden d.m.v. Firebase.

##### De planning
Vandaag:
* Kalender maken
* Spelersinfo proberen te laden vanuit Firebase

Weekend:
* AsyncTask + HttpRequestHelper maken, proberen SofaScore data op te halen en in
te laden.
* Data verwerken.

Volgende week:
* Beginnen aan chat (LoginActivity + ChatActivity schrijven + xmls).

## Dag 4 (Ma 12-06-17)

#### 10:00-11:00 Daily Standup
Planning (vandaag en deze week):
* Livescores in app laden vandaag, kost best veel tijd om het precies goed te
doen, en layout mooi te maken.
* Nadenken over hoe vaak livescores inladen, waarschijnlijk 1x per 10 seconden
(of zelf kiezen).
* Scores van andere dagen ook proberen op te halen (voor de kalender).
* Spelersinformatie mooi maken

#### 11:00-17:00 Data laden
Ik heb de volgende dingen gedaan:
* Ik heb besloten om de livescores 1 keer per 10 seconden te refreshen. Verder
maak ik ook een refresh-button, als je daarop klikt kan je de score verversen.
Als je rechtsboven klikt bij de drie puntjes komt er een autorefresh optie.
Als je die aanvinkt, wordt de score automatisch per 10 seconden ververst. Zo
niet, dan moet je handmatig verversen.
* Ik ben vandaag toch begonnen met data ophalen vanuit mijn eigen database, omdat
er momenteel geen darts gespeeld wordt en ik geen livescores kan zien. De data
is opgehaald, omdat mij dit in eerste instantie niet meer lukte, heb ik een
instructievideo gevolgd. <br>
Ik heb gebruik gemaakt van een PlayerProperty class,
die een id en een waarde bevat. Elk item van de database heeft een id en een
waarde, deze wordt hiermee gestructureerd. Verder heb ik een PropertyListAdapter
class gemaakt en twee xml-bestanden (player_item.xml en (adapter_view_player.xml).
Dit was nodig om meerdere kolommen in 1 list item te plaatsen. Uiteindelijk is
het gelukt, wanneer je nu klikt op Home -> Players, kan je momenteel tijdelijk
de spelersinformatie van Gary Anderson zien.

Dag 3                      |  Dag 4
:-------------------------:|:-------------------------:
![](/screenshots/Dag3.png) |  ![](/screenshots/Dag4.png)

## Dag 5 (Di 13-06-17)

#### 10:00-11:00 Daily Standup
Planning:
* Bij de spelersinformatie wil ik nog dat je bovenaan een foto te zien krijgt
van het land van de speler. Deze afbeeldingen zijn al gemaakt en geüpload in
Firebase Storage. Ik moet nog wel een manier vinden om deze afbeeldingen binnen
te laden en op het scherm te krijgen.
* Lijst met alle spelers maken. Als je op een speler klikt, zou je de
spelersinformatie te zien moeten krijgen.

#### 11:00-11:30 Bettercodehub
Geluisterd naar Bettercodehub presentatie van Julian Jansen.

#### 12:00-16:00 Players Activity
Ik heb de data geladen uit Firebase en een lijst met alle volledige spelersnamen
weergegeven op het scherm. Volgende stap: als je klikt op een speler, moet je
de informatie over de speler te zien krijgen.

Dag 5                      |  Dag 5
:-------------------------:|:-------------------------:
![](/screenshots/Dag5.png) |  ![](/screenshots/Dag5.png)

TODO: Sceenshots van vandaag.
