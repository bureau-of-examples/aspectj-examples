package zhy2002.aspectjexamples.infrastructure;


import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

public class LoggingConfigurer {

    public static void config() {

       try(InputStream configFile = ClassLoader.class.getResourceAsStream("/logging.properties")){

           LogManager.getLogManager().readConfiguration(configFile);

       } catch (IOException ex){
           throw new RuntimeException(ex);
       }

    }
}
