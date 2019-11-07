package ex23_baserelacionalf;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 Hemos usado la misma consola de sqlplus para lanza y probar los procedimientos 
 */
/*
 Desenvolver proxecto java que acceda ao procedemento  anterior pasandolle
 dous numeros calesqueira e  imprimindo o resultado devolto pola execucion 
 de dito procedemento.:
 */
//https://www.adictosaltrabajo.com/2011/08/04/usando-callable-statements-to-execute-stored-procedure/
public class Ex23_baserelacionalF {

    Connection conn;

    public void Conexion() throws SQLException {

        String driver = "jdbc:oracle:thin:";
        String host = "localhost.localdomain"; // tambien puede ser una ip como "192.168.1.14"
        String porto = "1521";
        String sid = "orcl";
        String usuario = "hr";
        String password = "hr";
        String url = driver + usuario + "/" + password + "@" + host + ":" + porto + ":" + sid;

        conn = DriverManager.getConnection(url);

    }

    public void Cerrar() throws SQLException {

        conn.close();

    }

    public void accederProce() {

        try {
            /*
             Por razones de rendimiento, intentad minimizar llamadas innecesarias 
             a Connection.prepareCall() reusando instancias de CallableStatement.
             */
            CallableStatement calS = conn.prepareCall("{call pjavaprocoracle(?,?)}");
            //especificamos los parámetros de entrada
            calS.setInt(1, 10);
            calS.setInt(2, 10);

            //registramos los parámetros de salida:
            //Params: posición relativa a su "?" y tipo 
            calS.registerOutParameter(2, Types.INTEGER);

            //recibimos los resultados:
            calS.execute();

            System.out.println(calS.getInt(2));

        } catch (SQLException ex) {
            Logger.getLogger(Ex23_baserelacionalF.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {

        Ex23_baserelacionalF obj = new Ex23_baserelacionalF();

        try {
            obj.Conexion();

            obj.accederProce();

            obj.Cerrar();

        } catch (SQLException ex) {
            Logger.getLogger(Ex23_baserelacionalF.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
