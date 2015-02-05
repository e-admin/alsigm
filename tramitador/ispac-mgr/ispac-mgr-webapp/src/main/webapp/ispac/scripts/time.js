/**
 * Funciones de tiempo.
 *
 * whenTry(condicion, comando[, msgErr]) -> Ejecuta el comando cuando se cumpla la condici?n. Si no se cumple transcurrido un tiempo, y se ha especificado msgErr, se muestra un alert con msgErr.
 *
 * waitFor(condicion, millis) -> Espera un tiempo m?ximo a que se cumpla la condici?n
 */

function whenTry(condicion, comando, msgErr, intento)
{
	var whenTry;
	try
	{
		whenTry = eval(condicion);
	}
	catch(e)
	{
		whenTry = false;
	}

	if(whenTry)
	{
		eval(comando);
	}
	else
	{
		if(intento == undefined)
		{
			intento = 1;
		}
		else 
		{
			if(intento > 100)
			{
				if(msgErr != undefined)
				{
					alert(msgErr);
				}
				return false;
			}
			else
			{
				intento = intento + 1;
			}
		}
		
		if(msgErr == undefined)
		{
			setTimeout("whenTry(\""+condicion+"\", \""+comando+"\", undefined, "+intento+")", 100);
		}
		else
		{
			setTimeout("whenTry(\""+condicion+"\", \""+comando+"\", \""+msgErr+"\", "+intento+")", 100);
		}
	}
}

function waitFor(_condition, _millis)
{
	var _aux	= new Date().getTime();
	
	var _dif = new Date().getTime() - _aux;
	while( !eval(_condition) && (_dif < _millis) )
	{
		_dif = new Date().getTime() - _aux;
	}
}

