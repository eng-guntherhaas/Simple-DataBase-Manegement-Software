package com.example.workshop;

import com.example.workshop.model.services.DepartmentService;
import com.example.workshop.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class MainController implements Initializable {
    @FXML
    private MenuItem menuItemSeller;

    @FXML
    private MenuItem menuItemDepartment;

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuItemSellerAction(){
        System.out.println("onMenuItemSellerAction");
    }

    @FXML
    public void onMenuItemDepartmentAction(){
        loadView("DepartmentListView.fxml", (DepartmentController controller) -> {
            controller.setDepartmentService(new DepartmentService());
            controller.updateTableView();
        });
    }

    @FXML
    public void onMenuItemAboutAction(){
       loadView("AboutView.fxml", x -> {});
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVBox = fxmlLoader.load();

            Scene mainScene = Main.getMainScene();
            VBox mainVBox = (VBox)((ScrollPane) mainScene.getRoot()).getContent();

            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVBox.getChildren());

            T controller = fxmlLoader.getController();
            initializingAction.accept(controller);
        }
        catch (IOException e){
            Alerts.showAlert("IO Excaption", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

}