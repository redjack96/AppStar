# AppStar
Questa applicazione permette di interrogare un database PostgreSQL contenente dati su stelle e filamenti di gas attraverso un'interfaccia desktop.

Funzioni disponibili:

- Menu Login: inserimento user e password di un utente registrato. L'amministratore ha (id, password) = (lorzar, lorzar)
	
- Menu Home: scelta dell'operazione da effettuare. Funzioni amministratore (ADMIN) nascoste per gli utenti semplici. Cliccare il tasto Avanti per procedere alla funzione scelta.

- Funzioni Menu Home:
	- (ADMIN) Registrazione di un nuovo utente: Permette di aggiungere l'utente al database
	- FINIRE: (ADMIN) Importazione di file csv: importa i file in una tra le tabelle "contorni_imp" , "filamenti_imp", 				"scheletri_imp", "stelle_imp", per poi smistarli nel database. Per ora vengono smistate correttamente solo le tabelle 			"contorni" e "punti_contorni"
	- (ADMIN) Inserire dati satelliti
	- FINIRE: (ADMIN) Inserire dati strumenti
	- TODO: Ricerca filamenti
	- TODO: Ricerca stelle
	- TODO: Calcoli su filamenti e stelle
