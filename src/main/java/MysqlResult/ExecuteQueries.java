package MysqlResult;
import FileHandler.ReadFile;
import java.sql.*;
import java.io.BufferedReader;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;
import javax.sql.DataSource;


public class ExecuteQueries {
    final Logger logger = Logger.getLogger(ExecuteQueries.class);
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String JDBC_DB_URL = "jdbc:mysql://localhost:3306/theatrobiz";
    static final String JDBC_USER = "theatro";
    static final String JDBC_PASS = "theatro";
    private static GenericObjectPool gPool = null;

    public DataSource setUpPool() throws Exception {
        Class.forName(JDBC_DRIVER);
        gPool = new GenericObjectPool();
        gPool.setMaxActive(5);
        ConnectionFactory cf = new DriverManagerConnectionFactory(JDBC_DB_URL, JDBC_USER, JDBC_PASS);
        PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, gPool, null, null, false, true);
        return new PoolingDataSource(gPool);
    }

    public GenericObjectPool getConnectionPool() {
        return gPool;
    }

    private void printDbStatus() {
        logger.info("Max.: " + getConnectionPool().getMaxActive() + "; Active: " + getConnectionPool().getNumActive() + "; Idle: " + getConnectionPool().getNumIdle());
    }

    public void executeQueries(String file) {
        final Logger logger = Logger.getLogger(ExecuteQueries.class);

        Connection con = null;
        PreparedStatement pst = null;
        ExecuteQueries jdbcObj = new ExecuteQueries();
        try {
            ReadFile rf = new ReadFile();
            BufferedReader reader = rf.getReaderObject(file);
            String line = reader.readLine();
            DataSource dataSource = jdbcObj.setUpPool();
            jdbcObj.printDbStatus();
            logger.info("\n=====Making A New Connection Object For Db Transaction=====\n");
            con = dataSource.getConnection();
            jdbcObj.printDbStatus();
            Statement st=con.createStatement();
            while (line != null) {
                logger.info(line);
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
            logger.info("\n=====Releasing Connection Object To Pool=====\n");
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        jdbcObj.printDbStatus();
    }
}




