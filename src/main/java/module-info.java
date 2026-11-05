module com.minigamelauncher.minigamelauncher {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.minigamelauncher to javafx.fxml;
    exports com.minigamelauncher;

    opens com.minigamelauncher.wordle to javafx.fxml;
    exports com.minigamelauncher.wordle;

    opens com.minigamelauncher.blackjack to javafx.fxml;
    exports com.minigamelauncher.blackjack;

    opens com.minigamelauncher.uno to javafx.fxml;
    exports  com.minigamelauncher.uno;

    opens com.minigamelauncher.flappybird to javafx.fxml;
    exports com.minigamelauncher.flappybird;
}