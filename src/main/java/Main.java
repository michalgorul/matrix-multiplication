import controller.TesterController;
import view.MainPage;

import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        MainPage mainPage = new MainPage();
        TesterController testerController = new TesterController(mainPage);
        mainPage.setVisible(true);
    }
}
