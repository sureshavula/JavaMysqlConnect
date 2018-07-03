package FileHandler;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import org.apache.log4j.Logger;

public class ReadFile{


    final  Logger logger = Logger.getLogger(ReadFile.class);


    public BufferedReader getReaderObject(String file){
        FileReader fr = null;
        try
        {
            fr = new FileReader(file);
        }
        catch (FileNotFoundException fe)
        {
            logger.info("File not found");
        }
        return new BufferedReader(fr);
    }
}

