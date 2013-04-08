- El certificado CERTIFICADO_SNTS.cer, se utiliza para la autorizacion de la firma de los mensajes recibidos de Correos.
- El certificado de CERTIFICADO_SNTS_SLL.cer es para la autorización de los servidores trusted para el SSL de Correos. Se debe configurar el keystore de confiables.jceks, para que tenga las Cas y Cas intermedias que sea necesario.

CERTIFICADO_FIRMA.cer -> CERTIFICADO_SNTS.cer
CERTIFICADO_SSL.cer -> CERTIFICADO_SNTS_SSL.cer + importarlo confiables.jceks

