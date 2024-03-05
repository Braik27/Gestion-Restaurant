package models;

public class Restaurant {

    int id ;
    String nom ;
    String adresse;
    String type ;
    String img ;



    public Restaurant(int id, String nom, String adresse, String type, String img) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.type = type;
        this.img = img;
    }

    public Restaurant(String nom, String adresse, String type, String img) {
        this.nom = nom;
        this.adresse = adresse;
        this.type = type;
        this.img = img;
    }

    public Restaurant() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", type='" + type + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
