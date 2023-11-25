<?php

include('../classes/login-contr.classes.php');

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

if ($action == "login") {


    $email = $_POST["email"];
    $pwd = $_POST["password"];

    $login =  new LoginContr($email, $pwd);


    $res = $login->loginUser();

 

    if($res["check"] == false){
        $res['message1'] = "Entre a check false";
        $res['error'] = true;
       
    }else{
        $res['email'] = $email;
        $res['error'] = false;
    }

}




    
//echo "ya me fui";
header('Content-type: application/json; charset=utf-8');
$json =  json_encode($res);
echo json_encode($res);
die();

?>