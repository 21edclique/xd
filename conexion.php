<?php
class Conexion{
   public function Conectar(){
    define("host","localhost:3309");
    define("username","root");
    define("password","");
    define("databasename","repaso2");
    $opc=array(PDO::MYSQL_ATTR_INIT_COMMAND>'SET NAMES utf8');
    try{
        $conexion=new PDO("mysql:host=".host.";dbname=".databasename,username,password,$opc);
       return $conexion;
    }catch(PDOException $e){
    die("Error en la conexion ".$e->getMessage());
    }
   }
  
}



?>