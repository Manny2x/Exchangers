package exchanger.project1;

import java.nio.ByteBuffer;
import java.util.concurrent.Exchanger;

public class MakeBuf implements Runnable{
    Exchanger<ByteBuffer> byteBufferExchanger;
    static String name;
    Buffers buffers;

    MakeBuf(String name,
            Exchanger<ByteBuffer> byteBufferExchanger,
            Buffers buffers){
        this.byteBufferExchanger = byteBufferExchanger;
        MakeBuf.name = name;
        this.buffers = buffers;


    }

    @Override
    public void run(){
        ByteBuffer byteBuffer = null;

        ByteBuffer buffer = buffers.makeBuffer();
        System.out.println(name + " created buffer");

        System.out.println(name + " is exchanging buffer");
        try {
            byteBuffer = byteBufferExchanger.exchange(buffer);
            System.out.println(name + " has exchanged the buffers with " + MakeFiles.name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assert byteBuffer != null;
        buffers.writeFile(byteBuffer);
        System.out.println(name + " has written to the file(s)");
    }
}
