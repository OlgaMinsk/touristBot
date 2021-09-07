package olga.suprun.bot;

import olga.suprun.bot.models.City;
import olga.suprun.bot.models.InformationAboutCity;
import olga.suprun.bot.service.CityService;
import olga.suprun.bot.service.InformationAboutCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {

    private CityService cityService;
    private InformationAboutCityService informationAboutCityService;

    @Autowired
    public Bot(CityService cityService, InformationAboutCityService informationAboutCityService) {
        this.cityService = cityService;
        this.informationAboutCityService = informationAboutCityService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String messageToSend = "Введите, пожалуйста, название города. " +
                "Сейчас доступна информация только о областных центрах и столице Беларуси, скоро появятся и другие города. " +
                "Спасибо за понимание!";
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                if (cityService.existsByNameOfCity(message.getText())) {
                    City city = cityService.findByNameOfCity(message.getText().trim());
                    List<InformationAboutCity> information = informationAboutCityService.findAllInformByCity(city);
                    if (information.size() > 0) {
                        messageToSend = information.get((int) (Math.random() * (information.size()))).getInformation();
                    } else {
                        messageToSend = "Гуляйте по городу и наслаждайтесь! Достопримечательности мы посоветуем чуть позже :)";
                    }
                } else {
                    messageToSend = "хм. я ничего не знаю о городе " + message.getText() + " (Вы же уверены, что это город?). " +
                            "Мы уже ищем достопримечательности в этом городе. " +
                            "Пока информация доступна только о областных городах и столице Беларуси )))";
                }
            }
            try {
                execute(new SendMessage().builder().chatId(message.getChatId().toString())
                        .text(messageToSend).build());
                System.out.println(message.getText());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "@ReslivTourist_bot";
    }

    @Override
    public String getBotToken() {
        return "1950538374:AAEG8cDVd0CQYKAy8q22i0VfQ62tsXQOqXc";
    }
}
