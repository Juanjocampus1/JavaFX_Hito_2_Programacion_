package com.empresa.hito_2_3t_programacion_fx.Controllers;

import com.empresa.hito_2_3t_programacion_fx.APIs.CrudApi.HTTP.Response.GetUserResponse;
import com.empresa.hito_2_3t_programacion_fx.DTO.DataDTO;
import com.empresa.hito_2_3t_programacion_fx.APIs.CrudApi.HTTP.Request.DeleteRequest;
import com.empresa.hito_2_3t_programacion_fx.APIs.CrudApi.HTTP.Request.PostRequest;
import com.empresa.hito_2_3t_programacion_fx.APIs.CrudApi.HTTP.Request.PutRequest;
import com.empresa.hito_2_3t_programacion_fx.APIs.CrudApi.HTTP.Response.GetResponse;
import com.empresa.hito_2_3t_programacion_fx.DTO.RoleDTO;
import com.empresa.hito_2_3t_programacion_fx.DTO.TokenDTO;
import com.empresa.hito_2_3t_programacion_fx.DTO.UserDTO;
import com.empresa.hito_2_3t_programacion_fx.Persistence.TokenStorage;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MainController {

    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField descriptionField;
    @FXML
    private ComboBox<String> categoryField;
    @FXML
    private TextField priceField;

    @FXML
    private TableView<DataDTO> dataTable;
    @FXML
    private TableColumn<DataDTO, Long> idColumn;
    @FXML
    private TableColumn<DataDTO, String> nameColumn;
    @FXML
    private TableColumn<DataDTO, String> descriptionColumn;
    @FXML
    private TableColumn<DataDTO, String> categoryColumn;
    @FXML
    private TableColumn<DataDTO, BigDecimal> priceColumn;

    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;

    @FXML
    private Label userEmailLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label userRolesLabel;
    private TokenDTO tokenDTO;

    public List<DataDTO> getDataList() {
        // return the data list
        return Collections.emptyList();
    }

    public void initialize() {

        // Obtener el token de TokenStorage
        String token = TokenStorage.getToken();

        // Crear un objeto GetUserResponse y enviar la solicitud GET a la API
        GetUserResponse getUserResponse = new GetUserResponse();
        UserDTO userDto = getUserResponse.getUserByToken();

        // Mapear los datos del usuario en las etiquetas
        if (userDto != null) {
            userEmailLabel.setText("Email: " + userDto.getEmail());
            usernameLabel.setText("Username: " + userDto.getUsername());
            userRolesLabel.setText("Roles: " + userDto.getRoles().stream().map(RoleDTO::getName).collect(Collectors.joining(", ")));
        }

        // Configurar ancho relativo para las columnas
        idColumn.prefWidthProperty().bind(dataTable.widthProperty().multiply(0.1)); // 10% del ancho total
        nameColumn.prefWidthProperty().bind(dataTable.widthProperty().multiply(0.2)); // 20% del ancho total
        descriptionColumn.prefWidthProperty().bind(dataTable.widthProperty().multiply(0.3)); // 30% del ancho total
        categoryColumn.prefWidthProperty().bind(dataTable.widthProperty().multiply(0.2)); // 20% del ancho total
        priceColumn.prefWidthProperty().bind(dataTable.widthProperty().multiply(0.2)); // 20% del ancho total

        // Configurar las columnas para mostrar los datos correctos
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Crear un objeto GetResponse y enviar la solicitud GET a la API
        GetResponse getResponse = new GetResponse();
        List<DataDTO> dataList = getResponse.sendGetRequest();

        // Mapear los datos recibidos en la tabla
        ObservableList<DataDTO> observableList = FXCollections.observableArrayList(dataList);
        dataTable.setItems(observableList);

        // Establecer el RowFactory personalizado
        dataTable.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(DataDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setStyle("");
                } else {
                    if (isSelected()) {
                        setStyle("-fx-text-fill: white;");
                    } else {
                        setStyle("-fx-text-fill: #000;");
                    }
                }
            }
        });

        // Agregar un manejador de eventos de clic a la tabla
        dataTable.setOnMouseClicked(event -> {
            if (!dataTable.getSelectionModel().isEmpty()) {
                DataDTO selectedData = dataTable.getSelectionModel().getSelectedItem();
                idField.setText(String.valueOf(selectedData.getId()));
                nameField.setText(selectedData.getName());
                descriptionField.setText(selectedData.getDescription());

                // Establecer la categoría seleccionada en el ComboBox
                categoryField.setValue(selectedData.getCategory());

                priceField.setText(selectedData.getPrice().toString());
            }
        });
    }

    @FXML
    public void onSearchButtonClick() {
        String searchText = searchField.getText().toLowerCase();

        // Crear un objeto GetResponse y enviar la solicitud GET a la API
        GetResponse getResponse = new GetResponse();
        List<DataDTO> dataList = getResponse.sendGetRequest();

        // Filtrar la lista de datos para incluir solo los que coinciden con el texto de búsqueda
        List<DataDTO> filteredList = new ArrayList<>();
        for (DataDTO data : dataList) {
            if (data.getName().toLowerCase().contains(searchText)) {
                filteredList.add(data);
            }
        }

        // Mapear los datos filtrados en la tabla
        ObservableList<DataDTO> observableList = FXCollections.observableArrayList(filteredList);
        dataTable.setItems(observableList);
    }

    @FXML
    public void onCreateButtonClick() {
        // Extraer los valores de los campos de texto
        String name = nameField.getText();
        String description = descriptionField.getText();
        String category = categoryField.getValue();
        String priceText = priceField.getText();

        //definir una constante con el valor error
        final String ERROR = "ERROR";

        // Verificar si algún campo está vacío
        if (name.isEmpty() || description.isEmpty() || category.isEmpty() || priceText.isEmpty()) {
            showAlert(ERROR, "Todos los campos deben estar llenos.");
            return;
        }

        // Verificar si el precio es un número
        if (!priceText.matches("\\d+(\\.\\d+)?")) {
            showAlert(ERROR, "El precio debe ser un número.");
            return;
        }

        // Verificar si el precio es negativo
        BigDecimal price = new BigDecimal(priceText);
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            showAlert(ERROR, "El precio no puede ser negativo.");
            return;
        }

        // Crear un objeto DataDTO con los valores extraídos
        DataDTO dataDTO = new DataDTO(null, name, description, category, price);

        // Crear un objeto PostRequest y enviar la solicitud POST a la API
        PostRequest postRequest = new PostRequest();
        postRequest.sendPostRequest(dataDTO);

        // Limpiar los campos de texto después de enviar la solicitud
        nameField.clear();
        descriptionField.clear();
        priceField.clear();

        // Crear un objeto GetResponse y enviar la solicitud GET a la API
        GetResponse getResponse = new GetResponse();
        List<DataDTO> dataList = getResponse.sendGetRequest();

        // Mapear los datos recibidos en la tabla
        ObservableList<DataDTO> observableList = FXCollections.observableArrayList(dataList);
        dataTable.setItems(observableList);
    }

    @FXML
    public void onUpdateButtonClick() {
        // Extraer los valores de los campos de texto
        String idText = idField.getText();
        String name = nameField.getText();
        String description = descriptionField.getText();
        String category = categoryField.getValue();
        String priceText = priceField.getText();

        final String ERROR = "ERROR";

        // Verificar si algún campo está vacío
        if (idText.isEmpty() || name.isEmpty() || description.isEmpty() || category.isEmpty() || priceText.isEmpty()) {
            showAlert(ERROR, "Todos los campos deben estar llenos.");
            return;
        }

        // Verificar si el precio es un número
        if (!priceText.matches("\\d+(\\.\\d+)?")) {
            showAlert(ERROR, "El precio debe ser un número.");
            return;
        }

        // Verificar si el precio es negativo
        BigDecimal price = new BigDecimal(priceText);
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            showAlert(ERROR, "El precio no puede ser negativo.");
            return;
        }

        // Crear un objeto DataDTO con los valores extraídos
        long id = Long.parseLong(idText);
        DataDTO dataDTO = new DataDTO(id, name, description, category, price);

        // Crear un objeto PutRequest y enviar la solicitud PUT a la API
        PutRequest putRequest = new PutRequest();
        putRequest.sendPutRequest(dataDTO, id);

        // Limpiar los campos de texto después de enviar la solicitud
        idField.clear();
        nameField.clear();
        descriptionField.clear();
        priceField.clear();

        // Crear un objeto GetResponse y enviar la solicitud GET a la API
        GetResponse getResponse = new GetResponse();
        List<DataDTO> dataList = getResponse.sendGetRequest();

        // Mapear los datos recibidos en la tabla
        ObservableList<DataDTO> observableList = FXCollections.observableArrayList(dataList);
        dataTable.setItems(observableList);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void onDeleteButtonClick() {
        // Extraer el valor del campo de texto ID
        Long id = Long.parseLong(idField.getText());

        // Crear un objeto DataDTO con el ID extraído
        DataDTO dataDTO = new DataDTO();
        dataDTO.setId(id);

        // Crear un objeto DeleteRequest y enviar la solicitud DELETE a la API
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.sendDeleteRequest(dataDTO);

        // Limpiar el campo de texto ID después de enviar la solicitud
        idField.clear();
        nameField.clear();
        descriptionField.clear();
        priceField.clear();

        // Crear un objeto GetResponse y enviar la solicitud GET a la API
        GetResponse getResponse = new GetResponse();
        List<DataDTO> dataList = getResponse.sendGetRequest();

        // Mapear los datos recibidos en la tabla
        ObservableList<DataDTO> observableList = FXCollections.observableArrayList(dataList);
        dataTable.setItems(observableList);
    }

    @FXML
    public void onClearButtonClick() {
        // Limpiar todos los campos de texto
        idField.clear();
        nameField.clear();
        descriptionField.clear();
        priceField.clear();
    }
}