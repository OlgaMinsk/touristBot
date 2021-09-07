package olga.suprun.bot.service;

import olga.suprun.bot.exception.DBException;
import olga.suprun.bot.models.City;
import olga.suprun.bot.models.InformationAboutCity;

import java.util.List;

public interface InformationAboutCityService {
    List<InformationAboutCity> findAllInformByCity(City city);

    InformationAboutCity findAllByCityAndAndId(City city, Long id);

    void createInformationAboutCity(String information, City city) throws DBException;

    void updateInformationAboutCity(Long idInform, InformationAboutCity informationAboutCity) throws DBException;

    void deleteInformationAboutCity(Long idInform) throws DBException;

    boolean existsById(Long id);
}
