package com.example.demo;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;

public class Controller {
    @FXML private TextField txtName, txtCourse;
    @FXML private ChoiceBox<YearLevel> cbYear;
    @FXML private TableView<Student> table;
    @FXML private TableColumn<Student, Integer> colId;
    @FXML private TableColumn<Student, String> colName, colCourse, colYear;

    private final ObservableList<Student> list = FXCollections.observableArrayList();
    private Connection conn;
    private int selectedId = -1;

    @FXML
    public void initialize() {
        conn = DBConnection.connect();
        cbYear.getItems().setAll(YearLevel.values());

        colId.setCellValueFactory(d -> d.getValue().idProperty().asObject());
        colName.setCellValueFactory(d -> d.getValue().nameProperty());
        colCourse.setCellValueFactory(d -> d.getValue().courseProperty());
        colYear.setCellValueFactory(d -> d.getValue().yearLevelProperty());

        loadData();

        // Click row to fill
        table.setOnMouseClicked(e -> {
            Student s = table.getSelectionModel().getSelectedItem();
            if (s != null) {
                selectedId = s.getId();
                txtName.setText(s.getName());
                txtCourse.setText(s.getCourse());
                for (YearLevel y : YearLevel.values())
                    if (y.toString().equals(s.getYearLevel())) cbYear.setValue(y);
            }
        });
    }

    private void loadData() {
        list.clear();
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM students");
            while (rs.next()) {
                // ✅ NEW: read number from DB → convert to text
                int yearNum = rs.getInt("year_level");
                String yearStr = switch (yearNum) {
                    case 1 -> "1st Year";
                    case 2 -> "2nd Year";
                    case 3 -> "3rd Year";
                    case 4 -> "4th Year";
                    default -> "Unknown";
                };

                list.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("course"),
                        yearStr // show text in table
                ));
            }
            table.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private void addStudent() {
        if (!valid()) return;
        try {
            PreparedStatement p = conn.prepareStatement(
                    "INSERT INTO students(name,course,year_level) VALUES(?,?,?)");
            p.setString(1, txtName.getText());
            p.setString(2, txtCourse.getText());
            p.setInt(3, cbYear.getValue().getValue()); // send NUMBER instead of text
            p.executeUpdate(); loadData(); clear();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void updateStudent() {
        if (selectedId == -1) { alert("Select record first!"); return; }
        if (!valid()) return;
        try {
            PreparedStatement p = conn.prepareStatement(
                    "UPDATE students SET name=?,course=?,year_level=? WHERE id=?");
            p.setString(1, txtName.getText());
            p.setString(2, txtCourse.getText());
            p.setInt(3, cbYear.getValue().getValue());
            p.setInt(4, selectedId);
            p.executeUpdate(); loadData(); clear();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void deleteStudent() {
        if (selectedId == -1) { alert("Select record first!"); return; }
        try {
            PreparedStatement p = conn.prepareStatement("DELETE FROM students WHERE id=?");
            p.setInt(1, selectedId);
            p.executeUpdate(); loadData(); clear();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void clear() {
        txtName.clear(); txtCourse.clear(); cbYear.setValue(null); selectedId = -1;
        table.getSelectionModel().clearSelection();
    }

    private boolean valid() {
        if (txtName.getText().isBlank()) { alert("Name required!"); return false; }
        if (txtCourse.getText().isBlank()) { alert("Course required!"); return false; }
        if (cbYear.getValue() == null) { alert("Select Year!"); return false; }
        return true;
    }

    private void alert(String m) { new Alert(Alert.AlertType.WARNING, m).showAndWait(); }
}
