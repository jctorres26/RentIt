<?php

include('../classes/register-contr.classes.php');

if (isset($_SERVER['HTTP_ORIGIN'])) {
    // Decide if the origin in $_SERVER['HTTP_ORIGIN'] is one
    // you want to allow, and if so: test
    header('Access-Control-Allow-Origin: *');
    header('Access-Control-Allow-Credentials: true');
    header('Access-Control-Max-Age: 1000');
}
if($_SERVER['REQUEST_METHOD'] == 'OPTIONS') {
    if(isset($_SERVER['HTTP_ACCESS_CONTROL_REQUEST_METHOD'])) {
        // may also be using PUT, PATCH, HEAD etc
        header("Access-Control-Allow-Headers: POST, GET, OPTIONS, PUT, DELETE");
    }

    if(isset($_SERVER['HTTP_ACCESS_CONTROL_REQUEST_HEADERS'])) {
        header("Access-Control-Allow-Headers: Accept, Content-Type, Content-Lenght, Accept-Encoding, X-CSRF-Token, Authorization");
    }
    exit(0);
}

$res = array('error' => false);
$action = '';

if (isset($_GET['action'])) {

    $action=$_GET['action'];
}

if($action=="create"){

    $name = $_POST["name"];
    $lastName = $_POST["lastName"];
    $username = $_POST["username"];
    $email = $_POST["email"];
    $pwd = $_POST["password"];
    $userType = $_POST["userType"];
    $img = $_POST["img"];
 


    $register = RegisterContr::withUserData($name,$lastName,$username,$email,$pwd,$userType,$img);

    $check = $register->registerUser();

    if($check == false){
        $res['message1'] = "Entre a check false";
        $res['error'] = true;
        $res['message'] = "El correo ya esta registrado";
    }

}

if($action == "update"){

    $userId = $_POST["userId"];
    $username = $_POST["username"];
    $pwd = $_POST["password"];

    $user = RegisterContr::withUserDataUpdate($userId,$username,$pwd);
   
    $user->updateUser();

}

if ($action == "selectSellers") {

    $sellers = new RegisterContr();

   $res = $sellers->selectSellers();
   header('Content-type: application/json');
 
    echo json_encode($res[0]);
    die();
    

}


header('Content-type: application/json');
    echo json_encode($res);
    die();

?>