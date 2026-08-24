package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPageV1 {
  private final SelenideElement loginInput = $("[data-test-id=login] input");
  private final SelenideElement passwordInput = $("[data-test-id=password] input");
  private final SelenideElement loginButton = $("[data-test-id=action-login]");

  public VerificationPage validLogin(DataHelper.AuthInfo authInfo) {
    loginInput.setValue(authInfo.getLogin());
    passwordInput.setValue(authInfo.getPassword());
    loginButton.click();
    return new VerificationPage();
  }
}