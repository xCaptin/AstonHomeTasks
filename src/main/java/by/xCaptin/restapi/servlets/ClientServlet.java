package by.xCaptin.restapi.servlets;

import by.xCaptin.restapi.entity.ClientEntity;
import by.xCaptin.restapi.repository.ClientRepositoryImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/client/*")
public class ClientServlet extends HttpServlet {
    private final ClientRepositoryImpl clientRepositoryImpl = new ClientRepositoryImpl();
    private final Gson gson = new Gson();

    static String NAME_COLUMN = "name";
    static String GROCERY_STORE_ID_COLUMN = "groceryStoreID";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestPath = req.getPathInfo();

        try {
            if (requestPath == null || requestPath.equals("/")) {
                List<ClientEntity> clientList = clientRepositoryImpl.selectAllClients();
                resp.setContentType("application/json");
                resp.getWriter().write(gson.toJson(clientList));
            } else {
                String[] pathParts = requestPath.split("/");
                if (pathParts.length == 2) {
                    long id = Long.parseLong(pathParts[1]);
                    ClientEntity client = clientRepositoryImpl.selectClient(id);
                    if (client != null) {
                        resp.setContentType("application/json");
                        resp.getWriter().write(gson.toJson(client));
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
            String groceryStoreId = req.getParameter(GROCERY_STORE_ID_COLUMN);
            if (name != null && groceryStoreId != null) {
                long groceryStoreIDValue = Long.parseLong(groceryStoreId);
                ClientEntity newClient = new ClientEntity();
                newClient.setName(name);
                newClient.setGroceryStoreID(groceryStoreIDValue);
                clientRepositoryImpl.insertClient(newClient);
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
                String groceryStoreIdString = req.getParameter(GROCERY_STORE_ID_COLUMN);

                if (name != null && groceryStoreIdString != null) {
                    long groceryStoreID = Long.parseLong(groceryStoreIdString);
                    ClientEntity client = clientRepositoryImpl.selectClient(id);
                    if (client != null) {
                        client.setName(name);
                        client.setGroceryStoreID(groceryStoreID);
                        clientRepositoryImpl.updateClient(client);
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
                    if (clientRepositoryImpl.deleteClient(id)) {
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