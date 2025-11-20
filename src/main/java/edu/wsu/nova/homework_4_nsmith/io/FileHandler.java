package edu.wsu.nova.homework_4_nsmith.io;

import edu.wsu.nova.homework_4_nsmith.model.abilities.Transformable;
import edu.wsu.nova.homework_4_nsmith.model.abilities.Vulnerability;
import edu.wsu.nova.homework_4_nsmith.model.characters.*;
import edu.wsu.nova.homework_4_nsmith.model.data.AppData;
import edu.wsu.nova.homework_4_nsmith.utility.SceneSwitcher;

import javafx.stage.FileChooser;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class FileHandler {

    /** Date format for CSV serialization. */
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Saves all HorrorCharacter data to CSV.
     * CSV format:
     * name, transformation status, health, vulnerabilities, date created, and type
     */
    public static void saveAsCSV(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (HorrorCharacter parsedCharacter : AppData.getAllCharacters()) {

                // This handles escaping commas and quotes in names
                String name = escapeCSV(parsedCharacter.getName());
                String health = String.valueOf(parsedCharacter.getHealth());
                String isTransformed = "FALSE";
                if (parsedCharacter instanceof Transformable) {
                    isTransformed = ((Transformable) parsedCharacter).getTransformed() ? "TRUE" : "FALSE";
                }
                String vulnerabilities = parsedCharacter.getVulnerabilities()
                        .stream()
                        .map(Enum::name)
                        .collect(Collectors.joining(";"));
                String date = parsedCharacter.getDateCreated().format(DATE_FORMAT);
                String type = characterType(parsedCharacter);

                writer.write(name + "," + isTransformed + "," + health + "," + vulnerabilities + "," + date + "," + type);
                writer.newLine();
            }
        }
        catch (IOException e) {
            System.err.println("Error saving CSV: " + e.getMessage());
        }
    }

    /** Determine subclass type for saving. */
    private static String characterType(HorrorCharacter character) {
        if (character instanceof Zombie) return "ZOMBIE";
        if (character instanceof Vampire) return "VAMPIRE";
        if (character instanceof Werewolf) return "WEREWOLF";
        return "UNKNOWN";
    }

    /** Escape fields that contain quotes or commas. */
    private static String escapeCSV(String field) {
        if (field.contains(",") || field.contains("\"")) {
            // Escape quotes by doubling them
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        // No special characters, return as is
        return field;
    }

    /**
     * Saves characters to binary. Java serialization handles subclass types automatically.
     */
    public static void saveAsBin(String filePath) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            objectOutputStream.writeObject(new ArrayList<>(AppData.getAllCharacters()));
        }
        catch (IOException e) {
            System.err.println("Error saving binary: " + e.getMessage());
        }
    }

    /**
     * Loads characters from CSV and instantiates correct subclasses.
     */
    public static void loadCSV(String filePath) {
        // Clear existing data to prevent duplication
        AppData.clearCharacters();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            while ((line = bufferedReader.readLine()) != null) {
                lineNumber++;

                List<String> parts = parseCSV(line);

                if (parts.size() != 6) {
                    System.err.println("Skipping malformed line " + lineNumber + ": " + line);
                    continue;
                }

                try {
                    String name = parts.get(0).trim();
                    boolean isTransformed = parts.get(1).trim().equalsIgnoreCase("TRUE");
                    int health = Integer.parseInt(parts.get(2).trim());
                    List<Vulnerability> vulnerabilities =
                            Arrays.stream(parts.get(3).split(";"))
                                    .filter(string -> !string.isBlank())
                                    .map(String::trim)
                                    .map(Vulnerability::valueOf)
                                    .collect(Collectors.toList());
                    LocalDate dateCreated =
                            LocalDate.parse(parts.get(4).trim(), DATE_FORMAT);
                    String type = parts.get(5).trim().toUpperCase();

                    HorrorCharacter character =
                            createCharacter(type, name, health, vulnerabilities, dateCreated);

                    if (character instanceof Transformable transformable) {
                        transformable.setTransformed(isTransformed);
                    }

                    AppData.addCharacter(character);
                }
                catch (IllegalArgumentException e) {
                    System.err.println("Error loading CSV: " + e.getMessage());
                }
            }
        }
        catch (IOException e) {
            System.err.println("Error loading CSV: " + e.getMessage());
        }
    }

    /** Instantiate the correct subclass based on type field. */
    private static HorrorCharacter createCharacter(
            String type,
            String name,
            int health,
            List<Vulnerability> vulnerabilities,
            LocalDate dateCreated) {

        return switch (type) {
            case "ZOMBIE" -> new Zombie(name, health, vulnerabilities, dateCreated);
            case "VAMPIRE" -> new Vampire(name, health, vulnerabilities, dateCreated);
            case "WEREWOLF" -> new Werewolf(name, health, vulnerabilities, dateCreated);
            default -> throw new IllegalArgumentException("Unknown character type: " + type);
        };
    }

    /**
     * CSV parser that supports quoted fields.
     */
    private static List<String> parseCSV(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        boolean inQuotes = false;

        for (char alphaNumericCharacter : line.toCharArray()) {
            if (alphaNumericCharacter == '"') {
                inQuotes = !inQuotes;
                continue;
            }

            if (alphaNumericCharacter == ',' && !inQuotes) {
                fields.add(stringBuilder.toString());
                stringBuilder.setLength(0);
            }

            else {
                stringBuilder.append(alphaNumericCharacter);
            }
        }

        fields.add(stringBuilder.toString());
        return fields;
    }

    /**
     * Loads subclassed HorrorCharacter objects from binary.
     */
    public static void loadBin(String filePath) {
        AppData.clearCharacters();

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            Object object = objectInputStream.readObject();

            if (object instanceof List<?> list) {
                for (Object o : list) {
                    if (o instanceof HorrorCharacter hc) {
                        AppData.addCharacter(hc);
                    }
                }
            }

        }

        catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading binary: " + e.getMessage());
        }
    }

    /**
     * File chooser for selecting save destination.
     */
    public static void saveAsFile() {
        // Create file chooser
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select File Location");
        chooser.setInitialDirectory(new File(System.getProperty("user.home"), "Downloads"));

        // Filters
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV File (*.csv)", "*.csv");
        FileChooser.ExtensionFilter binFilter = new FileChooser.ExtensionFilter("Binary File (*.bin)", "*.bin");
        chooser.getExtensionFilters().addAll(csvFilter, binFilter);

        // Determine default filter and filename
        chooser.setSelectedExtensionFilter(csvFilter);
        chooser.setInitialFileName("untitled.csv");

        // Show dialog
        File file = chooser.showSaveDialog(SceneSwitcher.getCurrentWindow());
        if (file == null) return;

        // Ensure filename extension matches selected filter
        FileChooser.ExtensionFilter selectedFilter = chooser.getSelectedExtensionFilter();
        String path = file.getAbsolutePath();

        // Append extension if missing
        if (selectedFilter == csvFilter && !path.toLowerCase().endsWith(".csv")) {
            path += ".csv";
        }
        else if (selectedFilter == binFilter && !path.toLowerCase().endsWith(".bin")) {
            path += ".bin";
        }

        // Save according to filter
        if (selectedFilter == csvFilter) {
            saveAsCSV(path);
        } else if (selectedFilter == binFilter) {
            saveAsBin(path);
        }
    }

    /**
     * File chooser for selecting load source.
     */
    public static void loadFromFile() {
        // Create file chooser
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select File to Load");
        chooser.setInitialDirectory(new File(System.getProperty("user.home"), "Downloads"));

        // Filters
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV File (*.csv)", "*.csv");
        FileChooser.ExtensionFilter binFilter = new FileChooser.ExtensionFilter("Binary File (*.bin)", "*.bin");
        chooser.getExtensionFilters().addAll(csvFilter, binFilter);

        // Show dialog
        File file = chooser.showOpenDialog(SceneSwitcher.getCurrentWindow());
        if (file == null) return;

        // Determine file extension
        String path = file.getAbsolutePath().toLowerCase();

        // Load according to file extension
        if (path.endsWith(".csv")) {
            loadCSV(path);
        }
        else if (path.endsWith(".bin")) {
            loadBin(path);
        }
        else {
            System.err.println("Unsupported file type: " + path);
        }
    }
}
