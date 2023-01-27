package main.java;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Executor {

    public static void readAndRun(String xmlName, String browserName) {

        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature( XMLConstants.FEATURE_SECURE_PROCESSING, true );

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse( new File( "src/filesXML/"+ xmlName +".xml" ) );

            // optional, but recommended
            doc.getDocumentElement().normalize();

            System.out.println( "Eseguo " + doc.getDocumentElement().getNodeName() );
            System.out.println( "------" );

            // get <tests>
            NodeList listTest = doc.getElementsByTagName( "test" );
            System.out.println("Trovati " + listTest.getLength() +  " tests");
            System.out.println( "------" );

            // itero gli elementi (nodi) all'interno della lista comandi
            for( int x = 0 ; x < listTest.getLength() ; x++ ) {

                // il nodo che si trova all'interno di <Istruzioni>
                Node test = listTest.item( x );
                System.out.println( "Test '" + test.getAttributes().item( 0 ).getTextContent() + "'" );

                // la lista di nodi che si trovano all'interno di <comandi>
                NodeList listaDiComandi = test.getChildNodes();
                List<String> comandiDaEseguire = new ArrayList<>();

                // itero la lista comandi
                for( int i = 0 ; i < listaDiComandi.getLength() ; i++ ) {

                    // il singolo comando
                    Node comando = listaDiComandi.item( i );

                    //  Serve per trovare il nodo dentro al comando
                    NodeList listaComando = comando.getChildNodes();

                    // prendo soli i nodi che mi interessano
                    if( comando.getNodeType() == 1 ) {
                        System.out.println("[" + comando.getNodeName() + " " + i +
                                "]" );

                        // itero il comando(solitamente avrà un elemento)
                        for( int z = 0 ; z < listaComando.getLength() ; z++ ) {

                            // il singolo comando (es. <clicca>tiscaTusca</clicca>)
                            Node comandoDaEseguire = listaComando.item( z );

                            // prendo solo il nodo che mi interessa
                            if( comandoDaEseguire.getNodeType() == 1 ) {
                                // la stringa che rappresenta il nome del tag(es. "clicca"
                                String nomeComando = comandoDaEseguire.getNodeName();

                                // la stringa che rappresenta il testo dentro al tag (es. "tiscaTusca")
                                String infoComando = comandoDaEseguire.getTextContent();
                                System.out.println( nomeComando + "' con testo '" + infoComando + "'" );
                                System.out.println( "------" );

                                // inserisco il comando nell'array sotto forma di stringa
                                comandiDaEseguire.add( nomeComando + "->" + infoComando );
                            }
                        }
                    }
                }
                System.out.println("Generato array di comandi sotto forma di stringhe:");
                System.out.println();
                System.out.println( comandiDaEseguire );
                // infine posso far partire i test grazie alla lista di comandi da eseguire
                // essendo un ciclo tanti più test ci saranno tanti più liste verranno create
                // ed eseguite
                System.out.println( "------" );
                System.out.println("-----> Eseguo istruzione con Seleniomator");
                Seleniomator.runTests( comandiDaEseguire, browserName );
            }
        } catch( ParserConfigurationException | SAXException | IOException e ) {
            e.printStackTrace();
        } catch( InterruptedException e ) {
            throw new RuntimeException( e );
        }

    }
}
