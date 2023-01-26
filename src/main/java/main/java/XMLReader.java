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

public class XMLReader {
    private static final String FILENAME = "src/filesXML/istruzioni.xml";

    public static void readFileXML() {

        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(FILENAME));

            // optional, but recommended
            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Elemento radice :" + doc.getDocumentElement().getNodeName());
            System.out.println("------");

            // get <comandi>
            NodeList list = doc.getElementsByTagName("comandi");

            // itero gli elementi (nodi) all'interno della lista comandi
            for (int temp = 0; temp < list.getLength(); temp++) {

                // il nodo che si trova all'interno di <Istruzioni>
                Node node = list.item(temp);

                // la lista di nodi che si trovano all'interno di <comandi>
                NodeList listaDiComandi = node.getChildNodes();
                System.out.println(listaDiComandi.getLength());

                // itero la lista comandi
                for (int i = 0; i < listaDiComandi.getLength(); i++) {

                    // il singolo comando
                    Node comando = listaDiComandi.item(i);
                    if (comando.getNodeType() == Node.ELEMENT_NODE ) {

                        // se il comando ha un nodo figlio
                        if (comando.hasChildNodes()) {
                            Node istruzione = comando.getChildNodes().item(0);
                            System.out.println(istruzione.getNodeName());

                            if (istruzione.getNodeName() == "apri") {
                                System.out.println("good");
                            }
                        }
                    }




                    //System.out.println(comando.getTextContent());
                }

                /* if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    // get comandi attribute
                    String id = element.getAttribute("id");

                    // get text
                    String firstname = element.getElementsByTagName("firstname").item(0).getTextContent();
                    String lastname = element.getElementsByTagName("lastname").item(0).getTextContent();
                    String nickname = element.getElementsByTagName("nickname").item(0).getTextContent();

                    NodeList salaryNodeList = element.getElementsByTagName("salary");
                    String salary = salaryNodeList.item(0).getTextContent();

                    // get salary's attribute
                    String currency = salaryNodeList.item(0).getAttributes().getNamedItem("currency").getTextContent();

                    System.out.println("Current Element :" + node.getNodeName());
                    System.out.println("Staff Id : " + id);
                    System.out.println("First Name : " + firstname);
                    System.out.println("Last Name : " + lastname);
                    System.out.println("Nick Name : " + nickname);
                    System.out.printf("Salary [Currency] : %,.2f [%s]%n%n", Float.parseFloat(salary), currency);

                } */
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }
}
