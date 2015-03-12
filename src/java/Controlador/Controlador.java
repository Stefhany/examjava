/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador;

import dao.LibroDAO;
import dao.PrestamoDAO;
import dto.PrestamoDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author -ADMIN-
 */
public class Controlador extends HttpServlet {

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
        
        if(request.getParameter("devolver") != null){
                int cc = Integer.parseInt( request.getParameter("txtUser").trim());
                int idLibro = Integer.parseInt( request.getParameter("txtLibro").trim() );
                int estado = Integer.parseInt( request.getParameter("estado").trim() );
                
                //PrestamoDTO prestado = PrestamoDAO.consultarPrestamo();
                
                
                switch(estado) {
                   case 0:
                       // libro entregado correctamnete
                       PrestamoDTO p = PrestamoDAO.consultarPrestamoByUser(cc);
                       int idPrestamo =  p.getIdPrestamo();
                       boolean res = PrestamoDAO.cambiarEstadoPrestamo(0, idPrestamo);
                       if(res == true)
                            response.sendRedirect("index.jsp?msg=ok devuelto libro");
                       else
                            response.sendRedirect("index.jsp?msg= Error");
                       
                       
                      break;
                   case 1:
                       // libro perdido
                       boolean result = LibroDAO.cambiarEstadoLibro(idLibro, estado);
                       response.sendRedirect("index.jsp?msg=Libro perdido");
                       // generar multa
                       break;
                   case 2:
                       // libro dañado
                       boolean rs = LibroDAO.cambiarEstadoLibro(idLibro, estado);
                       response.sendRedirect("index.jsp?msg= libro dañado");
                       
                       // generar multa
                      break;
        
                }
                
                
        }else{
            response.sendRedirect("error404.jsp");
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
        processRequest(request, response);
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
