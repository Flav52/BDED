package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Compte;
import init.Initializer;
import banking.LocalBank;

public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 7724116000656853982L;

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
        String title = "Ajouter un compte";

        printHTMLHeader(out, title);

        out.println("  <div class=\"links\">");
        out.println("    <table width=\"100%\" cellpadding=\"0\">");
        out.println("      <tr>");
        out.println("        <td>");

        // Try to init the DB if this is the very first call to this application
        if(bank.allCompte().size()==0)
            initComptes(out);

        out.println("<br />");

        printAddCompteForm(out);

        out.println("        </td>");
        out.println("        <td>");

        // Try to add the new author
        String id =request.getParameter("Identifiant");
        String solde =request.getParameter("Solde");
        try{
            int idINT = Integer.parseInt(id.trim());
            long soldeLONG = Long.parseLong(solde.trim());
            addCompte(out, idINT, soldeLONG);
        }catch(Exception e){
        }


        // Display updated list of authors
        printComptes(out);

        out.println("        </td>");
        out.println("      </tr>");
        out.println("    </table>");
        out.println("  </div>");
        out.close();
    }

    /**
     * Print the page Footer.
     * @param out Servlet PrintWriter
     */
    private void printFooter(final PrintWriter out) {
        out.println("  <div class=\"footer\">");
        out.println("<form action=\"/WEB\" method=\"get\">");
        out.println("      <div><input type=\"submit\" value=\"Home\"/></div>");
        out.println("    </form>");
        out.println("  </div>");

        out.println("</body>");
        out.println("</html>");
    }

    private void printHTMLHeader(final PrintWriter out, final String id) {
        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">");
        out.println("  <head>");
        out.println("    <link type=\"text/css\" href=\"../ow2_jonas.css\" rel=\"stylesheet\" id=\"stylesheet\"/>");
        out.println("    <title>" + id + "</title>");
        out.println("  </head>");
        out.println("<body style=\"background : white; color : black;\">");

        out.println("  <div><a href=\"http://www.ow2.org\"><img src=\"../img/logoOW2.png\" alt=\"logo\"/></a></div>");

        out.println("  <div class=\"titlepage\">" + id + "</div>");
    }

    private void addCompte(final PrintWriter out, final int id, final long solde) {
        if (id > 0) {
            try {
                Compte nv= new Compte(id, solde);
                bank.addCompte(nv);
            } catch (Exception e) {
                printException(out, "Cannot add a new Compte (" + id + ")", e);
                return;
            }
        }
        out.println(bank.allCompte().size());

    }

    private void initComptes(final PrintWriter out) {
        out.println("Initialize Comptes...<br/>");

        try {
            initializerBean.initializeEntities();
        } catch (Exception e) {
            printException(out, "Cannot init list Comptes", e);
            return;
        }
    }

    private void printComptes(final PrintWriter out) {
        out.println("Get accounts");
        out.println("<br /><br />");

        // Get list of Authors
        List<Compte> comptes = null;
        try {
            comptes = bank.allCompte();
        } catch (Exception e) {
            printException(out, "Cannot call allCompte on the bean", e);
            return;
        }

        // List for each author, the name of books
        if (comptes != null) {
            for (Compte cpt : comptes) {
                out.println("Compte avec l'identifiant '" + cpt.getId() + "' :");
                out.println("<ul>");
                out.println("<li>Solde '" + cpt.getSolde() + "'.</li>");
                out.println("</ul>");

            }
        } else {
            out.println("Aucun compte trouvé !");
        }
        printFooter(out);

    }

    private void printAddCompteForm(final PrintWriter out) {

        out.println("Ajouter un compte:");
        out.println("<form action=\"add-compte\" method=\"get\">");
        out.println("  <div>");
        out.println("    <input name=\"Identifiant\" type=\"text\" value=\"\"/>");
        out.println("    <input name=\"Solde\" type=\"text\" value=\"\"/>");
        out.println("    <input type=\"submit\" value=\"Add\"/>");
        out.println("  </div>");
        out.println("</form>");
    }


    private void printException(final PrintWriter out, final String errMsg, final Exception e) {
        out.println("<p>Exception : " + errMsg);
        out.println("<pre>");
        e.printStackTrace(out);
        out.println("</pre></p>");
    }

}
