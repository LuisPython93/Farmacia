package models;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class SalesDao {

    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    //Registrar las ventas
    public boolean registerSaleQuery(int customer_id, int employee_id, double total) {
        String query = "INSERT INTO sales (customer_id, employee_id, total, sale_date) "
                + "VALUES (?,?,?,?)";

        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, customer_id);
            pst.setInt(2, employee_id);
            pst.setDouble(3, total);
            pst.setTimestamp(4, datetime);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    //Registrar detalles de las ventas
    public boolean registerSaleDetailQuery(int product_id, int sale_id,
            int sale_quantity, double sale_price, double sale_subtotal) {

        String query = "INSERT INTO sale_details (product_id, sale_id, sale_quantity, sale_price, sale_subtotal) "
                + "VALUES (?,?,?,?,?)";
        Timestamp datetme = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, product_id);
            pst.setInt(2, sale_id);
            pst.setInt(3, sale_quantity);
            pst.setDouble(4, sale_price);
            pst.setDouble(5, sale_subtotal);
            pst.execute();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());

            return false;
        }
    }
    
    //Obtener el id máximo de la venta 
    public  int saleId(){
        int id = 0;
        String query = "SELECT MAX(id) AS id FROM sales";
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            //Este condicional verifica si el "ResultSet" tiene al menos tiene un registro
            //rs.next() mueve el cursor a la siguiente fila del "ResultSet"
            //Si "rs.next()" devuelve "true" significa que hay al menos una fila en el "ResultSet"
            if(rs.next()){
                id = rs.getInt("id");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return id;
    }
    
    //Listar las ventas realizadas
    public List listAllSalesQuery(){
        List<Sales> list_sales = new ArrayList();
        
        String query = "SELECT s.id AS invoice, c.full_name AS customer, e.full_name AS employee, s.sale_date FROM sales s " +
                       "INNER JOIN customers c ON s.customer_id = c.id INNER JOIN employees e ON s.employee_id = e.id ORDER BY s.id ASC";
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            //Recorrer
            while(rs.next()){
                Sales sale = new Sales();
                sale.setId(rs.getInt("invoice"));
                sale.setCustomer_name(rs.getString("customer"));
                sale.setEmployee_name(rs.getString("employee"));
                sale.setTotal_to_pay(rs.getDouble("total"));
                sale.setSale_date(rs.getString("sale_date"));
                
                list_sales.add(sale);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
        return list_sales;
    }
}
