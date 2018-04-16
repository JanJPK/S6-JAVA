package genotype.db;

import genotype.db.model.Examined;
import genotype.db.model.Flagella;
import genotype.db.model.Toughness;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController
{
    private Connection connection;
    private String url;

    //<editor-fold desc="connection">

    public void connect(String url)
    {
        this.url = "jdbc:sqlite:" + url;
        try
        {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(this.url);
            System.out.println("Connected to database.");
            //populateDatabase(15);
        } catch (Exception ex)
        {
            System.out.println("Exception when connecting.");
            System.out.println(ex.getMessage());
        }
    }

    public void disconnect()
    {
        try
        {
            connection.close();
            System.out.println("Disconnected from database.");
        } catch (Exception ex)
        {
            System.out.println("Exception when disconnecting.");
            System.out.println(ex.getMessage());
        }
    }

    //</editor-fold>

    //<editor-fold desc="public">

    public void addSingleExamined(Examined examined)
    {
        try
        {
            PreparedStatement statement =
                    connection.prepareStatement("INSERT INTO Examined (Id, Genotype, Rank) VALUES (NULL, ?, ?)");
            statement.setString(1, examined.getGenotype());
            statement.setString(2, examined.getRank());
            statement.executeUpdate();

        } catch (Exception ex)
        {
            System.out.println("Exception in addSingleExamined.");
            System.out.println(ex.getMessage());
        }

    }

    public void addMultipleExamined(List<Examined> examinedList)
    {
        try
        {
            connection.setAutoCommit(false);
            for (Examined examined : examinedList)
            {
                addSingleExamined(examined);
            }
            connection.commit();
            connection.setAutoCommit(true);

        } catch (Exception ex)
        {
            System.out.println("Exception in addMultipleExamined.");
            System.out.println(ex.getMessage());
        }

    }

    public List<Toughness> getAllToughness()
    {
        try
        {
            ResultSet results = executeQuery("SELECT Id, Beta, Gamma, Rank FROM Toughness");
            List<Toughness> toughnessList = new ArrayList<>();
            while (results.next())
            {
                toughnessList.add(new Toughness(
                        results.getInt("Id"),
                        results.getInt("Beta"),
                        results.getInt("Gamma"),
                        results.getString("Rank")));
            }
            return toughnessList;
        } catch (Exception ex)
        {
            System.out.println("Exception in getAllToughness.");
            System.out.println(ex.getMessage());
            return null;
        }

    }

    public List<Flagella> getAllFlagella()
    {
        try
        {
            ResultSet results = executeQuery("SELECT Id, Alpha, Beta, Number FROM Flagella");
            List<Flagella> flagellaList = new ArrayList<>();
            while (results.next())
            {
                flagellaList.add(new Flagella(
                        results.getInt("Id"),
                        results.getInt("Alpha"),
                        results.getInt("Beta"),
                        results.getInt("Number")));
            }
            return flagellaList;
        } catch (Exception ex)
        {
            System.out.println("Exception in getAllFlagella.");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    //</editor-fold>

    private ResultSet executeQuery(String query)
    {
        try
        {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (Exception ex)
        {
            System.out.println("Exception in executeQuery.");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    //<editor-fold desc="generateData">

    public void populateDatabase(int count)
    {

        try
        {
            DatabaseGenerator dbGenerator = new DatabaseGenerator();
            List<Flagella> flagellaList = dbGenerator.generateFlagella(count);
            List<Toughness> toughnessList = dbGenerator.generateToughness(count);

            connection.setAutoCommit(false);
            for (Flagella flagella : flagellaList)
            {
                PreparedStatement statement =
                        connection.prepareStatement("INSERT INTO Flagella (Id, Alpha, Beta, Number) VALUES (NULL, ?, ?, ?)");
                statement.setInt(1, flagella.getAlpha());
                statement.setInt(2, flagella.getBeta());
                statement.setInt(3, flagella.getNumber());
                statement.executeUpdate();
            }
            connection.commit();

            for (Toughness toughness : toughnessList)
            {
                PreparedStatement statement =
                        connection.prepareStatement("INSERT INTO Toughness (Id, Beta, Gamma, Rank) VALUES (NULL, ?, ?, ?)");
                statement.setInt(1, toughness.getBeta());
                statement.setInt(2, toughness.getGamma());
                statement.setString(3, toughness.getRank());
                statement.executeUpdate();
            }
            connection.commit();
            connection.setAutoCommit(true);

        } catch (Exception ex)
        {
            System.out.println("Exception in populateDatabase.");
            System.out.println(ex.getMessage());
        }
    }

    //</editor-fold>

}

