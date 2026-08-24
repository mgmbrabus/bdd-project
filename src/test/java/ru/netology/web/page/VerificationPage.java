package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
  private final SelenideElement codeInput = $("[data-test-id='code'] input");
  private final SelenideElement verifyButton = $("[data-test-id='action-verify']");

  public VerificationPage() {
    codeInput.shouldBe(visible);
  }

  public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
    codeInput.setValue(verificationCode.getCode());
    verifyButton.click();
    return new DashboardPage();
  }
}