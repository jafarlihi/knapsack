package com.mobiquity;

import com.mobiquity.model.Item;
import com.mobiquity.model.Package;

import java.util.ArrayList;
import java.util.List;

// Takes in list of strings as input and parses them into Item and Package POJOs.
public class Parser {

    private List<String> content;

    public Parser(List<String> content) {
        this.content = content;
    }

    public List<Package> parse() {
        List<Package> packages = new ArrayList<>();
        for (String taskString : content) {
            Integer maxWeight = Integer.parseInt(taskString.split(":", 2)[0].trim());
            List<Item> items = new ArrayList<>();
            int itemStringStart = -1;
            for (int i = 0; i < taskString.length(); i++) {
                if (taskString.charAt(i) == '(') {
                    itemStringStart = i;
                } else if (taskString.charAt(i) == ')' && itemStringStart != -1) {
                    items.add(parseItemString(taskString.substring(itemStringStart, i)));
                    itemStringStart = -1;
                }
            }
            packages.add(new Package(maxWeight, items));
        }
        return packages;
    }

    private Item parseItemString(String itemString) {
        String[] itemStringSplit = itemString.split(",", 3);
        Float weight = Float.parseFloat(itemStringSplit[1]);
        Integer price = Integer.parseInt(itemStringSplit[2].replace('€', ' ').trim());
        return new Item(weight, price);
    }
}
