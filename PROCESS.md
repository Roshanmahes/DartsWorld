# Logboek

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

Dag 3: Een kalender        |  Dag 4: Spelersinformatie
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

#### 12:00-17:00 Players Activity
Ik heb de data geladen uit Firebase en een lijst met alle volledige spelersnamen
weergegeven op het scherm. Wanneer je klikt op een speler, krijg je de
desbetreffende spelersinformatie te zien.

Dag 5: Een lijst van 70 spelers   | Dag 5: Informatie over een speler
:-------------------------:|:-------------------------:
![](/screenshots/Dag5.png) |  ![](/screenshots/Dag5.1.png)

TODO (voor beta-versie):
* Ik moet nog wel even kijken wat ik ga
doen meet bepaalde niet-ingevulde eigenschappen. Voor een voorbeeld: zie
hieronder (Majors 0 kan nog, maar Highest average 0.0 niet).
* De afbeelding van het land moet per speler nog toegevoegd worden.

## Dag 6 (Wo 14-06-17)
#### 10:00-11:00 Daily Standup
Style guide gemaakt.

#### 11:00-17:00 CalendarActivity
De CalendarActivity bestaat nu uit een CalendarView. Wanneer je een datum
selecteert, wordt de juiste datum naar de Log geprint. Deze wordt doorgestuurd
naar de DateAsyncTask, die de data van de juiste dag via de HttpRequestHelper
ophaalt. De json-data die binnenkomt, moet nog verwerkt worden.

## Dag 7 (Do 15-06-17)
#### 10:00-10:30 Daily Standup
Style guide nog eens kort besproken.

#### 10:30-17:00
De scores per dag worden nu via de CalendarActivity opgehaald en weergegeven op
het scherm. Wel is het zo dat er soms ook wedstrijden van de vorige/volgende dag
op een dag te zien zijn. Dit moet ik nog oplossen.

## Dag 8 (Vr 16-06-17)
#### 09:00-15:00 (Zie Weekend)

#### 15:00-17:00 Presentaties
Ik heb feedback ontvangen en zal de volgende dingen toevoegen:
- Spelers sorteren op ranking, rechtsboven in PlayersActivity menu met sorteermanieren;
- Een lijst of iets anders met (informatie over) toernooien toevoegen;
- Een punt toevoegen als er op die datum darts gespeeld wordt (check voor 1 maand).

### Weekend
Dit weekend heb ik de volgende dingen gedaan:
- Begin gemaakt aan chat (voornamelijk layout geïmplementeerd);
- Optie toegevoegd om per wedstrijd op een dag een speler aan te klikken en de
desbetreffende spelersinformatie te zien.
- Vlag toegevoegd per speler in PlayerActivity.
- Database met toernooi informatie gemaakt en geüpload op Firebase.

Hieronder enkele screenshots van de app tot nu toe.

Dag 7: De wedstrijden van 02-06-17|  Dag 8: Spelersinformatie: Price G.
:-------------------------:|:-------------------------:
![](/screenshots/Dag7.png) |  ![](/screenshots/Dag8.png)

## Dag 9 (Ma 19-06-17)
#### 10:00-11:00 Daily Standup
Planning (vandaag):
- (Calendar): Eerst kijken of er wedstrijden zijn, daarna pas naar een activity gaan;
- (Main): De livescores inladen en verwerken;
- (Players): De spelers op basis van ranking tonen;
- (Tournaments): Nieuwe optie 'Tournaments' met de desbetreffende toernooi
informatie toevoegen.

Planning (week):
- Refresh optie implementeren in MainActivity;
- Ervoor zorgen dat de juiste wedstrijden (en dus niet teveel) weergegeven worden
 op een dag [gelukt];
- Toernooien met spelers connecten;
- Verdergaan met chat;
- Bettercodehub goed gebruiken en de app/code alvast opschonen + bugs fixen.

#### 11:00-17:00 TournamentsActivity & TournamentActivity
Vandaag heb ik twee nieuwe activities gemaakt. De eerste, TournamentsActivity,
bevat een lijst met toernooien. Je kan op een toernooi klikken, en dan zie je
de toernooi-informatie. Verder kan je vanuit de kalender een datum selecteren.
Wanneer je op deze datum klikt, dan zie je de wedstrijden. Bovenaan staat het
toernooi. Als je op het toernooi klikt, dan krijg je, indien beschikbaar, de
toernooi-informatie te zien.

