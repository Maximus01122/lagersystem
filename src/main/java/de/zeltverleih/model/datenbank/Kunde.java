package de.zeltverleih.model.datenbank;
import javax.persistence.*;
import java.util.Objects;

@Entity
public class Kunde {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 60)
    private String email;
    @Column
    private String name;

    @Column(nullable = false)
    private String telefonnummer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adresse_id", nullable = false)
    private Adresse adresse;


    @Column(nullable = false)
    private int kundennummer;

    public Kunde() {
    }

    public Kunde(String name, String email,
                 String telefonnummer, Adresse adresse) {
        this.name = name;
        this.telefonnummer = telefonnummer;
        this.email = email;
        this.adresse = adresse;
    }

    public int getKundennummer() {
        return kundennummer;
    }

    public void setKundennummer(int kundennummer) {
        this.kundennummer = kundennummer;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null){
            if (name.isBlank() || name.isEmpty())
                throw new IllegalArgumentException("Vorname darf nicht leer sein");
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && !email.equals("")){
            if (!email.contains("@")){
                throw new IllegalArgumentException("Email muss @ enthalten");
            }

            if(!email.contains("."))
                throw new IllegalArgumentException("Email muss \".\" enthalten");
        }
        this.email = email;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    @Override
    public String toString() {
        return "Teilnehmer: \n" +
                "vorname " + name +
                ", email " + email  +
                ", telefonnummer " + telefonnummer +
                ", adresse " + adresse.toString();
    }

    public void checkAttribute(){
        setAdresse(getAdresse());
        setEmail(getEmail());
        setName(getName());
        setTelefonnummer(getTelefonnummer());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kunde kunde = (Kunde) o;
        return id == kunde.id && kundennummer == kunde.kundennummer && Objects.equals(email, kunde.email) && Objects.equals(name, kunde.name) && Objects.equals(telefonnummer, kunde.telefonnummer) && Objects.equals(adresse, kunde.adresse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, telefonnummer, adresse, kundennummer);
    }
}
