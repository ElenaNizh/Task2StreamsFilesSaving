//В данной задаче Вы потренируетесь сериализовывать Java класс, используя интерфейс Serializable,
        //записывать сериализованные файлы на жесткий диск, используя класс FileOutputStream,
     //   и упаковывать их в архив с помощью ZipOutputStream.
import java.io.Serializable;

public class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    private int health;
    private int weapons;
    private int lvl;
    private double distance;

    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "GameProgress: " +
                "\n1. Health: " + health +
                "\n2. Weapons: " + weapons +
                "\n3. Lvl: " + lvl +
                "\n4. Distance " + distance;
    }
}
