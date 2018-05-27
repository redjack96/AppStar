package test.RequisitoFunzionale_3;

/*
* --------------------------------------Tests del requisito funzionale 3---------------------------------------------
*
*   Per una migliore esperienza mandare in esecuzione RF_3_TestSuite.
* RF_3_TestSuite propone il seguente caso di test: l'utente connesso registra nell'applicazione un nuovo utente
* amministratore (albertone) che esegue il login e inserisce nuovi dati di uno strumento (ANDREA, banda 9.99, satellite
* Herschel). Dopo inserisce anche un satellite: AGILE, agenzia ISA, data inizio missione 27 marzo 2007, ancora in orbita.
*
* COSA VERRÀ INSERITO NEL DATABASE:       utenti("Alberto", "Rossi", "albertone", "albertone", "albertone@gmail.com",
*                                         true) [da RegistraUtenteTest]
*
*                                         strumenti("ANDREA", "Herschel") [da InserimentoDatiStrumentoTest]
*
*                                         bande(9.99, "ANDREA") [da InserimentoDatiStrumentoTest]
*
*                                         satelliti("AGILE", "2007-04-27", null)
*
*                                         agenzie("ASI", "AGILE")
*
* COSA VERRÀ MODIFICATO NEL DATABASE:     nulla.
*
* COSA VERRÀ ELIMINATO DAL DATABASE:      Alla fine del test i dati inseriti vengono eliminati.
* ----------------------------------------------------------------------------------------------------------------------
* */