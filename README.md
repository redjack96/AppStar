# AppStar
Questa applicazione e' un progetto universitario che permette di interrogare un database PostgreSQL contenente dati su stelle e filamenti di gas, attraverso un'interfaccia desktop.

## Descrizione Applicazione
- [x] __Requisito 1__
	- Menu Login: inserimento user e password di un utente registrato. L'amministratore ha (id, password) = (lorzar, lorzar)
	- Menu Home: scelta dell'operazione da effettuare. Funzioni amministratore (ADMIN) bloccate per gli utenti semplici. Cliccare il 	   tasto Avanti per andare alla schermata della funzione scelta. Funzioni dell'applicazione:
- [x] __Requisiti 2-4__
	- [x] (ADMIN) Registrazione di un nuovo utente: Permette di aggiungere l'utente al database
	- [x] (ADMIN) Importazione di file csv: importa i file in una tra le tabelle "contorni_imp" , "filamenti_imp", 				"scheletri_imp", "stelle_imp", per poi smistarli nel database.
	- [x] (ADMIN) Inserire dati satelliti
	- [x] (ADMIN) Inserire dati strumenti
- [x] __Requisiti 5-12__
	- [x] Calcolo Centroide, Estensione e numero segmenti di un filamento
	- [x] Ricerca filamenti per contrasto e ellitticità
	- [x] Ricerca filamenti per numero di segmenti
	- [x] Ricerca di filamenti in regioni quadrate o circolari di mappa
	- [x] Ricerca delle stelle interne a un filamento
	- [x] Ricerca di stelle in regioni rettangolari, interne o esterne ai filamenti
	- [x] Calcolo della distanza minima degli estremi di un segmento di un filamento dal contorno
	- [x] Calcolo della distanza minima dalla spina dorsale per stelle interne a un filamento
- [x] __TestCase per ogni requisito__
- [x] __Relazione__
- [x] __Class Diagrams__
