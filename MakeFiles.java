package exchanger.project1;

import java.nio.ByteBuffer;
import java.util.concurrent.Exchanger;

public class MakeFiles implements Runnable{
    Exchanger<ByteBuffer> byteBufferExchanger;
    static String name;
    Buffers buffers;

    MakeFiles(Exchanger<ByteBuffer> byteBufferExchanger,
              String name,
              Buffers buffers){
        this.byteBufferExchanger = byteBufferExchanger;
        MakeFiles.name = name;
        this.buffers = buffers;
    }

    @Override
    public void run() {
        ByteBuffer byteBuffer = null;
        ByteBuffer buffer = buffers.makeBuffer();

        try {
            byteBuffer = byteBufferExchanger.exchange(buffer);
            System.out.println(name + " exchanged the buffers with " + MakeBuf.name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assert byteBuffer != null;
        buffers.writeFile(byteBuffer);
        System.out.println(name + " has written to the file(s)");
    }
}
