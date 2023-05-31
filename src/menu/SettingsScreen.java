package menu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import readers.GameProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SettingsScreen {
    private JFrame frame;
    private JPanel settingsPanel;
    private JPanel buttonPanel;
    private JComboBox<String> fontNameDropDownMenu;
    private JScrollBar fontSizeScrollBar;
    private GameProperties gameProperties;

    public void show(JFrame frame) {
        gameProperties = new GameProperties();
        this.frame = frame;
        this.frame.getContentPane().removeAll();
        this.frame.setTitle("Settings");

        createContentPane();

        this.frame.setVisible(true);
    }

    private void createContentPane() {

        JPanel contentPane = new JPanel(new GridBagLayout());
        frame.setContentPane(contentPane);

        createSettingsPanel();
        createButtonsPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        contentPane.add(settingsPanel, gbc);
        
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.weighty = 0;
        gbc.insets = new Insets(10, 0, 0, 0);
        contentPane.add(buttonPanel, gbc);
    }
 
    private void createButtonsPanel() {

        JButton backButton = createButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenuScreen mainMenuScreen = new MainMenuScreen();
                mainMenuScreen.show(frame);
            }
        });
        JButton saveButton = createButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSettings();
            }
        });
        JButton resetButton = createButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reset settings to default
                resetSettings();
            }
        });

        buttonPanel = new JPanel(new GridLayout(1,0));
        buttonPanel.add(backButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(saveButton);

    }

    private void createSettingsPanel() {
        settingsPanel = new JPanel(new GridBagLayout());
        settingsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));


        // Create a scroll pane
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Create a panel to hold the settings list
        JPanel settingsListPanel = new JPanel(new GridBagLayout());

        // Add settings rows with labels and scroll bars or drop down menus
        fontNameDropDownMenu = createDropDownMenu(new String[] {"Arial", "Times New Roman", "Courier New"});
        fontNameDropDownMenu.setSelectedItem(gameProperties.getFontName());
        fontSizeScrollBar = createScrollBar(10, 40, 1);
        fontSizeScrollBar.setValue(gameProperties.getFontSize());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        

        addSettingsRow(settingsListPanel, "Font Name", fontNameDropDownMenu, gbc);
        gbc.gridy = 1;
        gbc.weighty = 0;
        gbc.insets = new Insets(10, 0, 0, 0);
        addSettingsRow(settingsListPanel, "Font Size", fontSizeScrollBar, gbc);

        // Add the settings list panel to the scroll pane
        scrollPane.setViewportView(settingsListPanel);

        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;

        // Add the scroll pane to the settings panel
        settingsPanel.add(scrollPane, gbc);

    }

    

    private JScrollBar createScrollBar(int min, int max, int unitIncrement) {
        JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL, min, unitIncrement, min, max);
        scrollBar.setUnitIncrement(unitIncrement);

        return scrollBar;
    }

    private JComboBox<String> createDropDownMenu(String[] options) {
        JComboBox<String> dropDownMenu = new JComboBox<>(options);

        return dropDownMenu;
    }
    private void addSettingsRow(JPanel settingsListPanel, String labelText, JComponent component, GridBagConstraints gbc) {
        JPanel rowPanel = new JPanel(new GridLayout(1,0));
        rowPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font(gameProperties.getFontName(), Font.PLAIN, gameProperties.getFontSize()));
        
        rowPanel.add(label);
        rowPanel.add(component);

        settingsListPanel.add(rowPanel, gbc);
    }
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        String fontName = gameProperties.getFontName();
        int fontSize = gameProperties.getFontSize();

        button.setFont(new Font(fontName, Font.PLAIN, fontSize));
        return button;
    }
    private void saveSettings() {
        // Load the current settings from game_config.json
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Pretty print the JSON (add newlines and indentation)

        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE.withIndent("    "));


        File configFile = new File("src/config/game_config.json");

        try {
            JsonNode rootNode = objectMapper.readTree(configFile);

            // Update the game_config values with the user-entered values from the settings panel
            // Assuming each setting has a corresponding field in the settings panel class
            ObjectNode fontNode = (ObjectNode) rootNode.get("font");

            // Update the settings values
            fontNode.put("name", getSettingFontName());
            fontNode.put("size", getSettingFontSize());

            // Save the updated settings to game_config.json
            ObjectWriter objectWriter = objectMapper.writer(prettyPrinter);
            objectWriter.writeValue(configFile, rootNode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        reloadSettingsPanel();

    }
    private void resetSettings() {
        // Delete the existing game_config file
        File configFile = new File("src/config/game_config.json");
        try {
            Files.deleteIfExists(Paths.get(configFile.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a new game_config file with the default values
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Pretty print the JSON (add newlines and indentation)
        
        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE.withIndent("    "));
        
        File defaultConfigFile = new File("src/config/game_config_default.json");
        try {
            JsonNode rootNode = objectMapper.readTree(defaultConfigFile);
            
            ObjectWriter objectWriter = objectMapper.writer(prettyPrinter);
            objectWriter.writeValue(configFile, rootNode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        reloadSettingsPanel();
    }

    private void reloadSettingsPanel() {
        gameProperties = new GameProperties();
        
        createSettingsPanel();
        createButtonsPanel();

        // Update the frame with the new settings panel
        frame.getContentPane().removeAll();
        createContentPane();
        
        frame.revalidate();
        frame.repaint();

        
    }
    private String getSettingFontName() {
        return (String) fontNameDropDownMenu.getSelectedItem();
        
    }
    private int getSettingFontSize() {
        return fontSizeScrollBar.getValue();
    }

}
