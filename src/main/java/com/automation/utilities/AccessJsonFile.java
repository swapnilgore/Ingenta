package com.automation.utilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class AccessJsonFile {
    public JSONObject jsonObject;

    private JSONParser parser = new JSONParser();
    private static String fileSeparatot = System.getProperty("file.separator");
    private static String projectNameDirectory = System.getProperty("user.dir");
    public static String testDataFilePath = projectNameDirectory + "/src/test/resources/user_data.json".replace("/", fileSeparatot);

    public AccessJsonFile() throws IOException, ParseException {
        this(testDataFilePath);//
    }

    public AccessJsonFile(String filePath) throws IOException, ParseException {
        jsonObject = (JSONObject) parser.parse(new BufferedReader(new FileReader(filePath)));
    }

    public JSONObject getInnerJson(String keyValue) {
        return (JSONObject) jsonObject.get(keyValue);
    }

    public String getString(String keyValue) {
        return (String) jsonObject.get(keyValue);
    }

    public JSONArray getArray(String keyValue) {
        return (JSONArray) jsonObject.get(keyValue);
    }
}
