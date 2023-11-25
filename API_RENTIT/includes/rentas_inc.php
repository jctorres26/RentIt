<?php

include('../classes/rentas-contr.classes.php');

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


    
    $titulo = $_POST["titulo"];
    $calificacion = $_POST["calificacion"];
    $precio = $_POST["precio"];
    $numCuartos = $_POST["numCuartos"];
    $numBanios = $_POST["numBanios"];
    $ciudad = $_POST["ciudad"];
    $colonia = $_POST["colonia"];
    $calle = $_POST["calle"];
    $numero = $_POST["numero"];
    $img = $_POST["img"];
    $isActive = $_POST["isActive"];
    $userId = $_POST["userId"];
 
  RentasContr::withRentaData($titulo, $calificacion, $precio, $numCuartos, $numBanios, $ciudad, $colonia, $calle, $numero, $img, $isActive, $userId)->registerRenta();


}

if($action == "update"){

    $rentaId = $_POST["rentaId"];
    $titulo = $_POST["titulo"];
    $calificacion = $_POST["calificacion"];
    $precio = $_POST["precio"];
    $numCuartos = $_POST["numCuartos"];
    $numBanios = $_POST["numBanios"];
    $ciudad = $_POST["ciudad"];
    $colonia = $_POST["colonia"];
    $calle = $_POST["calle"];
    $numero = $_POST["numero"];
    $isActive = $_POST["isActive"];
   
    
    $posts = RentasContr::withRentaDataUpdate($rentaId, $titulo, $calificacion, $precio, $numCuartos, $numBanios, $ciudad, $colonia, $calle, $numero, $isActive);
   
    $posts->updateRenta();

}

if($action=="getActivePosts"){


    
    $posts = new RentasContr();
    $res = $posts->selectActivePosts();

    header('Content-type: application/json');
     echo json_encode($res[0]);
     die();

}

if($action=="getUserPosts"){


    
    $userId = $_GET["userId"];
  
 

 $res = RentasContr::withUserId($userId)->selectUserPosts();

 header('Content-type: application/json');
 echo json_encode($res[0]);
 die();
     

}

if ($action == "getByRentaId") {


    $rentaId = $_POST["rentaId"];

    $post = RentasContr::withRentaId($rentaId);


    $res = $post->selectByRentaId();

 

    if($res["check"] == false){
        $res['message1'] = "Entre a check false";
        $res['error'] = true;
       
    }else{
        $res['error'] = false;
    }

}

if ($action == "getDetailsByRentaId") {


    $rentaId = $_POST["rentaId"];

    $post = RentasContr::withRentaId($rentaId);


    $res = $post->selectDetailsByRentaId();

 

    if($res["check"] == false){
        $res['message1'] = "Entre a check false";
        $res['error'] = true;
       
    }else{
        $res['error'] = false;
    }

}





header('Content-type: application/json');
    echo json_encode($res);
    die();

?>