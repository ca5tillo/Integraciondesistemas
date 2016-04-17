# Integracion de sistemas 
![Icon](https://raw.githubusercontent.com/ca5tillo/Integraciondesistemas/master/src/assets/wsdl/cap.jpg)


Java simulacion de un servicio web.
Consistede un cliente y un servidor. 
El cliente:
Descarga desde un servidor web el wsdl para despues parsear su contenido, 
Genera un arbol de objetos a partir del xml del wsdl. Tiene metodos de busqueda de nodos por atributo o por etiqueta.
Genera soap para enviarlo al servidor, posteriormente el servidor recibe el soap lo presesa, crear el soap de respuesta y se lo envia al cliente. 

Para iniciar el servidor hay que ejecutar el archivo servidor.java que se encuentra en el paquete socket_server. 
Para iniciar el cliente  hay que ejecutar el archivo main.java del paquete gui.

fuentes:

http://www.jc-mouse.net/java/crear-componentes-en-tiempo-de-ejecucion-con-netbeans

http://www.w3schools.com/xml/xml_soap.asp

http://di002.edv.uniovi.es/~falvarez/WSDL.pdf
