package main.java;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Seleniomator {

    public static void setTimeoutSync(Runnable runnable, int delay) {
        try {
            Thread.sleep(delay);
            runnable.run();
        }
        catch (Exception e){
            System.err.println(e);
        }
    }

    public static void runTests( List<String> arrayDiComandi, String browserName ) throws InterruptedException {
        // se non esistono istruzioni
        if( arrayDiComandi.size() == 0 ) {
            System.out.println("nessun comando da eseguire");
        } else {
            WebDriver driver = null;
            System.out.println("Apro il browser");
            switch (browserName){

                case "chrome" -> {
                    // apri chrome
                     driver = new ChromeDriver();
                }

                case "firefox" -> {
                    // apri firefox
                     driver = new FirefoxDriver();
                }

                case "edge" -> {
                    // apri edge
                     driver = new EdgeDriver();
                }

                case "safari" -> {
                    // apri safari
                     driver = new SafariDriver();
                }
            }


            int seconds = 0;

            // l'url da raggiungere
            System.out.println("Contatto l'url ---> " + arrayDiComandi.get( 0 ).split( "->" )[ 1 ] );
            System.out.println( "------" );

            if( driver != null ) {
                // contatto url
                driver.get( arrayDiComandi.get( 0 ).split( "->" )[ 1 ] );
            }

            // eseguo ciclo sui comandi
            for( String comando : arrayDiComandi ) {

                // indice del comando
                int indexOfComando = arrayDiComandi.indexOf(comando);
                System.out.println("Eseguo comando numero " + indexOfComando);

                try {
                    // i vari comandi da eseguire
                    switch (comando.split( "->" )[0]) {
                        case "clicca" -> {
                            System.out.println("" + comando.split( "->")[0]);


                            // la stringa del nome comando
                            String cssSelector = comando.split( "->")[1];

                            System.out.println("Contenuto del comando ---> " + cssSelector);
                            System.out.println( "------" );

                            System.out.println("Seleziono elemento web con il selettore css '" + cssSelector + "'");
                            System.out.println( "------" );
                            WebElement selected = driver.findElement(By.cssSelector( cssSelector ) );
                            System.out.println("Elemento trovato");
                            System.out.println(selected);
                            System.out.println( "------" );
                            System.out.println("Clicco elemento");
                            selected.click();
                            System.out.println("------FINE COMANDO");
                        }

                        case "scrivi" -> {
                            System.out.println("" + comando.split( "->")[0]);
                            System.out.println( "------" );

                            // prendo l'indice precedente
                            int indexOfPreComando = indexOfComando - 1;
                            System.out.println("Indice del comando precedente ->" + indexOfPreComando);
                            System.out.println( "------" );

                            String stringaPrecomando = arrayDiComandi.get( indexOfPreComando ).split( "->" )[1];
                            System.out.println("Questa è la stringa del comando precedente -> " + stringaPrecomando);
                            System.out.println( "------" );

                            // la stringa che definisce cosa scrivere nell'input
                            String stringaDaScrivere = comando.split( "->")[1];
                            System.out.println("Questa è la stringa da scrivere nel comando precedente");
                            System.out.println( "------" );

                            System.out.println("Seleziono elemento web :");
                            WebElement selected = driver.findElement(By.cssSelector( stringaPrecomando ) );
                            System.out.println("Elemento selezionato ---> " + selected);

                            System.out.println("Eseguo comando di scrittura");
                            selected.sendKeys( stringaDaScrivere );
                        }

                        case "aspetta" -> {
                            System.out.println("Contenuto del comando ---> " + comando);
                            System.out.println( "------" );

                            // secondi da aspettare
                            seconds = Integer.parseInt(comando.split( "->" )[1].split( " " )[0]);

                            System.out.println("Eseguo pausa di " + seconds + " secondi");
                            setTimeoutSync(() -> System.out.println("test"), seconds * 1000);
                            System.out.println("----Fine pausa");
                        }

                    }
                } catch ( Exception e ) {
                    System.out.println("----ERRORE NEL TEST, ESEGUO TEST SUCCESSIVO");
                }


            }

            driver.close();
        }


//        driver.close();
    }
}
