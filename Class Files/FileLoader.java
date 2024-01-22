package inf_retrieval;

import org.apache.tika.exception.TikaException;

import java.io.File;
import java.io.IOException;
import org.apache.tika.Tika;

public class FileLoader {
    static Tika tika = new Tika();
    public static String load(File file){
        String filestr = "";
        try {
            filestr = tika.parseToString(file);
        } catch (TikaException | IOException e){
            e.printStackTrace();
        }
        return filestr;
    }
}
