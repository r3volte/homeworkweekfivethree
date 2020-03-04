package homeworkweekfivethree.service;

import com.vaadin.flow.component.textfield.TextField;
import homeworkweekfivethree.model.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class ExchangeService {

  private static final String URL = "https://api.exchangerate-api.com/v4/latest/PLN";
  private static final String EQUALS = "Gratki! Udało się za %s razem!";
  private static final String HIGH = "Za dużo";
  private static final String LOW = "Za mało";
  private RestTemplate restTemplate;
  private Logger logger = LoggerFactory.getLogger(this.getClass());
  private int scoreTries = 1;

  public ExchangeService() {
    this.restTemplate = new RestTemplate();
  }

  public String getRandomCurrency() {
    return getCurrencyNames()
            .get(getRandomInteger());
  }

  public String checkValueForRandomCurrency(double value, String text) {
    int result = getResult(value, text);
    if (result == 0) {
      return String.format(EQUALS, getScoreTries());
    } else if (result == -1) {
      setScoreTries(getScoreTries() + 1);
      return HIGH;
    } else {
      setScoreTries(getScoreTries() + 1);
      return LOW;
    }
  }

  public double getTextValue(TextField textField) {
    NumberFormat nf = NumberFormat.getInstance();
    double textValue = 0;
    try {
      textValue = nf.parse(textField.getValue()).doubleValue();
    } catch (ParseException e) {
      logger.error(e.getMessage());
    }
    return textValue;
  }

  private int getResult(double value, String text) {
    BigDecimal decimal = BigDecimal.valueOf(value);
    decimal = scaleHalfDown(decimal);
    return currencyValue(text)
            .compareTo(decimal);
  }

  private BigDecimal currencyValue(String value) {
    BigDecimal bigDecimal = rates().get(value);
    bigDecimal = scaleHalfDown(bigDecimal);
    return bigDecimal;
  }

  private BigDecimal scaleHalfDown(BigDecimal bigDecimal) {
    bigDecimal = bigDecimal
            .setScale(2, RoundingMode.HALF_DOWN);
    return bigDecimal;
  }

  private int getRandomInteger() {
    Random random = new Random();
    return random.nextInt(getCurrencyNames().size() - 1);
  }

  private List<String> getCurrencyNames() {
    return new ArrayList<>(rates()
            .keySet());
  }

  private Map<String, BigDecimal> rates() {
    return getExchangeRates().getRates();
  }

  private Exchange getExchangeRates() {
    return restTemplate.getForObject(URL, Exchange.class);
  }


  public int getScoreTries() {
    return scoreTries;
  }

  public void setScoreTries(int scoreTries) {
    this.scoreTries = scoreTries;
  }

}
