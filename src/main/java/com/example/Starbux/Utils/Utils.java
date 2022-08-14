package com.example.Starbux.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {
    public Utils() {}

    // Converts string to lowercase and removes whitespace from both ends
    public static String convertString(String str) {

        // Handling if null is passed
        if (str != null) {
            String converted = str.trim().toLowerCase();
            return converted;
        }
        else return str;
    }

    public static List<String> convertStringArray(List<String> items) {
        if (items != null) {
            List<String> converted = new ArrayList<String>();
                for (String item : items) {
                    if (item != null) {
                        String convertedStr = item.trim().toLowerCase();
                        converted.add(convertedStr);
                    }
                }
                return converted;
            }
        return items;
    }

/*     public static List<Long> getMostCommonElement(List<Long> data){
        Long mostCommon = null;
        List<Long> frequents = new ArrayList<Long>();

        if (data != null) {
            List<Long> sorted = data.stream().sorted().collect(Collectors.toList()); 
            Long last = null;
            int mostCount = 0;
            int lastCount = 0;

            for (Long item : sorted) {
                if (item.equals(last)) {
                    lastCount++;
                } else if (lastCount > mostCount) {
                    mostCount = lastCount;
                    mostCommon = last;
                    frequents = new ArrayList<Long>();
                    frequents.add(mostCommon);
                }
                if(lastCount == mostCount){
                    frequents.add(last);
                }
                last = item;
            }

            // In case the list doesn't have any unique values
            if(mostCommon == null) frequents.add(last);
        }
        return frequents;
    } */

    public static List<Long> getMostCommonElement(List<Long> arr){
        Map<Long, Integer> count = new HashMap<Long, Integer>();
        ArrayList<Long> list = new ArrayList<Long>();

        if (arr != null) {
        for (Long element : arr) {
            if (!count.containsKey(element)) {
                count.put(element,0);
            }
            count.put(element, count.get(element) + 1);
        }
    
        Map.Entry<Long, Integer> maxEntry = null;
    
        for (Map.Entry<Long, Integer> entry : count.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                list.clear();
                list.add(entry.getKey());
                maxEntry = entry;
            }
            else if (entry.getValue() == maxEntry.getValue()) {
                list.add(entry.getKey());
            }
        } }
        return list;
    }

}
