# ProgettoProgrammazione



# Introduzione

Lo scopo di questo progetto è quello di offrire un servizio meteo all'utente con cui, scelta a suo piacimento una città, si visualizzino tutte le informazioni attuali relative alla temperatura, temperatura percepita e velocità del vento delle città circostanti a quella scelta al momento della richiesta.
Una volta effettuata la richiesta, il programma cercherà ed aggiungerà dati meteo alle città coinvolte nella ricerca con un intervallo di due ore, per un tempo stabilito dall'utente; al termine di questo tempo, o se l'utente vuole anche prima, sarà possibile richiedere al programma di generare statistiche sulle città circostanti a quella cercata tramite vari parametri


# L'applicazione

Tramite L'API OpenWeather il programma riceve, salva e processa i dati meteo riguardanti le città circostanti a quella cercata dall'utente; per far questo utilizza più precisamente l'API "Cities in circle", la quale descrizione è disponibile al seguente [link](https://openweathermap.org/current#cycle)
Più in particolare con dati meteo si intendono la temperatura effettiva, la temperatura percepita e la velocità del vento.


## Rotte disponibili

Dal programma vengono rese disponibili le seguenti rotte sulla porta 8080 del localhost:

| Rotta         |    Metodo    |        Funzione                        |
|---------------|--------------|----------------------------------------|
| /ricerca      | POST         | Avvia ricerca e mostra dati istantanei |
| /stats        | POST         | Richiede statistiche                   |
| /save         | GET          | Salva le ricerche effettuate           |
| /getFromFile  | GET          | Carica le ricerche da un file          |
| /getDataBase  | GET          | Richiede tutti i dati sulle ricerche   |
| /removeAll    | GET          | Rimuove tutte le ricerche salvate      |


### Ricerca 
Per poter effettuare una ricerca viene resa disponibile la rotta "/ricerca", che deve essere utilizzata con il metodo POST.
I parametri della ricerca devono essere passati tramite il body della richiesta che deve contenere un file JSON che deve contnere le seguenti copppie "chiave":"valore":

| Chiave                   | Valore                                                         |
|--------------------------|----------------------------------------------------------------|
| "nome"                   | "(nome della città centro del cerchio di ricerca)"             |
| "raggio"                 | (raggio di ricerca in km)                                      |
| "durata_raccolta"        | (durata del periodo di ricerca e aggiunta dei dati in ore)     |
| "cnt"                    | (numero di città da ricercare, compreso tra 1 e 50)            |


Verrà quindi creata una ricerca avente come città centrale quella scelta dall'utente, al quale verranno poi restituiti i dati riguardanti le città che rientramo nei parametri di ricerca.
Il programma richiederà all'API di OpenWeather comunque il massimo numero di città consentite, ossia 50, e ne aggiornerà i dati meteo ad esse relativi, mentre il numero di città e i relativi dati ritornati terrà conto del parametro "cnt" fornito dall'utente; questo per fare in modo che poi l'utente possa richiedere statistiche su un numero più alto di città rispetto a quello scelto nell'avvio della ricerca.
Può accadere che nella risposta siano presenti meno città di quelle desiderate dall'utente, questo è dovuto al fatto che all'interno del raggio di ricerca sono presenti meno stazioni meteo di quelle desiderate.

La rottà restituirà un file JSON contenente i seguenti campi:
"id_ricerca" che conterrà l'id assegnato alla ricerca che è stata avviata in seguito alla richiesta
"dati" che sarà un array contenente tutte le città che sono state incluse nella ricerca e, per ognuna di queste, il dato meteo istantaneo ad essa riferito.

### Statistiche
Per poter ottenere delle statistiche riguardo i dati meteo delle città di una determinata ricerca viene resa disponibile la rott "/stats", che deve essere utilizzata con il metodo POST.
Le statistiche generate sono riguardo le città con valori medi dei dati maggiori e minori e con la massima varianza. 
I parametri da utilizzare per generare le statistiche devono essere passati tramite il body della richiesta che deve contenere un file JSON contenente le seguenti coppie "chiave":"valore":

| Chiave              | Valore                                                                        |
|---------------------|-------------------------------------------------------------------------------|
| "id"                | (id della ricerca alla quale si fa riferimento per generare le statistiche)   |
| "tipo"              | "(tipo di dati sui quali generare le statistiche)"                            |
| "raggio"            | (raggio entro il quale vengono prese in considerazione le città)              |
| "cnt"               | (numero di città da prendere in considerazione)                               |
| "from"              | "(data dalla quale prendere in considerazione i dati meteo)"                  |
| "to"                | "(data fino alla quale prendere in considerazione i dati meteo)"              |

Dove:
* **"id"** : il codice della ricerca dalla quale si vogliono prendere i dati per generare le statistiche, corrisponde al **City ID** della città centrale della ricerca.
* **"tipo"** : tipo di statistiche da generare, deve essere scelto tra [ all / temp / tempPerc / vento ]:
  * **all** : genera statistiche riguardanti tutti i 3 tipi di dati
  * **temp** : genera statistiche sulla temperatura effettiva
  * **tempPerc** : genera statistiche sulla temperatura percepita
  * **vento** : genera statistiche riguardo la velocità del vento
 * **"raggio"** : definisce il raggio della circonferenza all'interno della quale considerare le città per generare le statistiche, in km
 * **cnt** : numero di città da prendere in considerazione per generare le statistiche


La rotta restituirà un file JSON contenente le statistiche desiderate

  
  
## codice rotte
  
## Software utilizzati
* [Eclipse](https://www.eclipse.org/downloads/) - Ambiente di sviluppo
* [UML Designer](http://www.umldesigner.org/) - software per la realizzazione dei diagrammi UML
* [Maven](https://maven.apache.org/) - software di gestione di progetti e librerie
* [Spring Boot](https://spring.io/projects/spring-boot) - framework per sviluppo di applicazioni in Java
* [Postman](https://www.postman.com/) - ambiente di sviluppo API per effettuare richieste









<img src="/Users/luca/Desktop/OOP.jpg"> caso d'uso delle rotte 

<img src="diagclassi.jpg"> aggiungere da eclipse 

<img src="diagseq.jpg"> 
