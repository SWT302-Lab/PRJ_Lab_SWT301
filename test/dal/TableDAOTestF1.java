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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)  // Dùng MockitoJUnitRunner để tự động khởi tạo mocks
public class TableDAOTestF1 {

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

    /**
     * Test ID hợp lệ (50)
     */
    @Test
    public void testValidId() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("TableStatus")).thenReturn("Available");

        String status = tableDAO.getTableStatusById(50);

        assertEquals("Available", status);
    }

    /**
     * Test ID không hợp lệ (999 - không tồn tại)
     */
    @Test
    public void testInvalidId_NotFound() throws SQLException {
//        when(mockResultSet.next()).thenReturn(false);
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tableDAO.getTableStatusById(200);
        });

        assertEquals("Valid value: 1-100", exception.getMessage());
    }

    /**
     * Test giá trị biên: ID = 1 (biên dưới)
     */
    @Test
    public void testBoundaryValue_Lower() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("TableStatus")).thenReturn("Occupied");

        String status = tableDAO.getTableStatusById(1);

        assertEquals("Occupied", status);
    }

    /**
     * Test giá trị biên: ID = 100 (biên trên)
     */
    @Test
    public void testBoundaryValue_Upper() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("TableStatus")).thenReturn("occupied");

        String status = tableDAO.getTableStatusById(100);

        assertEquals("occupied", status);
    }
    /**
     * Test giá trị ngoài biên: ID = 0 (dưới giới hạn)
     */
    @Test
    public void testOutOfBoundary_Lower() throws SQLException {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tableDAO.getTableStatusById(0);
        });

        assertEquals("Valid value: 1-100", exception.getMessage());
    }

    /**
     * Test giá trị ngoài biên: ID = 101 (trên giới hạn)
     */
    @Test
    public void testOutOfBoundary_Upper() throws SQLException {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tableDAO.getTableStatusById(101);
        });

        assertEquals("Valid value: 1-100", exception.getMessage());
    }

}
