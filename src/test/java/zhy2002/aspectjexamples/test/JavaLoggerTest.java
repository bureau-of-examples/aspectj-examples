package zhy2002.aspectjexamples.test;

import org.testng.annotations.*;
import zhy2002.aspectjexamples.infrastructure.LoggingConfigurer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class JavaLoggerTest {

    private static final String LOG_FILE_NAME = "JavaLoggerTest.log";

    private FileHandler handler = null;

    @BeforeClass
    public void prepareClass() throws IOException{

        LoggingConfigurer.config(); //read logging.properties
    }

    @BeforeMethod
    public void prepareMethod() throws IOException{

        if(handler != null){
            Logger.getLogger("").removeHandler(handler);
        }
        handler = new FileHandler(LOG_FILE_NAME, false);
        handler.setFormatter(new SimpleFormatter());
        Logger.getLogger("").addHandler(handler);
    }

    @AfterMethod
    public void cleanUpMethod() throws IOException{
        try {
            if(handler != null)
                handler.close();
            Files.deleteIfExists(Paths.get(LOG_FILE_NAME));
        }catch (Exception ex){
            System.out.println(ex);
        }
    }

    private String readFileContent(String fileName) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        Files.readAllLines(Paths.get(fileName)).forEach(line -> {stringBuilder.append(line); stringBuilder.append("\n");});
        return stringBuilder.toString();
    }

    @Test
    public void canReadLogFileOutput() throws IOException{

        //arrange
        String uuid = UUID.randomUUID().toString();

        //action
        Logger.getLogger("").log(Level.INFO,"log id = {0}", uuid);
        String output = readFileContent(LOG_FILE_NAME);

        //assertion
        String expected = "java.util.logging.LogManager$RootLogger log\n" +
                "INFO: log id = " + uuid + "\n";
        assertThat(output, endsWith(expected));
    }

    @Test
    public void shouldNotLogFineByDefault() throws IOException{

        //arrange
        String uuid = UUID.randomUUID().toString();

        //action
        Logger.getLogger("default.logger").log(Level.FINE,"log id = {0}", uuid);
        String output = readFileContent(LOG_FILE_NAME);

        //assertion
        assertThat(output, equalTo(""));
    }

    @Test
    public void shouldLogFineWhenConfigured() throws IOException{

        //arrange
        String uuid = UUID.randomUUID().toString();

        //action
        Logger.getLogger("fine.logger").log(Level.FINE,"log id = {0}", uuid);
        String output = readFileContent(LOG_FILE_NAME);

        //assertion
        String expected = "zhy2002.aspectjexamples.test.JavaLoggerTest shouldLogFineWhenConfigured\n" +
                "FINE: log id = " + uuid + "\n";
        assertThat(output, endsWith(expected));
    }

    @Test
    public void shouldLogFineWhenParentIsConfigured() throws IOException{

        //arrange
        String uuid = UUID.randomUUID().toString();

        //action
        Logger.getLogger("fine.logger.child").log(Level.FINE,"log id = {0}", uuid);
        String output = readFileContent(LOG_FILE_NAME);

        //assertion
        String expected = "zhy2002.aspectjexamples.test.JavaLoggerTest shouldLogFineWhenParentIsConfigured\n" +
                "FINE: log id = " + uuid + "\n";
        assertThat(output, endsWith(expected));
    }


}
