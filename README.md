# ProcesarJSON

## Objetivo

El objetivo de este programa es acceder a un fichero .JSON con muchos objetos (jobs) y extraer cierta información de cada uno de ellos.

Generaremos un fichero .JSON para cada uno de los jobs con los siguientes datos:

  -Id
  -Description
  -Size
  -Runtime
  -Streaming
  -Params
  -Env
  -Tags
  -Kind
  
  ## Requisitos
  
  En este proyecto hemos utilizado la librería JSON-java.
  
  -JSON-java: https://github.com/stleary/JSON-java
  
  Para el control de las librerías hemos implementado maven, para evitar problemas a la hora de realizar los cambios en git. La dependencia que hemos agregado es la sigueinte:
  
  ![image](https://user-images.githubusercontent.com/81249604/116520098-8e5d2680-a8d2-11eb-874b-d9bf1a46f62c.png)
  
  ## Ejecución
  
  El archivo del cual vamos a extraer la información contiene muchos jobs.
  
 
  De aqui nos vamos a quedar con la información antes mencionada, introduciendola en un fichero por cada uno de los jobs.
  
  Una vez se ejecute nos generará todos los ficheros donde le hayamos indicado, en este caso en una carpeta dentro del directorio del proyecto. Para el nombre de cada fichero    hemos utilizado el id añadiendo la extension .json.

  
  
  Aunque la información variará dependiendo del tipo de job que procesemos (kirby, hammurabi...). Siempre se asemejarán al siguiente formato.
  
  ### Formas de Ejecución
  
 Tenemos dos maneras de ejecutar el programa:
 
  -IntelliJ. Para ejecutarlo desde IntelliJ debemos descargarnos el proyecto desde el repositorio de github (https://github.com/Jose-zapataguil/ProcesarJSON) y abrirlo. Es necesario indicarle dos parametros a la hora de la ejecución, para ello debemos editar la configuración del main.
  
  ![image](https://user-images.githubusercontent.com/81249604/116528719-738faf80-a8dc-11eb-9b7f-16e933deec14.png)
  
  Indicamos la ruta de entrada y la de salida separadas por un espacio en los argumentos del programa.
  
  ![image](https://user-images.githubusercontent.com/81249604/116529138-e39e3580-a8dc-11eb-9a23-516b001f0c15.png)
  
  Y lo ejecutamos.
  
  ![image](https://user-images.githubusercontent.com/81249604/116529219-ff094080-a8dc-11eb-940f-985310f89fc8.png)

  -CMD. A la hora de ejecutarlo desde el cmd




  

 
  

