package main.java;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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

    public static void runTests( List<String> arrayDiComandi ) throws InterruptedException {
        // se non esistono istruzioni
        if( arrayDiComandi.size() == 0 ) {
            System.out.println("nessun comando da eseguire");
        } else {
            // apri chrome
            WebDriver driver = new ChromeDriver();

            int seconds = 0;

            // l'url da raggiungere
            System.out.println( arrayDiComandi.get( 0 ).split( "->" )[ 1 ] );

            // contatto url
            driver.get( arrayDiComandi.get( 0 ).split( "->" )[ 1 ] );
            boolean flag = true;
            // eseguo ciclo sui comandi
            for( String comando : arrayDiComandi ) {

                // i vari comandi da eseguire
                switch (comando.split( "->" )[0]) {
                    case "clicca" -> {
                        // la stringa
                        String cssSelector = comando.split( "->")[1];
                        System.out.println(cssSelector);
                        WebElement selected = driver.findElement(By.cssSelector( cssSelector ) );
                        System.out.println(selected);
                        selected.click();
                        System.out.println(cssSelector);
                    }

                    case "scrivi" -> {
                        int indexOfComando = arrayDiComandi.indexOf(comando);
                        int indexOfPreComando = indexOfComando - 1;
                        System.out.println("Indice del comando precedente ->" + indexOfPreComando);
                        String stringaPrecomando = arrayDiComandi.get( indexOfPreComando ).split( "->" )[1];
                        System.out.println("Questa è la stringa del precomando -> " + stringaPrecomando);
                        String stringaDaScrivere = comando.split( "->")[1];
                        WebElement selected = driver.findElement(By.cssSelector( stringaPrecomando ) );
                        selected.sendKeys( stringaDaScrivere );
                    }

                    case "aspetta" -> {
                        System.out.println("aspetto");
                        seconds = Integer.parseInt(comando.split( "->" )[1].split( " " )[0]);
                        setTimeoutSync(() -> System.out.println("test"), seconds * 1000);
                    }

                }
            }

            driver.close();
        }


//        driver.close();
    }
}
