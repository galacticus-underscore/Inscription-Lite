package models.processors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;

import models.Card;
import models.Character;
import models.Sigil;

public class CardDataProcessor {
    public static Stack<Card> read(File csv) {
        Stack<Card> out = new Stack<Card>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(csv));
            String line = "";
            String[] card_data;
            boolean skipped_headers = false;

            while ((line = br.readLine()) != null) {
                if (!skipped_headers) {
                    skipped_headers = true;
                    continue;
                }
                
                card_data = Arrays.stream(line.split(","))
                    .filter(x -> !x.isEmpty())
                    .toArray(String[]::new);

                switch (card_data[0].charAt(0)) {
                    case 'C':
                        out.push(new Character(
                            card_data[1],
                            card_data[2],
                            Integer.parseInt(card_data[3]),
                            Integer.parseInt(card_data[4]),
                            Integer.parseInt(card_data[5])
                        ));
                        break;
                    case 'S':
                        out.push(new Sigil(
                            card_data[1],
                            card_data[2],
                            Integer.parseInt(card_data[3]),
                            Boolean.parseBoolean(card_data[4]),
                            Boolean.parseBoolean(card_data[5]),
                            card_data[6]
                        ));
                        break;
                }
            }

            br.close();
            return out;
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }
}
