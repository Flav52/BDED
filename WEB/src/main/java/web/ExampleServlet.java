package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import banking.LocalBank;
import entity.Compte;
import init.Initializer;

@WebServlet("/display")
public class ExampleServlet extends HttpServlet {

    private static final long serialVersionUID = -3172627111841538912L;

    @EJB
    private LocalBank bank;

    @EJB
    private Initializer initializerBean;


    /**
     * Called by the server (via the service method) to allow a servlet to
     * handle a GET request.
     * @param request an HttpServletRequest object that contains the request the
     *        client has made of the servlet
     * @param response an HttpServletResponse object that contains the response
     *        the servlet sends to the client
     * @throws IOException if an input or output error is detected when the
     *         servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">");
        out.println("  <head>");
        out.println("    <link type=\"text/css\" href=\"ow2_jonas.css\" rel=\"stylesheet\" id=\"stylesheet\" />");
        out.println("    <title>Comptes bancaires</title>");
        out.println("  </head>");
        out.println("<body style=\"background : white; color : black;\">");


        out.println("  </div>");

        out.println("  <div class=\"titlepage\">Comptes bancaires</div>");

        out.println("  <div class=\"links\">");
        initComptes(out);
        out.println("    <br />");
        out.println("  </div>");

        out.println("  <div class=\"links\">");
        displayComptes(out);
        out.println("  </div>");

        out.println("  <div class=\"links\">");
        out.println("    <form action=\"secured/Admin\" method=\"get\">");
        out.println("      <div><input type=\"submit\" value=\"Ajouter un compte\"/></div>");
        out.println("    </form>");
        out.println("  </div>");

        out.println("<form action=\"/WEB\" method=\"get\">");
        out.println("      <div><input type=\"submit\" value=\"Home\"/></div>");
        out.println("    </form>");

        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    private void initComptes(final PrintWriter out) {
        out.println("Initialize accounts...<br/>");

        try {
            initializerBean.initializeEntities();
        } catch (Exception e) {
            displayException(out, "Cannot init list of accounts", e);
            return;
        }
    }

    private void displayComptes(final PrintWriter out) {
        out.println("Get accounts");
        out.println("<br /><br />");

        List<Compte> comptes = null;
        try {
            comptes = bank.allCompte();
        } catch (Exception e) {
            displayException(out, "Cannot call allCompte on the bean", e);
            return;
        }

        if (comptes != null) {
            for (Compte cpt : comptes) {
                out.println("Compte '" + cpt.getId() + "\t' : Solde:"+cpt.getSolde()+"<br/>");
            }
        } else {
            out.println("No account found !");
        }

    }

    /**
     * If there is an exception, print the exception.
     * @param out the given writer
     * @param errMsg the error message
     * @param e the content of the exception
     */
    private void displayException(final PrintWriter out, final String errMsg, final Exception e) {
        out.println("<p>Exception : " + errMsg);
        out.println("<pre>");
        e.printStackTrace(out);
        out.println("</pre></p>");
    }

}
