package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPageV1;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
  DashboardPage dashboardPage;

  @BeforeEach
  void setUp() {
    open("http://localhost:9999");
    var loginPage = new LoginPageV1();
    var authInfo = DataHelper.getAuthInfo();
    var verificationPage = loginPage.validLogin(authInfo);
    var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    dashboardPage = verificationPage.validVerify(verificationCode);
  }

  @Test
  @DisplayName("Успешный перевод денег с первой карты на вторую")
  void shouldTransferMoneyFromFirstToSecondCard() {
    var firstCardBalance = dashboardPage.getCardBalance(0);
    var secondCardBalance = dashboardPage.getCardBalance(1);

    int amount = 1000;
    var expectedFirstCardBalance = firstCardBalance - amount;
    var expectedSecondCardBalance = secondCardBalance + amount;

    var transferPage = dashboardPage.selectCardToTransfer(1);
    dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), DataHelper.getFirstCardInfo());

    var actualFirstCardBalance = dashboardPage.getCardBalance(0);
    var actualSecondCardBalance = dashboardPage.getCardBalance(1);

    assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
    assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
  }

  @Test
  @DisplayName("Успешный перевод денег со второй карты на первую")
  void shouldTransferMoneyFromSecondToFirstCard() {
    var firstCardBalance = dashboardPage.getCardBalance(0);
    var secondCardBalance = dashboardPage.getCardBalance(1);

    int amount = 500;
    var expectedFirstCardBalance = firstCardBalance + amount;
    var expectedSecondCardBalance = secondCardBalance - amount;

    var transferPage = dashboardPage.selectCardToTransfer(0);
    dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), DataHelper.getSecondCardInfo());

    var actualFirstCardBalance = dashboardPage.getCardBalance(0);
    var actualSecondCardBalance = dashboardPage.getCardBalance(1);

    assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
    assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
  }

  @Test
  @DisplayName("Ошибка при переводе суммы, превышающей остаток на карте")
  void shouldNotTransferMoneyIfAmountExceedsBalance() {
    var firstCardBalance = dashboardPage.getCardBalance(0);
    int amount = firstCardBalance + 5000; // Пытаемся перевести больше, чем есть

    var transferPage = dashboardPage.selectCardToTransfer(1);
    transferPage.makeTransfer(String.valueOf(amount), DataHelper.getFirstCardInfo());

    // Проверяем появление сообщения об ошибке (этот тест упадет из-за бага SUT — заводим Issue на GitHub)
    transferPage.findErrorMessage("Ошибка");
  }
}