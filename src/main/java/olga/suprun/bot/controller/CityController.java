package olga.suprun.bot.controller;

import olga.suprun.bot.exception.DBException;
import olga.suprun.bot.exception.NotFoundException;
import olga.suprun.bot.models.City;
import olga.suprun.bot.models.InformationAboutCity;
import olga.suprun.bot.service.CityService;
import olga.suprun.bot.service.InformationAboutCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/city")
public class CityController {
    /**
     * GET/city/ — получить список всех книг
     * GET /city/{id}/ — получить город с id
     * POST /city/ — добавить город (данные в теле запроса)
     * PUT /city/{id}/ – изменить город с id (данные в теле запроса)
     * DELETE /city/{id}/ – удалить город c id
     * GET /city/{id}/information/ — получить туристическую информацию о городе с id
     * GET /city/{id}/information/{idInform}/ — получить туристическую информацию с idInform о городе с id
     * POST  /city/{id}/information/ — добавить информацию о городе с id (данные в теле запроса)
     * PUT /city/{id}/information/{idInform}/ – изменить туристическую информацию с idInform о городе с id (данные в теле запроса)
     * DELETE /city/{id}/information/{idInform}/ – удалить туристическую информацию с idInform о городе с id
     */
    private CityService cityService;
    private InformationAboutCityService informationAboutCityService;

    @Autowired
    public CityController(CityService cityService, InformationAboutCityService informationAboutCityService) {
        this.cityService = cityService;
        this.informationAboutCityService = informationAboutCityService;
    }

    @GetMapping
    public List<Map<String, String>> getAllCities() {
        List<Map<String, String>> cities = new LinkedList<>();
        if (cityService.findAllCities() != null) {
            for (City city : cityService.findAllCities()
            ) {
                Map<String, String> cityToAdd = new HashMap<String, String>();
                cityToAdd.put("id", city.getId().toString());
                cityToAdd.put("nameOfCity", city.getNameOfCity());
                cities.add(cityToAdd);
            }
            return cities;
        } else {
            throw new NotFoundException("К сожалению, пока в списке нет городов");
        }
    }

    @GetMapping("/{id}")
    public Map<String, String> getCity(@PathVariable Long id) {
        Map<String, String> city = new HashMap<>();
        if (cityService.existsById(id)) {
            city.put("id", cityService.findById(id).getId().toString());
            city.put("nameOfCity", cityService.findById(id).getNameOfCity());
            return city;
        } else {
            throw new NotFoundException("К сожалению, нет города с id=" + id);
        }
    }

    @PostMapping
    public void createCity(@RequestBody Map<String, String> city) {
        if (!city.isEmpty() || city.get("nameOfCity") != null) {

            String nameOfCity = city.get("nameOfCity");
            try {
                cityService.createCity(nameOfCity);
            } catch (DBException e) {
                throw new NotFoundException(e.getMessage());
            }

        } else {
            throw new NotFoundException("Не указано название города");
        }
    }


    @PutMapping("/{id}")
    void update(@PathVariable Long id, @RequestBody Map<String, String> city) {
        if (city.get("nameOfCity").isEmpty()) {
            throw new NotFoundException("Нет нового названия города");
        }
        if (cityService.existsByNameOfCity(city.get("nameOfCity"))) {
            throw new NotFoundException("Город с новым названием уже существует");
        }
        if (cityService.existsById(id)) {
            City cityToUpdate = new City(city.get("nameOfCity"));
            try {
                cityService.updateCity(id, cityToUpdate);
            } catch (DBException e) {
                throw new NotFoundException(e.getMessage());
            }
        } else {
            throw new NotFoundException("Города с таким id " + id + " не существует");
        }
    }

    @DeleteMapping("/{id}")
    void deleteCity(@PathVariable Long id) {
        try {
            cityService.deleteCity(id);
        } catch (DBException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    @GetMapping("/{id}/information")
    public List<Map<String, String>> getInformationAboutCity(@PathVariable Long id) {
        if (cityService.existsById(id)) {
            if (!informationAboutCityService.findAllInformByCity(cityService.findById(id)).isEmpty()) {
                List<Map<String, String>> informationAboutCity = new LinkedList<>();
                for (InformationAboutCity information : informationAboutCityService.findAllInformByCity(cityService.findById(id))) {
                    Map<String, String> inform = new HashMap<>();
                    inform.put("id", information.getId().toString());
                    inform.put("information", information.getInformation().toString());
                    informationAboutCity.add(inform);
                }
                return informationAboutCity;
            } else {
                throw new NotFoundException("Информации о городе с id " + id + " нет");
            }
        } else {
            throw new NotFoundException("Города с таким id " + id + " нет");
        }
    }

    @GetMapping("/{id}/information/{idInform}")
    public Map<String, String> getInformationAboutCity(@PathVariable Long id, @PathVariable Long idInform) {
        if (cityService.existsById(id)) {
            InformationAboutCity informationFromDB = informationAboutCityService.findAllByCityAndAndId(cityService.findById(id), idInform);
            if (informationFromDB != null) {
                Map<String, String> informationAboutCity = new HashMap<>();
                informationAboutCity.put("information", informationFromDB.getInformation());
                return informationAboutCity;
            } else {
                throw new NotFoundException("Информации о городе с id " + id + " нет");
            }
        } else {
            throw new NotFoundException("Города с таким id " + id + " нет");
        }
    }

    @PostMapping("/{id}/information")
    public void createInformationAboutCity(@PathVariable Long id, @RequestBody Map<String, String> information) {
        if (!cityService.existsById(id)) {
            throw new NotFoundException("Города с таким id " + id + " нет");
        }
        if (!information.isEmpty() || information.get("information") != null) {
            try {
                informationAboutCityService.createInformationAboutCity(information.get("information"), cityService.findById(id));
            } catch (DBException e) {
                throw new NotFoundException(e.getMessage());
            }
        } else {
            throw new NotFoundException("Не указана информация о городе");
        }
    }

    @PutMapping("/{id}/information/{idInform}")
    void updateInformationAboutCity(@PathVariable Long id, @PathVariable Long idInform, @RequestBody Map<String, String> city) {
        if (cityService.existsById(id)) {
            if (informationAboutCityService.existsById(idInform)) {
                InformationAboutCity informationAboutCity = new InformationAboutCity(city.get("information"));
                informationAboutCity.setCity(cityService.findById(id));
                try {
                    informationAboutCityService.updateInformationAboutCity(idInform, informationAboutCity);
                } catch (DBException e) {
                    e.printStackTrace();
                }
            } else {
                throw new NotFoundException("Информации с таким id " + id + " не существует");
            }
        } else {
            throw new NotFoundException("Города с таким id " + id + " не существует");
        }
    }

    @DeleteMapping("/{id}/information/{idInform}")
    void deleteInformationAboutCity(@PathVariable Long id, @PathVariable Long idInform) {
        if (!cityService.existsById(id)) {
            throw new NotFoundException("Города с таким id " + id + " не существует");
        }
        try {
            informationAboutCityService.deleteInformationAboutCity(idInform);
        } catch (DBException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
}
