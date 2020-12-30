# Progetto per l'esame di Programmazione ad Oggetti

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

---

### Ricerca 
Per poter effettuare una ricerca viene resa disponibile la rotta "/ricerca", che deve essere utilizzata con il metodo POST.
I parametri della ricerca devono essere passati tramite il body della richiesta che deve contenere un file JSON nel quale devono essere presenti le seguenti coppie "chiave":"valore":

| Chiave                   | Valore                                                         |
|--------------------------|----------------------------------------------------------------|
| "nome"                   | "(nome della città centro del cerchio di ricerca)"             |
| "raggio"                 | (raggio di ricerca in km)                                      |
| "durata_raccolta"        | (durata del periodo di ricerca e aggiunta dei dati in ore)     |
| "cnt"                    | (numero di città da ricercare, compreso tra 1 e 50)            |

Un esempio può essere:
```
{
    "nome": "Milano",
    "raggio": 100,
    "durata_raccolta": 96,
    "cnt": 40
}
```
Dove:
* **"nome"** : nome della città centro del cerchio di ricerca
* **"raggio"** : raggio, in km, entro il quale considerare le città che devono essere contenute nella risposta
* **"durata_raccolta"** : periodo di tempo, in ore, durante il quale, ogni 2 ore, devono essere richiesti nuovi dati meteo e aggiunti alle città coinvolte nella ricerca
* **"cnt"** : numero di città che devono essere contenute nella risposta

Verrà quindi creata una ricerca avente come città centrale quella scelta dall'utente, al quale verranno poi restituiti i dati riguardanti le città che rientramo nei parametri di ricerca.

Il programma richiederà all'API di OpenWeather comunque il massimo numero di città consentite, ossia 50, e ne aggiornerà i dati meteo ad esse relativi, mentre il numero di città e i relativi dati ritornati terrà conto del parametro "cnt" fornito dall'utente; questo per fare in modo che poi l'utente possa richiedere statistiche su un numero più alto di città rispetto a quello scelto nell'avvio della ricerca.

Può accadere che nella risposta siano presenti meno città di quelle desiderate dall'utente, questo è dovuto al fatto che all'interno del raggio di ricerca sono presenti meno stazioni meteo di quelle desiderate.

Se si invia una richiesta come quella in esempio si avvierà una ricerca che avrà come città centrale Milano, e all'utente verranno forniti i dati meteo di Milano e delle prime 40 città circostanti in un raggio di 100km; in seguito il programma richiederà e aggiungerà dati meteo alle città coinvolte nella ricerca ogni 2 ore per 96 ore, ossia 4 giorni, al termine o durante i quali l'utente potrà richiedere di generare delle statistiche.

La rottà restituirà un file JSON contenente i seguenti campi:
* **"id_ricerca"** che conterrà l'id assegnato alla ricerca che è stata avviata in seguito alla richiesta
* **"dati"** che sarà un array contenente tutte le città che sono state incluse nella ricerca e ognuna di queste con i seguenti campi:
    * **nome_citta"** nome della città
    * **lat** latitudine della città in gradi sessagesimali
    * **lon** longitudine della città in gradi sessagesimali
    * **id** id della città nei database di OpenWeather
    * **dati_meteo** array con all'interno il dato meteo istantaneo della città, con i campi:
        * **temperatura** temperatura effettiva in °C
        * **temperatura_percepita** temperatura percepita in °C
        * **velocità_vento** velocità del vento in km/h
        * **data** data del rilevamento in formato UNIX, ossia il numero di millisecondi passati dal 1 Gennaio 1970
  
Nel caso dell'esempio, la prima parte del ritorno sarebbe simile a questa:

```
{
    "id_ricerca": 3173435,
    "dati": [
        {
            "nome_citta": "Milan",
            "dati_meteo": [
                {
                    "data": 1609340994000,
                    "temperatura": 4.07,
                    "temperatura_percepita": 1.53,
                    "velocita_vento": 1.0
                }
            ],
            "lon": 9.19,
            "id": 3173435,
            "lat": 45.46
        }
```
Si noti che l'id della ricerca corrisponde con quello della città centrale, in questo caso Milano.

