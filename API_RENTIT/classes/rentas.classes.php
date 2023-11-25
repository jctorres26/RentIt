<?php

include('../classes/dbh.classes.php');

class Rentas extends Dbh{


protected function insertRenta($titulo, $calificacion, $precio, $numCuartos, $numBanios, $ciudad, $colonia, $calle, $numero, $img, $isActive, $userId){

    $stmt = $this->connect()->prepare('INSERT INTO rentas(titulo, calificacion, precio, numCuartos, numBanios, ciudad, colonia, calle, numero, img, isActive, userId) 
                                        VALUES(?,?,?,?,?,?,?,?,?,?,?,?);');

    if(!$stmt->execute(array($titulo, $calificacion, $precio, $numCuartos, $numBanios, $ciudad, $colonia, $calle, $numero, $img, $isActive, $userId))){

        
    }
    $stmt = null;

}

///////////////////////////////////////////////

protected function updateRentaData($rentaId, $titulo, $calificacion, $precio, $numCuartos, $numBanios, $ciudad, $colonia, $calle, $numero, $isActive){

    $stmt = $this->connect()->prepare('UPDATE rentas SET titulo = ?, calificacion = ?, precio = ?, numCuartos = ?, numBanios = ?, ciudad = ?, colonia =?, calle=?, 
                                    numero=?, isActive=? WHERE rentaId = ?;');

    if(!$stmt->execute(array($titulo, $calificacion, $precio, $numCuartos, $numBanios, $ciudad, $colonia, $calle, $numero, $isActive, $rentaId))){
        
    }

    $stmt = null;

}



//////////////////////////////////////////



protected function getActivePosts(){

    $arr = [];


    $stmt = $this->connect()->prepare('SELECT rentaId, titulo, calificacion, precio, numCuartos, numBanios, ciudad, colonia, calle, numero, img, userId FROM rentas WHERE isActive = 1;');

    if(!$stmt->execute()){ //SI NO SE LOGRA EJECUTAR EL QUERY, ENTRA AQUI
     echo "No se trajeron las noticias";
        exit();

    }

    for($arr; $row= $stmt->fetchAll(PDO::FETCH_ASSOC); $arr[] = $row);

    $stmt = null;

    return $arr;


}


///////////////////////////////////////////////

protected function getUserPosts($userId){

    $arr = [];

    $stmt = $this->connect()->prepare('SELECT rentaId, titulo, calificacion, precio, numCuartos, numBanios, ciudad, colonia, calle, numero, img, userId 
                                        FROM rentas WHERE isActive = 1 AND userId = ?;');

    if(!$stmt->execute(array($userId))){
        echo "algo paso";
       
    }

    for($arr; $row= $stmt->fetchAll(PDO::FETCH_ASSOC); $arr[] = $row);

    $stmt = null;

    return $arr;
  

}

///////////////////////////////////////////////////////

protected function getDetailsByRentaId($rentaId){

    $arr = [];

    $stmt = $this->connect()->prepare('SELECT rentas.rentaId, rentas.titulo, rentas.calificacion, rentas.precio, rentas.numCuartos, rentas.numBanios, rentas.ciudad, rentas.colonia, rentas.calle, rentas.numero, rentas.img,
    users.name, users.lastname FROM rentas
    INNER JOIN users ON rentas.userId = users.userId AND rentas.rentaId = ?;');

    if(!$stmt->execute(array($rentaId))){ //SI NO SE LOGRA EJECUTAR EL QUERY, ENTRA AQUI
       echo "NO se registro el correo";
        exit();

    }

    $check;

    if($stmt->rowCount() == 0){ //SI NO COINCIDE NINGUN CORREO Y CONTRASENIA
        $stmt =  null;
        $arr['check'] = false;
        
    }else{
        $arr['check'] = true;
        $userRow = $stmt->fetchAll(PDO::FETCH_ASSOC);
        $arr["rentaId"] = $userRow[0]["rentaId"];
        $arr["titulo"] = $userRow[0]["titulo"];
        $arr["calificacion"] = $userRow[0]["calificacion"];
        $arr["precio"] = $userRow[0]["precio"];
        $arr["numCuartos"] = $userRow[0]["numCuartos"];
        $arr["numBanios"] = $userRow[0]["numBanios"];
        $arr["ciudad"] = $userRow[0]["ciudad"];
        $arr["colonia"] = $userRow[0]["colonia"];
        $arr["calle"] = $userRow[0]["calle"];
        $arr["numero"] = $userRow[0]["numero"];
        $arr["img"] = $userRow[0]["img"];
        $arr["name"] = $userRow[0]["name"];
        $arr["lastname"] = $userRow[0]["lastname"];
        

        $stmt = null;
    }

    return $arr;

}

/////////////////////////////////////////////////////////////

protected function getByRentaId($rentaId){

    $arr = [];

    $stmt = $this->connect()->prepare('SELECT rentaId, titulo, calificacion, precio, numCuartos, numBanios, ciudad, colonia, calle, numero, img, userId 
                                    FROM rentas WHERE rentaId = ?;');

    if(!$stmt->execute(array($rentaId))){ //SI NO SE LOGRA EJECUTAR EL QUERY, ENTRA AQUI
       echo "NO se registro el correo";
        exit();

    }

    $check;

    if($stmt->rowCount() == 0){ //SI NO COINCIDE NINGUN CORREO Y CONTRASENIA
        $stmt =  null;
        $arr['check'] = false;
        
    }else{
        $arr['check'] = true;
        $userRow = $stmt->fetchAll(PDO::FETCH_ASSOC);
        $arr["rentaId"] = $userRow[0]["rentaId"];
        $arr["titulo"] = $userRow[0]["titulo"];
        $arr["calificacion"] = $userRow[0]["calificacion"];
        $arr["precio"] = $userRow[0]["precio"];
        $arr["numCuartos"] = $userRow[0]["numCuartos"];
        $arr["numBanios"] = $userRow[0]["numBanios"];
        $arr["ciudad"] = $userRow[0]["ciudad"];
        $arr["colonia"] = $userRow[0]["colonia"];
        $arr["calle"] = $userRow[0]["calle"];
        $arr["numero"] = $userRow[0]["numero"];
        $arr["img"] = $userRow[0]["img"];
        
        

        $stmt = null;
    }

    return $arr;

}

}


?>