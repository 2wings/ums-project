package samples.ums.ejb;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.internal.SessionImpl;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import samples.ums.ejb.domain.Role;
import samples.ums.ejb.domain.User;

public class PersistenceUnitTest {

    private static final String JDBC_DRIVER = "org.hsqldb.jdbcDriver";

    private static final String DB_URL = "jdbc:hsqldb:mem:umsdb";

    private static final String USER = "sa";

    private static final String PASS = "";

    private static Logger logger = Logger.getLogger(PersistenceUnitTest.class.getName());

    private static String mPersistenceUnit = "user-test";
    private static EntityManagerFactory emFactory;

    private static EntityManager em;

    private static IDatabaseConnection mDBUnitConnection;
    private static IDataSet roleDataset;

    private static IDataSet userDataset;

    @BeforeClass
    public static void initEntityManager() throws Exception {
        // Get the entity manager for the tests.
        emFactory = Persistence.createEntityManagerFactory(mPersistenceUnit);
        em = emFactory.createEntityManager();

        mDBUnitConnection = new DatabaseConnection(((SessionImpl) (em.getDelegate())).connection());

        // Loads the data set from a file named students-datasets.xml
        roleDataset = new FlatXmlDataSetBuilder().build(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("role-datasets.xml"));
        userDataset = new FlatXmlDataSetBuilder().build(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("users-datasets.xml"));
    }

    @Before
    public void initTest() throws Exception {
        // Clean the data from previous test and insert new data test.
        // DatabaseOperation.CLEAN_INSERT.execute(mDBUnitConnection, roleDataset);
        //
        // DatabaseOperation.CLEAN_INSERT.execute(mDBUnitConnection, userDataset);
    }

    @AfterClass
    public static void closeEntityManager() {
        em.close();
        emFactory.close();
    }

    @Test
    public void testDisplayUserList() throws Exception {
        Connection conn = null;
        Statement stmt = null;

        Class.forName(JDBC_DRIVER);
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);

        DatabaseMetaData dbMetaData = conn.getMetaData();
        String productName = dbMetaData.getDatabaseProductName();
        System.out.println("Database: " + productName);
        String productVersion = dbMetaData.getDatabaseProductVersion();
        System.out.println("Version: " + productVersion);

        System.out.println("Creating statement...");
        stmt = conn.createStatement();
        String sql;
        sql = "select ROLE_ID,ROLE_NAME from role";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String id = rs.getString("ROLE_ID");
            String name = rs.getString("ROLE_NAME");
            // Display values
            System.out.print("ID: " + id);
            System.out.print(",  Name: " + name);
        }
        System.out.println();

        sql = "SELECT USER_ID, firstName, lastName FROM users";
        rs = stmt.executeQuery(sql);

        while (rs.next()) {
            String id = rs.getString("USER_ID");
            String firstname = rs.getString("firstName");
            String lastname = rs.getString("lastName");
            // Display values
            System.out.print("ID: " + id);
            System.out.print(", First Name: " + firstname);
            System.out.print(", Last Name: " + lastname);
        }
    }

    @Test
    public void userCrud() {

        User u1 = new User("aa ", "ffd", "dfs", "fjdks", 1);
        em.persist(u1);
        assertTrue(em.contains(u1));
        assertTrue(em.contains(u1.getRole()));
        
        em.remove(u1);
        assertFalse(em.contains(u1));
        assertFalse(em.contains(u1.getRole()));

    }
}
