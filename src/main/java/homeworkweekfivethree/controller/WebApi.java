package homeworkweekfivethree.controller;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import homeworkweekfivethree.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;


@Route("currency")
public class WebApi extends VerticalLayout {

  private static String currency;
  private static final String INPUT_VALUE = "Wartosc";
  private static final String CHECK = "Sprawdz !";
  private static final String MSG = "Wprowadź aktualny kurs %s uwzględniając 2 miejsca po przecinku";
  private ExchangeService exchangeService;

  @Autowired
  public WebApi(ExchangeService exchangeService) {
    this.exchangeService = exchangeService;
    currency = exchangeService.getRandomCurrency();
    exchangeService.setScoreTries(1);
    add(formLayout());
  }

  FormLayout formLayout() {
    FormLayout formLayout = new FormLayout();
    VerticalLayout horizontalLayout = new VerticalLayout();
    TextField textField = new TextField(INPUT_VALUE);
    TextArea textArea = new TextArea();
    textArea.setValue(String.format(MSG, currency));
    Button button = new Button(CHECK);
    TextArea output = new TextArea();
    horizontalLayout.add(textField, button, textArea, output);
    formLayout.add(horizontalLayout);
    clickEvent(textField, button, output);
    return formLayout;
  }

  private void clickEvent(TextField textField, Button button, TextArea output) {
    button.addClickListener(ad -> {
      double textValue = exchangeService
              .getTextValue(textField);
      output.setValue(exchangeService
              .checkValueForRandomCurrency(textValue, currency));
    });
  }

}
