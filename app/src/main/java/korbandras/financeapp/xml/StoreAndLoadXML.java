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

                serializer.startTag("","savedSoFar");
                serializer.text(String.valueOf(data.getSavedSoFar()));
                serializer.endTag("","savedSoFar");
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
            int savedSoFar = 0; // Variable to hold the savedSoFar value
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName;
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();
                        if ("data".equals(tagName)) {
                            currentId = Integer.parseInt(parser.getAttributeValue(null, "id"));
                            // Initialize a new Datas object with default values, including savedSoFar
                            currentData = new Datas(currentId, "", "", "", "", 0);
                        } else if ("income".equals(tagName)) {
                            income = parser.nextText();
                        } else if ("expenses".equals(tagName)) {
                            expenses = parser.nextText();
                        } else if ("dueDate".equals(tagName)) {
                            dueDate = parser.nextText();
                        } else if ("targetSum".equals(tagName)) {
                            targetSum = parser.nextText();
                        } else if ("savedSoFar".equals(tagName)) {
                            // Parse the savedSoFar value, if it exists
                            String savedSoFarText = parser.nextText();
                            savedSoFar = savedSoFarText.isEmpty() ? 0 : Integer.parseInt(savedSoFarText);
                            if (currentData != null) {
                                currentData.setSavedSoFar(savedSoFar);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tagName = parser.getName();
                        if ("data".equals(tagName) && currentData != null) {
                            // Set the parsed text to the current Datas object
                            currentData.setIncome(income);
                            currentData.setExpenses(expenses);
                            currentData.setDueDate(dueDate);
                            currentData.setTargetSum(targetSum);
                            // Add the fully constructed Datas object to the list
                            dataList.add(currentData);
                            // Reset variables for the next data entry
                            income = expenses = dueDate = targetSum = "";
                            savedSoFar = 0;
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
    /*
    public static void updateEntry(Context context, int id, String newIncome, String newExpenses, String newDueDate, String newTargetSum, int savedSoFar) {
        List<Datas> datasList = readFromXML(context);
        for (Datas data : datasList) {
            if (data.getId() == id) {
                data.setIncome(newIncome);
                data.setExpenses(newExpenses);
                data.setDueDate(newDueDate);
                data.setTargetSum(newTargetSum);
                data.setSavedSoFar(savedSoFar);
                break; // Stop loop once the matching ID is found and updated
            }
        }
        recreateXMLFile(context);
        saveXML(context, datasList); // Save the updated list back to XML
    }
     */

    public static void updateEntryNew(Context context, int id, String newIncome, String newExpenses, String newDueDate, String newTargetSum, Integer savedSoFar) {
        List<Datas> datasList = readFromXML(context);
        boolean isDataUpdated = false; // Flag to check if any data was updated

        for (Datas data : datasList) {
            if (data.getId() == id) {
                // Check if each parameter is not null and not empty, then update accordingly
                if (newIncome != null && !newIncome.trim().isEmpty()) data.setIncome(newIncome);
                if (newExpenses != null && !newExpenses.trim().isEmpty()) data.setExpenses(newExpenses);
                if (newDueDate != null && !newDueDate.trim().isEmpty()) data.setDueDate(newDueDate);
                if (newTargetSum != null && !newTargetSum.trim().isEmpty()) data.setTargetSum(newTargetSum);

                // Assuming savedSoFar being null means it's not being updated
                // And it's valid to have savedSoFar as 0 or any positive number
                if (savedSoFar != null && savedSoFar >= 0) data.setSavedSoFar(savedSoFar);

                isDataUpdated = true;
                break; // Stop loop once the matching ID is found and updated
            }
        }

        // Only save to XML if any data was actually updated
        if (isDataUpdated) {
            recreateXMLFile(context);
            saveXML(context, datasList); // Save the updated list back to XML
        }
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
        saveAndReassignID(context, datasList);
    }

    public static void recreateXMLFile(Context context) {
        File file = new File(context.getExternalFilesDir(null), FILENAME);

        // Try to delete the file if it exists
        if (file.exists()) {
            if (!file.delete()) {
                // If the file couldn't be deleted, return false
                return;
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

            }
        } catch (IOException e) {
            Log.e(TAG, "Error recreating XML file", e);
        }

    }

    public static void saveAndReassignID(Context context, List<Datas> datasList){
        int newID = 1;
        for(Datas data : datasList){
            data.setId(newID);
            newID++;
        }
        saveXML(context,datasList);
    }

    public static Datas getEntryById(Context context, int entryId) {
        List<Datas> dataList = new ArrayList<>();
        try {
            File file = new File(context.getExternalFilesDir(null), FILENAME);
            if (!file.exists()) {
                return null; // File doesn't exist, so no data to read
            }
            FileInputStream fis = new FileInputStream(file);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(fis, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && "data".equals(parser.getName())) {
                    int id = Integer.parseInt(parser.getAttributeValue(null, "id"));
                    if (id == entryId) {
                        // Initialize a new Datas object if the current ID matches the entryId
                        Datas currentData = new Datas(id, "", "", "", "", 0);
                        while (!(eventType == XmlPullParser.END_TAG && "data".equals(parser.getName()))) {
                            if (eventType == XmlPullParser.START_TAG) {
                                String tagName = parser.getName();
                                switch (tagName) {
                                    case "income":
                                        currentData.setIncome(parser.nextText());
                                        break;
                                    case "expenses":
                                        currentData.setExpenses(parser.nextText());
                                        break;
                                    case "dueDate":
                                        currentData.setDueDate(parser.nextText());
                                        break;
                                    case "targetSum":
                                        currentData.setTargetSum(parser.nextText());
                                        break;
                                    case "savedSoFar":
                                        currentData.setSavedSoFar(Integer.parseInt(parser.nextText()));
                                        break;
                                }
                            }
                            eventType = parser.next();
                        }
                        fis.close();
                        return currentData; // Return the found Datas object
                    }
                }
                eventType = parser.next();
            }
            fis.close();
        } catch (Exception e) {
            Log.e(TAG, "Error reading data from XML", e);
        }
        return null; // Return null if the entry is not found
    }

}