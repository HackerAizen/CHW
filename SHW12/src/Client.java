import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    private String hostName;
    private int port;
    private String userName;

    public Client(String hostName, int port, String userName) {
        this.hostName = hostName;
        this.port = port;
        this.userName = userName;
    }

    public void start() {
        try (Socket socket = new Socket(hostName, port);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             OutputStream output = socket.getOutputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            // Send username to the server
            output.write(("username " + userName + "\n").getBytes());
            output.flush();

            // Read messages from the server
            new Thread(() -> {
                String line;
                try {
                    while ((line = input.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Read messages from the user and send them to the server
            String userInput;
            while ((userInput = reader.readLine()) != null) {
                output.write((userName + ": " + userInput + "\n").getBytes());
                output.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
