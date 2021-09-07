package olga.suprun.bot.service;

import olga.suprun.bot.exception.DBException;
import olga.suprun.bot.models.City;

import java.util.Set;

public interface CityService {
    boolean existsByNameOfCity(String nameOfCity);

    boolean existsById(Long id);

    City findByNameOfCity(String nameOfCity);

    City findById(Long id);

    Set<City> findAllCities();

    void createCity(String nameOfCity) throws DBException;

    void updateCity(Long id, City newCity) throws DBException;

    void deleteCity(Long id) throws DBException;
}
