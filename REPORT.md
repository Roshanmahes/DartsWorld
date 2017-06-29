<!-- Create a report (REPORT.md), based on your design document, containing important decisions that you’ve made, e.g. where you changed your mind during the past weeks. This is how you show the reviewer that you actually understand what you have done. -->

<!--
Clearly describe the technical design: how is the functionality implemented in your code? This should be like your DESIGN.md but updated to reflect the final application. First, give a high level overview, which helps us navigate and understand the total of your code (which components are there?).
Second, go into detail, and describe the modules/classes/functions and how they relate.

Clearly describe challenges that your have met during development. Document all important changes that your have made with regard to your design document (from the PROCESS.md). Here, we can see how much you have learned in the past month.

Defend your decisions by writing an argument of a most a single paragraph. Why was it good to do it different than you thought before? Are there trade-offs for your current solution? In an ideal world, given much more time, would you choose another solution?-->

## Inleiding
De app DartsWorld is bedoeld voor mensen die graag naar darts willen kijken, maar
dat niet kunnen. Deze app laat namelijk de livescores zien als er darts is. Verder
is deze app ook geschikt voor kijkers, aangezien er veel spelers- en toernooi-informatie
te vinden is.

<img src="/screenshots/Dag13.1.png" alt="Drawing" style="width: 400px;"/>

## Technische Design
De app bestaat uit 10 activities, die allemaal een basis, genaamd BaseActivity,
hebben. Er is ook een Helper-class, die enige functies bevat die meermaals
gebruikt worden. Verder zijn er nog diverse AsyncTasks en structs. In totaal zijn
er 21 classes.

#### BaseActivity
- onBackPressed: een terugknop die ook werkt voor de navigatie.
- setUpBars: functie die de toolbar en het Hamburger-menu gereed maken.

#### LauncherActivity
Wanneer je de app opstart, wordt de LauncherActivity aangeroepen. Deze toont enkel
2.5 seconden lang een splash screen. Vervolgens stuurt deze je naar de MainActivity.
- showSplashScreen.

#### MainActivity
De MainActivity toont livescores indien er live wedstrijden zijn. Als dit niet het
geval is, dan staat er 'No live matches' bovenaan. Aangezien er nooit veel wedstrijden
tegelijk zijn en het laden van de data zeer snel gaat, was het niet nodig om de
data in de LauncherActivity te laden en in een mee te geven.
- refreshActivity: functie die eens in de refreshTime de livescores opnieuw laadt.
- fetchLiveScore:
- retrievePlayerInfo:
- tournamentInfoClick:

#### CalendarActivity
De eerste optie in het menu is de Kalender. Hierin krijg je een kalender te zien.
Je kan naar de gewenste datum gaan om te kijken wat voor wedstrijden er op die dag
gespeeld worden/werden.

#### DateActivity
Hiermee zie je alle wedstrijden van een bepaalde datum. In het groen staat wie
er gewonnen heeft. Indien er een wedstrijd bezig is, dan zie je deze ook. Wanneer
je op een speler klikt, word je verwezen naar de PlayerActivity.

#### PlayersActivity
Dit is de tweede optie in het Hamburger-menu. Hier zie je een lijst, alfabetisch
gesorteerd op achternaam, met alle in Firebase ingevoerde spelers. Wanneer je op
een speler klikt, word je ook gestuurd naar de PlayerActivity.

#### PlayerActivity
Hierin zie je de spelersinformatie

#### Helper

## Uitdagingen tijdens het ontwikkelen van de app

#### Chat
De eerste twee weken was ik van plan om een chat toe te voegen aan de app.
Uiteindelijk heb ik dit idee laten varen, aangezien deze chat niet noodzakelijk
is voor mijn app. Dit was een verstandig besluit, aangezien een chat eigenlijk
ook een losse applicatie zou kunnen zijn.

#### Toernooien
Na de eerste presentatie werd er als feedback gegeven dat ik wat met toernooien
moest doen. Het was voorheen namelijk bijvoorbeeld erg onduidelijk wanneer er
precies toernooien waren. Verder was er geen informatie over zo een toernooi
bekend. Dit heb ik opgelost door zelf nog een kleine database met toernooi-informatie
te maken. Dit was eerst niet mijn plan, maar het heeft mijn app wel beter/bruikbaarder
gemaakt. Hierdoor kan je namelijk niet per wedstrijd wat meer informatie te weten
komen over de speler, maar ook over het toernooi wat gaande is.
