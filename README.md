# AppStar
Questa applicazione e' un progetto universitario che permette di interrogare un database PostgreSQL contenente dati su stelle e filamenti di gas, attraverso un'interfaccia desktop.

Funzioni disponibili:

- Menu Login: inserimento user e password di un utente registrato. L'amministratore ha (id, password) = (lorzar, lorzar)
	
- Menu Home: scelta dell'operazione da effettuare. Funzioni amministratore (ADMIN) nascoste per gli utenti semplici. Cliccare il tasto Avanti per procedere alla funzione scelta.

- Funzioni Menu Home:
	- (ADMIN) Registrazione di un nuovo utente: Permette di aggiungere l'utente al database
	- (ADMIN) Importazione di file csv: importa i file in una tra le tabelle "contorni_imp" , "filamenti_imp", 				"scheletri_imp", "stelle_imp", per poi smistarli nel database.
	- (ADMIN) Inserire dati satelliti
	- (ADMIN) Inserire dati strumenti
	- Calcolo Centroide, Estensione e numero segmenti di un filamento
	- Ricerca filamenti per contrasto e ellitticità
	- TODO: Ricerca filamenti per numero segmenti
	- TODO: Ricerca di filamenti in regioni quadrate o circolari di mappa
	- TODO: Ricerca di stelle in regioni rettangolari
	- TODO: Ricerca stelle
	- TODO: Calcoli su filamenti e stelle
