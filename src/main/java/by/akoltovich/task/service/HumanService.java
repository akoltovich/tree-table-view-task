package by.akoltovich.task.service;

import by.akoltovich.task.DAO.HumanDAO;
import by.akoltovich.task.DAO.impl.HumanDAOImpl;
import by.akoltovich.task.entity.Human;
import javafx.collections.ObservableList;

import java.util.Optional;

public class HumanService {

    private static HumanService instance;

    public static HumanService getInstance() {
        if (instance == null) {
            instance = new HumanService();
        }
        return instance;
    }

    HumanDAO humanDAO = HumanDAOImpl.getInstance();

    public Optional<Human> getHuman(Long id) {
        return humanDAO.getHuman(id);
    }

    public ObservableList<Human> getAllHumans() {
        return humanDAO.getAllHumans();
    }

    public void saveHuman(Human human) {
        humanDAO.saveHuman(human);
    }

    public void updateHuman(Long id, Human human) {
        humanDAO.updateHuman(id, human);
    }

    public void deleteHuman(Long id) {
        humanDAO.deleteHuman(id);
    }
}
