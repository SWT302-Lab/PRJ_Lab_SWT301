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
public class AccountDAOTestP1 {

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


    /* Tests for getAll method */
    @Test
    public void testGetAll_ValidData() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt(1)).thenReturn(1);
        when(mockResultSet.getString(2)).thenReturn("user1");
        when(mockResultSet.getString(3)).thenReturn("pass1");
        when(mockResultSet.getString(4)).thenReturn("user");

        List<Account> accounts = accountDAO.getAll();

        assertEquals(1, accounts.size());
        assertEquals(1, accounts.get(0).getAccountID());
        assertEquals("user1", accounts.get(0).getUsername());
        assertEquals("pass1", accounts.get(0).getPassword());
        assertEquals("user", accounts.get(0).getRole());
    }

    @Test
    public void testGetAll_EmptyList() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);

        List<Account> accounts = accountDAO.getAll();

        assertTrue(accounts.isEmpty());
    }

    @Test
    public void testGetAll_EmptyFields() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt(1)).thenReturn(1);

        when(mockResultSet.getString(2)).thenReturn("");
        when(mockResultSet.getString(3)).thenReturn("");
        when(mockResultSet.getString(4)).thenReturn("");

        List<Account> accounts = accountDAO.getAll();
        assertEquals(1, accounts.size());
        assertEquals(1, accounts.get(0).getAccountID());
        assertEquals("", accounts.get(0).getUsername());  // Thay đổi từ assertNull 
        assertEquals("", accounts.get(0).getPassword());  // Thay đổi từ assertNull
        assertEquals("", accounts.get(0).getRole());      // Thay đổi từ assertNull
    }

    @Test
    public void testGetAll_NullFields() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt(1)).thenReturn(1);
        when(mockResultSet.getString(2)).thenReturn(null);
        when(mockResultSet.getString(3)).thenReturn(null);
        when(mockResultSet.getString(4)).thenReturn(null);

        List<Account> accounts = accountDAO.getAll();

        assertEquals(1, accounts.size());
        assertEquals(1, accounts.get(0).getAccountID());
        assertNull(accounts.get(0).getUsername());
        assertNull(accounts.get(0).getPassword());
        assertNull(accounts.get(0).getRole());
    }

    @Test
    public void testGetAll_MultipleAccounts() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt(1)).thenReturn(1, 2);
        when(mockResultSet.getString(2)).thenReturn("user1", "user2");
        when(mockResultSet.getString(3)).thenReturn("pass1", "pass2");
        when(mockResultSet.getString(4)).thenReturn("user", "admin");

        List<Account> accounts = accountDAO.getAll();

        assertEquals(2, accounts.size());
        assertEquals(1, accounts.get(0).getAccountID());
        assertEquals("user1", accounts.get(0).getUsername());
        assertEquals("pass1", accounts.get(0).getPassword());
        assertEquals("user", accounts.get(0).getRole());

        assertEquals(2, accounts.get(1).getAccountID());
        assertEquals("user2", accounts.get(1).getUsername());
        assertEquals("pass2", accounts.get(1).getPassword());
        assertEquals("admin", accounts.get(1).getRole());

        //verify(preparedStatement).executeQuery();
    }

}
