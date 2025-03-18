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
public class AccountDAOTestP2 {

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
    public void testInsert_ValidAccount() throws SQLException {
        Account account = new Account(9, "newuser9", "newpass9", "user");
        
        accountDAO.insert(account);
        
        verify(mockStatement).setString(1, "newuser9");
        verify(mockStatement).setString(2, "newpass9");
        verify(mockStatement).setString(3, "user");
        verify(mockStatement).executeUpdate();
    }
    
    @Test
    public void testInsert_BoundaryValue_Lower() throws SQLException {
        Account account = new Account(1, "newuser1", "newpass1", "user");
        
        accountDAO.insert(account);
        
        verify(mockStatement).setString(1, "newuser1");
        verify(mockStatement).setString(2, "newpass1");
        verify(mockStatement).setString(3, "user");
        verify(mockStatement).executeUpdate();
    }
    
    @Test
    public void testInsert_BoundaryValue_Upper() throws SQLException {
        Account account = new Account(99, "newuser99", "newpass99", "user");
        
        accountDAO.insert(account);
        
        verify(mockStatement).setString(1, "newuser99");
        verify(mockStatement).setString(2, "newpass99");
        verify(mockStatement).setString(3, "user");
        verify(mockStatement).executeUpdate();
    }
    
    @Test
    public void testInsert_OutOfBoundary_Lower() throws SQLException {
        Account account = new Account(0, "newuser1", "newpass1", "user");
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            accountDAO.insert(account);
        });

        assertEquals("Valid value: 1->99", exception.getMessage());
    }
    
    @Test
    public void testInsert_OutOfBoundary_Upper() throws SQLException {
        Account account = new Account(100, "newuser1", "newpass1", "user");
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            accountDAO.insert(account);
        });

        assertEquals("Valid value: 1->99", exception.getMessage());
    }
    
    /** ✅ Test: Thêm tài khoản với các trường rỗng */
    @Test
    public void testInsert_EmptyFields() throws SQLException {
        Account account = new Account(1, "", "", "");

        accountDAO.insert(account);

        verify(mockStatement).setString(1, "");
        verify(mockStatement).setString(2, "");
        verify(mockStatement).setString(3, "");
        verify(mockStatement).executeUpdate();
    }

    /** ✅ Test: Thêm tài khoản với các trường null */
    @Test
    public void testInsert_NullFields() throws SQLException {
        Account account = new Account(1, null, null, null);

        accountDAO.insert(account);

        verify(mockStatement).setString(1, null);
        verify(mockStatement).setString(2, null);
        verify(mockStatement).setString(3, null);
        verify(mockStatement).executeUpdate();
    }
    
}       