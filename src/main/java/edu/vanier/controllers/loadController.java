package edu.vanier.controllers;

import edu.vanier.database.SqliteDB;
import java.io.IOException;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class loadController {

    private final SqliteDB database = new SqliteDB();
    private final Stage primaryStage;

    @FXML
    private ChoiceBox<String> cb_model;
    
    @FXML
    private ListView<String> modelList;
    
    public loadController(Stage stage) {
        primaryStage = stage;
    }

    /**
     * This method gets the name of the model that the user selects. It then calls returnToEditor(String selectedModel).
     */
    @FXML
    void initialize() {
        modelList.getItems().addAll(database.readModelName());

        modelList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            String selectedModel = modelList.getSelectionModel().getSelectedItem();
            try {
                System.out.println("Load controller name :" + selectedModel);
                returnToEditor(selectedModel);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        });
        
        cb_model.getItems().addAll(database.readModelName());
    }
    
    /**
     * This deletes the selected model from the database.
     * 
     * @param event The ActionEvent from the GUI
     */
    @FXML
    void deleteOnAction(ActionEvent event) {
        if (cb_model.getValue() != null) {
            cb_model.getItems().remove(cb_model.getValue());
            database.deleteModel(cb_model.getValue());
            
            modelList.getItems().clear();
            modelList.getItems().addAll(database.readModelName());

            cb_model.setValue(null);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please chose a medel to delete");
            alert.show();
        }
    }
    
    /**
     * This method switches the scene back to the editor.
     * 
     * @param event The ActionEvent from the GUI
     * @throws IOException 
     */
    @FXML
    void returnOnAction(ActionEvent event) throws IOException {
        FXMLLoader mainAppLoader = new FXMLLoader(getClass().getResource("/fxml/Editor_layout.fxml"));
        EditorController controller = new EditorController(primaryStage);
        mainAppLoader.setController(controller);
        Pane root = mainAppLoader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(false);
        primaryStage.setMaximized(true);
        // We just need to bring the main window to front.
        primaryStage.setTitle("Editor");
        primaryStage.show();
    }

    /**
     * The method switches the scene to the Editor. It makes the editor load the selected walker.
     * 
     * @param selectedModel The name of the model that the user chose.
     * @throws IOException 
     */
    public void returnToEditor(String selectedModel) throws IOException {
        FXMLLoader mainAppLoader = new FXMLLoader(getClass().getResource("/fxml/Editor_layout.fxml"));
        EditorController controller = new EditorController(primaryStage, selectedModel);
        mainAppLoader.setController(controller);
        Pane root = mainAppLoader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(false);
        primaryStage.setMaximized(true);
        // We just need to bring the main window to front.
        primaryStage.setAlwaysOnTop(false);
        primaryStage.setTitle("Editor");
        primaryStage.show();
    }
    
}