Durante l'utilizzo della rotta possono essere lanciate, in caso di errori, le seguenti eccezioni:
* **ParseException** : se si verifica un errore durante il parsing dei dati forniti da OpenWeather
* **BadRequestException** : se la richiesta dei dati all'API di OpenWeather non è andata a buon fine
* **MalformedURLException** : se l'URL della richiesta ad OpenWeather non è formato correttamente
* **IOException** : se ci sono errori durante la lettura dei dati forniti da OpenWeather
* **RaggioNotValidException** : se l'utente ha inserito un raggio minore o uguale a 0
* **CntNotValidException** : se il numero di citta da ricercare inserito dall'utente è minore di 1 o maggiore di 50

---

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

Un esempio può essere, riferendosi a quello fatto sopra, il seguente:
```
{
    "id": 3173435,
    "tipo":"all",
    "raggio":100,
    "cnt": 40,
    "from":"29/12/20",
    "to": "31/12/20"
}
```

Dove:
* **"id"** : il codice della ricerca dalla quale si vogliono prendere i dati per generare le statistiche, corrisponde al **City ID** della città centrale della ricerca.
* **"tipo"** : tipo di statistiche da generare, deve essere scelto tra [ all / temp / tempPerc / vento ]:
  * **all** : genera statistiche riguardanti tutti i 3 tipi di dati
  * **temp** : genera statistiche sulla temperatura effettiva
  * **tempPerc** : genera statistiche sulla temperatura percepita
  * **vento** : genera statistiche riguardo la velocità del vento
 * **"raggio"** : definisce il raggio della circonferenza all'interno della quale considerare le città per generare le statistiche, in km
 * **cnt** : numero di città da prendere in considerazione per generare le statistiche
 * **from** : data a partire dalla quale prendere in considerzione i dati meteo per generare le statistiche, nel formato [ gg/mm/aa ]
 * **to** : data fino alla quale prendere in considerzione i dati meteo per generare le statistiche, nel formato [ gg/mm/aa ] (quest'ultimo campo può essere omesso, in caso di omissione verranno presi in considerazione tutti i dati meteo a partire dalla data inserita nel campo ***from***

Nel caso dell'esempio si richiedono tutti i tipi di statistiche possibili, riguardanti la ricerca con id *3173435*, ossia quella con città centrale Milano avviata in precedenza con il primo esempio, prendendo in considerazione le prime 40 città entro un raggio di 100km e generandole sulla base dei dati meteo che sono stati rilevati tra il 29/12/20 e il 31/12/20.

La rotta restituirà un file JSON contenente le statistiche desiderate, divise nei seguenti campi:
* **stats_velocita_vento** : contiente le statistiche riguardanti la velocità del vento
    * **citta_piu_ventosa** : città con velocità del vento media maggiore 
        * **nome_citta** : nome della città
        * **vel_vento_media** : velocità del vento media
    * **citta_meno_ventosa** : città con velocità del vento media minore
        * **nome_citta** : nome della città
        * **vel_vento_media** : velocità del vento media
    * **citta_piu_variabile** : città con velocità del vento più variabile
        * **nome_citta** : nome della città
        * **var_vel_vento** : varianza della velocità del vento
* **stats_temperatura_perc** : contiene le statistiche riguardanti la temperatura percepita
    * **citta_piu_calda** : città con temperatura percepita media maggiore
        * **nome_citta** : nome della città
        * **temp_perc_media** : temperatura percepita media
    * **citta_piu_fredda** : città con temperatura percepita media minore
        * **nome_citta** : nome della città
        * **temp_perc_media** : temperatura percepita media
    * **citta_piu_variabile** : città con temperatura percepita più variabile
        * **nome_citta** : nome della città
        * **var_temp_perc** : varianza della temperatura percepita
* **stats_temperatura** : contiene le statistiche riguardanti la temperatura effettiva
    * **citta_piu_calda** : città con temperatura effettiva media maggiore
        * **nome_citta** : nome della città
        * **temp_media** : temperatura effettiva media
    * **citta_piu_fredda** : città con temperatura effettiva media minore
        * **nome_citta** : nome della città
        * **temp_perc_media** : temperatura effettiva media
    * **citta_piu_variabile** : città con temperatura effettiva più variabile
        * **nome_citta** : nome della città
        * **var_temp_perc** : varianza della temperatura effettiva
 
Verrano restituiti tutti i 3 campi principali se il tipo di statistiche richieste è *all*, altrimenti verrà restituito solo il campo richiesto dall'utente.

Nel caso dell'esempio il ritorno sarebbe simile a questo:
```
{
    "stats_velocita_vento": {
        "citta_piu_ventosa": {
            "nome_citta": "Milan",
            "vel_vento_media": 1.0
        },
        "citta_meno_ventosa": {
            "nome_citta": "Buccinasco",
            "vel_vento_media": 1.0
        },
        "citta_piu_variabile": {
            "nome_citta": "Cologno Monzese",
            "var_vel_vento": 0.71574
        }
    },
    "stats_temperatura_perc": {
        "citta_piu_fredda": {
            "nome_citta": "Rozzano",
            "temp_perc_media": 1.38
        },
        "citta_piu_calda": {
            "nome_citta": "Cologno Monzese",
            "temp_perc_media": 1.846
        },
        "citta_piu_variabile": {
            "var_temp_perc": 1.45,
            "nome_citta": "Baranzate"
        }
    },
    "stats_temperatura": {
        "citta_piu_fredda": {
            "nome_citta": "Rozzano",
            "temp_media": 3.94
        },
        "citta_piu_calda": {
            "nome_citta": "Cologno Monzese",
            "temp_media": 4.34
        },
        "citta_piu_variabile": {
            "nome_citta": "Baranzate",
            "var_temp_media": 1.2089
        }
    }
}
```

Durante l'utilizzo della rotta possono essere lanciate, in caso di errori, le seguenti eccezioni:
* **RaggioNotValidException** : se l'utente ha inserito un raggio minore o uguale a 0
* **CntNotValidException** : se il numero di citta da ricercare inserito dall'utente è minore di 1 o maggiore di 50
* **IdNotFoundException** : se l'id della ricerca dalla quale prendere i dati non esiste all'interno del database
* **StatsNotValidException** : se il tipo di statistiche richiesto dall'utente non è valido
* **DateNotValidException** : se le date inserite non sono nel formato corretto

---

### Salvataggio su file

Viene data la possibilità all'utente di salvare tutte le ricerche e i dati raccolti fino a quel momento su un file JSON tramite la rotta "/save", che deve essere utilizzata tramite il metodo GET.
All'utente verrà ritornato *true* se il salvataggio è andato a buon fine, *false* altrimenti.
Il salvataggio si troverà nel file denominato database.JSON presente all'interno della cartella del progetto e conterrà un array di ricerche, e ognuna di queste sarà formattata allo stesso modo della risposta della rotta "/ricerca".

---

### Caricamento del database da un file

È possibile per l'utente caricare un database in formato JSON correttamente formattato anche dopo l'avvio del programma, per far questo bisogna sostituire il file database.JSON presente nella cartella del progetto con il database da caricare e rinominarlo allo stesso modo.
Dopo aver sostituito il file è possibile utilizzare la rotta "/getFromFile", tramite il metodo GET, che restituirà *true* se il database è stato caricato correttamente, *false* altrimenti.
Il caricamento di un database cancella tutte le ricerche precedenti.

---

### Ottenere il database corrente

Tramite la rotta "/getDataBase", che deve essere utilizzata tramite il metodo GET, è possibile ottenere il database formattato in JSON in modo che possa essere poi ricaricato se l'utente lo desidera

---

### Cancellare tutte le ricerche

Utilizzando la rotta "/removeAll" tramite il metodo DELETE, l'utente può cancellare tutte le ricerche effettuate fino a quel momento



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
