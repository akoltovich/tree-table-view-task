package by.akoltovich.task.util;

import javafx.scene.control.Alert;

public class AlertUtil {

    public static Alert createAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert;
    }
}
