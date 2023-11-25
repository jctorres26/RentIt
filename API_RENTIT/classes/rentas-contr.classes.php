<?php

include('../classes/rentas.classes.php');

class RentasContr extends Rentas{

private $rentaId;
private $titulo;
private $calificacion;
private $precio; 
private $numCuartos;
private $numBanios;
private $ciudad;
private $colonia;
private $calle;
private $numero;
private $img;
private $isActive;
private $userId;


public function __construct(){ }

public static function withRentaData($titulo, $calificacion, $precio, $numCuartos, $numBanios, $ciudad, $colonia, $calle, $numero, $img, $isActive, $userId){

    $instance =  new self();
    $instance->fillWithRentaData($titulo, $calificacion, $precio, $numCuartos, $numBanios, $ciudad, $colonia, $calle, $numero, $img, $isActive, $userId);

    return $instance;

}

protected function fillWithRentaData($titulo, $calificacion, $precio, $numCuartos, $numBanios, $ciudad, $colonia, $calle, $numero, $img, $isActive, $userId){

   
    $this->titulo = $titulo;
    $this->calificacion = $calificacion;
    $this->precio = $precio;
    $this->numCuartos = $numCuartos;
    $this->numBanios = $numBanios;
    $this->ciudad = $ciudad;
    $this->colonia = $colonia;
    $this->calle = $calle;
    $this->numero = $numero;
    $this->img = $img;
    $this->isActive = $isActive;
    $this->userId = $userId;


}

public function registerRenta(){


    $this->insertRenta($this->titulo, $this->calificacion, $this->precio, $this->numCuartos, $this->numBanios, $this->ciudad, 
                        $this->colonia, $this->calle, $this->numero, $this->img, $this->isActive, $this->userId);

  
}

/////////////////////////////////


public function selectActivePosts(){

    $posts;

    $posts = $this->getActivePosts();

    return $posts;

}



///////////////////////////////////////////////

public static function withUserId($userId){

    $instance =  new self();
    $instance->fillWithUserId($userId);

    return $instance;

}

protected function fillWithUserId($userId){

   
    $this->userId = $userId;
   


}

public function selectUserPosts(){

    $posts;
    $posts = $this->getUserPosts($this->userId);

  return $posts;
}

///////////////////////////////////////

public static function withRentaId($rentaId){

    $instance =  new self();
    $instance->fillWithRentaId($rentaId);

    return $instance;

}

protected function fillWithRentaId($rentaId){

   
    $this->rentaId = $rentaId;
   


}

public function selectDetailsByRentaId(){

    $posts;
    $posts = $this->getDetailsByRentaId($this->rentaId);

  return $posts;
}

public function selectByRentaId(){

    $posts;
    $posts = $this->getByRentaId($this->rentaId);

  return $posts;
}

/////////////////////////////////////////////////

public static function withRentaDataUpdate($rentaId, $titulo, $calificacion, $precio, $numCuartos, $numBanios, $ciudad, $colonia, $calle, $numero, $isActive){

    $instance =  new self();
    $instance->fillWithRentaDataUpdate($rentaId, $titulo, $calificacion, $precio, $numCuartos, $numBanios, $ciudad, $colonia, $calle, $numero, $isActive);

    return $instance;

}

protected function fillWithRentaDataUpdate($rentaId, $titulo, $calificacion, $precio, $numCuartos, $numBanios, $ciudad, $colonia, $calle, $numero, $isActive){

    $this->rentaId = $rentaId;
    $this->titulo = $titulo;
    $this->calificacion = $calificacion;
    $this->precio = $precio;
    $this->numCuartos = $numCuartos;
    $this->numBanios = $numBanios;
    $this->ciudad = $ciudad;
    $this->colonia = $colonia;
    $this->calle = $calle;
    $this->numero = $numero;
    $this->isActive = $isActive;
   
}

public function updateRenta(){

    
    $this->updateRentaData($this->rentaId, $this->titulo, $this->calificacion, $this->precio, $this->numCuartos, $this->numBanios, $this->ciudad, 
    $this->colonia, $this->calle, $this->numero, $this->isActive);

    
}


}
?>