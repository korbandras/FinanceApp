package korbandras.financeapp.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class StoreDataXML {
    public static void saveToXML(ArrayList<Datas> data, String filePath) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = document.createElement("DataEntry");
            document.appendChild(root);
            for (Datas datas : data) {
                Element dataElement = document.createElement("Entry");
                root.appendChild(dataElement);
                childElement(document, dataElement, "Income", String.valueOf(datas.getIncome()));
                childElement(document, dataElement, "Expenses", String.valueOf(datas.getExpenses()));
                childElement(document, dataElement, "TargetDate", String.valueOf(datas.getDueDate()));
                childElement(document, dataElement, "TargetSum", String.valueOf(datas.getTargetSum()));
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileOutputStream(filePath));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void childElement(Document document, Element par, String tag, String text) {
        Element element = document.createElement(tag);
        element.setTextContent(text);
        par.appendChild(element);
    }

    public static ArrayList<Datas> readFromXML(String fileName) {
        ArrayList<Datas> datas = new ArrayList<>();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(fileName);
            Element rootElement = document.getDocumentElement();
            NodeList childNodeList = rootElement.getChildNodes();
            Node node;

            for (int i = 0; i < childNodeList.getLength(); i++) {
                node = childNodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList childNodesOfDatas = node.getChildNodes();
                    String income = "", expenses = "", targetDate = "", targetSum = "";
                    for (int j = 0; j < childNodesOfDatas.getLength(); j++) {
                        Node childNode = childNodesOfDatas.item(j);
                        if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                            if (childNode.getNodeName().equals("Income")) {
                                income = childNode.getTextContent();
                            } else if (childNode.getNodeName().equals("Expenses")) {
                                expenses = childNode.getTextContent();
                            } else if (childNode.getNodeName().equals("TargetDate")) {
                                targetDate = childNode.getTextContent();
                            } else if (childNode.getNodeName().equals("TargetSum")) {
                                targetSum = childNode.getTextContent();
                            }
                        }
                    }
                    // Create a new Datas object with the retrieved data and add it to the list
                    datas.add(new Datas(income, expenses, targetDate, targetSum));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }
}
