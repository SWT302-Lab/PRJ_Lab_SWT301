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
public class AccountDAOTestP4 {

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

    /* Tests for findId method */
    @Test
    public void testFindId_ValidId() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(9);
        when(mockResultSet.getString(2)).thenReturn("user9");
        when(mockResultSet.getString(3)).thenReturn("pass9");
        when(mockResultSet.getString(4)).thenReturn("user");

        Account account = accountDAO.findId(9);
        verify(mockStatement).setInt(1, 9);
        assertNotNull(account);
        assertEquals(9, account.getAccountID());
    }

    @Test
    public void testFindId_InvalidId_NotFound() throws SQLException {
//        when(mockResultSet.next()).thenReturn(false);
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            accountDAO.findId(900);
        });

        assertEquals("Valid value: 1->99", exception.getMessage());
    }

    @Test
    public void testFindId_BoundaryValue_Lower() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);
        when(mockResultSet.getString(2)).thenReturn("user1");
        when(mockResultSet.getString(3)).thenReturn("pass1");
        when(mockResultSet.getString(4)).thenReturn("user");

        Account account = accountDAO.findId(1);

        verify(mockStatement).setInt(1, 1);
        assertNotNull(account);
        assertEquals(1, account.getAccountID());
    }

    @Test
    public void testFindId_BoundaryValue_Upper() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(99);
        when(mockResultSet.getString(2)).thenReturn("user99");
        when(mockResultSet.getString(3)).thenReturn("pass99");
        when(mockResultSet.getString(4)).thenReturn("user");

        Account account = accountDAO.findId(99);

        verify(mockStatement).setInt(1, 99);
        assertNotNull(account);
        assertEquals(99, account.getAccountID());
    }
    
    @Test
    public void testOutOfBoundary_Lower() throws SQLException {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            tableDAO.getTableStatusById(0);
              accountDAO.findId(100);
        });

        assertEquals("Valid value: 1->99", exception.getMessage());
    }

    
    @Test
    public void testOutOfBoundary_Upper() throws SQLException {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            accountDAO.findId(0);
        });

        assertEquals("Valid value: 1->99", exception.getMessage());
    }

}