package ru.netology.web.data;

import lombok.Value;

public class DataHelper {
  private DataHelper() {}

  @Value
  public static class AuthInfo {
    String login;
    String password;
  }

  public static AuthInfo getAuthInfo() {
    return new AuthInfo("vasya", "qwerty123");
  }

  @Value
  public static class VerificationCode {
    String code;
  }

  public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
    return new VerificationCode("12345");
  }

  @Value
  public static class CardInfo {
    String cardNumber;
  }

  public static CardInfo getFirstCardInfo() {
    return new CardInfo("5559000000000001");
  }

  public static CardInfo getSecondCardInfo() {
    return new CardInfo("5559000000000002");
  }
}