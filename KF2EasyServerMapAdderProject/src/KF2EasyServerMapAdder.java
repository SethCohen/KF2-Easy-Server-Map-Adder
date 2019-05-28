import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.*;

public class KF2EasyServerMapAdder extends Application {
    static int mapsCounter = 0;
    static File filePath;
    static Label alert;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Killing Floor 2 Easy Server Map Adder");
        primaryStage.setOnCloseRequest(e -> System.exit(0));

        BorderPane bPane = new BorderPane();
        HBox hBox = new HBox();
        VBox vBox = new VBox();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open");

        Label mapsFound = new Label("Maps Found: 0");
        mapsFound.setFont(new Font(20));
        mapsFound.setStyle("-fx-text-fill: white;-fx-font-weight: bold;");

        alert = new Label("");
        alert.setStyle("-fx-text-fill: white;-fx-font-weight: bold;");

        Label folderDirLbl = new Label("Choose Maps Location...");
        folderDirLbl.setStyle("-fx-text-fill: white;");

        TextField folderDirTF = new TextField("C:\\Program Files (x86)\\Steam\\steamapps\\workshop\\content\\232090");
        folderDirTF.setStyle("-fx-background-color: rgb(54,57,62);-fx-border-color: white;-fx-text-fill: white");

        bPane.setStyle("-fx-background-color: rgb(54,57,62)");
        bPane.setPrefSize(640,360);
        bPane.setMargin(hBox, new Insets(0, 5, 5, 5));
        hBox.setSpacing(5);

        Button websiteBtn = new Button("GitHub");
        websiteBtn.setStyle("-fx-background-color: rgb(54,57,62);-fx-text-fill: white");
        websiteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                websiteBtn.setStyle("-fx-background-color: rgb(66,69,73);-fx-text-fill: cyan");

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
        bPane.setTop(websiteBtn);

        Button browseBtn = new Button("Browse");
        browseBtn.setStyle("-fx-background-color: rgb(54,57,62);-fx-border-color: white;-fx-text-fill: white");
        browseBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                browseBtn.setStyle("-fx-background-color: rgb(66,69,73);-fx-border-color: cyan;-fx-text-fill: white");

                new Thread(()->{
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    browseBtn.setStyle("-fx-background-color: rgb(54,57,62);-fx-border-color: white;-fx-text-fill: white");
                }).start();

                File selectedDirectory = directoryChooser.showDialog(primaryStage);
                if (selectedDirectory != null) {
                    folderDirTF.setText(selectedDirectory.toString());
                }
            }
        });

        Button confirmBtn = new Button("Confirm");
        confirmBtn.setStyle("-fx-background-color: rgb(54,57,62);-fx-border-color: white;-fx-text-fill: white");
        confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                confirmBtn.setStyle("-fx-background-color: rgb(66,69,73);-fx-border-color: cyan;-fx-text-fill: white");

                mapsCounter = 0;
                filePath = new File(folderDirTF.getText());

                if (filePath != null){
                    createOutput();
                    mapsFound.setText("Maps Found: " + mapsCounter);
                }
                else {
                    alert.setText("No maps found. Please select a different directory.");
                }

                new Thread(()->{
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    confirmBtn.setStyle("-fx-background-color: rgb(54,57,62);-fx-border-color: white;-fx-text-fill: white");
                }).start();
            }
        });

        vBox.getChildren().add(mapsFound);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(alert);
        bPane.setCenter(vBox);

        hBox.setMargin(folderDirLbl, new Insets(5, 0,0,0));
        hBox.setHgrow(folderDirTF, Priority.ALWAYS);
        hBox.getChildren().addAll(folderDirLbl, folderDirTF, browseBtn, confirmBtn);
        bPane.setBottom(hBox);

        Scene scene = new Scene(bPane);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    // Creates & writes to files
    public void createOutput(){
        // Creates output dir
        File outputDir = new File("Output");
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }

        File dir = filePath;
        File[] dirList = dir.listFiles();


        if (dirList != null) {
            BufferedWriter gameMapCyclesTxtWriter = null;
            BufferedWriter serverSubbedWSItemsTxtWriter = null;
            BufferedWriter webAdminTxtWriter = null;

            try {
                // Open streams
                gameMapCyclesTxtWriter = new BufferedWriter(new FileWriter("Output\\GameMapCycles.txt"));
                serverSubbedWSItemsTxtWriter = new BufferedWriter(new FileWriter("Output\\ServerSubscribedWorkshopItems.txt"));
                webAdminTxtWriter = new BufferedWriter(new FileWriter("Output\\WebAdmin.txt"));

                // Iterates through each file in directory
                for (File mapID : dirList) {
                    File kfmFile = findFile(mapID); // Finds .kfm

                    // Write to streams
                    if (kfmFile != null){
                        gameMapCyclesTxtWriter.write(",\"" + kfmFile.getName() + "\"");
                        serverSubbedWSItemsTxtWriter.write("ServerSubscribedWorkshopItems=" + mapID.getName() + "\n");
                        webAdminTxtWriter.write("[" + kfmFile.getName() + " KFMapSummary]\nMapName=" + kfmFile.getName() + "\n\n");

                        mapsCounter++;
                    }
                }

                // Close streams
                gameMapCyclesTxtWriter.close();
                serverSubbedWSItemsTxtWriter.close();
                webAdminTxtWriter.close();

                // Sets alert
                if (mapsCounter != 0) {
                    alert.setText("Done. Please check Output folder & files.");
                }
                else {
                    alert.setText("No maps found. Please select a different directory.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            alert.setText("No maps found. Please select a different directory.");
        }

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
                    return dirList[i];
                }
            }
        }

        return result;
    }
}
