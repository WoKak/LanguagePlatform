package logic;

import database.Connection;
import logic.Logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

/**
 * Created by Michał on 2017-03-10.
 */
public class Exercise {

    private ArrayList<Word> tasks;
    private int size = 0;

    private static String insertQuery = "SELECT * FROM Main WHERE Main.wordid = ?";

    public Exercise() {

        tasks = new ArrayList<Word>(0);

        Logic logic = Logic.getInstance();

        ArrayList<Integer> alreadyInTasks = new ArrayList<Integer>(0);
        Random random = new Random();

        try (java.sql.Connection conn = Connection.getConnection()) {

            Statement stat = conn.createStatement();

            try (ResultSet result = stat.executeQuery("SELECT * FROM Main"))
            {
                while (result.next())
                    size++;
            }

            for(int i = 0; i < 20; i++) {

                Integer idx = random.nextInt(size);

                while (alreadyInTasks.contains(idx)) {
                    idx = random.nextInt(size);
                }


                PreparedStatement pstat = conn.prepareStatement(insertQuery);
                pstat.setString(1, idx.toString());

                try (ResultSet result = pstat.executeQuery())
                {
                    if (result.next()) {

                        Word tmp = new Word(result.getString(1), result.getString(2));
                        tasks.add(tmp);
                    }

                }

                alreadyInTasks.add(idx);
            }

        } catch (SQLException ex) {

            for (Throwable t : ex)
                t.printStackTrace();

        } catch (IOException ex) {
            System.out.println("Błąd w czytaniu!");
        }

    }

    public ArrayList<Word> getTasks() {
        return tasks;
    }

    public String findWordInEng(String wordInPolish) {

        String toReturn = null;

        for (Word t: tasks) {
            if (t.getWordInPolish().equals(wordInPolish))
                toReturn = t.getWordInEnglish();
        }

        return Optional.ofNullable(toReturn).orElse("ERROR");
    }

    public String findWordInPl(String wordInEnglish){

        String toReturn = null;

        for (Word t: tasks) {
            if (t.getWordInEnglish().equals(wordInEnglish))
                toReturn =  t.getWordInPolish();
        }

        return Optional.ofNullable(toReturn).orElse("ERROR");
    }
}
