package controller;

import dal.DishDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.text.Normalizer;
import java.util.regex.Pattern;
import model.Dish;

/**
 *
 * @author Acer
 */
@WebServlet(name = "addDish", urlPatterns = {"/addDish"})
public class addDish extends HttpServlet {

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
            out.println("<title>Servlet addDish</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addDish at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // Normalize Vietnamese text for comparison
    private String normalizeVietnamese(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.toLowerCase().trim();
        return str;
    }
    
    // Check if two Vietnamese strings are equivalent
    private boolean areVietnameseStringsEqual(String str1, String str2) {
        return normalizeVietnamese(str1).equals(normalizeVietnamese(str2));
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
        //processRequest(request, response);
        request.setCharacterEncoding("UTF-8");
        
        String name = request.getParameter("name");
        String price_raw = request.getParameter("price");
        String status = request.getParameter("status");
        String image = request.getParameter("image");
        
        // Validate data
        StringBuilder errorMessage = new StringBuilder();
        boolean hasError = false;
        
        // Validate name
        if (name == null || name.trim().isEmpty()) {
            errorMessage.append("Dish name must be between 3 and 100 characters, containing only letters, numbers, and spaces<br>");
            hasError = true;
        } else if (name.length() < 3) {  // Add this new check
            errorMessage.append("Dish name must be between 3 and 100 characters, containing only letters, numbers, and spaces<br>");
            hasError = true;
        } else if (name.length() > 100) {
            errorMessage.append("Dish name must be between 3 and 100 characters, containing only letters, numbers, and spaces<br>");
            hasError = true;
        } else {
            // Allow Vietnamese characters and alphanumeric characters
            String regex = "^[\\p{L}\\p{M}\\p{N}\\s]+$";
            if (!Pattern.matches(regex, name)) {
                errorMessage.append("Dish name must be between 3 and 100 characters, containing only letters, numbers, and spaces<br>");
                hasError = true;
            }
            
            // Check for duplicate name
            if (!hasError) {
                DishDAO dao = new DishDAO();
                List<Dish> dishList = dao.getAll();
                
                for (Dish dish : dishList) {
                    if (areVietnameseStringsEqual(dish.getDishName(), name)) {
                        errorMessage.append("Dish name already exists. Please choose a different name.<br>");
                        hasError = true;
                        break;
                    }
                }
            }
        }
        
        // Validate price
        double price = 0;
        if (price_raw == null || price_raw.trim().isEmpty()) {
            errorMessage.append("Price must be a valid number between 1000 and 999,999,999<br>");
            hasError = true;
        } else {
            try {
                // Check digits count
                String digitsOnly = price_raw.replaceAll("[^0-9]", "");

                if (digitsOnly.length() > 9) {
                    errorMessage.append("Price must be a valid number between 1000 and 999,999,999<br>");
                    hasError = true;
                } else if (digitsOnly.length() < 4) {  // Add this new check
                    errorMessage.append("Price must be a valid number between 1000 and 999,999,999<br>");
                    hasError = true;
                } else {
                    price = Double.parseDouble(price_raw);
                    if (price <= 0) {
                        errorMessage.append("Price must be a valid number between 1000 and 999,999,999<br>");
                        hasError = true;
                    }
                }
            } catch (NumberFormatException e) {
                errorMessage.append("Price must be a valid number between 1000 and 999,999,999<br>");
                hasError = true;
            }
        }
        
        // Validate status
        if (status == null || (!status.equals("yes") && !status.equals("no"))) {
            errorMessage.append("Status must be 'yes' or 'no'.<br>");
            hasError = true;
        }
        
        // Validate image URL
        if (image == null || image.trim().isEmpty()) {
            errorMessage.append("Image URL must be between 16 and 255 characters and start with http:// or https://<br>");
            hasError = true;
        } else if (image.length() < 16 || image.length() > 255 || !Pattern.matches("^https?://.*$", image)) {
            errorMessage.append("Image URL must be between 16 and 255 characters and start with http:// or https://<br>");
            hasError = true;
        }
        
        // If there are validation errors, return to the form
        if (hasError) {
            request.setAttribute("error", errorMessage.toString());
            request.setAttribute("name", name);
            request.setAttribute("price", price_raw);
            request.setAttribute("status", status);
            request.setAttribute("image", image);
            request.getRequestDispatcher("./createDish.jsp").forward(request, response);
            return;
        }
        
        try {
            DishDAO dao = new DishDAO();
            Dish newd = new Dish(0, name, price, status, image);
            dao.insert(newd);
            response.sendRedirect("getDish");
        } catch (Exception e) {
            System.out.println(e);
            request.setAttribute("error", "An unexpected error occurred. Please try again.");
            request.setAttribute("name", name);
            request.setAttribute("price", price_raw);
            request.setAttribute("status", status);
            request.setAttribute("image", image);
            request.getRequestDispatcher("./createDish.jsp").forward(request, response);
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
        processRequest(request, response);
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