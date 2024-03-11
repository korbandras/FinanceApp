package korbandras.financeapp.xml;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class StoreAndLoadXML {

    private static final String TAG = "StoreDataXML";
    private static final String FILENAME = "Data.xml";

    // Save data to XML file
    public static void saveXML(Context context, List<Datas> newDataList) {
        List<Datas> existingDataList = readFromXML(context);

        int highestID = existingDataList.stream().mapToInt(Datas::getId).max().orElse(0);
        for (Datas data : newDataList) {
            if (data.getId() == 0) { // Assign ID only if it's a new entry without an ID
                highestID++;
                data.setId(highestID);
            }
        }

        existingDataList.addAll(newDataList); // Combine existing with new

        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try {
            File file = new File(context.getExternalFilesDir(null), FILENAME);
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);

            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "datas");

            for (Datas data : existingDataList) {
                serializer.startTag("", "data");
                serializer.attribute("", "id", Integer.toString(data.getId()));

                serializer.startTag("", "income");
                serializer.text(data.getIncome());
                serializer.endTag("", "income");

                serializer.startTag("", "expenses");
                serializer.text(data.getExpenses());
                serializer.endTag("", "expenses");

                serializer.startTag("", "dueDate");
                serializer.text(data.getDueDate());
                serializer.endTag("", "dueDate");

                serializer.startTag("", "targetSum");
                serializer.text(data.getTargetSum());
                serializer.endTag("", "targetSum");

                serializer.endTag("", "data");
            }

            serializer.endTag("", "datas");
            serializer.endDocument();

            fileOutputStream.write(writer.toString().getBytes());
            fileOutputStream.close();

        } catch (Exception e) {
            Log.e(TAG, "Error saving data", e);
        }
    }

    // Read data from XML file
    public static List<Datas> readFromXML(Context context) {
        List<Datas> dataList = new ArrayList<>();
        try {
            File file = new File(context.getExternalFilesDir(null), FILENAME);
            if (!file.exists()) {
                return dataList; // Return an empty list if the file doesn't exist
            }
            FileInputStream fis = new FileInputStream(file);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(fis, null);

            int eventType = parser.getEventType();
            Datas currentData = null;
            int currentId = 0; // Variable to hold the current ID
            String income = "", expenses = "", dueDate = "", targetSum = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName;
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();
                        if ("data".equals(tagName)) {
                            currentId = Integer.parseInt(parser.getAttributeValue(null, "id"));
                        } else if ("income".equals(tagName)) {
                            income = parser.nextText();
                        } else if ("expenses".equals(tagName)) {
                            expenses = parser.nextText();
                        } else if ("dueDate".equals(tagName)) {
                            dueDate = parser.nextText();
                        } else if ("targetSum".equals(tagName)) {
                            targetSum = parser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tagName = parser.getName();
                        if ("data".equals(tagName)) {
                            Datas newData = new Datas(currentId, income, expenses, dueDate, targetSum);
                            dataList.add(newData);
                            // Reset variables for the next data entry
                            income = expenses = dueDate = targetSum = "";
                            currentId = 0;
                        }
                        break;
                }
                eventType = parser.next();
            }

            fis.close();
        } catch (Exception e) {
            Log.e(TAG, "Error reading data from XML", e);
        }
        return dataList;
    }

    // Update an entry by ID
    public static void updateEntry(Context context, int id, String newIncome, String newExpenses, String newDueDate, String newTargetSum) {
        List<Datas> datasList = readFromXML(context);
        for (Datas data : datasList) {
            if (data.getId() == id) {
                data.setIncome(newIncome);
                data.setExpenses(newExpenses);
                data.setDueDate(newDueDate);
                data.setTargetSum(newTargetSum);
                break; // Stop loop once the matching ID is found and updated
            }
        }
        saveXML(context, datasList); // Save the updated list back to XML
    }

    // Delete all data
    public static void deleteAllData(Context context) {
        saveXML(context, new ArrayList<>()); // Save an empty list to overwrite existing data
    }

    // Delete a specific entry by ID
    public static void deleteByID(Context context, int id) {
        List<Datas> datasList = readFromXML(context);
        datasList.removeIf(data -> data.getId() == id); // Remove the entry with the specified ID
        saveXML(context, datasList); // Save the modified list back to XML
    }
}
