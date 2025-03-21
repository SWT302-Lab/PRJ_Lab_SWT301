package dal;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TableDAOTestF4 {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @InjectMocks
    private TableDAO tableDAO;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        System.setOut(new PrintStream(outContent));  // Redirect System.out for assertion
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);  // Restore System.out after each test
    }

    // ✅ Xóa table hợp lệ (ID = 50)
    @Test
    public void testDeleteTable_ValidID() throws SQLException {
        when(mockStatement.executeUpdate()).thenReturn(1);

        tableDAO.deleteTable(50);

        verify(mockStatement, times(1)).setInt(1, 50);
        verify(mockStatement, times(1)).executeUpdate();

        assertEquals("Deleted 1 row(s)" + System.lineSeparator(), outContent.toString());
    }
    
    // ✅ Xóa table không hợp lệ (ID = 51)
    @Test
    public void testDeleteTable_ValidID_Fail() throws SQLException {
        when(mockStatement.executeUpdate()).thenReturn(0);

        tableDAO.deleteTable(51);

        verify(mockStatement, times(1)).setInt(1, 51);
        verify(mockStatement, times(1)).executeUpdate();

        assertEquals("Deleted 0 row(s)" + System.lineSeparator(), outContent.toString());
    }

    // ❌ Xóa table không tồn tại (ID = 101)
    @Test
    public void testDeleteTable_InvalidID_Above100() throws SQLException {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tableDAO.deleteTable(101);
        });

        assertEquals("Valid value: 1-100", exception.getMessage());
    }

    // ❌ Xóa table với ID nhỏ hơn 1 (ví dụ: 0)
    @Test
    public void testDeleteTable_InvalidID_Below1() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tableDAO.deleteTable(0);
        });
        assertEquals("Valid value: 1-100", exception.getMessage());
    }

    // 🟠 Giá trị biên: ID = 1 (hợp lệ)
    @Test
    public void testDeleteTable_BoundaryValue_Lower() throws SQLException {
        when(mockStatement.executeUpdate()).thenReturn(1);

        tableDAO.deleteTable(1);

        verify(mockStatement, times(1)).setInt(1, 1);
        verify(mockStatement, times(1)).executeUpdate();

        assertEquals("Deleted 1 row(s)" + System.lineSeparator(), outContent.toString());
    }

    // 🟠 Giá trị biên: ID = 100 (hợp lệ)
    @Test
    public void testDeleteTable_BoundaryValue_Upper() throws SQLException {
        when(mockStatement.executeUpdate()).thenReturn(1);

        tableDAO.deleteTable(100);

        verify(mockStatement, times(1)).setInt(1, 100);
        verify(mockStatement, times(1)).executeUpdate();

        assertEquals("Deleted 1 row(s)" + System.lineSeparator(), outContent.toString());
    }

    // ❌ Xóa thất bại do ràng buộc khóa ngoại (Foreign Key Constraint)
    @Test
    public void testDeleteTable_ForeignKeyConstraintViolation() throws SQLException {
        doThrow(new SQLException("Foreign key constraint violation")).when(mockStatement).executeUpdate();

        Exception exception = assertThrows(SQLException.class, () -> {
            tableDAO.deleteTable(50);
        });

        assertTrue(outContent.toString().contains("Error while deleting table: Foreign key constraint violation"));
    }
}
