package olga.suprun.bot.service;

import olga.suprun.bot.exception.DBException;
import olga.suprun.bot.models.City;
import olga.suprun.bot.repo.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CityServiceImpl implements CityService {
    private CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public boolean existsByNameOfCity(String nameOfCity) {
        return cityRepository.existsByNameOfCity(nameOfCity);
    }

    @Override
    public boolean existsById(Long id) {
        return cityRepository.existsById(id);
    }

    @Override
    public City findByNameOfCity(String nameOfCity) {
        return cityRepository.findByNameOfCity(nameOfCity);
    }

    @Override
    public City findById(Long id) {
        return cityRepository.findCityById(id);
    }

    @Override
    public Set<City> findAllCities() {
        HashSet<City> cities = new HashSet<>();
        for (City city : cityRepository.findAll()
        ) {
            cities.add(city);
        }
        return cities;
    }

    @Override
    public void createCity(String nameOfCity) throws DBException {
        if (cityRepository.existsByNameOfCity(nameOfCity)) {
            throw new DBException("Такой город " + nameOfCity + " уже существует");
        }
        City city = new City(nameOfCity);
        cityRepository.save(city);
    }

    @Override
    public void updateCity(Long id, City newCity) throws DBException {
        if (cityRepository.existsById(id)) {
            newCity.setId(id);
            cityRepository.save(newCity);
        } else {
            throw new DBException("Города с таким id= " + id + " не существует");
        }
    }

    @Override
    public void deleteCity(Long id) throws DBException {
        if (cityRepository.existsById(id)) {
            cityRepository.delete(cityRepository.findCityById(id));
        } else {
            throw new DBException("Города с таким id= " + id + " не существует");
        }
    }
}
