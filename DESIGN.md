Hieronder een schets van alle schermen in mijn app:

![alt text](/doc/design_sketch.png "Design schets")

Linksboven de layout van de MainActivity (rechts), samen met de Navigation Drawer
(links). De MainActivity zal worden getoond wanneer de app volledig afgesloten opstart.
Hierop zullen live scores van het darten te zien zijn (misschien wel alle scores van
vandaag, aangezien er vaak niet veel wedstrijden op een dag zijn, en vaak max. 1
tegelijk). Hiervoor is een AsyncTask vereist, die, afhankelijk van de huidige datum,
de juiste data binnen zal laden. Verder is er een HttpRequestHelper nodig,
waarmee de data wordt opgevraagd. De data die hiermee opgevraagd zal worden, komt
uit de onofficiële SofaScore API, zie http://www.sofascore.com/darts/livescore/json. <br>
Vanuit dit scherm kan je, wanneer er op één van de spelers geklikt wordt, naar
de PlayerActivity gaan, die informatie geeft over de aangeklikte speler. Deze
informatie zal komen uit een zelfgemaakte database, die inmiddels online staat op
Firebase. Hetzelfde geldt voor de bijbehorende afbeeldingen. Aangezien deze
database in Firebase staat, is hier geen HttpRequestHelper en/of AsyncTask voor
nodig. <br>
Verder is er in de Action Bar een refresh-knop, die de activiteit opnieuw zal
laden, en een bericht-knop, om naar de chat te gaan.

De Navigation Drawer is vanuit elk scherm te vinden. Hiermee kan je naar het
Homescherm (MainActivity) gaan, naar de CalendarActivity, naar de PlayersActivity,
of naar de InfoActivity.

De CalendarActivity (linksonder, links) bestaat uit een kalender, waarop je een
datum kan selecteren. Eer dit is gebeurd, krijg je in de DateActivity alle
wedstrijden van die datum te zien. Dit betreft niet alleen wedstrijden uit het
verleden, maar ook uit de nabije toekomst. Hiervoor wordt weer de SofaScore API
aangeroepen (http://www.sofascore.com/darts//2017-05-07/json) d.m.v. een
HttpRequestHelper en een AsyncTask. Ook hier kan je op een speler klikken en zo
naar de PlayerActivity gaan en spelersinformatie zien. Deze komt weer uit de eigen
database. Bovenaan zijn een refresh-knop en de chat-knop te vinden.

De InfoActivity (rechtsboven, links) bevat informatie over hoe de app te gebruiken
en eventueel contactinfo.

Tot slot bestaat deze app uit een chat-functie, die d.m.v. de chat-knop, die overal
in de Action Bar te vinden is, aangeroepen kan worden. Hiervoor dien je eerst (eenmalig)
in te loggen. Je gaat dus eerst naar de LoginActivity. De accounts worden in Firebase
opgeslagen, deze optie lijkt erg veel op het login-scherm bij mijn laatste app bij het
vak Native App Studio. Eenmaal ingelogd, kom je in de ChatActivity, en krijg je een
mooie real-time chat te zien, waar je zelf ook aan mee kan doen. De chat wordt
opgeslagen in Firebase.
