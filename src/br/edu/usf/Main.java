package br.edu.usf;

import org.bson.Document;

import java.text.MessageFormat;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            DBConnection.gi().insert(Randomizes.name(), Randomizes.address());
        }

        final List<Document> all = DBConnection.gi().findAll();
        for (Document document : all) {
            final Object name = document.get("name");
            final Object address = document.get("address");

            System.out.println(MessageFormat.format("Name: {0}, Address: {1}", name, address));
        }
    }
}
