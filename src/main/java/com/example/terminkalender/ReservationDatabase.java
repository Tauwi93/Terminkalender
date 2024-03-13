package com.example.terminkalender;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReservationDatabase {
    private static List<Reservation> reservations = new ArrayList<>();
    private static final String FILE_PATH = "reservation.txt";

    static {
        loadReservationsFromFile();
    }

    public static List<Reservation> getReservations() {
        return reservations;
    }

    public static void addReservation(Reservation reservation) {
        reservations.add(reservation);
        saveReservationsToFile();
    }

    private static void loadReservationsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(FILE_PATH)))) {
            reservations = (List<Reservation>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not load reservations from file. Starting with an empty list.");
        }
    }

    private static void saveReservationsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(reservations);
        } catch (IOException e) {
            System.out.println("Could not save reservations to file. Trying to create a new file.");

            try {
                File file = new File(FILE_PATH);
                if (file.createNewFile()) {
                    System.out.println("New file created: " + FILE_PATH);
                    saveReservationsToFile();
                } else {
                    System.out.println("Failed to create a new file.");
                }
            } catch (IOException ex) {
                System.out.println("Error creating a new file.");
            }
        }
    }
}



