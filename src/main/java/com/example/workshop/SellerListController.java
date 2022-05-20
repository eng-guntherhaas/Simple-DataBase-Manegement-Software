package com.example.workshop;

import com.example.workshop.db.DbIntegrityException;
import com.example.workshop.gui.listeners.DataChangeListener;
import com.example.workshop.gui.util.Alerts;
import com.example.workshop.model.entities.Seller;
import com.example.workshop.model.services.SellerService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SellerListController implements Initializable, DataChangeListener {

    private SellerService service;

    private ObservableList<Seller> obsList;

    @FXML
    private TableView<Seller> tableViewSeller;

    @FXML
    private TableColumn<Seller, Integer> tableColumnId;

    @FXML
    private TableColumn<Seller, String> tableColumnName;

    @FXML
    private TableColumn<Seller, Seller> tableColumnEdit;

    @FXML
    private TableColumn<Seller, Seller> tableColumnRemove;

    @FXML
    private Button btNew;


//    @FXML
//    public void onBtNewAction(ActionEvent event) {
//        Stage parentStage = Utils.currentStage(event);
//        Seller obj = new Seller();
//        createDialogForm(obj, "SellerFormView.fxml", parentStage);
//    }

    public void setSellerService(SellerService service) {
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewSeller.prefHeightProperty().bind(stage.heightProperty());
    }

    public void updateTableView() {
        if (service == null) {
            throw new IllegalStateException("Service is null");
        }
        List<Seller> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewSeller.setItems(obsList);
//        initEditButtons();
        initRemoveButtons();
    }

//    private void createDialogForm(Seller obj, String absoluteName, Stage parentStage) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(absoluteName));
//            Pane pane = fxmlLoader.load();
//
//            SellerFormController controller = fxmlLoader.getController();
//            controller.setSeller(obj);
//            controller.setSellerService(new SellerService());
//            controller.subscribeDataChangeListener(this);
//            controller.updateFormData();
//
//            Stage dialogeStage = new Stage();
//            dialogeStage.setTitle("Enter seller data");
//            dialogeStage.setScene(new Scene(pane));
//            dialogeStage.setResizable(false);
//            dialogeStage.initOwner(parentStage);
//            dialogeStage.initModality(Modality.WINDOW_MODAL);
//            dialogeStage.showAndWait();
//        } catch (IOException e) {
//            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
//        }
//    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }

//    private void initEditButtons(){
//        tableColumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
//        tableColumnEdit.setCellFactory(param -> new TableCell<Seller, Seller>() {
//            private final Button button = new Button("Edit");
//
//            @Override
//            protected void updateItem(Seller obj, boolean empty) {
//                super.updateItem(obj, empty);
//
//                if (obj == null) {
//                    setGraphic(null);
//                    return;
//                }
//
//                setGraphic(button);
//                button.setOnAction(
//                        event -> createDialogForm(obj, "SellerFormView.fxml", Utils.currentStage(event))
//                );
//            }
//        });
//    }

    private void initRemoveButtons(){
        tableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnRemove.setCellFactory(param -> new TableCell<Seller, Seller>(){
            private final Button button = new Button("Delete");

            @Override
            protected void updateItem(Seller obj, boolean empty) {
                super.updateItem(obj, empty);

                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(
                        event -> removeEntity(obj)
                );
            }
        });
    }

    private void removeEntity(Seller obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure?");
        if (result.get() == ButtonType.OK){
            if (service == null) {
                throw new IllegalStateException("Service is null");
            }
            try {
                service.remove(obj);
                updateTableView();
            }
            catch (DbIntegrityException e){
                Alerts.showAlert("Error removing object", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
}
