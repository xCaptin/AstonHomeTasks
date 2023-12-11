package by.xCaptin.restapi.servlets;


import by.xCaptin.restapi.entity.ProductEntity;
import by.xCaptin.restapi.repository.ProductRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/")
public class ProductsServlet extends HttpServlet {
    private final ProductRepository productRepository = new ProductRepository();

    public ProductsServlet() {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getServletPath();

        try {
            switch (action) {
                case "/update":
                    this.updateProducts(req, resp);
                    return;
                case "/new":
                    this.showNewForm(req, resp);
                    return;
                case "/edit":
                    this.showEditForm(req, resp);
                    return;
                case "/delete":
                    this.deleteProducts(req, resp);
                    return;
                case "/insert":
                    this.insertProducts(req, resp);
                    return;
            }

            this.listProducts(req, resp);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<ProductEntity> productList = this.productRepository.selectAllProducts();
        request.setAttribute("productList", productList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("product-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        long id = Integer.parseInt(request.getParameter("id"));
        ProductEntity existingProduct = this.productRepository.selectProducts(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product-form.jsp");
        request.setAttribute("product", existingProduct);
        dispatcher.forward(request, response);
    }

    private void insertProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String name = request.getParameter("name");
        int kcal = Integer.parseInt(request.getParameter("kcal"));
        ProductEntity newProduct = new ProductEntity();
        newProduct.setName(name);
        newProduct.setKcal(kcal);
        this.productRepository.insertProducts(newProduct);
        response.sendRedirect("list");
    }

    private void updateProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int kcal = Integer.parseInt(request.getParameter("kcal"));
        ProductEntity product = new ProductEntity();
        product.setId(id);
        product.setName(name);
        product.setKcal(kcal);
        this.productRepository.updateProducts(product);
        response.sendRedirect("list");
    }

    private void deleteProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        long id = Integer.parseInt(request.getParameter("id"));
        this.productRepository.deleteProducts(id);
        response.sendRedirect("list");
    }
}




