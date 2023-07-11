package by.akoltovich.task.DAO;

import by.akoltovich.task.entity.Human;
import javafx.collections.ObservableList;

import java.util.Optional;

public interface HumanDAO {

    Optional<Human> getHuman(Long id);

    ObservableList<Human> getAllHumans();

    void saveHuman(Human human);

    void updateHuman(Long id, Human human);

    void deleteHuman(Long id);
}
