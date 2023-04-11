package ru.netology.bddpageobj.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.bddpageobj.data.DataHelper;
import ru.netology.bddpageobj.pages.DashboardPage;
import ru.netology.bddpageobj.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    static private DashboardPage dashboardPage;

    @BeforeAll
    static void initEverything() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    @DisplayName("Should transfer existed amount of money between own cards")
    void shouldTransferExistAmountMoneyBetweenOwnCards() throws InterruptedException {
        int firstCardInitialBalance = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getNumber());
        int secondCardInitialBalance = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getNumber());

        int moneyAmountToTransfer = firstCardInitialBalance / 4 * 3;
        dashboardPage.DepositFromFirstToSecondCard(moneyAmountToTransfer);

        int expectedFirstCardBalance = firstCardInitialBalance - moneyAmountToTransfer;
        int expectedSecondCardBalance = secondCardInitialBalance + moneyAmountToTransfer;
        int actualFirstCardBalance = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getNumber());
        int actualSecondCardBalance = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getNumber());

        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);

        firstCardInitialBalance = actualFirstCardBalance;
        secondCardInitialBalance = actualSecondCardBalance;

        dashboardPage.DepositFromSecondToFirstCard(moneyAmountToTransfer);

        expectedFirstCardBalance = firstCardInitialBalance + moneyAmountToTransfer;
        expectedSecondCardBalance = secondCardInitialBalance - moneyAmountToTransfer;
        actualFirstCardBalance = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getNumber());
        actualSecondCardBalance = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getNumber());

        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    @DisplayName("Should not transfer negative amount of money between own cards")
    void shouldNotTransferNegativeAmountMoneyBetweenOwnCards() throws InterruptedException {
        int firstCardInitialBalance = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getNumber());
        int secondCardInitialBalance = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getNumber());

        int moneyAmountToTransfer = -100;
        dashboardPage.DepositFromFirstToSecondCard(moneyAmountToTransfer);

        int expectedFirstCardBalance = firstCardInitialBalance;
        int expectedSecondCardBalance = secondCardInitialBalance;
        int actualFirstCardBalance = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getNumber());
        int actualSecondCardBalance = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getNumber());

        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);

        firstCardInitialBalance = actualFirstCardBalance;
        secondCardInitialBalance = actualSecondCardBalance;

        dashboardPage.DepositFromSecondToFirstCard(moneyAmountToTransfer);

        expectedFirstCardBalance = firstCardInitialBalance + moneyAmountToTransfer;
        expectedSecondCardBalance = secondCardInitialBalance - moneyAmountToTransfer;
        actualFirstCardBalance = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getNumber());
        actualSecondCardBalance = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getNumber());

        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    @DisplayName("Should not transfer exceeded amount of money between own cards")
    void shouldNotTransferExceededAmountMoneyBetweenOwnCards() throws InterruptedException {
        int firstCardInitialBalance = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getNumber());
        int secondCardInitialBalance = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getNumber());

        int moneyAmountToTransfer = firstCardInitialBalance + 100;
        dashboardPage.DepositFromFirstToSecondCard(moneyAmountToTransfer);

        int expectedFirstCardBalance = firstCardInitialBalance;
        int expectedSecondCardBalance = secondCardInitialBalance;
        int actualFirstCardBalance = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getNumber());
        int actualSecondCardBalance = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getNumber());

        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);

        firstCardInitialBalance = actualFirstCardBalance;
        secondCardInitialBalance = actualSecondCardBalance;

        dashboardPage.DepositFromSecondToFirstCard(moneyAmountToTransfer);

        expectedFirstCardBalance = firstCardInitialBalance;
        expectedSecondCardBalance = secondCardInitialBalance;
        actualFirstCardBalance = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getNumber());
        actualSecondCardBalance = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getNumber());

        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }


    @Test
    @DisplayName("Should transfer all amount of money between own cards")
    void shouldTransferAllAmountMoneyBetweenOwnCards() throws InterruptedException {
        int firstCardInitialBalance = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getNumber());
        int secondCardInitialBalance = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getNumber());

        int moneyAmountToTransfer = firstCardInitialBalance;
        dashboardPage.DepositFromFirstToSecondCard(moneyAmountToTransfer);

        int expectedFirstCardBalance = firstCardInitialBalance - moneyAmountToTransfer;
        int expectedSecondCardBalance = secondCardInitialBalance + moneyAmountToTransfer;
        int actualFirstCardBalance = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getNumber());
        int actualSecondCardBalance = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getNumber());

        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);

        firstCardInitialBalance = actualFirstCardBalance;
        secondCardInitialBalance = actualSecondCardBalance;

        dashboardPage.DepositFromSecondToFirstCard(moneyAmountToTransfer);

        expectedFirstCardBalance = firstCardInitialBalance + moneyAmountToTransfer;
        expectedSecondCardBalance = secondCardInitialBalance - moneyAmountToTransfer;
        actualFirstCardBalance = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getNumber());
        actualSecondCardBalance = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getNumber());

        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }
}
