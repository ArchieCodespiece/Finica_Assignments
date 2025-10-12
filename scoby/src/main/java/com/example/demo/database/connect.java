package com.example.demo.database;

import com.example.demo.entity.Jornalentry;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class connect {
    private Connection conn;

    public connect() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/mydb",
                    "postgres",
                    "0000"
            );
        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed: " + e.getMessage());
        }
    }

    public void save(Jornalentry j) {
        String query = "INSERT INTO tasks (task_description) VALUES (?)";
        try (PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, j.getTaskDescription());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save task: " + e.getMessage());
        }
    }

    public List<Jornalentry> getAll() {
        List<Jornalentry> list = new ArrayList<>();
        String query = "SELECT id, task_description FROM tasks";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Jornalentry j = new Jornalentry();
                j.setId(rs.getLong("id"));
                j.setTaskDescription(rs.getString("task_description"));
                list.add(j);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch tasks: " + e.getMessage());
        }
        return list;
    }

    public void update(Jornalentry j) {
        String query = "UPDATE tasks SET task_description=? WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, j.getTaskDescription());
            st.setLong(2, j.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update task: " + e.getMessage());
        }
    }

    public void delete(long id) {
        String query = "DELETE FROM tasks WHERE id=?";
        try (PreparedStatement st = conn.prepareStatement(query)) {
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete task: " + e.getMessage());
        }
    }
}
