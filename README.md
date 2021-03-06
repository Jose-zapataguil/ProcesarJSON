# ProcesarJSON

## Objetivo
El objetivo de este programa es clasificar la iformación de varios ficheros de jobs, de los cuales nos vamos a quedar con cierta información y los vamos a guardar en ficheros
.json en función de su uuaa.
Tambien existen jobs que no se procesan, que son los que no cuentan con uuaa en el configurl, o no tienen el parámetro "extralibs". En ese caso se indicará que ese job no ha sido procesado en el fichero de log correspondiente.

  
  ## Requisitos
  
  En este proyecto hemos utilizado la librería JSON-java.
  
  -JSON-java: https://github.com/stleary/JSON-java
  
  Para el control de las librerías hemos implementado maven, para evitar problemas a la hora de realizar los cambios en git. La dependencia que hemos agregado es la sigueinte:
  
  ![image](https://user-images.githubusercontent.com/81249604/116520098-8e5d2680-a8d2-11eb-874b-d9bf1a46f62c.png)
  
  ## Ejecución
  
  El proyecto contiene un archivo "ProcesarJSON.jar" el cual podemos ejecutar desde la consola de comandos (cmd) o bien abrir el proyecto desde IntelliJ y ejecutarlo desde ahi.
  En ambos casos deberemos proporcionarle un parametro para la ejecución, el cual será un fichero JSON con la información necesaria de los jobs para su procesado.

  
  
  ### Formas de Ejecución
  
 Tenemos dos maneras de ejecutar el programa:
 
  ###### IntelliJ
  
  Para ejecutarlo desde IntelliJ debemos descargarnos el proyecto desde el repositorio de github (https://github.com/Jose-zapataguil/ProcesarJSON) y abrirlo. Es necesario indicarle la ruta del fichero JSON de configuracion a la hora de la ejecución, para ello debemos editar la configuración del main.
  
  ![image](https://user-images.githubusercontent.com/81249604/116528719-738faf80-a8dc-11eb-9b7f-16e933deec14.png)
  
  Indicamos la ruta del fichereo
  
  ![image](https://user-images.githubusercontent.com/81249604/117668218-061a3380-b1a6-11eb-8ebd-f19987e9ab8b.png)

  
  Y lo ejecutamos.
  
  ![image](https://user-images.githubusercontent.com/81249604/117668371-2944e300-b1a6-11eb-8ae9-e42980eeb992.png)


  ###### CMD 
  
  Al igual que con el paso anterior para ejecutarlo desde el cmd descargamos el proyecto desde el github (https://github.com/Jose-zapataguil/ProcesarJSON) y lo descomprimimos. Abrimos una terminal y nos colocamos en la carpeta donde esté el .jar.
  
  Ejecutamos el comando "java -jar ProcesarJSON.jar "rutaJSON"
  
   ![image](https://user-images.githubusercontent.com/81249604/117668561-585b5480-b1a6-11eb-8a31-8999a3c31c65.png)

   
   Y comprobamos la ruta de salida para verificar que todos los ficheros se han creado correctamente. 
   
   ![image](https://user-images.githubusercontent.com/81249604/117668860-9eb0b380-b1a6-11eb-8553-b3afe7998d7b.png)


Nos deberá crear una estructura de directorios parecida a esta.

  
  ![image](https://user-images.githubusercontent.com/81249604/117668737-83de3f00-b1a6-11eb-8031-04cb9f94dba7.png)






  

 
  

