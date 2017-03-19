package database;

import java.io.IOException;
import java.sql.*;

/**
 * Created by Michał on 2017-03-19.
 */
public class DropTable {

    public static void runDrop() throws SQLException, IOException {

        try (java.sql.Connection conn = Connection.getConnection()) {

            Statement stat = conn.createStatement();

            stat.executeUpdate("DROP TABLE Main");
        }
    }

}
