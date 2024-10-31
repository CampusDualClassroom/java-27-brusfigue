package com.campusdual.classroom;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Exercise27 {

    private static void createDocument() throws ParserConfigurationException, TransformerException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element root = document.createElement("shoppinglist");
        document.appendChild(root);
        Element items = document.createElement("items");
        root.appendChild(items);

        items.appendChild(createItem(document,"item","quantity","2","Manzana"));
        items.appendChild(createItem(document,"item","quantity","1","Leche"));
        items.appendChild(createItem(document,"item","quantity","3","Pan"));
        items.appendChild(createItem(document,"item","quantity","2","Huevo"));
        items.appendChild(createItem(document,"item","quantity","1","Queso"));
        items.appendChild(createItem(document,"item","quantity","1","Cereal"));
        items.appendChild(createItem(document,"item","quantity","4","Agua"));
        items.appendChild(createItem(document,"item","quantity","6","Yogur"));
        items.appendChild(createItem(document,"item","quantity","2","Arroz"));
        writeToFile(document,"src/main/resources/shoppingList.xml");
    }

    private static void writeToFile(Document document,String pathFile) throws TransformerException, IOException {
        File file = new File(pathFile);
        File parent =file.getParentFile();
        if(!parent.exists() && !parent.mkdirs()){
            throw new IOException("Unable to create " + parent.getAbsolutePath());
        }


        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty( "{http://xml.apache.org/xslt}indent-amount", "3");
        DOMSource source = new DOMSource((document));
        StreamResult result = new StreamResult(file);
        transformer.transform(source,result);
    }

    private static Element createItem(Document document, String tagName, String attribute, String attrValue, String textComponent) {
        Element item = document.createElement(tagName);
        item.setAttribute(attribute, attrValue);
        item.setTextContent(textComponent);
        return item;

    }

    private static void createFile(){
        JsonObject shopping = new JsonObject();
        JsonArray itemsArray = new JsonArray();
        itemsArray.add(createItem(2, "Manzana"));
        itemsArray.add(createItem(1, "Leche"));
        itemsArray.add(createItem(3, "Pan"));
        itemsArray.add(createItem(2, "Huevo"));
        itemsArray.add(createItem(1, "Queso"));
        itemsArray.add(createItem(1, "Cereal"));
        itemsArray.add(createItem(4, "Agua"));
        itemsArray.add(createItem(6, "Yogur"));
        itemsArray.add(createItem(2, "Arroz"));
        shopping.add("items", itemsArray);

        try(FileWriter fw = new FileWriter("src/main/resources/shoppingList.json")) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(shopping);
            fw.write(json);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static JsonObject createItem (int quantity, String itemDesc) {
        JsonObject item = new JsonObject();
        item.addProperty("quantity", quantity);
        item.addProperty("text", itemDesc);
        return item;
    }


    public static void main(String[] args) {
        try {
            Exercise27.createDocument();
        } catch (ParserConfigurationException | TransformerException | IOException e) {
            throw new RuntimeException(e);
        }
        Exercise27.createFile();
    }
}