package by.xCaptin.restapi.servlets;

import by.xCaptin.restapi.entity.ProductEntity;
import by.xCaptin.restapi.repository.ProductRepositoryImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/product/*")
public class ProductServlet extends HttpServlet {
    private final ProductRepositoryImpl productRepositoryImpl = new ProductRepositoryImpl();
    private final Gson gson = new Gson();

    static String NAME_COLUMN = "name";
    static String KCAL_COLUMN = "kcal";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestPath = req.getPathInfo();

        try {
            if (requestPath == null || requestPath.equals("/")) {
                List<ProductEntity> productList = productRepositoryImpl.selectAllProducts();
                resp.setContentType("application/json");
                resp.getWriter().write(gson.toJson(productList));
            } else {
                String[] pathParts = requestPath.split("/");
                if (pathParts.length == 2) {
                    long id = Long.parseLong(pathParts[1]);
                    ProductEntity product = productRepositoryImpl.selectProducts(id);
                    if (product != null) {
                        resp.setContentType("application/json");
                        resp.getWriter().write(gson.toJson(product));
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    }
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        } catch (SQLException ex) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter(NAME_COLUMN);
            String kcal = req.getParameter(KCAL_COLUMN);
            if (name != null && kcal != null) {
                System.out.println(name);
                System.out.println(kcal);
                int kcalValue = Integer.parseInt(kcal);
                ProductEntity newProduct = new ProductEntity();
                newProduct.setName(name);
                newProduct.setKcal(kcalValue);
                productRepositoryImpl.insertProducts(newProduct);
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException ex) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestPath = req.getPathInfo();
        if (requestPath != null && requestPath.matches("/\\d+")) {
            try {
                long id = Long.parseLong(requestPath.substring(1));
                String name = req.getParameter(NAME_COLUMN);
                String kcalString = req.getParameter(KCAL_COLUMN);

                System.out.println(name);
                System.out.println(kcalString);

                if (name != null && kcalString != null) {
                    int kcal = Integer.parseInt(kcalString);
                    ProductEntity product = productRepositoryImpl.selectProducts(id);
                    if (product != null) {
                        product.setName(name);
                        product.setKcal(kcal);
                        productRepositoryImpl.updateProducts(product);
                        resp.setStatus(HttpServletResponse.SC_OK);
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    }
                } else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } catch (SQLException ex) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String requestPath = req.getPathInfo();
            if (requestPath != null && !requestPath.equals("/")) {
                String[] pathParts = requestPath.split("/");
                if (pathParts.length == 2) {
                    long id = Long.parseLong(pathParts[1]);
                    if (productRepositoryImpl.deleteProducts(id)) {
                        resp.setStatus(HttpServletResponse.SC_OK);
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    }
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException ex) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
