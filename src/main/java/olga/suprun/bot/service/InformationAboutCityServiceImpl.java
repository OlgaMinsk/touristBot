package olga.suprun.bot.service;

import olga.suprun.bot.exception.DBException;
import olga.suprun.bot.models.City;
import olga.suprun.bot.models.InformationAboutCity;
import olga.suprun.bot.repo.InformationAboutCityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformationAboutCityServiceImpl implements InformationAboutCityService {

    private InformationAboutCityRepository informationAboutCityRepository;

    @Autowired
    public InformationAboutCityServiceImpl(InformationAboutCityRepository informationAboutCityRepository) {
        this.informationAboutCityRepository = informationAboutCityRepository;
    }

    public List<InformationAboutCity> findAllInformByCity(City city) {
        return informationAboutCityRepository.findAllByCity(city);
    }

    @Override
    public InformationAboutCity findAllByCityAndAndId(City city, Long id) {
        return informationAboutCityRepository.findAllByCityAndAndId(city, id);
    }

    @Override
    public void createInformationAboutCity(String information, City city) throws DBException {
        if (!informationAboutCityRepository.existsByInformationAndCity(information, city)) {
            InformationAboutCity informationAboutCity = new InformationAboutCity(information);
            informationAboutCity.setCity(city);
            informationAboutCityRepository.save(informationAboutCity);
        } else {
            throw new DBException("Такая информация о городе уже существует");
        }
    }

    @Override
    public void updateInformationAboutCity(Long idInform, InformationAboutCity informationAboutCity) throws DBException {
        if (informationAboutCityRepository.existsById(idInform)) {
            if (!informationAboutCityRepository.existsByInformationAndCity(informationAboutCity.getInformation(), informationAboutCity.getCity())) {
                informationAboutCity.setId(idInform);
                informationAboutCityRepository.save(informationAboutCity);
            } else {
                throw new DBException("Такая информация о городе уже есть");
            }
        } else {
            throw new DBException("Информации с таким id " + idInform + " нет");
        }
    }

    @Override
    public void deleteInformationAboutCity(Long idInform) throws DBException {
        if (informationAboutCityRepository.existsById(idInform)) {
            informationAboutCityRepository.delete(informationAboutCityRepository.findInformationAboutCityById(idInform));
        } else {
            throw new DBException("Информации с таким id " + idInform + " не существует");
        }
    }

    @Override
    public boolean existsById(Long id) {
        return informationAboutCityRepository.existsById(id);
    }

}
