package olga.suprun.bot.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name_of_city", unique = true, nullable = false)
    private String nameOfCity;

    public City() {

    }

    public City(String nameOfCity) {
        this.nameOfCity = nameOfCity;
    }

    @OneToMany(mappedBy = "city",
            cascade = CascadeType.ALL)
    private List<InformationAboutCity> informationAboutCity = new LinkedList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOfCity() {
        return nameOfCity;
    }

    public void setNameOfCity(String nameOfCity) {
        this.nameOfCity = nameOfCity;
    }

    @Override
    public String toString() {
        return "City: " + nameOfCity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        City city = (City) o;
        return city.getId() == this.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
