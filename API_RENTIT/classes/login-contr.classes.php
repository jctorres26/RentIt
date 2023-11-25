<?php

include('../classes/login.classes.php');




class LoginContr extends Login{


private $email; 
private $pwd;

    public function __construct($email, $pwd){

        $this->email = $email;
        $this->pwd = $pwd;

    }

    public function loginUser(){


       
        $check;

       $check =  $this->logUser($this->email, $this->pwd);
     //  echo "funcion de login";
        
        

        return $check;


    }


  

}
?>