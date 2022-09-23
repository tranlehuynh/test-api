package api;

import com.google.gson.Gson;
import helper.sendRequest;
import object.Flavor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CompareNameAndPrice {

    public static String getDataFromApi() {
        String URL = "https://portal3.vngcloud.vn/flavor-rest/flavor/list";
        Hashtable<String, String> header = new Hashtable<>();

        header.put("authority", "portal3.vngcloud.vn");
        header.put("accept", "application/json, text/plain, */*");
        header.put("accept-language", "en-US,en;q=0.9");
        header.put("cache-control", "no-cache");
        header.put("cookie", "SESSION=37935a31-70b3-4e94-b355-ec549a0820fb");
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

    public static List<Flavor> convertCsvData2(List<String> data) {
        List<Flavor> list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            //split semi-colon from data items
            List<String> items = Arrays.asList(data.get(i).split("\\s*,\\s*"));

            Flavor flavor = new Flavor();

//            String myName = removeAllSpaceAndSpecialCharacterInString(items.get(0));
            flavor.setName(items.get(0));

            if (items.size() == 2) {
                flavor.setPrice(Integer.parseInt(items.get(1)));
//                if (checkStringHasSpecialCharacter(items.get(1)) == true) {
//                    String kept = items.get(1).substring( 0, items.get(1).indexOf("."));
//                    flavor.setPrice(Integer.parseInt(kept));
//                } else {
//                    flavor.setPrice(Integer.parseInt(items.get(1)));
//                }
            } else if (items.size() == 1){
                flavor.setPrice(1);
            }

            list.add(flavor);
        }

        return list;
    }

    public static void compareList(List<Flavor> listFlavorApiData, List<Flavor> listFlavorCsvData) {
        for (int i = 0; i < listFlavorApiData.size(); i++) {

            for (int j = 0; j < listFlavorCsvData.size(); j++) {

                String temp1 = removeAllSpaceAndSpecialCharacterInString(listFlavorApiData.get(i).getName()).toLowerCase();
                String temp2 = removeAllSpaceAndSpecialCharacterInString(listFlavorCsvData.get(j).getName()).toLowerCase();

                if (temp1.equals(temp2) &&
                        listFlavorApiData.get(i).getPrice().equals(listFlavorCsvData.get(j).getPrice())) {

                    listFlavorApiData.remove(i);
                    j = 0;
                    i = 0;
                }
            }
        }
    }

    public static List<Flavor> compareList2(List<Flavor> listFlavorApiData, List<Flavor> listFlavorCsvData2) {
        List<Flavor> listFlavorTemp = new ArrayList<>();
        for (Flavor emp : listFlavorApiData) {
            boolean found = false;
            for (Flavor tgtEmp : listFlavorCsvData2) {
                if ((removeAllSpaceAndSpecialCharacterInString(emp.getName().toLowerCase()).equals(removeAllSpaceAndSpecialCharacterInString(tgtEmp.getName().toLowerCase()))) && (emp.getPrice().equals(tgtEmp.getPrice()))) {
                    found =true;
                    break;
                }
            }
            if(!found){
                listFlavorTemp.add(emp);
            }
        }

        return listFlavorTemp;
    }

    public static List<Flavor> compareList3(List<Flavor> listFlavorApiData, List<Flavor> listFlavorCsvData2) {
        List<Flavor> listFlavorTemp = new ArrayList<>();
        for (Flavor emp : listFlavorCsvData2) {
            boolean found = false;
            for (Flavor tgtEmp : listFlavorApiData) {
                if ((removeAllSpaceAndSpecialCharacterInString(emp.getName().toLowerCase()).equals(removeAllSpaceAndSpecialCharacterInString(tgtEmp.getName().toLowerCase()))) && (emp.getPrice().equals(tgtEmp.getPrice()))) {
                    found =true;
                    break;
                }
            }
            if(!found){
                listFlavorTemp.add(emp);
            }
        }

        return listFlavorTemp;
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

    public static void writeFileCsv2(List<Flavor> listFlavorApiData) {
        try {
            PrintWriter writerApiData = new PrintWriter("listApiData.csv");
            List<String> listName = new ArrayList<>();
            List<String> listPrice = new ArrayList<>();
            List<String> listData = new ArrayList<>();

            for (Flavor flavor : listFlavorApiData) {
                listName.add(flavor.getName());
                listPrice.add(flavor.getPrice().toString());
            }

            for (int i = 0; i < listFlavorApiData.size(); i++) {
                listData.add(i,  listName.get(i) + "," + listPrice.get(i));
            }

            for (String sample : listData) {
                writerApiData.println(sample);
            }

            writerApiData.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void writeFileCsv3(List<Flavor> listFlavorApiData) {
        try {
            PrintWriter writerApiData = new PrintWriter("listCsvData.csv");
            List<String> listName = new ArrayList<>();
            List<String> listPrice = new ArrayList<>();
            List<String> listData = new ArrayList<>();

            for (Flavor flavor : listFlavorApiData) {
                listName.add(flavor.getName());
                listPrice.add(flavor.getPrice().toString());
            }

            for (int i = 0; i < listFlavorApiData.size(); i++) {
                listData.add(i,  listName.get(i) + "," + listPrice.get(i));
            }

            for (String sample : listData) {
                writerApiData.println(sample);
            }

            writerApiData.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    public static String removeAllSpaceAndSpecialCharacterInString(String data) {
        char[] strArray = data.toCharArray();

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < strArray.length; i++) {
            if ((strArray[i] != ' ') && (strArray[i] != '\t')) {
                stringBuffer.append(strArray[i]);
            }
        }

        String noSpaceStr = stringBuffer.toString();
        String newString = noSpaceStr.replaceAll("[^a-zA-Z0-9]", "");
        return newString;
    }

    public static boolean checkStringHasSpecialCharacter(String string) {
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        boolean b = m.find();

        if (b) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        String data = getDataFromApi();

        //get data from file csv, CHANGE LOCATION WHEN TEST
        String location = "C:\\\\Users\\\\LAP14593-local\\\\Desktop\\\\file-csv.csv";
        List<String> dataFileCsv = readFileCsv(location);

        //convert data api and data csv file to list object
        List<Flavor> listFlavorApiData = convertApiData(data);
        System.out.println(listFlavorApiData.size());
//        List<Flavor> listFlavorCsvData = convertCsvData(dataFileCsv);
        List<Flavor> listFlavorCsvData2 = convertCsvData2(dataFileCsv);
        System.out.println(listFlavorCsvData2.size());

        //compare data api and data csv file
        List<Flavor> newListFlavor = compareList2(listFlavorApiData, listFlavorCsvData2);
        List<Flavor> newListFlavor2 = compareList3(listFlavorApiData, listFlavorCsvData2);

        //write to file csv
        writeFileCsv2(newListFlavor);
        writeFileCsv3(newListFlavor2);

        System.out.println(newListFlavor2.size());
        System.out.println(newListFlavor.size());
    }
}
