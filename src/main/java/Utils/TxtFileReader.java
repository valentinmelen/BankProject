package Utils;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Logger;

public class TxtFileReader {

    private String filename;
    private static final Logger logger = Logger.getLogger(Logger.class.getName());

    public TxtFileReader(String filename) {
        this.filename = filename;
    }

    public ArrayList<String> read() {
        ArrayList<String> lines = new ArrayList<String>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            String line;
            while ((line = reader.readLine()) != null)
               lines.add(line);

    } catch (FileNotFoundException e) {
            logger.warning("File '"+filename +"' not found!");
        } catch (IOException e) {
           logger.warning("Error!" +e.getMessage());
        }
        return lines;
    }
}