Screenshots:

Dag 9: Lijst met toernooien|  Dag 9: Toernooi-informatie
:-------------------------:|:-------------------------:
![](/screenshots/Dag9.png) |  ![](/screenshots/Dag9.1.png)

## Dag 10 (Di 20-06-17)
#### 10:00-11:00 Daily Standup
Planning voor vandaag:
- App crasht wanneer internet uitstaat;
- MainActivity met livescores moet nog gemaakt worden;
- In CalendarActivity onder de kalender een lijst met toernooien van de maand;
- Month played toevoegen aan elk toernooi.

#### 11:00-16:00 MainActivity (1)
- Ik heb met Wouter een oplossing bedacht om de app niet te laten crashen. Het
begin van de code staat weggecomment in de MainActivity, dit ga ik volgende week
afmaken;
- Begin gemaakt aan de MainActivity.

## Dag 11 (Wo 21-06-17)
#### 10:00-11:00 Daily Standup
Bettercodehub gecheckt, feedback (voor volgende week):
- Voortaan in 1 taal committen (Nederlands);
- Kopjes toevoegen in DESIGN.md;
- De namen in de map 'doc' veranderen;
- De namen van de classes TournamentsActivity en PlayersActivity veranderen in TournamentListActivity en PlayerListActivity;
- Code verbeteren (5/10).

#### 11:00-16:00 Logo
- Verder gewwerkt aan MainActivity;
- Ik heb de logo van mijn app gemaakt;
- De kleuren van mijn app heb ik een beetje aangepast;
- Het logo is nu ook te zien in het Hamburger-menu.

Dag 11: Het logo van mijn app, DartsWorld|
:-------------------------:|
![](/screenshots/Dag11.png) |

 Dag 11: Het bijgewerkte Hamburger-menu.|
 :-------------------------:|
 ![](/screenshots/Dag11.1.png)|

## Dag 12 (Do 22-06-17) MainActivity en RankingActivity
- Ik heb besloten toch de chat niet te implementeren, aangezien de tutorial wat
verouderd was en ik mogelijk tegen veel problemen aan zou kijken. Verder was
deze chat een extra feature, dus minder belangrijk. Diverse stukken code voor de
chat heb ik dan ook verwijderd;
- De MainActivity laat nu als het goed is livescores zien (ik kan dit nog niet
  checken omdat er pas vrijdag darts is).
- Begin gemaakt aan RankingActivity, waar de spelers op basis van hun rang getoond
dienen te worden;
- Een probleem met Bettercodehub is nu verholpen.

## Dag 13 (Vr 23-06-17) Beta-versie
Ik heb de volgende dingen gedaan:
- Een lijst PDC Order of Merit gemaakt. Als je deze optie selecteert, krijg je
nu de laatst ingevoerde ranking te zien (wordt opgehaald uit Firebase).
- Begin gemaakt aan auto-refresh optie;
- Alle functionaliteit zit nu in mijn app, behalve de auto refresh optie, en
een lijst met toernooien van de maand onder de kalender;
- Livescore bleek niet te werken, ik heb het aangepast, maar na ongeveer 1 minuut
werkt het niet meer.

Screenshots:

Dag 13: PDC Order of Merit|  Dag 13: De Livescore doet het even!
:-------------------------:|:-------------------------:
![](/screenshots/Dag13.png) |  ![](/screenshots/Dag13.1.png)

#### 15:00-17:00 Presentaties
Feedback:
- Ik moet een nieuwe kalender uitzoeken die de maand bijhoudt, opdat ik daaronder
een lijst met alle toernooien van die maand kan laten zien.
- Oriëntatie moet vastgezet worden op Portrait i.v.m. lelijke layout in
Landscape-modus.

## Weekend
- De livescore werkt nu volledig, alleen soms als er geen wedstrijd is, laat ie
toch even een wedstrijd zien (erg raar).

## Dag 14 (Ma 26-06-17) Code opschonen
Vandaag heb ik voornamelijk code opgeschoond en de PDC Order of Merit bijgewerkt
naar de huidige ranking.

##  Dag 15 (Di 27-06-17) Duplicated code verwijderd
Ik had heel veel dubbele code, waaronder in de navigatie, bij het ophalen van
spelersinformatie, toernooi-informatie etc. Deze heb ik allemaal verwijderd.
Doel (vandaag): 6 op BCH ipv 5. (duplicated code verhelpen)
