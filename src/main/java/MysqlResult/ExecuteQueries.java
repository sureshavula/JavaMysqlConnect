package MysqlResult;
import FileHandler.ReadFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.BufferedReader;
import org.apache.log4j.Logger;



public class ExecuteQueries {
    final String url = "jdbc:mysql://localhost:3306/theatrobiz";
    final String user = "theatro";
    final String password = "theatro";

    public void executeQueries(String file){
        final  Logger logger = Logger.getLogger(ExecuteQueries.class);
        try {
            ReadFile rf = new ReadFile();
            BufferedReader reader = rf.getReaderObject(file);
            String line = reader.readLine();
            Connection con = DriverManager.getConnection(url, user, password);
            Statement st=con.createStatement();
            while(line != null) {
                System.out.println(line);
                logger.info(line);
                ResultSet rs = st.executeQuery(line);
                while (rs.next()) {
                    logger.info(rs.getString("Name"));
                }
                line = reader.readLine();
            }
            reader.close();
            st.close();
            con.close();

        } catch (Exception e) {
            logger.error(e);
        }
    }
}
