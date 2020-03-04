package homeworkweekfivethree.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Exchange {

  @JsonProperty("base")
  private String base;
  @JsonProperty("date")
  private String date;
  @JsonProperty("time_last_updated")
  private Integer timeLastUpdated;
  @JsonProperty("rates")
  private Map<String, BigDecimal> rates;

  public Exchange() {
    this.rates = new HashMap<>();
  }

  public String getBase() {
    return base;
  }

  public void setBase(String base) {
    this.base = base;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Integer getTimeLastUpdated() {
    return timeLastUpdated;
  }

  public void setTimeLastUpdated(Integer timeLastUpdated) {
    this.timeLastUpdated = timeLastUpdated;
  }

  public Map<String, BigDecimal> getRates() {
    return rates;
  }

  public void setRates(Map<String, BigDecimal> rates) {
    this.rates = rates;
  }
}
