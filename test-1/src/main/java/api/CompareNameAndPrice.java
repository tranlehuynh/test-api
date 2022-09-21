package api;

import com.google.gson.Gson;
import helper.sendRequest;
import object.Flavor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class CompareNameAndPrice {

    public static String getDataFromApi() {
        String URL = "https://portal3.vngcloud.vn/flavor-rest/flavor/list";
        Hashtable<String, String> header = new Hashtable<>();

        header.put("authority", "portal3.vngcloud.vn");
        header.put("accept", "application/json, text/plain, */*");
        header.put("accept-language", "en-US,en;q=0.9");
        header.put("cache-control", "no-cache");
        header.put("cookie", "SESSION=9810fae1-2e46-4e63-a42e-f5ebcc16437f");
        header.put("pragma", "no-cache");
        header.put("referer", "https://portal3.vngcloud.vn/servers/create.html");
        header.put("sec-ch-ua", "\" Not;A Brand\";v=\"99\", \"Microsoft Edge\";v=\"103\", \"Chromium\";v=\"103\"");
        header.put("sec-ch-ua-mobile", "?0");
        header.put("sec-ch-ua-platform", "\"macOS\"");
        header.put("sec-fetch-dest", "empty");
        header.put("sec-fetch-site", "same-origin");
        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.5060.134 Safari/537.36 Edg/103.0.1264.71");
        header.put("x-requested-with", "XMLHttpRequest");

        return sendRequest.sendGetRequest(URL, header);
    }

    public static List<Flavor> convertApiData(String data) {
        //string to json
        JSONObject json = new JSONObject(data);
        //get items key in response
        JSONArray items = json.getJSONArray("items");

        ArrayList<JSONObject> arrays = new ArrayList<>();
        ArrayList<Flavor> list = new ArrayList<>();

        for (int i = 0; i < items.length(); i++) {
            //add items key to json array
            JSONObject item = items.getJSONObject(i);
            arrays.add(item);

            //json to object
            Gson gson = new Gson();
            Flavor flavor = gson.fromJson(arrays.get(i).toString(), Flavor.class);
            list.add(flavor);
        }

        return list;
    }

    public static List<String> readFileCsv(String location) {
        List<String> list = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(location));

            while (sc.hasNext()) {
                list.add(sc.nextLine());
            }

            sc.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static List<Flavor> convertCsvData(List<String> data) {
        List<Flavor> list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            //split semi-colon from data items
            List<String> items = Arrays.asList(data.get(i).split("\\s*,\\s*"));

            Flavor flavor = new Flavor();

            flavor.setName(items.get(0));
            flavor.setCpu(Integer.parseInt(items.get(1)));
            flavor.setMemory(Integer.parseInt(items.get(2)));
            flavor.setDisk(Integer.parseInt(items.get(3)));
            flavor.setVolumeTypeId(items.get(4));
            flavor.setGpu(Integer.parseInt(items.get(5)));
            flavor.setBandwidth(Integer.parseInt(items.get(6)));
            flavor.setBandwidthUnit(items.get(7));
            flavor.setFlavorId(items.get(8));
            flavor.setWeight(Integer.parseInt(items.get(9)));
            flavor.setStatus(items.get(10));
            flavor.setZoneName(items.get(11));
            flavor.setZoneId(items.get(12));
            flavor.setPriceKey(items.get(13));
            flavor.setPrice(Integer.parseInt(items.get(14)));
            flavor.setZoneStatus(items.get(15));

            list.add(flavor);
        }

        return list;
    }

    public static void compareList(List<Flavor> listFlavorApiData, List<Flavor> listFlavorCsvData) {
        for (int i = 0; i < listFlavorApiData.size(); i++) {

            for (int j = 0; j < listFlavorCsvData.size(); j++) {

                if (listFlavorApiData.get(i).getName().equals(listFlavorCsvData.get(j).getName()) &&
                        listFlavorApiData.get(i).getPrice().equals(listFlavorCsvData.get(j).getPrice())) {

                    listFlavorApiData.remove(i);
                    listFlavorCsvData.remove(i);
                    j--;

                }
            }

            if (listFlavorCsvData.size() == 0)
                break;
        }
    }

    public static void writeFileCsv(List<Flavor> listFlavorApiData, List<Flavor> listFlavorCsvData) {

        //create writer
        try {
            PrintWriter writerApiData = new PrintWriter("listApiData.csv");
            PrintWriter writerCsvData = new PrintWriter("listCsvData.csv");

            List<String> listName = new ArrayList<>();
            List<String> listPrice = new ArrayList<>();
            List<String> listData = new ArrayList<>();

            if (listFlavorApiData.size() == 0 && listFlavorCsvData.size() == 0) {
                System.out.println("Pass");
            } else if (listFlavorApiData.size() == 0 && listFlavorCsvData.size() > 0) {
                System.out.println("Not pass");

                for (Flavor flavor : listFlavorCsvData) {
                    listName.add(flavor.getName());
                    listPrice.add(flavor.getPrice().toString());
                }

                for (int i = 0; i < listName.size(); i++) {
                    listData.add(i,  listName.get(i) + "," + listPrice.get(i));
                }

                for (String data : listData) {
                    writerCsvData.println(data);
                }

            } else if (listFlavorApiData.size() > 0 && listFlavorCsvData.size() == 0) {
                System.out.println("Missing");

                for (Flavor flavor : listFlavorApiData) {
                    listName.add(flavor.getName());
                    listPrice.add(flavor.getPrice().toString());
                }

                for (int i = 0; i < listName.size(); i++) {
                    listData.add(i,  listName.get(i) + "," + listPrice.get(i));
                }

                for (String data : listData) {
                    writerApiData.println(data);
                }
            } else if (listFlavorApiData.size() > 0 && listFlavorCsvData.size() > 0) {
                System.out.println("Error");

                List<String> listNameTemp = new ArrayList<>();
                List<String> listPriceTemp = new ArrayList<>();
                List<String> listDataTemp = new ArrayList<>();

                for (Flavor flavor : listFlavorApiData) {
                    listName.add(flavor.getName());
                    listPrice.add(flavor.getPrice().toString());
                }

                for (Flavor flavor : listFlavorCsvData) {
                    listNameTemp.add(flavor.getName());
                    listPriceTemp.add(flavor.getPrice().toString());
                }

                for (int i = 0; i < listName.size(); i++) {
                    listData.add(i,  listName.get(i) + "," + listPrice.get(i));
                }

                for (int i = 0; i < listNameTemp.size(); i++) {
                    listDataTemp.add(i,  listNameTemp.get(i) + "," + listPriceTemp.get(i));
                }

                for (String data : listData) {
                    writerApiData.println(data);
                }

                for (String data : listDataTemp) {
                    writerCsvData.println(data);
                }
            }

            writerApiData.close();
            writerCsvData.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String data = getDataFromApi();

        //get data from file csv, CHANGE LOCATION WHEN TEST
        String location = "C:\\\\Users\\\\LAP14593-local\\\\Desktop\\\\Book1.csv";
        List<String> dataFileCsv = readFileCsv(location);

        //convert data api and data csv file to list object
        List<Flavor> listFlavorApiData = convertApiData(data);
        List<Flavor> listFlavorCsvData = convertCsvData(dataFileCsv);

        //compare data api and data csv file
        compareList(listFlavorApiData, listFlavorCsvData);

        //write to new file csv after compare
        writeFileCsv(listFlavorApiData, listFlavorCsvData);
    }
}
