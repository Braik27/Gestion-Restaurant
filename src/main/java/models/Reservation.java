package models;

import java.sql.Time;
import java.util.Date;

public class Reservation {
    int id,nombre_personnes;
    String nom,email,telephone;
    Date date_reservation;
    Time heure_reservation;


    public Reservation(int id, int nombre_personnes, String nom, String email, String telephone, Date date_reservation, Time heure_reservation) {
        this.id = id;
        this.nombre_personnes = nombre_personnes;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.date_reservation = date_reservation;
        this.heure_reservation = heure_reservation;
    }

    public Reservation(int nombre_personnes, String nom, String email, String telephone, Date date_reservation, Time heure_reservation) {
        this.nombre_personnes = nombre_personnes;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.date_reservation = date_reservation;
        this.heure_reservation = heure_reservation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNombre_personnes() {
        return nombre_personnes;
    }

    public void setNombre_personnes(int nombre_personnes) {
        this.nombre_personnes = nombre_personnes;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getDate_reservation() {
        return date_reservation;
    }

    public void setDate_reservation(Date date_reservation) {
        this.date_reservation = date_reservation;
    }

    public Time getHeure_reservation() {
        return heure_reservation;
    }

    public void setHeure_reservation(Time heure_reservation) {
        this.heure_reservation = heure_reservation;
    }


    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", nombre_personnes=" + nombre_personnes +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", date_reservation=" + date_reservation +
                ", heure_reservation=" + heure_reservation +
                '}';
    }
}
