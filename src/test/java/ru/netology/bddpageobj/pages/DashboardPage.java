package ru.netology.bddpageobj.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
//import com.codeborne.selenide.conditions.MatchText;
import lombok.val;
import org.openqa.selenium.Keys;
import ru.netology.bddpageobj.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");

    private SelenideElement subHeading = $("[data-test-id=dashboard] + h1");

    private SelenideElement amountField = $("[data-test-id=amount] input");

    private SelenideElement fromField = $("[data-test-id=from] input");

    private SelenideElement toField = $("[data-test-id=to]");

    private SelenideElement transferButton = $("[data-test-id=action-transfer]");

    private ElementsCollection cardInfoListItems = $$("ul div[data-test-id]");

    private ElementsCollection depositButtons = $$("ul div[data-test-id] button[data-test-id=action-deposit]");

    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public void DepositFromFirstToSecondCard(int moneyAmount) {
        subHeading.shouldHave(text("Ваши карты"));
        depositButtons.get(1).click();
        subHeading.shouldHave(text("Пополнение карты"));

        amountField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        amountField.setValue(String.valueOf(moneyAmount));

        fromField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        fromField.setValue(DataHelper.getFirstCardInfo().getNumber());

        transferButton.click();
        subHeading.shouldHave(text("Ваши карты"));
    }

    public void DepositFromSecondToFirstCard(int moneyAmount) throws InterruptedException {
        subHeading.shouldHave(text("Ваши карты"));
        depositButtons.get(0).click();
        subHeading.shouldHave(text("Пополнение карты"));

        amountField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        amountField.setValue(String.valueOf(moneyAmount));

        fromField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        fromField.setValue(DataHelper.getSecondCardInfo().getNumber());

        transferButton.click();
        subHeading.shouldHave(text("Ваши карты"));
    }

    public int getFirstCardBalance() {
        val text = cardInfoListItems.first().text();
        return extractBalance(text);
    }

    public int getCardBalance(String id) {
        String shortCardId = id.substring(id.length() - 4);
        int result = 0;
        for (SelenideElement cardInfoListItem : cardInfoListItems) {
            if (cardInfoListItem.text().indexOf(shortCardId) != -1) {
                result = extractBalance(cardInfoListItem.text());
            }
        }
        return result;
    }
    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}
