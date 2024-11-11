# uniud-c2pa
*Uniud C2PA manifest viewer*<br/><br/>
Il seguente progetto &egrave; stato realizzato come appendice al lavoro di tesi conclusivo del <b>Master in Intelligence and Emerging Technologies</b> (edizione 2023/2024), Universit&agrave; degli studi di Udine.

Il software &egrave; stato scritto in Java, su piattaforma [Eclipse RCP](https://www.vogella.com/tutorials/EclipseRCP/article.html).

Struttura del progetto:
<ul>
<li>
  <b>c2pa-product</b>
  
  Contiene la definizione del product RCP
</li>
<li>
  <b>c2pa-releng</b>
  
  Release engineering
  
  
> Per eseguire una build dell'applicativo &egrave; sufficiente lanciare un <code>maven install</code> sul progetto <code>c2pa-releng</code>

</li>
<li>
  <b>c2pa-rootfiles</b>
  
  Plugin che definisce i files da copiare nella root della build
  
  
> Utile per distribuire la jre assieme all'applicativo: &egrave; sufficiente aggiungere una cartella denominata <code>jre</code> e copiare al suo interno il contenuto della jre adatta al sistema operativo target
 
</li>
<li>
  <b>c2pa</b>
  
  Plugin principale
</li>
</ul>
