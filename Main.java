package exchanger.project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.concurrent.Exchanger;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader =
                new BufferedReader(
                        new InputStreamReader(
                                System.in
                        )
                );

        System.out.println("Specify the files you want to write to ");
        String firstWriteFile = bufferedReader.readLine();
        String secondWriteFile = bufferedReader.readLine();

        System.out.println("Specify the files you want to read from");
        String firstReadFile = bufferedReader.readLine();
        String secondReadFile = bufferedReader.readLine();

        Exchanger<ByteBuffer> byteBufferExchanger = new Exchanger<>();

        Thread a1 = new Thread(new MakeBuf("A1",
                byteBufferExchanger, new Buffers(
                Path.of("C:\\" +
                        "Users\\" +
                        "Emman\\" +
                        "IdeaProjects\\" +
                        "CouncurrencyUtilities\\" +
                        "src\\exchanger\\" +
                        "project1\\" +
                        firstWriteFile +
                        ".txt"),
                Path.of("C:\\" +
                        "Users\\" +
                        "Emman\\" +
                        "IdeaProjects\\" +
                        "CouncurrencyUtilities\\" +
                        "src\\exchanger\\" +
                        "project1\\" +
                        secondReadFile +
                        ".txt")
        )));

        Thread b1 = new Thread(new MakeFiles(byteBufferExchanger,
                "B1", new Buffers(
                Path.of("C:\\" +
                        "Users\\" +
                        "Emman\\" +
                        "IdeaProjects\\" +
                        "CouncurrencyUtilities\\" +
                        "src\\exchanger\\" +
                        "project1\\" +
                        secondWriteFile +
                        ".txt"),
                Path.of("C:\\" +
                        "Users\\" +
                        "Emman\\" +
                        "IdeaProjects\\" +
                        "CouncurrencyUtilities\\" +
                        "src\\exchanger\\" +
                        "project1\\" +
                        firstReadFile +
                        ".txt")
        )));

        a1.start();
        b1.start();
    }
}
