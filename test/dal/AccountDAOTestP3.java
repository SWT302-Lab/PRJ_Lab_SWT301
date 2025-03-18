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

import model.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;

@RunWith(MockitoJUnitRunner.class)
public class AccountDAOTestP3 {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private AccountDAO accountDAO;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    /* Tests for insert method */
    @Test
    public void testUpdate_ValidAccount() throws SQLException {
        Account account = new Account(9, "updateuser9", "updatepass9", "user");
        
        accountDAO.update(account);
        
        verify(mockStatement).setString(1, "updateuser9");
        verify(mockStatement).setString(2, "updatepass9");
        verify(mockStatement).setString(3, "user");
        verify(mockStatement).executeUpdate();
    }
    
    @Test
    public void testUpdate_BoundaryValue_Lower() throws SQLException {
        Account account = new Account(1, "updateuser1", "updatepass1", "user");
        
        accountDAO.update(account);
        
        verify(mockStatement).setString(1, "updateuser1");
        verify(mockStatement).setString(2, "updatepass1");
        verify(mockStatement).setString(3, "user");
        verify(mockStatement).executeUpdate();
    }
    
    @Test
    public void testUpdate_BoundaryValue_Upper() throws SQLException {
        Account account = new Account(99, "updateuser99", "updatepass99", "user");
        
        accountDAO.update(account);
        
        verify(mockStatement).setString(1, "updateuser99");
        verify(mockStatement).setString(2, "updatepass99");
        verify(mockStatement).setString(3, "user");
        verify(mockStatement).executeUpdate();
    }
    
    @Test
    public void testUpdate_OutOfBoundary_Lower() throws SQLException {
        Account account = new Account(0, "updateuser1", "updatepass1", "user");
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            accountDAO.update(account);
        });

        assertEquals("Valid value: 1->99", exception.getMessage());
    }
    
    @Test
    public void testUpdate_OutOfBoundary_Upper() throws SQLException {
        Account account = new Account(100, "updateuser1", "updatepass1", "user");
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            accountDAO.update(account);
        });

        assertEquals("Valid value: 1->99", exception.getMessage());
    }
    
    /** ✅ Test: Thêm tài khoản với các trường rỗng */
    @Test
    public void testUpdate_EmptyFields() throws SQLException {
        Account account = new Account(1, "", "", "");

        accountDAO.update(account);

        verify(mockStatement).setString(1, "");
        verify(mockStatement).setString(2, "");
        verify(mockStatement).setString(3, "");
        verify(mockStatement).executeUpdate();
    }

    /** ✅ Test: Thêm tài khoản với các trường null */
    @Test
    public void testUpdate_NullFields() throws SQLException {
        Account account = new Account(1, null, null, null);

        accountDAO.update(account);

        verify(mockStatement).setString(1, null);
        verify(mockStatement).setString(2, null);
        verify(mockStatement).setString(3, null);
        verify(mockStatement).executeUpdate();
    }
    
}       