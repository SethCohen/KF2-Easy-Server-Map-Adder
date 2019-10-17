import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KF2EasyServerMapAdder extends Application {
    static int mapsFound = 0;
    static File filePath;
    ArrayList<String> mapNames = new ArrayList<String>();
    ArrayList<CheckBox> checkboxes = new ArrayList<CheckBox>();
    Map<String, String> hashMap = new HashMap<String, String>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Killing Floor 2 Easy Map Adder For Servers");
        primaryStage.setOnCloseRequest(e -> System.exit(0));

        VBox mainVbox = new VBox();
        mainVbox.setStyle("-fx-background-color: rgb(54,57,62);");
        mainVbox.setSpacing(5);
        VBox checkboxesVbox = new VBox();
        ScrollPane scrollpane = new ScrollPane();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open");

        CheckBox selectAll = new CheckBox();
        Button confirmBtn = new Button("Confirm");
        confirmBtn.setStyle("-fx-background-color: rgb(54,57,62);-fx-border-color: white;-fx-text-fill: white");
        scrollpane.setContent(checkboxesVbox);
        scrollpane.setPadding(new Insets(0, 0, 0, 0));
        scrollpane.setStyle("-fx-background-color:transparent;-fx-background: rgb(54,57,62);");

        confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                confirmBtn.setStyle("-fx-background-color: rgb(66,69,73);-fx-border-color: gray;-fx-text-fill: white");
                new Thread(()->{
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    confirmBtn.setStyle("-fx-background-color: rgb(54,57,62);-fx-border-color: white;-fx-text-fill: white");
                }).start();

                int mapsSelected = 0;
                for (CheckBox i : checkboxes){
                    if (i.isSelected()){
                        mapsSelected++;
                    }
                }
                selectAll.setText("\t" + checkboxes.size() + " Maps Found" + "\t\t" + mapsSelected + " Maps Outputted");

                createOutput(hashMap);
            }
        });

        selectAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (selectAll.isSelected()) {
                    for (CheckBox i : checkboxes) {
                        i.setSelected(true);
                    }
                }
                else {
                    for (CheckBox i : checkboxes) {
                        i.setSelected(false);
                    }
                }
            }
        });

        Button websiteBtn = new Button("GitHub");
        websiteBtn.setStyle("-fx-background-color: rgb(54,57,62);-fx-text-fill: white");
        websiteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                websiteBtn.setStyle("-fx-background-color: rgb(66,69,73);-fx-text-fill: gray");

                new Thread(()->{
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    websiteBtn.setStyle("-fx-background-color: rgb(54,57,62);-fx-text-fill: white");
                }).start();

                getHostServices().showDocument("https://github.com/SethCohen/KF2-Easy-Server-Map-Adder");
            }
        });

        HBox hbox = new HBox();
        Button chooseMapsBtn = new Button("Choose Maps Location");
        chooseMapsBtn.setStyle("-fx-background-color: rgb(54,57,62);-fx-border-color: white;-fx-text-fill: white");
        TextField chooseMapsTF = new TextField("C:\\Program Files (x86)\\Steam\\steamapps\\workshop\\content\\232090");
        chooseMapsTF.setStyle("-fx-background-color: rgb(54,57,62);-fx-border-color: white;-fx-text-fill: white");
        Button mapsLocConfirmBtn = new Button("Confirm");
        mapsLocConfirmBtn.setStyle("-fx-background-color: rgb(54,57,62);-fx-border-color: white;-fx-text-fill: white");
        hbox.setSpacing(1);
        hbox.setHgrow(chooseMapsTF, Priority.ALWAYS);
        hbox.getChildren().addAll(websiteBtn, chooseMapsBtn,chooseMapsTF, mapsLocConfirmBtn);
        mainVbox.getChildren().add(hbox);

        chooseMapsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chooseMapsBtn.setStyle("-fx-background-color: rgb(66,69,73);-fx-border-color: gray;-fx-text-fill: white");
                new Thread(()->{
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    chooseMapsBtn.setStyle("-fx-background-color: rgb(54,57,62);-fx-border-color: white;-fx-text-fill: white");
                }).start();

                File selectedDirectory = directoryChooser.showDialog(primaryStage);
                if (selectedDirectory != null) {
                    chooseMapsTF.setText(selectedDirectory.toString());
                }
            }
        });
        mapsLocConfirmBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mapsLocConfirmBtn.setStyle("-fx-background-color: rgb(66,69,73);-fx-border-color: gray;-fx-text-fill: white");
                new Thread(()->{
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mapsLocConfirmBtn.setStyle("-fx-background-color: rgb(54,57,62);-fx-border-color: white;-fx-text-fill: white");
                }).start();

                try {
                    filePath = new File(chooseMapsTF.getText());
                    File[] dirList = filePath.listFiles();

                    if (filePath != null){
                        mapNames.clear();
                        hashMap.clear();

                        for (File mapID : dirList){
                            File kfmFile = findFile(mapID);
                            if (kfmFile != null){
                                mapNames.add(kfmFile.getName());
                                hashMap.put(kfmFile.getName(), mapID.getName());
                            }
                        }
                    }
                    Collections.sort(mapNames);
                    for (String i : mapNames){
                        CheckBox box = new CheckBox();
                        box.setText(i);
                        checkboxes.add(box);
                        checkboxesVbox.getChildren().add(box);
                    }

                    selectAll.setText("\t" + checkboxes.size() + " Maps Found");
                    selectAll.setStyle("-fx-text-fill: white;-fx-font-weight: bold;");
                    mainVbox.getChildren().addAll(selectAll, scrollpane, confirmBtn);
                } catch (IllegalArgumentException e) {
                    selectAll.setSelected(false);
                    checkboxes.clear();
                    checkboxesVbox.getChildren().clear();
                    mainVbox.getChildren().removeAll(selectAll, scrollpane, confirmBtn);

                    filePath = new File(chooseMapsTF.getText());
                    File[] dirList = filePath.listFiles();

                    if (filePath != null){
                        mapNames.clear();
                        hashMap.clear();

                        for (File mapID : dirList){
                            File kfmFile = findFile(mapID);
                            if (kfmFile != null){
                                mapNames.add(kfmFile.getName());
                                hashMap.put(kfmFile.getName(), mapID.getName());
                            }
                        }
                    }
                    Collections.sort(mapNames);
                    for (String i : mapNames){
                        CheckBox box = new CheckBox();
                        box.setText(i);
                        checkboxes.add(box);
                        checkboxesVbox.getChildren().add(box);
                    }

                    selectAll.setText("\t" + checkboxes.size() + " Maps Found");
                    selectAll.setStyle("-fx-text-fill: white;-fx-font-weight: bold;");
                    mainVbox.getChildren().addAll(selectAll, scrollpane, confirmBtn);

                } catch (NullPointerException e) {
                    chooseMapsTF.setText("No maps found! Please select a valid directory.");

                }
            }
        });

        mainVbox.setPadding(new Insets(5, 5, 5, 5));
        Scene scene = new Scene(mainVbox, 630, 360);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Recursively looks for .kfm files, returns null if one isn't found.
    public static File findFile(File dir) {
        File result = null;
        File[] dirList  = dir.listFiles();

        if (dirList != null){
            for(int i = 0; i < dirList.length; i++) {
                if(dirList[i].isDirectory()) {
                    result = findFile(dirList[i]);
                    if (result != null) break;
                } else if(dirList[i].getName().contains(".kfm")) {
                    if (dirList[i].getName().contains("kf-")){
                        return dirList[i];
                    }
                    else if (dirList[i].getName().contains("KF-")){
                        return dirList[i];
                    }
                }
            }
        }

        return result;
    }

    public void createOutput(Map<String, String> hashMap){
        File outputDir = new File("Output");
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }

        BufferedWriter gameMapCyclesTxtWriter = null;
        BufferedWriter serverSubbedWSItemsTxtWriter = null;
        BufferedWriter webAdminTxtWriter = null;

        try {
            // Open streams
            gameMapCyclesTxtWriter = new BufferedWriter(new FileWriter("Output\\GameMapCycles.txt"));
            serverSubbedWSItemsTxtWriter = new BufferedWriter(new FileWriter("Output\\ServerSubscribedWorkshopItems.txt"));
            webAdminTxtWriter = new BufferedWriter(new FileWriter("Output\\WebAdmin.txt"));

            for (CheckBox checkbox : checkboxes){
                if (checkbox.isSelected()){
                    gameMapCyclesTxtWriter.write(",\"" + checkbox.getText().substring(0, checkbox.getText().length() - 4) + "\"");
                    webAdminTxtWriter.write("[" + checkbox.getText().substring(0, checkbox.getText().length() - 4) + " KFMapSummary]\nMapName=" + checkbox.getText().substring(0, checkbox.getText().length() - 4) + "\n\n");
                    serverSubbedWSItemsTxtWriter.write("ServerSubscribedWorkshopItems=" + hashMap.get(checkbox.getText()) + "\n");


                }
            }

            // Close streams
            gameMapCyclesTxtWriter.close();
            serverSubbedWSItemsTxtWriter.close();
            webAdminTxtWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
