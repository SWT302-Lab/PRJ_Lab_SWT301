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
import java.util.List;
import model.Table;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)  // Dùng MockitoJUnitRunner để tự động khởi tạo mocks
public class TableDAOTestF2 {

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
    /** ✅ Nhóm hợp lệ: Test với dữ liệu bình thường */
    @Test
    public void testGetAllTable_ValidData() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("TableID")).thenReturn(1);
        when(mockResultSet.getString("TableStatus")).thenReturn("Available");

        List<Table> tables = tableDAO.getAllTable();

        assertEquals(1, tables.size());
        assertEquals(1, tables.get(0).getId());
        assertEquals("Available", tables.get(0).getStatus());

    }

    /** ✅ Nhóm rỗng: Không có dữ liệu */
    @Test
    public void testGetAllTable_EmptyList() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);

        List<Table> tables = tableDAO.getAllTable();

        assertTrue(tables.isEmpty());
    }

    /** ✅ Nhóm không hợp lệ: TableStatus là null */
    @Test
    public void testGetAllTable_TableStatusNull() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("TableID")).thenReturn(1);
        when(mockResultSet.getString("TableStatus")).thenReturn(null);

        List<Table> tables = tableDAO.getAllTable();

        assertEquals(1, tables.size());
        assertEquals(1, tables.get(0).getId());
        assertNull(tables.get(0).getStatus());
    }

    /** ✅ Nhóm không hợp lệ: TableStatus là rỗng */
    @Test
    public void testGetAllTable_TableStatusEmpty() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("TableID")).thenReturn(1);
        when(mockResultSet.getString("TableStatus")).thenReturn("");

        List<Table> tables = tableDAO.getAllTable();

        assertEquals(1, tables.size());
        assertEquals(1, tables.get(0).getId());
        assertEquals("", tables.get(0).getStatus());
    }
}
