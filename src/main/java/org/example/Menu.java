package org.example;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.HashMap;
import java.util.TreeMap;

public class Menu {
    public static HashMap<String, MenuOption> menuOptionMap = new HashMap<>();

    public static void addMenuOption(String optionKey, String optionName, Runnable optionOperation) {
        menuOptionMap.put(optionKey.toLowerCase(), new MenuOption(optionName, optionOperation));
    }

    public static class MenuOption {
        private final String optionName;
        private final Runnable optionOperation;

        public MenuOption(String optionName, Runnable optionOperation) {
            this.optionName = optionName;
            this.optionOperation = optionOperation;
        }

        public void execute() {
            this.optionOperation.run();
        }

        public String getOptionName() {
            return optionName;
        }
    }

    public static String getMenuTable() {
        Table menuTable = new Table(6, BorderStyle.UNICODE_BOX, ShownBorders.NONE);
        String[] optionOrder = {"W", "R", "U", "D", "S", "Se", "Sa", "Un", "E"};
        for (String optionKey : optionOrder) {
            menuTable.addCell("  " + optionKey + "). " + menuOptionMap.get(optionKey.toLowerCase()).optionName + "  ");
        }

        return menuTable.render();
    }
}

