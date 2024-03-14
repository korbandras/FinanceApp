package korbandras.financeapp.xml;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
            serializer.text("\n");
            serializer.startTag("", "datas");
            serializer.text("\n");

            for (Datas data : existingDataList) {
                serializer.startTag("", "data");
                serializer.attribute("", "id", Integer.toString(data.getId()));
                serializer.text("\n");

                serializer.startTag("", "income");
                serializer.text(data.getIncome());
                serializer.endTag("", "income");
                serializer.text("\n");

                serializer.startTag("", "expenses");
                serializer.text(data.getExpenses());
                serializer.endTag("", "expenses");
                serializer.text("\n");

                serializer.startTag("", "dueDate");
                serializer.text(data.getDueDate());
                serializer.endTag("", "dueDate");
                serializer.text("\n");

                serializer.startTag("", "targetSum");
                serializer.text(data.getTargetSum());
                serializer.endTag("", "targetSum");
                serializer.text("\n");

                serializer.endTag("", "data");
                serializer.text("\n");
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
        recreateXMLFile(context);
        saveXML(context, datasList); // Save the updated list back to XML
    }

    public static void showDeleteConfirmation(final Context context){
        new AlertDialog.Builder(context).setTitle("Delete All Data").setMessage("Are you sure you want to delete all data?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        recreateXMLFile(context);
                    }
                })
                .setNegativeButton(android.R.string.no,null).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    // Delete a specific entry by ID
    public static void deleteByID(Context context, int id) {
        List<Datas> datasList = readFromXML(context);
        datasList.removeIf(data -> data.getId() == id);
        recreateXMLFile(context);
        saveXML(context, datasList);
    }

    public static boolean recreateXMLFile(Context context) {
        File file = new File(context.getExternalFilesDir(null), FILENAME);

        // Try to delete the file if it exists
        if (file.exists()) {
            if (!file.delete()) {
                // If the file couldn't be deleted, return false
                return false;
            }
        }

        // Now, create a new, empty XML file
        try {
            if (file.createNewFile()) {
                // If the file is successfully created, initialize it with a root XML structure
                XmlSerializer serializer = Xml.newSerializer();
                StringWriter writer = new StringWriter();
                serializer.setOutput(writer);

                serializer.startDocument("UTF-8", true);
                serializer.startTag("", "datas"); // Assuming "datas" is your root element
                serializer.endTag("", "datas");
                serializer.endDocument();
                serializer.flush();

                String initialXMLContent = writer.toString();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(initialXMLContent.getBytes());
                fos.close();

                return true; // The file was successfully recreated and initialized
            }
        } catch (IOException e) {
            Log.e(TAG, "Error recreating XML file", e);
        }

        return false; // Returning false if the file wasn't created or initialized
    }

}
