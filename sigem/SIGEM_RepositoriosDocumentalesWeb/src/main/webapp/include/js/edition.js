var userGeneral = new Array();
userGeneral[0]="document.getElementById('user.name');"
userGeneral[1]="document.getElementById('user.pwd');"
userGeneral[2]="document.getElementById('user.repwd');"	
userGeneral[3]="document.getElementById('user.description');"
userGeneral[4]="document.getElementById('user.pwdmbc');"
userGeneral[5]="document.getElementById('user.pwdvpcheck');"
userGeneral[6]="document.getElementById('user.idCert');"


var deptGeneral = new Array();
deptGeneral[0]="document.getElementById('dept.name');"
deptGeneral[1]="document.getElementById('dept.manager');"
deptGeneral[2]="document.getElementById('dept.description');"

var groupGeneral = new Array();
groupGeneral[0]="document.getElementById('group.name');"
groupGeneral[1]="document.getElementById('group.manager');"
groupGeneral[2]="document.getElementById('group.description');"


var permisos = new Array();
permisos[0] = "document.getElementById('idocConsulta');"
permisos[1] = "document.getElementById('idocModificacion');"
permisos[2] = "document.getElementById('idocCreacion');"
permisos[3] = "document.getElementById('idocBorrado');"
permisos[4] = "document.getElementById('idocImpresion');"

var system = "document.getElementById('system.superuser')";
var user = new Array();
user[0] = "document.getElementById('user.superuser');"
user[1] = "document.getElementById('user.manager');"
user[2] = "document.getElementById('user.standard');"
user[3] = "document.getElementById('user.none');"
var idoc = new Array();	
idoc[0] = "document.getElementById('idoc.superuser');"
idoc[1] = "document.getElementById('idoc.manager');"
idoc[2] = "document.getElementById('idoc.standard');"
idoc[3] = "document.getElementById('idoc.none');"
var volume = "document.getElementById('volume.superuser')";

// Funcion llamada cuando se modifica el checkbox superUsuario, del producto system
function System(obj){
	if (obj.checked){
		eval(user[0]).checked = true; // Administrador de usuarios a superuser
		for (i = 0; i < user.length ; i ++ )
			desabilitar (eval(user[i])); // y desabilitarlas 
		eval (idoc[0]).checked = true; // InvesDoc a superuser
		for (i = 0; i < 4; i ++ )
			desabilitar (eval(idoc[i]));
			
		eval(volume).checked = true // Administrador de volumenes a superuser
		desabilitar (eval(volume));
		seleccionarTodosLosPermisos();				
		}
	else {
		// Volverlas a habilitar
		for ( i = 0; i < user.length; i++)
			habilitar(eval (user[i])); 
		for (i = 0; i < 4; i ++ )
			habilitar(eval (idoc[i])); 
		habilitar(eval(volume));
	}
}
// Funcion asociada a las variables de administracion de usuarios
function adminIdoc(obj)
{
	if (obj == eval(idoc[0]) || obj == eval(idoc[1]) ){ // si es superuser o manager sobre invesdoc
		seleccionarTodosLosPermisos();				
	}
	else if (obj == eval(idoc[2]) ){ // si es standard
		eval(permisos[0]).checked=true; // seleccionar permiso de consulta
		desabilitar(eval(permisos[0]));
		for (i = 1; i < 5; i ++ )
			eval(permisos[i]).checked=false;
			
		habilitar(eval(permisos[1])); // modificacion
		habilitar(eval(permisos[4])); // impresion
					
		desabilitar(eval(permisos[2])); // creacion
		desabilitar(eval(permisos[3])); // borrado
		 
	}
	else if (obj == eval(idoc[3])){ // privilegios none
		prohibirTodosLosPermisos();
	}
	
}
function seleccionarTodosLosPermisos()
{
	for (i = 0; i <= 4; i ++ )
	{
		temp = eval(permisos[i]);
		temp.checked = true;
		desabilitar(temp);			
	}
}

function prohibirTodosLosPermisos()
{
	for (i = 0; i <= 4; i ++ )
	{
		temp = eval(permisos[i]);
		temp.checked = false;
		desabilitar(temp);
		
	}

}



