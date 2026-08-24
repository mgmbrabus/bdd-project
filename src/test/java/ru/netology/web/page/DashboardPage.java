package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
  private final SelenideElement heading = $("[data-test-id='dashboard']");
  // Выбираем строго элементы карточек
  private final ElementsCollection cards = $$(".list__item div");

  public DashboardPage() {
    heading.shouldBe(visible);
  }

  public int getCardBalance(int index) {
    String text = cards.get(index).getText();
    return extractBalance(text);
  }

  public TransferPage selectCardToTransfer(int index) {
    cards.get(index).$("[data-test-id='action-deposit']").click();
    return new TransferPage();
  }

  private int extractBalance(String text) {
    // Извлекаем подстроку между "баланс: " и " р."
    String balanceStart = "баланс: ";
    String balanceFinish = " р.";

    int start = text.indexOf(balanceStart);
    int finish = text.indexOf(balanceFinish);

    if (start == -1 || finish == -1) {
      // Запасной вариант через регулярку (на случай различий в пробелах/символах)
      return Integer.parseInt(text.replaceAll("[^0-9-]", ""));
    }

    String value = text.substring(start + balanceStart.length(), finish).trim();
    return Integer.parseInt(value);
  }
}