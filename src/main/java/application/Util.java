package application;

import java.util.List;

public final class Util {

    public static int validateIntegerInput(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void displayInfoText(List<String> textItems) {
        textItems.forEach(System.out::println);
    }
}
