import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileDownloader {

    private static final String HELP_MESSAGE = "Available commands:\n" +
            "/help - Show this message\n" +
            "/load URL1 URL2 ... - Download file(s) from the specified URL(s)\n" +
            "/dest PATH - Set download destination directory\n" +
            "/exit - Exit the program\n";

    private String destDir = System.getProperty("user.dir");

    public static void main(String[] args) {
        FileDownloader downloader = new FileDownloader();
        downloader.run();
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine();

            if (command.startsWith("/load")) {
                List<String> urls = parseUrls(command.substring(5));
                downloadFiles(urls);
            } else if (command.startsWith("/dest")) {
                destDir = command.substring(5).trim();
                System.out.println("Destination directory set to " + destDir);
            } else if (command.startsWith("/help")) {
                System.out.println(HELP_MESSAGE);
            } else if (command.startsWith("/exit")) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid command. Type /help for available commands.");
            }
        }

        scanner.close();
    }

    private List<String> parseUrls(String urlList) {
        List<String> urls = new ArrayList<>();
        for (String url : urlList.split("\\s+")) {
            urls.add(url);
        }
        return urls;
    }

    private void downloadFiles(List<String> urls) {
        for (String url : urls) {
            Thread thread = new Thread(() -> {
                try {
                    URL sourceUrl = new URL(url);
                    String fileName = FilenameUtils.getName(url);
                    File destFile = new File(destDir + File.separator + fileName);
                    FileUtils.copyURLToFile(sourceUrl, destFile);
                    System.out.println("Downloaded " + fileName + " to " + destDir);
                } catch (IOException e) {
                    System.out.println("Error downloading file from " + url + ": " + e.getMessage());
                }
            });
            thread.start();
        }
    }
}
