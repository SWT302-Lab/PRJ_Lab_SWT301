/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.Dish;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Đức Thắng
 */
public class DishDAOTest {
    
    public DishDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetAll() {
        System.out.println("getAll");
        DishDAO instance = new DishDAO();
        ArrayList<Dish> expResult = null;
        ArrayList<Dish> result = instance.getAll();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testInsert() {
        System.out.println("insert");
        Dish d = null;
        DishDAO instance = new DishDAO();
        instance.insert(d);
        fail("The test case is a prototype.");
    }

    @Test
    public void testFindId() {
        System.out.println("findId");
        int id = 0;
        DishDAO instance = new DishDAO();
        Dish expResult = null;
        Dish result = instance.findId(id);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testDelete() {
        System.out.println("delete");
        int id = 0;
        DishDAO instance = new DishDAO();
        instance.delete(id);
        fail("The test case is a prototype.");
    }

    @Test
    public void testUpdate() {
        System.out.println("update");
        Dish d = null;
        DishDAO instance = new DishDAO();
        instance.update(d);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSearchDishByName() {
        System.out.println("searchDishByName");
        String name = "";
        DishDAO instance = new DishDAO();
        ArrayList<Dish> expResult = null;
        ArrayList<Dish> result = instance.searchDishByName(name);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
    
}
