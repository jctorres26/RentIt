<?php

class Dbh{

    

    protected static $instance;  
    private $server;
    private $username;
    private $password;
    private $database;

    
 

private function __construct(){


     $server = "localhost";
     $username = "id21572744_admin";
     $password = "#Password123";
     $database= "id21572744_rentit";


    //  $server = "localhost";
    //  $username = "root";
    //  $password = "admin";
    //  $database= "rentit";


    try{
    
    self::$instance = new PDO("mysql:host=$server;dbname=$database; ",$username, $password);
    self::$instance->query("SET collation_connection = utf8_unicode_ci;");
        
    }catch(PDOException $error){

       // echo "conexion fallida";

        die("Connection failed" . $error->getMessage());

    }

    //echo "conexion";
}

public static function connect() {

    if(!self::$instance){
        new Dbh();
    }

    return self::$instance;
}

}

?>