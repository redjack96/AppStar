package test.RequisitoFunzionale_4;

/*
 * --------------------------------------Tests del requisito funzionale 4---------------------------------------------
 *   Per una migliore esperienza mandare in esecuzione RF_4_TestSuite.
 * RF_4_TestSuite propone il seguente caso di test: l'utente registrato albertone tenta di importare due file del
 * satellite Herschel: filamenti_Herschel e stelle_Herschel. I dati saranno sovrascritti a quelli già presenti.
 *
 * Dopo provera' a importare il file Herschel_nulla.txt che non e' un csv e non contiene dati, quindi l'importazione fallirà
 *
 * COSA VERRÀ IMPORTATO NEL DATABASE:  file filamenti_Herschel e stelle_Herschel
 *
 * COSA VERRÀ INSERITO NEL DATABASE:       nulla.
 *
 * COSA VERRÀ MODIFICATO NEL DATABASE:     filamenti, segmenti, punti_segmenti, misurazione, stelle, visibilita
 *
 * COSA VERRÀ ELIMINATO DAL DATABASE:      nulla.
 * ----------------------------------------------------------------------------------------------------------------------
 * */