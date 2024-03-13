package com.example.terminkalender;

import org.apache.commons.lang3.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@ManagedBean
@SessionScoped
public class Terminkalender implements Serializable {
    private Date date;
    private String von;
    private String bis;
    private int zimmer;
    private String bemerkung;
    private String liste;
    private String publicCode;
    private String privateCode;



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getVon() {
        return von;
    }

    public void setVon(String von) {
        this.von = von;
    }

    public String getBis() {
        return bis;
    }

    public void setBis(String bis) {
        this.bis = bis;
    }

    public int getZimmer() {
        return zimmer;
    }

    public void setZimmer(int zimmer) {
        this.zimmer = zimmer;
    }

    public String getBemerkung() {
        return bemerkung;
    }

    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

    public String getListe() {
        return liste;
    }

    public void setListe(String liste) { this.liste = liste;
    }

    public String getPublicCode(){
        return publicCode;
    }

    public void setPublicCode(String publicCode){
        this.publicCode = publicCode;
    }

    public String getPrivateCode(){
        return privateCode;
    }

    public void setPrivateCode(String privateCode){
        this.privateCode = privateCode;
    }



    public String next() {
        return "/editReservation.xhtml";
    }
    public String save() {
        if (isAnyFieldEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Bitte füllen Sie alle Felder aus."));
            return null;
        }
        this.publicCode = KeyGenerator.generatePublicCode();
        this.privateCode = KeyGenerator.generatePrivateCode();

        Reservation newReservation = new  Reservation();
        newReservation.setDate(date);
        newReservation.setVon(von);
        newReservation.setBis(bis);
        newReservation.setZimmer(zimmer);
        newReservation.setBemerkung(bemerkung);
        newReservation.setliste(liste);
        newReservation.setPublicCode(publicCode);
        newReservation.setPrivateCode(privateCode);

        ReservationDatabase.addReservation(newReservation);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Reservation saved successfully!"));
        return "/showReservation.xhtml?faces-redirect=true";
    }
    private boolean isAnyFieldEmpty() {
        return StringUtils.isEmpty(getVon())
                || StringUtils.isEmpty(getBis())
                || getZimmer() == 0
                || StringUtils.isEmpty(getBemerkung())
                || StringUtils.isEmpty(getListe());
    }
    public List<Reservation> getReservations() {
        return ReservationDatabase.getReservations();
    }

    public String all() {
        List<Reservation> reservations = getReservations();
        return "/AllSavedReservation.xhtml";
    }

}
