<?php

include('../classes/dbh.classes.php');

class Register extends Dbh{


protected function insertUser($name,$lastName,$username,$email,$pwd,$userType,$img){

    $stmt = $this->connect()->prepare('INSERT INTO users(name, lastName, username, email, password, userType, img) VALUES (?,?,?,?,?,?,?);');

    if(!$stmt->execute(array($name,$lastName,$username,$email,$pwd,$userType,$img))){


    }
    $stmt = null;

}

/////////////////////////////////////////////////

protected function updateUserData($userId,$username,$pwd){

    $stmt = $this->connect()->prepare('UPDATE users SET username = ?, password = ? WHERE userId = ?;');

    if(!$stmt->execute(array($username,$pwd,$userId))){
        
    }

    $stmt = null;

}

/////////////////////////////////////////////////

protected function getSellers(){

    $arr = [];


    $stmt = $this->connect()->prepare('SELECT userId, name, email, school, phoneNumber, img from users');

    if(!$stmt->execute()){ //SI NO SE LOGRA EJECUTAR EL QUERY, ENTRA AQUI
     echo "No se trajeron las noticias";
        exit();

    }

    for($arr; $row= $stmt->fetchAll(PDO::FETCH_ASSOC); $arr[] = $row);

    $stmt = null;

    return $arr;


}





///////////////////////////////////////////////

protected function checkUser($email){

  

    $stmt = $this->connect()->prepare('SELECT email FROM users WHERE email = ?;');
    
    
    
    
        if(!$stmt->execute(array($email))){
          
            $stmt = null;
            exit();
        }
        
       
    
        $check;
    
        if($stmt->rowCount() > 0){
            $check = false;
        }else{
            $check = true;
        }
    
        return $check;
    
    }


}


?>