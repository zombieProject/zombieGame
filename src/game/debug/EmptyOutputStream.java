package game.debug;

import java.io.IOException;
import java.io.OutputStream;

public class EmptyOutputStream extends OutputStream {
    @Override
    public void write(int b) throws IOException {
    }
}
