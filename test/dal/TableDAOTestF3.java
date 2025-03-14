/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Table;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)  // Dùng MockitoJUnitRunner để tự động khởi tạo mocks
public class TableDAOTestF3 {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private TableDAO tableDAO;  // Inject mock Connection vào TableDAO

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this); // Khởi tạo @Mock

        // Giả lập Connection trả về PreparedStatement
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        // Giả lập PreparedStatement trả về ResultSet
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    }
    /** ✅ Nhóm hợp lệ: Chèn thành công */
    @Test
    public void testInsertTable_ValidData() throws SQLException {
        Table table = new Table(50, "Available");

        tableDAO.insertTable(table);

        verify(mockStatement, times(1)).setInt(1, 50);
        verify(mockStatement, times(1)).setString(2, "Available");
    }

    /** ❌ Nhóm không hợp lệ: TableID = 0 */
    @Test
    public void testInsertTable_InvalidTableID_LowerBoundary() {
        Table table = new Table(0, "Available");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tableDAO.insertTable(table);
        });

        assertEquals("Valid value: 1-100", exception.getMessage());
    }

    /** ❌ Nhóm không hợp lệ: TableID = 101 */
    @Test
    public void testInsertTable_InvalidTableID_UpperBoundary() {
        Table table = new Table(101, "occupied");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tableDAO.insertTable(table);
        });

        assertEquals("Valid value: 1-100", exception.getMessage());
    }

    /** ❌ Nhóm không hợp lệ: TableStatus = null */
    @Test
    public void testInsertTable_InvalidTableStatus_Null() {
        Table table = new Table(50, null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tableDAO.insertTable(table);
        });

        assertEquals("TableStatus cannot be null or empty", exception.getMessage());
    }

    /** ❌ Nhóm không hợp lệ: TableStatus = "" */
    @Test
    public void testInsertTable_InvalidTableStatus_Empty() {
        Table table = new Table(50, "");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tableDAO.insertTable(table);
        });

        assertEquals("TableStatus cannot be null or empty", exception.getMessage());
    }

    /** ✅ Biên dưới: TableID = 1 */
    @Test
    public void testInsertTable_Boundary_Lower() throws SQLException {
        Table table = new Table(1, "Available");

        tableDAO.insertTable(table);

        verify(mockStatement, times(1)).setInt(1, 1);
        verify(mockStatement, times(1)).setString(2, "Available");
        verify(mockStatement, times(1)).executeUpdate();
    }

    /** ✅ Biên trên: TableID = 100 */
    @Test
    public void testInsertTable_Boundary_Upper() throws SQLException {
        Table table = new Table(100, "Occupied");

        tableDAO.insertTable(table);

        verify(mockStatement, times(1)).setInt(1, 100);
        verify(mockStatement, times(1)).setString(2, "Occupied");
        verify(mockStatement, times(1)).executeUpdate();
    }
}
