<?php

include('../classes/register.classes.php');

class RegisterContr extends Register{

private $userId;
private $name;
private $lastName;
private $username; 
private $email; 
private $pwd;
private $userType;
private $img;


public function __construct(){ }

public static function withUserData($name, $lastName, $username, $email, $pwd, $userType, $img){

    $instance =  new self();
    $instance->fillWithUserData($name, $lastName, $username, $email, $pwd, $userType, $img);

    return $instance;

}

protected function fillWithUserData($name, $lastName, $username, $email, $pwd, $userType, $img){

    $this->name = $name;
    $this->lastName = $lastName;
    $this->username = $username;
    $this->email = $email;
    $this->pwd = $pwd;
    $this->userType = $userType;
    $this->img = $img;


}

public function registerUser(){

    $check;

    if($this->userCheck() == false){
        $check = false;
    }else{

    $this->insertUser($this->name,$this->lastName, $this->username, $this->email, $this->pwd, $this->userType, $this->img);

    $check = true;

    }

    return $check;
}

///////////////////////////////////////////

public static function withUserDataUpdate($userId, $username, $pwd){

    $instance =  new self();
    $instance->fillWithUserDataUpdate($userId, $username, $pwd);

    return $instance;

}

protected function fillWithUserDataUpdate($userId, $username, $pwd){

    $this->userId = $userId;
    $this->username = $username;
    $this->pwd = $pwd;
   


}

public function updateUser(){

    
    $this->updateUserData($this->userId, $this->username, $this->pwd);

    
}

//////////////////////////////////////

public function selectSellers(){

    $sellers;

    $sellers = $this->getSellers();

    return $sellers;

}


/////////////////////////////////////// CHECA SI SE REPITE EMAIL

private function userCheck(){
    $check;

    if(!$this->checkUser($this->email)){
        $check = false;
    }else {
        $check = true;
    }

    return $check;
}

}
?>