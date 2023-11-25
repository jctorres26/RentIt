<?php

include('../classes/dbh.classes.php');



class Login extends Dbh{


protected function logUser($email,$pwd){

    $arr = [];

    $stmt = $this->connect()->prepare('SELECT userId, name, lastName, username ,email, password, userType, img FROM users WHERE BINARY email = ? AND BINARY password = ?;');

    if(!$stmt->execute(array($email,$pwd))){ //SI NO SE LOGRA EJECUTAR EL QUERY, ENTRA AQUI
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
        $arr["userId"] = $userRow[0]["userId"];
        $arr["name"] = $userRow[0]["name"];
        $arr["lastName"] = $userRow[0]["lastName"];
        $arr["username"] = $userRow[0]["username"];
        $arr["email"] = $userRow[0]["email"];
        $arr["password"] = $userRow[0]["password"];
        $arr["userType"] = $userRow[0]["userType"];
        $arr["img"] = $userRow[0]["img"];


        $stmt = null;
    }

   

   
    $stmt = null;
   // echo json_encode($arr);
    return $arr;
    

}



}


?>