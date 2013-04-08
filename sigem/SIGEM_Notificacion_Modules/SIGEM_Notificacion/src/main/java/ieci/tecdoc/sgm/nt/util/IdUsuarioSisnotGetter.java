package ieci.tecdoc.sgm.nt.util;

import ieci.tecdoc.sgm.nt.config.NotificacionesConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class IdUsuarioSisnotGetter {
		static Logger logger=Logger.getLogger(IdUsuarioSisnotGetter.class);
		
		public static String getIdUsuarioFromDEU(String deu,String entidad){
			String idUsuarioSisnot=null;
			
			Connection con=null;
			try {
				con=NotificacionesConfig.getSisnotConnection(entidad);
		      	if(con!=null){
		      		//si hay conexion -> realiza la consulta pasandole como parametro el deu
		      		PreparedStatement pstmt = con.prepareStatement(NotificacionesConfig.getSisnotIdUsuarioQuery(entidad));
		      		pstmt.setString(1,deu);
		      		ResultSet rs= pstmt.executeQuery();
		      		//solo se utiliza la primera columna de la primera fila devuelta por la consulta
		      		//un DEU se corresponde con un único ID de usuario.
		      		if(rs.next()) idUsuarioSisnot=rs.getString(1); 
		      		pstmt.close();
		      	}
			} catch (Exception e) {
				// Exception processing 
				logger.error(e);
			} finally { 
				try { if(con != null) con.close(); } 
				catch (Exception e) { logger.debug(e); } 
			}
			return idUsuarioSisnot;
		}
		
		/*
		public static void main(String [] args){
			System.out.println(IdUsuarioSisnotGetter.getIdUsuarioFromDEU("71882675QMOR"));
		}
		*/
}
