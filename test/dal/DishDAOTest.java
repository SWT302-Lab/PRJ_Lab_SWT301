/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dal;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import model.Dish;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.OrderDetail;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Đức Thắng
 */
public class DishDAOTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private DishDAO dishDAO;

    @InjectMocks
    private OrderDetailDAO orderDetailDAO;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(connection.prepareStatement(anyString())).
                thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).
                thenReturn(resultSet);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testGetAllDish() throws SQLException {
        when(resultSet.next()).
                thenReturn(true, true, false);
        when(resultSet.getInt("DishID")).
                thenReturn(1, 2);
        when(resultSet.getString("DishName")).
                thenReturn("Pizza", "Burger");
        when(resultSet.getDouble("Price")).
                thenReturn(190.99, 140.99);
        when(resultSet.getString("Status")).
                thenReturn("yes", "no");
        when(resultSet.getString("ImageLink")).
                thenReturn("pizza.jpg", "burger.jpg");

        ArrayList<Dish> dishes = dishDAO.getAll();
//        assertNotNull(dishes);
//        assertEquals(2, dishes.size());
        assertEquals(1, dishes.get(0).getDishId());
        assertEquals("Pizza", dishes.get(0).getDishName());
        assertEquals(190.99, dishes.get(0).getPrice(), 0.01);
        assertEquals("yes", dishes.get(0).getStatus());
        assertEquals("pizza.jpg", dishes.get(0).getImage());

        assertEquals(2, dishes.get(1).getDishId());
        assertEquals("Burger", dishes.get(1).getDishName());
        assertEquals(140.99, dishes.get(1).getPrice(), 0.01);
        assertEquals("no", dishes.get(1).getStatus());
        assertEquals("burger.jpg", dishes.get(1).getImage());
    }

    @Test
    public void testGetAllDish_EmptyTable() throws SQLException {
        when(resultSet.next()).thenReturn(false);
        ArrayList<Dish> dishes = dishDAO.getAll();
        assertNotNull(dishes);
        assertTrue(dishes.isEmpty());
    }

    @Test
    public void testGetAllDish_SQLException() throws SQLException {
        when(preparedStatement.executeQuery()).
                thenThrow(new SQLException("Database error"));
        ArrayList<Dish> emails = dishDAO.getAll();
        assertNotNull(emails);
        assertTrue(emails.isEmpty());
    }

    @Test
    public void testInsertDish_DishNameNull() throws SQLException {
        Dish dish = new Dish(1, null, 35000.00, "yes", "pho.jpg");
        Exception exception = assertThrows(NullPointerException.class,
                () -> dishDAO.insert(dish));
        assertEquals("Please enter the values in the correct fields",
                exception.getMessage());
    }

    @Test
    public void testInsertDish_StatusNull() throws SQLException {
        Dish dish = new Dish(1, "Pho", 35000.00, null, "pho.jpg");
        Exception exception = assertThrows(NullPointerException.class,
                () -> dishDAO.insert(dish));
        assertEquals("Please enter the values in the correct fields",
                exception.getMessage());
    }

    @Test
    public void testInsertDish_ImageLinkNull() throws SQLException {
        Dish dish = new Dish(1, "Pho", 35000.00, "yes", null);
        Exception exception = assertThrows(NullPointerException.class,
                () -> dishDAO.insert(dish));
        assertEquals("Please enter the values in the correct fields",
                exception.getMessage());
    }

    @Test
    public void testInsertDish_PriceIsNegative() throws SQLException {
        Dish dish = new Dish(1, "Pho", -100.00, "yes", null);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> dishDAO.insert(dish));
        assertEquals("Please enter the values in the correct fields",
                exception.getMessage());
    }
    
     @Test
    public void testInsertDish_Success() throws SQLException {
        Dish dish = new Dish(1, "Pizza", 12.99, "Available", "image.jpg");
        dishDAO.insert(dish);
        verify(connection).prepareStatement(any(String.class)); // Kiểm tra có gọi prepareStatement
        verify(preparedStatement).setString(1, "Pizza");
        verify(preparedStatement).setDouble(2, 12.99);
        verify(preparedStatement).setString(3, "Available");
        verify(preparedStatement).setString(4, "image.jpg");
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testDeleteDishById_Success() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(1);
        dishDAO.delete(1);
        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeUpdate();
        assertEquals("Deleted 1 row(s)" + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void testDeleteDish_WithOrderDetail_ShouldFail() throws SQLException {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDishId(1);
        orderDetail.setQuantity(2);
        orderDetail.setPrice(200);
        orderDetail.setStatus("Ordered");
        orderDetailDAO.updateOrderDetail(orderDetail);
        dishDAO.delete(1);
        assertEquals("Deleted 0 row(s)" + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void testDeleteDish_NonExistentId() throws SQLException {
        dishDAO.delete(9999);
        assertEquals("Deleted 0 row(s)" + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void testFindId_ExistingDish_ShouldReturnDish() throws SQLException {
        when(resultSet.next()).
                thenReturn(true);
        when(resultSet.getInt("DishID")).
                thenReturn(1);
        when(resultSet.getString("DishName")).
                thenReturn("Pizza");
        when(resultSet.getDouble("Price")).
                thenReturn(190.99);
        when(resultSet.getString("Status")).
                thenReturn("yes");
        when(resultSet.getString("ImageLink")).
                thenReturn("pizza.jpg");
        Dish actualDish = dishDAO.findId(1);
        assertEquals(1, actualDish.getDishId());
        assertEquals("Pizza", actualDish.getDishName());
        assertEquals(190.99, actualDish.getPrice(), 0.001);
        assertEquals("yes", actualDish.getStatus());
        assertEquals("pizza.jpg", actualDish.getImage());
    }

    @Test
    public void testSearchDishByName_ExistingDish_ShouldReturnList() throws SQLException {
        when(resultSet.next()).
                thenReturn(true, true, false);
        when(resultSet.getInt("DishID")).
                thenReturn(1, 2);
        when(resultSet.getString("DishName")).
                thenReturn("Pizza", "Pizza");
        when(resultSet.getDouble("Price")).
                thenReturn(190.99, 140.99);
        when(resultSet.getString("Status")).
                thenReturn("yes", "no");
        when(resultSet.getString("ImageLink")).
                thenReturn("pizza.jpg", "pizza2.jpg");
        ArrayList<Dish> dishes = dishDAO.searchDishByName("Pizza");
        assertEquals(1, dishes.get(0).getDishId());
        assertEquals("Pizza", dishes.get(0).getDishName());
        assertEquals(190.99, dishes.get(0).getPrice(), 0.01);
        assertEquals("yes", dishes.get(0).getStatus());
        assertEquals("pizza.jpg", dishes.get(0).getImage());

        assertEquals(2, dishes.get(1).getDishId());
        assertEquals("Pizza", dishes.get(1).getDishName());
        assertEquals(140.99, dishes.get(1).getPrice(), 0.01);
        assertEquals("no", dishes.get(1).getStatus());
        assertEquals("pizza2.jpg", dishes.get(1).getImage());
    }

    @Test
    public void testSearchDishByName_WrongName_ShouldReturnList() throws SQLException {
        when(resultSet.next()).
                thenReturn(true, true, false);
        when(resultSet.getInt("DishID")).
                thenReturn(1, 2);
        when(resultSet.getString("DishName")).
                thenReturn("Pizza", "Pizza");
        when(resultSet.getDouble("Price")).
                thenReturn(190.99, 140.99);
        when(resultSet.getString("Status")).
                thenReturn("yes", "no");
        when(resultSet.getString("ImageLink")).
                thenReturn("pizza.jpg", "pizza2.jpg");
        ArrayList<Dish> result = dishDAO.searchDishByName("Burger");
        assertEquals(0, result.size());
    }

    @Test
    public void testSearchDishByName_EmptyTable() throws SQLException {
        when(resultSet.next()).thenReturn(false);
        ArrayList<Dish> dishes = dishDAO.searchDishByName("Pizza");
        assertNotNull(dishes);
        assertEquals(0, dishes.size());
    }

}
