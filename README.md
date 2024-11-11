# uniud-c2pa
***Uniud C2PA manifest viewer***


Il seguente progetto &egrave; stato realizzato come appendice al lavoro di tesi conclusivo del **Master in Intelligence and Emerging Technologies** (edizione 2023/2024), Universit&agrave; degli studi di Udine.

Il software &egrave; stato scritto in Java, su piattaforma [Eclipse RCP](https://www.vogella.com/tutorials/EclipseRCP/article.html).

Struttura del progetto:
- **c2pa-product**
  
  Contiene la definizione del product RCP
- **c2pa-releng**
  
  Release engineering
  
  
> Per eseguire una build dell'applicativo &egrave; sufficiente lanciare un `mvn install` sul progetto `c2pa-releng`

- **c2pa-rootfiles**
  
  Plugin che definisce i files da copiare nella root della build
  
  
> Utile per distribuire la jre assieme all'applicativo: &egrave; sufficiente aggiungere una cartella denominata `jre` e copiare al suo interno il contenuto della jre adatta al sistema operativo target
 
- **c2pa**
  
  Plugin principale
