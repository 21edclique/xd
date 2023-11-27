<?php
 include_once('conexion.php');
class CRUD{
public static function ObtenerEstudiantes($cedula){
   
    $objeto=new Conexion();
    $conectar=$objeto->Conectar();
    $select=" SELECT
        e.nombre AS nombre_estudiante,
        (
            SELECT nombre_curso
            FROM curso
            WHERE cedula = e.cedula
            ) AS curso,
        (
            SELECT nombre_nivel
            FROM nivel
            WHERE id_nivel = e.id_nivel
            ) AS nivel
    FROM
        estudiantes e
    WHERE
        e.cedula = $cedula; ";
    $resultado=$conectar->prepare($select);
    $resultado->execute();
    $data=$resultado->fetchAll(PDO::FETCH_ASSOC);
    echo json_encode($data);
}
}

?>