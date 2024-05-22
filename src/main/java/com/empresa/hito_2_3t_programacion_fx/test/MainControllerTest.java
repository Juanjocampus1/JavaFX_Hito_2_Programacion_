package com.empresa.hito_2_3t_programacion_fx.test;

import com.empresa.hito_2_3t_programacion_fx.Controllers.MainController;
import com.empresa.hito_2_3t_programacion_fx.DTO.DataDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainControllerTest {
    private MainController mainController;

    @BeforeEach
    public void setUp() {
        mainController = new MainController();
        // Aquí puedes agregar código para inicializar dataList con algunos datos de prueba
    }

    @Test
    public void testOnSearchButtonClick() {
        // Aquí puedes agregar código para probar el método onSearchButtonClick
        // Por ejemplo, podrías buscar un nombre que sabes que está en dataList y verificar que el método devuelve el resultado correcto
        mainController.onSearchButtonClick();
        List<DataDTO> results = mainController.getDataList();
        assertFalse(results.isEmpty());
    }

    @Test
    public void testOnCreateButtonClick() {
        // Aquí puedes agregar código para probar el método onCreateButtonClick
        // Por ejemplo, podrías crear un nuevo objeto DataDTO y verificar que se agrega correctamente a dataList
        mainController.onCreateButtonClick();
        List<DataDTO> results = mainController.getDataList();
        assertFalse(results.isEmpty());
    }

    @Test
    public void testOnUpdateButtonClick() {
        // Aquí puedes agregar código para probar el método onUpdateButtonClick
        // Por ejemplo, podrías actualizar un objeto DataDTO existente y verificar que los cambios se reflejan correctamente en dataList
        mainController.onUpdateButtonClick();
        List<DataDTO> results = mainController.getDataList();
        assertFalse(results.isEmpty());
    }

    @Test
    public void testOnDeleteButtonClick() {
        // Aquí puedes agregar código para probar el método onDeleteButtonClick
        // Por ejemplo, podrías eliminar un objeto DataDTO existente y verificar que se elimina correctamente de dataList
        mainController.onDeleteButtonClick();
        List<DataDTO> results = mainController.getDataList();
        assertTrue(results.isEmpty());
    }
}