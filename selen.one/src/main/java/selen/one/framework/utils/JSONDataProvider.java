package selen.one.framework.utils;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;

public class JSONDataProvider {

	public static String dataFile = "";
	public static String testCaseName = "";

	@SuppressWarnings("unchecked")
	@DataProvider(name = "dataFromJSON")
	public static Object[][] getData(Method method) {
		Object rowID, description;
		Object [][] retVal;
		testCaseName = method.getName();
		List<JSONObject> testDataList = (List<JSONObject>) extractDataFromJSON(dataFile).get(testCaseName);
		
		try {
			retVal = new Object[testDataList.size()][testDataList.get(0).size()];
			for (int i = 0; i < testDataList.size(); i++ ) {
				rowID = testDataList.get(i).get("rowID");
				description = testDataList.get(i).get("description");
				retVal[i] = new Object[] { rowID, description, testDataList.get(i) };
			}
		} catch ( IndexOutOfBoundsException e) {
			retVal = new Object[0][];
			e.printStackTrace();
		}
//		Arrays.deepToString(retVal);
		return retVal;
	}

	public static JSONObject extractDataFromJSON(String file) {
		try {
			return (JSONObject) new JSONParser().parse(new FileReader(file));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
