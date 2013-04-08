var tramo = 20;

function activarBotones(nombreCampo, ampliar, reducir, restaurar) {
	activarBoton(nombreCampo, 'ampliar', ampliar);
	activarBoton(nombreCampo, 'reducir', reducir);
	activarBoton(nombreCampo, 'restaurar', restaurar);
}

function activarBoton(nombreCampo, accion, activar) {
	var nombreImagen = nombreCampo + "I" + accion;
	var nombreUrl = nombreCampo + "Url";
	var urlImagen = getControl(nombreUrl).value;
	if (activar) {
		urlImagen = getControl(nombreImagen).src = urlImagen + accion + ".gif";
	} else {
		urlImagen = getControl(nombreImagen).src = urlImagen + accion
				+ "_disabled.gif";
	}

	getControl(nombreImagen).src = urlImagen;
}

function getNumRows(nombreCampo) {
	return getControl(nombreCampo).rows;
}

function setNumRows(nombreCampo, filas) {
	getControl(nombreCampo).rows = filas;
}

function ampliarTexto(boton, nombreCampo) {
	var filas = getNumRows(nombreCampo);
	setNumRows(nombreCampo, filas + tramo);
	getControl(nombreCampo + "B" + 'ampliar').focus();
	activarBotones(nombreCampo, true, true, true);
}

function reducirTexto(boton, nombreCampo) {
	var filas = getNumRows(nombreCampo);
	if (filas > tramo) {
		setNumRows(nombreCampo, filas - tramo);
	}

	filas = getNumRows(nombreCampo);

	if (filas <= tramo) {
		activarBotones(nombreCampo, true, false, false);
	}
	getControl(nombreCampo + "B" + 'reducir').focus();
}

function restaurarTexto(boton, nombreCampo) {
	var filas = getNumRows(nombreCampo);
	setNumRows(nombreCampo, tramo);
	activarBotones(nombreCampo, true, false, false);
	getControl(nombreCampo + "B" + 'restaurar').focus();

}