function valida(obj){

if ( obj.name == 'idocModificacion')
	{
	if (obj.checked == true){
		eval (permisos[0]).disabled = true; // Deshabilitar consulta
		eval (permisos[0]).checked = true;
		eval (permisos[2]).disabled = false; // Habilitar creacion
		eval (permisos[3]).disabled = false; // habilitar borrado
		}
	else
		{
		eval (permisos[0]).disabled = false; // habilitar consulta
		eval (permisos[2]).disabled = true; // Desabilitar creacion
		eval (permisos[3]).disabled = true; // y borrado
		}			
	}
else if (obj.name == 'idocCreacion' || obj.name=='idocBorrado'){
	if (!eval (permisos[2]).checked && !eval (permisos[3]).checked ) // si no esta pulsado creacion y borrado
		eval (permisos[1]).disabled=false; // desabilitar modificacion
	else
		eval (permisos[1]).disabled=true;
			
	}
else if (obj.name == 'idocConsulta'	){
	if (obj.checked == false){ // desabilita todos los eltos
		for (i = 1; i <= 4; i ++ )
		{
			temp = eval(permisos[i]);
			temp.checked = false;
			temp.disabled = true
		}
	}
	else
		{ // habilita modificacion e impresion
			eval(permisos[1]).disabled = false;
			eval(permisos[4]).disabled = false;
			valida(eval(permisos[2]));
			valida(eval(permisos[3]));			
		}
		
	}
}
function carga()
{	

	if ( eval(system).checked ){
		System(eval(system));
		}
	else if (eval(idoc[2]).checked) // Es administrador estandar sobre invesdoc
	{
		eval(permisos[0]).checked = true; // Por si acaso se activa permiso de consulta
		desabilitar(eval(permisos[0]));
		
		if (eval(permisos[2]).checked || eval(permisos[3]).checked )
			{
			eval(permisos[1]).checked = true; // Por si acaso
			desabilitar (eval(permisos[1]));
				
			}
		else if ( ! eval(permisos[1]).checked) { // Si no se permite modificar
			desabilitar(eval(permisos[2])); // desabilitar permiso creacion y borrado
			desabilitar(eval(permisos[3]));
		}
		
	}else if (eval(idoc[3]).checked ){ // No tiene derechos sobre invesdoc
		prohibirTodosLosPermisos();	
	}
	else if ( eval(idoc[0]).checked || eval(idoc[1]).checked ) { // Es Administrador (manager) o superusuario (superuser) sobre invesdoc
		seleccionarTodosLosPermisos();
		}

} // fin carga()
	
function desabilitar(obj)
{
	obj.disabled = true;
}
function habilitar (obj)
{
	obj.disabled = false;
}

function disable(obj)
{
	obj.disabled = true;
}
function enable (obj)
{
	obj.disabled = false;
}

function isChecked(obj)
{
	var success = false;
	if (obj.checked == true )
		success = true;
	return success;
}

function habilitarTodo()
{
	for ( i = 0; i < user.length; i++){
		habilitar(eval(user[i]) );
		}
	for (i = 0; i < 4; i ++ ){
	habilitar(eval(idoc[i]));
		}
	habilitar (eval(volume) );
	for (i = 1; i <= 4; i ++ )
	{
		temp = eval(permisos[i]);
		habilitar(temp);
	}
}



function desabilitarTodo()
{
	for (i = 0; i < permisos.length; i ++)
		desabilitar(eval(permisos[i]));
	desabilitar(eval(system));
	
	for (i = 0; i < user.length ; i ++ )
		desabilitar(eval(user[i]));
	for (i = 0;  i < idoc.length; i++ )
		desabilitar (eval(idoc[i]));
	desabilitar (eval(volume));
}


function disableUserGeneral()
{
	for (i = 0; i < userGeneral.length; i ++ )
	{
		var tmp = eval(userGeneral[i])
		if (tmp != null )
			disable(tmp);
	}
}
function enableUserGeneral()
{
	for (i = 0; i < userGeneral.length; i ++ )
	{
		var tmp = eval(userGeneral[i])
		if (tmp != null )
			enable(tmp);
	}
}
function enableProfileGeneral()
{
	enable(eval(system));
	for (i = 0; i < user.length; i ++ )
		enable ( eval(user[i]) );
	for (i = 0; i < idoc.length; i ++ )
		enable ( eval(idoc[i]) );		
	enable(eval(volume));
}
function disableDeptGeneral()
{
	for (i = 0; i < deptGeneral.length; i ++ )
	{
		var tmp = eval(deptGeneral[i])
		disable(tmp);
	}	
}
function enableDeptGeneral()
{
	for (i = 0; i < deptGeneral.length; i ++ )
	{
		var tmp = eval(deptGeneral[i])
		enable(tmp);
	}	
}

function disableGroupGeneral()
{
	for (i = 0; i < groupGeneral.length; i ++ )
	{
		var tmp = eval(groupGeneral[i])
		disable(tmp);
	}	
}

function enableGroupGeneral()
{
	for (i = 0; i < groupGeneral.length; i ++ )
	{
		var tmp = eval(groupGeneral[i])
		enable(tmp);
	}	
}

function disablePermission()
{
	for (i = 0; i < permisos.length; i++)
		desabilitar(eval(permisos[i]));
}

function enableAllPermission()
{
	for (i = 0; i <= 4; i ++ )
		enable ( eval(permisos[i]));
}
