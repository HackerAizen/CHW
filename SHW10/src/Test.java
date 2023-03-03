import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

    private static final List<String> data = Arrays.asList(
            "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d",
            "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d",
            "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d",
            "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d", "aa", "bbb", "c", "d");

    public static void main(String[] args) {
        doParallelWork(data);
    }

    public static List<Integer> doParallelWork(List<String> data) {
        int size = data.size();
        int partSize = size / 3;
        int lastPartSize = partSize + size % 3;

        List<String> part1 = data.subList(0, partSize);
        List<String> part2 = data.subList(partSize, partSize * 2);
        List<String> part3 = data.subList(partSize * 2, partSize * 2 + lastPartSize);

        List<Integer> results = new ArrayList<>();

        Thread t1 = new Thread(() -> {
            int count = 0;
            for (String s : part1) {
                count += s.length();
            }
            results.add(count);
        });

        Thread t2 = new Thread(() -> {
            int count = 0;
            for (String s : part2) {
                count += s.length();
            }
            results.add(count);
        });

        Thread t3 = new Thread(() -> {
            int count = 0;
            for (String s : part3) {
                count += s.length();
            }
            results.add(count);
        });

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return results;
    }


    private static class CharacterCounter implements Runnable {
        private final List<String> data;
        private int characterCount;

        public CharacterCounter(List<String> data) {
            this.data = data;
        }

        public void run() {
            for (String s : data) {
                characterCount += s.length();
            }
        }

        public int getCharacterCount() {
            return characterCount;
        }
    }
}
