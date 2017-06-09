# DartsWorld
Programmeerproject: Darts app - Roshan Mahes [![BCH compliance](https://bettercodehub.com/edge/badge/Roshanmahes/dartsWorld?branch=master)](https://bettercodehub.com/)

Deze app is bedoeld voor mensen die veel van darts houden. Ze willen naar de
wedstrijd kijken, maar kunnen dit om een of andere reden niet doen. Of ze zijn
op zoek naar iets wat niet tijdens de wedstrijd verteld wordt. Verder is deze
app ook bedoeld voor mensen die graag hun ervaringen willen delen tijdens de
wedstrijd.

## Kenmerken
De applicatie zal bij elke grote dartswedstrijd live de score laten zien. Verder
kan er tijdens elke wedstrijd op de spelers geklikt worden, waardoor je meer
informatie over hen te zien krijgt. De spelersinformatie van de overige bekende
spelers is te zien in een 'Order of Merit'-optie die te vinden is in het
Hamburger-menu. De gebruiker kan zijn ervaringen delen met overige liefhebbers
d.m.v. een chat.

## Schetsen
![alt text](/doc/sketch0.png "Schets 1: Beginscherm, Navigatie, Spelersinfo")

![alt text](/doc/sketch1.png "Schets 2: Chat, Ranking, Kalender")

## Externe componenten
De externe componenten zijn o.a. een (onofficiële) API (gescraped) met darts
livescores, namelijk de site van SofaScore, een site waarop livescores van
allerlei sporten te vinden zijn. Hieronder de te gebruiken links met JSON-data:

Livescore darts: http://www.sofascore.com/darts/livescore/json.<br>
Afgelopen/toekomstige wedstrijden: http://www.sofascore.com/darts//2017-05-07/json.

Verder heb ik, aangezien er geen online database te vinden was, de
spelersinformatie gedestilleerd uit diverse bronnen (o.a. Wikipedia, online fora
  en video's). De informatie heb ik opgeslagen in een Excel-bestand, maar
  converteer ik naar een JSON-bestand, opdat alle data van dezelfde vorm is.

## Technische problemen/Beperkingen
Enkele technische problemen kunnen optreden bij het ontwikkelen van de app. Zo
moeten er maatregelen getroffen worden bij de chat, opdat deze niet volgespamd
wordt door een aantal personen. Hiervoor zal ik een _berichtlimiet_
(een maximaal aantal berichten per minuut per gebruiker) instellen. Indien er
veel berichten verstuurd worden in een bepaalde tijd, kunnen sommige gebruikers
mogelijk niet chatten. Dit kan een technisch probleem worden.<br>
Verder was er geen eenvoudige gratis livescore API te vinden, waardoor ik dus
besloten heb om de links van SofaScore te gebruiken. Omdat dit geen API is, kan
het misschien lastig worden om deze goed te gebruiken.

## Vergelijkbare applicaties
Er zijn diverse livescore applicaties beschikbaar. Echter is er geen specifieke
voor het darten. De app die het meest overeenkomt met die van mij is de app
_SofaScore_. Deze applicatie bevat toont live uitslagen voor alle sporten,
waaronder darten. Verder heeft deze applicatie ook een chat per wedstrijd.
Helaas ontbreekt er bij het darten spelersinformatie, en zijn de chats meestal
nogal chaotisch. Mijn app zal er hopelijk wel in slagen om de chat wat
overzichtelijker te maken. Verder zal mijn app bestaan uit één enkele chat in
plaats van duizenden per dag.

## Minimum Viable Product
De applicatie zal minimaal bestaan uit de livescores van het darten en de
zelf gedestilleerde spelersinformatie. De eerdere uitslagen horen hier dus niet
bij. Hoogstwaarschijnlijk zal een simpele chat niet ontbreken.

## Optionele functies
Deze app is op diverse manieren uit te breiden. Hieronder vijf optionele
uitbreidingen:

1. In plaats van alleen scores, is het misschien ook mogelijk om wat meer data
te laden, zoals de score per pijl. Hiervoor zou ik wel een andere ingewikkelde
onofficiële API moeten gebruiken, mijn voorkeur zal niet meteen uitgaan naar
deze uitbreiding.
2. Het is nuttig om een zoekfunctie in te bouwen. Niet alleen de spelers
zouden hiermee eenvoudig gezocht kunnen worden, ook in de chat is iets eenvoudig
terug te vinden, mits je nog wel weet waar je naar moet zoeken.
3. Het is ook mogelijk om de zelfgevonden spelersinformatie te sorteren in de
app. Je kan dan niet alleen de huidige ranking zien, maar ook bijvoorbeeld
sorteren op nationaliteit, gewonnen toernooien, gegooide 9-darts etc.
4. De chat kan, ondanks de gelegde beperkingen, nog steeds chaotisch worden. Een
manier om wat meer 'spam' te verwijderen, is door _Robot9000 (r9k)_ te gebruiken.
Dit is een open-source script, die gebruikers tijdelijk blokkeren als ze eerder
gestuurde berichten opnieuw versturen.
5. Tot slot is het misschien mogelijk om wat meer informatie over het huidige
toernooi te verspreiden (prijzengeld, schema, nieuws). Helaas zou ik dan weer
zelf informatie moeten destilleren.

Indien mijn app snel af zal zijn (wat waarschijnlijk niet zal gebeuren) en ik
mijn app nog kan uitbreiden, zal ik bovenstaand lijstje afgaan in de volgorde
3-2-4-1-5.
