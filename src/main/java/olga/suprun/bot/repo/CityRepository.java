package olga.suprun.bot.repo;

import olga.suprun.bot.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    boolean existsByNameOfCity(String nameOfCity);

    boolean existsById(Long id);

    City findCityById(Long id);

    City findByNameOfCity(String nameOfCity);
}
