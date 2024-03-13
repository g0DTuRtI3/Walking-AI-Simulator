package edu.vanier.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller class of the MainApp's UI.
 *
 * @author frostybee
 */
public class MainAppController {

    private final static Logger logger = LoggerFactory.getLogger(MainAppController.class);
    @FXML
    Button btn_Simulation;

    @FXML
    public void initialize() {
        logger.info("Initializing MainAppController...");
        btn_Simulation.setOnAction((event) -> {
            handleClickMe();
        });
    }

    private void handleClickMe() {
        System.out.println("Click me called.");
        logger.info("Click me button has been pressed...");        
    }
}