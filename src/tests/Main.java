package tests;

import java.util.Scanner;

import models.App;

import models.exceptions.DeadAvatarException;
import models.exceptions.DeadCharacterException;
import models.exceptions.EmptyDeckException;
import models.exceptions.NullSessionException;

public class Main {
    public static void main(String[] args) throws NullSessionException, EmptyDeckException, DeadAvatarException, DeadCharacterException {
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("Inscryption Lite");
            System.out.println("Inspired by Devolver Digital's Inscryption");
            System.out.print("Enter any key to start the game: ");
            input.nextLine();

            System.out.println();
            App.startSession();
            System.out.println("Game started!");

            // try {
            //     while (true) {
            //         System.out.println();
            //         System.out.println("=".repeat(80));
            //         System.out.println("Player 1's turn");
            //     }   
            // }
        }
    }
}
