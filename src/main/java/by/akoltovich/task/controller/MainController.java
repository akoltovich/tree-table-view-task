package by.akoltovich.task.controller;

import by.akoltovich.task.DAO.impl.HumanDAOImpl;
import by.akoltovich.task.entity.Human;
import by.akoltovich.task.service.HumanService;
import by.akoltovich.task.util.AlertUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TreeTableColumn<Human, Integer> age;

    @FXML
    private TreeTableColumn<Human, Date> birthDate;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private TreeTableColumn<Human, String> name;

    @FXML
    private TreeTableView<Human> treeTableView;

    private HumanService humanService = HumanService.getInstance();

    public MainController() {
    }

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        treeTableView.setStyle("-fx-background-color: red");
        showHumans();
        btnCreate.setStyle("-fx-background-color: green");
        btnCreate.setOnAction(event -> {
            saveNewHuman();
        });
        btnEdit.setStyle("-fx-background-color: yellow");
        btnEdit.setOnAction(event -> {
            editHuman();
        });
        btnDelete.setStyle("-fx-text-fill: white");
        btnDelete.setStyle("-fx-background-color: red");
        btnDelete.setOnAction(event -> {
            deleteHuman();
        });
        treeTableView.setOnMouseClicked(event -> {
            Human human = treeTableView.getSelectionModel().getSelectedItem().getValue();
            Date currentDate = Date.valueOf(LocalDateTime.now().toLocalDate());
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                if (human.getBirthDate().getMonth() == currentDate.getMonth() &&
                        human.getBirthDate().getDate() == currentDate.getDate()) {
                    AlertUtil.createAlert(Alert.AlertType.INFORMATION,
                            "Congratulations!", "Happy Birthday!").show();
                }
            }
        });
    }

    private void deleteHuman() {
        if (treeTableView.getSelectionModel().getSelectedItem() != null) {
            TreeItem<Human> humans = treeTableView.getRoot();
            Human human = treeTableView.getSelectionModel().getSelectedItem().getValue();
            humanService.deleteHuman(human.getId());
            humans.getChildren().remove(treeTableView.getSelectionModel().getSelectedItem());
            treeTableView.setRoot(humans);
            treeTableView.getSelectionModel().clearSelection();
        } else {
            AlertUtil.createAlert(Alert.AlertType.WARNING,
                    "Warning!", "Please, select human that you want delete").show();
        }
    }

    private void editHuman() {
        if (treeTableView.getSelectionModel().getSelectedItem() != null) {
            Human human = treeTableView.getSelectionModel().getSelectedItem().getValue();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/UpdateHumanForm.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            UpdateButtonController updateButtonController = loader.getController();
            updateButtonController.initData(human, treeTableView);
            Stage stage = new Stage();
            stage.setScene(loader.getRoot());
            stage.show();
        } else {
            AlertUtil.createAlert(Alert.AlertType.WARNING,
                    "Warning!", "Please, select human that you want update").show();
        }
    }

    private void saveNewHuman() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/NewHumanForm.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CreateButtonController createButtonController = loader.getController();
        createButtonController.initData(treeTableView);
        Stage stage = new Stage();
        stage.setScene(loader.getRoot());
        stage.show();
    }

    @FXML
    public void showHumans() {
        ObservableList<Human> humans = humanService.getAllHumans();
        Comparator<Human> humanComparator
                = Comparator.comparing(Human::getName);
        humans.sort(humanComparator);
        treeTableView.setShowRoot(false);

        name.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue().getName()));
        age.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue().getAge()));
        birthDate.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue().getBirthDate()));

        TreeItem<Human> treeItem = new TreeItem<>();
        for (Human human : humans) {
            TreeItem<Human> humanTreeItem = new TreeItem<>(human);
            treeItem.getChildren().add(humanTreeItem);
        }
        treeTableView.setRoot(treeItem);
    }
}
