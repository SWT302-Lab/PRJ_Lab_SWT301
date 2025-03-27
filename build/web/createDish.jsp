<%-- 
    Document   : create
    Created on : Oct 13, 2024, 8:17:26 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Dish</title>
        <link rel="stylesheet" href="css/createDish.css">
<!--        <script>
            function validateForm() {
                let isValid = true;
                let errorMessages = [];
                
                // Validate name
                const name = document.getElementById("dishName").value.trim();
                if (name === "") {
                    errorMessages.push("Dish name cannot be empty");
                    isValid = false;
                } else if (name.length < 3) {  // Add this new check
                    errorMessages.push("Dish name must have at least 3 characters");
                    isValid = false;
                } else if (name.length > 100) {
                    errorMessages.push("Dish name must be less than 100 characters");
                    isValid = false;
                } else {
                    // Accept Vietnamese characters with regex
                    // This regex allows Vietnamese characters, letters, numbers and spaces
                    const regex = /^[\p{L}\p{M}\p{N}\s]+$/u;
                    if (!regex.test(name)) {
                        errorMessages.push("Dish name can only contain letters, numbers, and spaces");
                        isValid = false;
                    }
                    // Check for duplicate name
                    if (!hasError) {
                        DishDAO dao = new DishDAO();
                        List<Dish> dishList = dao.getAll();

                        for (Dish dish : dishList) {
                            if (areVietnameseStringsEqual(dish.getDishName(), name)) {
                                errorMessage.append("Dish name already exists. Please choose a different name. ");
                                hasError = true;
                                break;
                            }
                        }
                    }
                }
                
                // Validate price
                const price = document.getElementById("dishPrice").value.trim();
                if (price === "") {
                    errorMessages.push("Price cannot be empty");
                    isValid = false;
                } else if (isNaN(price) || parseFloat(price) <= 0) {
                    errorMessages.push("Price must be a valid number greater than 0");
                    isValid = false;
                } else {
                    // Check digits count
                    const digitsOnly = price.replace(/[^0-9]/g, "");
                    if (digitsOnly.length > 9) {
                        errorMessages.push("Price cannot exceed 999999999");
                        isValid = false;
                    } else if (digitsOnly.length < 4) {  // Add this new check
                        errorMessages.push("Price must have at least from 1000");
                        isValid = false;
                    }
                }
                
                // Validate image URL
                const image = document.getElementById("dishImage").value.trim();
                if (image === "") {
                    errorMessages.push("Image URL cannot be empty");
                    isValid = false;
                } else if (image.length < 16 || image.length > 255 || (!image.startsWith("http://") && !image.startsWith("https://"))) {
                    errorMessages.push("URL must be between 16 and 255 characters and start with http:// or https://");
                    isValid = false;
                }
                
                // Display error messages
                if (!isValid) {
                    document.getElementById("client-error").innerHTML = errorMessages.join("<br>");
                    document.getElementById("client-error").style.display = "block";
                } else {
                    document.getElementById("client-error").style.display = "none";
                }
                
                return isValid;
            }
        </script>-->
    </head>
    <body>
        <div id="client-error" style="color: red; text-align: center; display: none;"></div>
        <h3 style="color: red">${requestScope.error}</h3>
        <h1>Create a dish</h1>
        <form action="addDish" method="get" onsubmit="return validateForm()" accept-charset="UTF-8">
            Enter name: <input type="text" id="dishName" name="name" value="${requestScope.name}" /> <br>
<!--            <span class="hint">Vietnamese characters are allowed for dish names</span> <br> -->
            Enter price: <input type="text" id="dishPrice" name="price" value="${requestScope.price}" maxlength="10" /> <br>
            Enter status (yes/no):
            <select name="status" id="dishStatus">
                <option value="yes" ${requestScope.status == 'yes' ? 'selected' : ''}>Yes</option>
                <option value="no" ${requestScope.status == 'no' ? 'selected' : ''}>No</option>
            </select>
            <br><br>
            Enter Image Link: <input type="text" id="dishImage" name="image" value="${requestScope.image}" /> <br>
            <input type="submit" value="Add a dish" />
        </form>
        <div style="text-align: center; margin-top: 20px;">
            <a href="getDish">Back to Dish List</a>
        </div>
    </body>
</html>