package ru.netology.bddpageobj.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");

    private SelenideElement subHeading = $("[data-test-id=dashboard] + h1");

    private SelenideElement amountField = $("[data-test-id=amount] input");

    private SelenideElement fromField = $("[data-test-id=from] input");

    private SelenideElement toField = $("[data-test-id=to]");

    private SelenideElement transferButton = $("[data-test-id=action-transfer]");

    public TransferPage() {
        heading.shouldBe(visible);
        subHeading.shouldHave(text("Пополнение карты"));
    }

    public DashboardPage depositMoneyFromCard(int moneyAmount, String cardNumber) throws InterruptedException {
        amountField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        amountField.setValue(String.valueOf(moneyAmount));

        fromField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        fromField.setValue(cardNumber);
        transferButton.click();
        return new DashboardPage();
    }
}
