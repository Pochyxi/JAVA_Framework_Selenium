package main.java;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class Seleniomator {


    public static void runTests( List<String> arrayDiComandi ) {
        // se non esistono istruzioni
        if( arrayDiComandi.size() == 0 ) {
            System.out.println("nessun comando da eseguire");
        } else {
            // apri chrome
            WebDriver driver = new ChromeDriver();

            // l'url da raggiungere
            System.out.println( arrayDiComandi.get( 0 ).split( "->" )[ 1 ] );

            // contatto url
            driver.get( arrayDiComandi.get( 0 ).split( "->" )[ 1 ] );

            // eseguo ciclo sui comandi
            for( String comando : arrayDiComandi ) {

                // i vari comandi da eseguire
                switch (comando.split( "->" )[0]) {
                    case "clicca" -> {
                        // la stringa
                        String cssSelector = comando.split( "->")[1];
                        WebElement selected = driver.findElement(By.cssSelector( cssSelector ) );
                        System.out.println(selected);
                        selected.click();
                        System.out.println(cssSelector);
                    }
                }
            }

            driver.close();
        }


//        driver.close();
    }
}
