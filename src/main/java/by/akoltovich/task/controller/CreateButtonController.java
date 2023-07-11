package by.akoltovich.task.controller;

import by.akoltovich.task.DAO.HumanDAO;
import by.akoltovich.task.DAO.impl.HumanDAOImpl;
import by.akoltovich.task.entity.Human;
import by.akoltovich.task.service.HumanService;
import by.akoltovich.task.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import static by.akoltovich.task.util.ValidationUtil.validateAgeField;
import static by.akoltovich.task.util.ValidationUtil.validateDate;
import static by.akoltovich.task.util.ValidationUtil.validateDateFormat;

public class CreateButtonController implements Initializable {
    @FXML
    private TextField age;

    @FXML
    private TextField birthdate;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private TextField name;

    private HumanService humanService = HumanService.getInstance();

    private TreeTableView<Human> treeTableView;

    public CreateButtonController() {
    }

    @FXML
    void closeForm() {
        btnCancel.getScene().getWindow().hide();
    }

    @FXML
    void saveHuman() {
        if (age.getText().isEmpty() || name.getText().isEmpty() || birthdate.getText().isEmpty()) {
            AlertUtil.createAlert(Alert.AlertType.WARNING,
                    "Warning!", "All fields must be filled!").show();
        } else if (!validateAgeField(age.getText())) {
            AlertUtil.createAlert(Alert.AlertType.WARNING,
                    "Warning!", "Please, input only integer non-negative numbers").show();
        } else if (!validateDate(Date.valueOf(birthdate.getText()), Integer.valueOf(age.getText()))) {
            AlertUtil.createAlert(Alert.AlertType.WARNING,
                    "Warning!", "Year and age not match").show();
        } else if (!validateDateFormat(birthdate.getText())) {
            AlertUtil.createAlert(Alert.AlertType.WARNING,
                    "Warning!", "Please, use yyyy-mm-dd pattern to set date").show();
        } else {
            Human human = new Human(name.getText(), Integer.valueOf(age.getText()), Date.valueOf(birthdate.getText()));
            humanService.saveHuman(human);

            btnSave.getScene().getWindow().hide();
            updateTreeItem(human);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnCancel.setStyle("-fx-background-color: red");
        btnSave.setStyle("-fx-background-color: green");
    }

    public void initData(TreeTableView<Human> treeTableView) {
        this.treeTableView = treeTableView;
    }

    public void updateTreeItem(Human human) {
        TreeItem<Human> humans = treeTableView.getRoot();
        humans.getChildren().add(new TreeItem<>(human));
        treeTableView.setRoot(humans);
    }
}
