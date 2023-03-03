import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

interface Observer {
    void update(Game game);
}

class GameConsole {
    private List<Observer> observers = new ArrayList <> ();

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notify(Game game) {
        for (Observer observer : observers) {
            observer.update(game);
        }
    }
}

class Player implements Observer {
    private String name;

    public Player(String name) {
        this.name = name;
    }

    @Override
    public void update(Game game) {
        System.out.println("New game released: " + game.getTitle());
        System.out.println("Achievements: " + game.getAchievements());
        playGame(game);
    }

    public void playGame(Game game) {
        System.out.println(name + " is playing " + game.getTitle() + "...");
    }
}

class Journalist implements Observer {
    private String name;

    public Journalist(String name) {
        this.name = name;
    }

    @Override
    public void update(Game game) {
        System.out.println("New game released: " + game.getTitle());
        System.out.println("Description: " + game.getDescription());
        writeArticle(game);
    }

    public void writeArticle(Game game) {
        System.out.println(name + " is writing an article about " + game.getTitle() + "...");
    }
}

class Developer implements Observer {
    private String name;

    public Developer(String name) {
        this.name = name;
    }

    @Override
    public void update(Game game) {
        System.out.println("New game released: " + game.getTitle());
        System.out.println("Technical details: " + game.getTechnicalDetails());
        analyzeGame(game);
    }

    public void analyzeGame(Game game) {
        System.out.println(name + " is analyzing " + game.getTitle() + "...");
    }
}

class Game {
    private String title;
    private List<String> achievements;
    private String description;
    private String technicalDetails;

    public Game(String title, List<String> achievements, String description, String technicalDetails) {
        this.title = title;
        this.achievements = achievements;
        this.description = description;
        this.technicalDetails = technicalDetails;
    }

    public String getTitle() {
        return title;
    }

    public List < String > getAchievements() {
        return achievements;
    }

    public String getDescription() {
        return description;
    }

    public String getTechnicalDetails() {
        return technicalDetails;
    }
}

public class Main {
    public static void main(String[] args) {
        // Создаем игровую консоль
        GameConsole console = new GameConsole();

        // Создаем игроков, журналистов и разработчиков
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Journalist journalist1 = new Journalist("Journalist 1");
        Journalist journalist2 = new Journalist("Journalist 2");
        Developer developer1 = new Developer("Developer 1");
        Developer developer2 = new Developer("Developer 2");

        // Подписываем игроков, журналист и разработчиков на уведомления от игровой консоли
        console.attach(player1);
        console.attach(player2);
        console.attach(journalist1);
        console.attach(journalist2);
        console.attach(developer1);
        console.attach(developer2);

// Создаем новую игру и уведомляем всех наблюдателей
        Game game = new Game("New Game", Arrays.asList("Achievement 1", "Achievement 2"), "This is a new game", "Technical details");
        console.notify(game);

        // Отписываем игрока от уведомлений об играх
        console.detach(player2);

// Создаем другую игру и уведомляем всех наблюдателей, включая только что отписанного игрока
        Game game2 = new Game("Another New Game", Arrays.asList("Achievement 1", "Achievement 2", "Achievement 3"), "This is another new game", "Technical details");
        console.notify(game2);
    }
}