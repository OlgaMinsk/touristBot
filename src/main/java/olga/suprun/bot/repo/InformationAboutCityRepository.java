package olga.suprun.bot.repo;

import olga.suprun.bot.models.City;
import olga.suprun.bot.models.InformationAboutCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformationAboutCityRepository extends JpaRepository<InformationAboutCity, Long> {
    List<InformationAboutCity> findAllByCity(City city);

    InformationAboutCity findAllByCityAndAndId(City city, Long id);

    boolean existsByInformationAndCity(String information, City city);

    boolean existsById(Long id);

    InformationAboutCity findInformationAboutCityById(Long id);
}
