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

public class UpdateButtonController implements Initializable {


    @FXML
    private TextField age;

    @FXML
    private TextField birthdate;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField name;

    private Long id;

    private HumanService humanService = HumanService.getInstance();

    private TreeTableView<Human> treeTableView;

    public UpdateButtonController() {
    }

    @FXML
    void closeForm() {
        btnCancel.getScene().getWindow().hide();
    }

    @FXML
    void updateHuman() {
        if (!validateAgeField(age.getText())) {
            AlertUtil.createAlert(Alert.AlertType.WARNING,
                    "Warning!", "Please, input only integer non-negative numbers").show();
        } else if (!validateDateFormat(birthdate.getText())) {
            AlertUtil.createAlert(Alert.AlertType.WARNING,
                    "Warning!", "Please, use yyyy-mm-dd pattern to set date").show();
        } else if (!validateDate(Date.valueOf(birthdate.getText()), Integer.valueOf(age.getText()))) {
            AlertUtil.createAlert(Alert.AlertType.WARNING,
                    "Warning!", "Year and age not match").show();
        } else {
            Human human = new Human(name.getText(), Integer.valueOf(age.getText()), Date.valueOf(birthdate.getText()));
            humanService.updateHuman(id, human);

            btnUpdate.getScene().getWindow().hide();
            updateTreeItem(human);
        }
    }

    public void initData(Human human, TreeTableView<Human> treeTableView) {
        age.setText(String.valueOf(human.getAge()));
        birthdate.setText(human.getBirthDate().toString());
        name.setText(human.getName());
        this.id = human.getId();
        this.treeTableView = treeTableView;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnCancel.setStyle("-fx-background-color: red");
        btnUpdate.setStyle("-fx-background-color: green");
    }

    public void updateTreeItem(Human human) {
        TreeItem<Human> humans = treeTableView.getRoot();
        humans.getChildren().remove(treeTableView.getSelectionModel().getSelectedItem());
        humans.getChildren().add(new TreeItem<>(human));
        treeTableView.setRoot(humans);
        treeTableView.getSelectionModel().clearSelection();
    }
}
