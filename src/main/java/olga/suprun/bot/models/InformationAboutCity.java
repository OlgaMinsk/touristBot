package olga.suprun.bot.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "information_about_city")
public class InformationAboutCity {
    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "information",
            unique = true,
            nullable = false)
    private String information;

    public InformationAboutCity() {
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public InformationAboutCity(String information) {
        this.information = information;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @ManyToOne
    @JoinColumn(name = "cities_id")
    private City city;

    @Override
    public String toString() {
        return "Information= " + information + ", city" + city.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InformationAboutCity that = (InformationAboutCity) o;
        return Objects.equals(id, that.id) && Objects.equals(information, that.information);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
