package com.example.terminkalender;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Reservation implements Serializable {
    private Date date;
    private String von;
    private String bis;
    private int zimmer;
    private String bemerkung;
    private String liste;
    private String privateCode;
    private String publicCode;

    public Reservation(String date, String von, String bis, int zimmer, String bemerkung, String liste) {
        this.date = parseDate(date);
        this.von = von;
        this.bis = bis;
        this.zimmer = zimmer;
        this.bemerkung = bemerkung;
        this.liste = liste;
        this.privateCode = KeyGenerator.generatePublicCode();
        this.publicCode = KeyGenerator.generatePrivateCode();


        saveToFile(this);
    }

    public Reservation() {

    }


    private Date parseDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveToFile(Reservation reservation) {
        List<Reservation> reservations = loadFromFile();

        if (reservations == null) {
            reservations = new ArrayList<>();
        }

        reservations.add(reservation);

        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get("reservation.txt")))) {
            oos.writeObject(reservations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Reservation> loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get("reservation.txt")))) {
            return (List<Reservation>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
    public String save(String date, String von, String bis, int zimmer, String bemerkung, String liste) {
        if (isAnyFieldEmpty(date, von, bis, zimmer, bemerkung, liste)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Bitte füllen Sie alle Felder aus."));
            return null;
        }

        Reservation newReservation = new Reservation(date, von, bis, zimmer, bemerkung, liste);
        ReservationDatabase.addReservation(newReservation);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Reservation saved successfully!"));
        return "/showReservation.xhtml?faces-redirect=true";
    }

    private boolean isAnyFieldEmpty(String date, String von, String bis, int zimmer, String bemerkung, String liste) {
        return date == null || date.trim().isEmpty() ||
                von == null || von.trim().isEmpty() ||
                bis == null || bis.trim().isEmpty() ||
                zimmer <= 0 ||
                bemerkung == null || bemerkung.trim().isEmpty() ||
                liste == null || liste.trim().isEmpty();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setVon(String von) {
        this.von = von;
    }

    public String getVon() {
        return von;
    }

    public void setBis(String bis) {
        this.bis = bis;
    }

    public String getBis() {
        return bis;
    }

    public void setZimmer(int zimmer) {
        this.zimmer = zimmer;
    }

    public int getZimmer() {
        return zimmer;
    }

    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

    public String getBemerkung() {
        return bemerkung;
    }

    public void setliste(String liste) {
        this.liste = liste;
    }
    public String getListe(){
        return liste;
    }

    public void setPublicCode(String publicCode) {
        this.publicCode = publicCode;
    }

    public String getPublicCode() {
        return publicCode;
    }

    public void setPrivateCode(String privateCode) {
        this.privateCode = privateCode;
    }

    public String getPrivateCode() {
        return privateCode;
    }

}






