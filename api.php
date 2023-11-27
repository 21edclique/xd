<?php
//El metodo server me devuelve en donde estoy si post , delete o put 
$opc=$_SERVER["REQUEST_METHOD"];
include_once 'seleccion.php';
switch($opc){
    case "GET":
        $cedula=$_GET["cedula"];
    //$resultado="Tu estas en un get";
    CRUD::ObtenerEstudiantes($cedula);
    //echo($resultado);
    break;
}
?>