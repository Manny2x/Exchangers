package exchanger.project1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Buffers {
    Path path;
    Path read;
    String file;

    Buffers(Path path,
            Path read){
        this.path = path;
        this.read = read;

        StringBuffer stringBuffer = new StringBuffer();


        try(SeekableByteChannel seekableByteChannel =
                    Files.newByteChannel(
                            read
                    )){
            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
            int count;


            do {
                count = seekableByteChannel.read(byteBuffer);

                if(count != -1){
                    byteBuffer.rewind();

                    for(int i = 0; i < count; i++)
                        stringBuffer.append((char) byteBuffer.get());
                }
            } while(count != -1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = String.valueOf(stringBuffer);
    }

    public ByteBuffer makeBuffer(){
        try{
            ByteBuffer byteBuffer = ByteBuffer.allocate(1000000);

            System.out.println(file.length());
            char[] chars = file.toCharArray();
            for (char aChar : chars) {
                byteBuffer.put((byte) aChar);
            }

            byteBuffer.rewind();
            return byteBuffer;
        } catch(Exception exc){
            exc.printStackTrace();
            System.exit(2);
            return null;
        }
    }

    public void writeFile(ByteBuffer byteBuffer){
        try(FileChannel fileChannel =
                    (FileChannel) Files.newByteChannel(
                            path,
                            StandardOpenOption.WRITE,
                            StandardOpenOption.CREATE
                    )){

            fileChannel.write(byteBuffer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
