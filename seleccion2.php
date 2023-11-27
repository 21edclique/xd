<?php
 include_once('conexion.php');
class CRUD{
public static function ObtenerEstudiantes($cedula){
    $objeto=new Conexion();
    $conectar=$objeto->Conectar();
    $select="SELECT e.nombre, c.nombre_curso, n.nombre_nivel
    FROM estudiantes e
    JOIN curso c ON e.id_curso = c.id_curso
    JOIN nivel n ON e.id_nivel = n.id_nivel
    WHERE e.cedula = ".$cedula;
    $resultado=$conectar->prepare($select);
    $resultado->execute();
    $data=$resultado->fetchAll(PDO::FETCH_ASSOC);
    echo json_encode($data);
}
}

?>