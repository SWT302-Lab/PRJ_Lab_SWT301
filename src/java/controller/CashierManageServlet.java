/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.OrderDetailDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.OrderDetail;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CashierManageServlet", urlPatterns = {"/manage"})
public class CashierManageServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CashierManageServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CashierManageServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tableId_raw = request.getParameter("tableID");
        int tableID;
        try {
            tableID = Integer.parseInt(tableId_raw);
            OrderDetailDAO oddao = new OrderDetailDAO();
            List<OrderDetail> list =  oddao.getOrderDetailsByTableId(tableID);
            
            HttpSession session = request.getSession();
            session.setAttribute("orderList", list);
            
            request.setAttribute("list", list);
            request.getRequestDispatcher("cashierManage.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderId_raw = request.getParameter("orderId");
        String dishId_raw = request.getParameter("dishId");
        String tableId_raw = request.getParameter("tableId");
        int dishId, orderId, tableId;
        try {
            dishId = Integer.parseInt(dishId_raw);
            orderId = Integer.parseInt(orderId_raw);
            tableId = Integer.parseInt(tableId_raw);
            OrderDetailDAO oddao = new OrderDetailDAO();
            oddao.confirm(dishId, orderId);
            response.sendRedirect("manage?tableID=" + tableId);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
