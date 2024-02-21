package by.xCaptin.restapi.servlets;

import by.xCaptin.restapi.entity.GroceryStoreEntity;
import by.xCaptin.restapi.repository.GroceryStoreRepositoryImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/groceryStore/*")
public class GroceryStoreServlet extends HttpServlet {
    private final GroceryStoreRepositoryImpl groceryStoreRepositoryImpl = new GroceryStoreRepositoryImpl();
    private final Gson gson = new Gson();

    static String NAME_COLUMN = "name";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestPath = req.getPathInfo();

        try {
            if (requestPath == null || requestPath.equals("/")) {
                List<GroceryStoreEntity> groceryStoreList = groceryStoreRepositoryImpl.selectAllGroceryStore();
                resp.setContentType("application/json");
                resp.getWriter().write(gson.toJson(groceryStoreList));
            } else {
                String[] pathParts = requestPath.split("/");
                if (pathParts.length == 2) {
                    long id = Long.parseLong(pathParts[1]);
                    GroceryStoreEntity groceryStore = groceryStoreRepositoryImpl.selectGroceryStore(id);
                    if (groceryStore != null) {
                        resp.setContentType("application/json");
                        resp.getWriter().write(gson.toJson(groceryStore));
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
            if (name != null) {
                GroceryStoreEntity newGroceryStore = new GroceryStoreEntity();
                newGroceryStore.setName(name);
                groceryStoreRepositoryImpl.insertGroceryStore(newGroceryStore);
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

                if (name != null) {
                    GroceryStoreEntity groceryStore = groceryStoreRepositoryImpl.selectGroceryStore(id);
                    if (groceryStore != null) {
                        groceryStore.setName(name);
                        groceryStoreRepositoryImpl.updateGroceryStore(groceryStore);
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
                    if (groceryStoreRepositoryImpl.deleteGroceryStore(id)) {
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