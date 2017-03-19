package database;

import java.sql.*;
import java.io.*;


/**
 * Created by Michał on 2017-03-19.
 */

public class CreateTable {

    private static String insertUpdate = "INSERT INTO Main VALUES (?, ?, ?)";

    public static void main(String args[]) throws IOException {

        try {
            runInit();

        } catch (SQLException ex) {

            for (Throwable t : ex)
                t.printStackTrace();

        }
    }

    public static void runInit() throws SQLException, IOException {

        try (java.sql.Connection conn = Connection.getConnection()) {
            Statement stat = conn.createStatement();

            stat.executeUpdate("CREATE TABLE Main (WordInPolish CHAR(50), WordInEnglish CHAR(50), WordID CHAR(6))");

            PreparedStatement pstat = conn.prepareStatement(insertUpdate);

            Integer i = 0;
            File file = new File("base.txt");

            try {
                FileReader reader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    String[] tokens = line.split(", ");
                    pstat.setString(1, tokens[0]);
                    pstat.setString(2, tokens[1]);
                    pstat.setString(3, i.toString());
                    pstat.executeUpdate();
                    i++;
                    line = bufferedReader.readLine();
                }
            } catch (IOException ex) {
                System.out.println("Błąd w czytaniu!");
            }

        }
    }
}

