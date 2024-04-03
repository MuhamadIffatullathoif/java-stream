package org.iffat.resource_bundle_project;

import javax.swing.*;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {

    public static void main(String[] args) {

        for (Locale locale : List.of(Locale.US, Locale.CANADA_FRENCH, Locale.CANADA)) {
            ResourceBundle rb = ResourceBundle.getBundle("BasicText", locale);
//            System.out.println(rb.getClass().getName());
//            System.out.println(rb.getBaseBundleName());
//            System.out.println(rb.keySet());

            String message = "%s %s!%n".formatted(rb.getString("hello"), rb.getString("world"));

            ResourceBundle ui = ResourceBundle.getBundle("UIComponents", locale);

            JOptionPane.showOptionDialog(null,
                    message,
                    ui.getString("first.title"),
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{rb.getString("yes"), rb.getString("no")},
                    null);
        }
    }
}
