
// Provide a default path to dwr.engine
if (typeof this['dwr'] == 'undefined') this.dwr = {};
if (typeof dwr['engine'] == 'undefined') dwr.engine = {};

if (typeof this['dwrService'] == 'undefined') this.dwrService = {};

dwrService._path = '/ISicres/dwr';

dwrService.getPaises = function(callback) {
  dwr.engine._execute(dwrService._path, 'dwrService', 'getPaises', callback);
};

dwrService.getProvincias = function(callback) {
  dwr.engine._execute(dwrService._path, 'dwrService', 'getProvincias', callback);
};

dwrService.getProvinciaPorDefecto = function(callback) {
  dwr.engine._execute(dwrService._path, 'dwrService', 'getProvinciaPorDefecto', callback);
};

dwrService.getCiudades = function(p0, callback) {
  dwr.engine._execute(dwrService._path, 'dwrService', 'getCiudades', p0, callback);
};

dwrService.getTercerosFisicos = function(p0, p1, callback) {
  dwr.engine._execute(dwrService._path, 'dwrService', 'getTercerosFisicos', p0, p1, callback);
};

dwrService.getTercerosJuridicos = function(p0, p1, callback) {
  dwr.engine._execute(dwrService._path, 'dwrService', 'getTercerosJuridicos', p0, p1, callback);
};

