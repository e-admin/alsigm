package es.ieci.tecdoc.isicres.admin.estructura.keys;

public class ISicresAdminLdapKeys {
		public static final long ERROR_BASE = 1011000000; 
	
		/**
		 * Error inesperado en la aplicación
		 */
		public static final long ERROR_INESPERADO = ERROR_BASE + 1;
		
		/**
		* Error en la base de datos de usuarios.
		*/
		public static final long EC_ERR_DB = ERROR_BASE + 2;
		   
		/**
		* El usuario no es válido.
		*/
		public static final long EC_BAD_USER_OR_PASS = ERROR_BASE + 3;
		
		/**
		* Error al crear el usuario.
		*/
		public static final long EC_ADD_USER = ERROR_BASE + 4;
		
		/**
		* Error al eliminar el usuario.
		*/
		public static final long EC_DELETE_USER = ERROR_BASE + 5;
		
		/**
		* Error al recuperar la información del usuario.
		*/
		public static final long EC_GET_USER = ERROR_BASE + 6;
		
		/**
		* Error al actualizar la información del usuario.
		*/
		public static final long EC_UPDATE_USER = ERROR_BASE + 7;
		
		/**
		* Error obteniendo lista de usuarios.
		*/
		public static final long EC_FIND_USERS = ERROR_BASE + 8;
		
		/**
		* Error obteniendo URL de aplicacion
		*/
		public static final long EC_OBTENER_URL_APLICACION = ERROR_BASE + 9;
		
		/**
		* Error obteniendo URL de aplicacion
		*/
		public static final long EC_OBTENER_URL_LOGIN = ERROR_BASE + 10;
		
		/**
		* Error obteniendo URL de aplicacion
		*/
		public static final long EC_OBTENER_URL_LOGOUT = ERROR_BASE + 11;
}
