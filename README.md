# ProgettoProgrammazione



# Introduzione

Lo scopo di questo progetto è quello di offrire un servizio meteo all'utente con cui, scelta a suo piacimento una città, si visualizzino tutte le informazioni attuali relative alla temperatura, temperatura percepita e velocità del vento delle città circostanti a quella scelta al momento della richiesta.
Una volta effettuata la richiesta, il programma cercherà ed aggiungerà dati meteo alle città coinvolte nella ricerca con un intervallo di due ore, per un tempo stabilito dall'utente; al termine di questo tempo, o se l'utente vuole anche prima, sarà possibile richiedere al programma di generare statistiche sulle città circostanti a quella cercata tramite vari parametri


# L'applicazione

Tramite L'API OpenWeather il programma riceve, salva e processa i dati meteo riguardanti le città circostanti a quella cercata dall'utente; per far questo utilizza più precisamente l'API "Cities in circle", la quale descrizione è disponibile al seguente link: https://openweathermap.org/current#cycle


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
I parametri della ricerca devono essere passati tramite il body della richiesta che deve contenere un file JSON così formattato:

stats {​​​​​​​
"nome": "milano",
"raggio": 100,
"durata_raccolta": 1,
"cnt": 50
}​​​​​​​

{​​​​​
"id": 34,
"tipo":"all",
"raggio":100,
"cnt": 50,
"from":"26/12/20",
"to": "30/12/20"
}​​​​​

  
  
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
