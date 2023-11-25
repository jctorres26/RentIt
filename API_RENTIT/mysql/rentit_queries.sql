CREATE DATABASE rentit;
use rentit;

select * from users;
select * from rentas;

UPDATE users SET username = "chunk", password = "12345678" WHERE userId = 15;

INSERT INTO rentas(titulo, calificacion, precio, numCuartos, numBanios, ciudad, colonia, calle, numero, img, isActive, userId) 
VALUES("asda", TRUNCATE(5,2), TRUNCATE(4500.2323,2), 2, 1, "gpe" , "rincon de la sierra", "belgica", 122, "img", 1, 15);

SELECT titulo, calificacion, precio, numCuartos, numBanios, ciudad, colonia, calle, numero, img, userId FROM rentas WHERE isActive = 1;

SELECT rentaId, titulo, calificacion, precio, numCuartos, numBanios, ciudad, colonia, calle, numero, img, userId FROM rentas WHERE rentaId = 6;

SELECT rentas.rentaId, rentas.titulo, rentas.calificacion, rentas.precio, rentas.numCuartos, rentas.numBanios, rentas.ciudad, rentas.colonia, rentas.calle, rentas.numero, rentas.img,
users.name, users.lastname FROM rentas
INNER JOIN users ON rentas.userId = users.userId AND rentas.rentaId = 5;

UPDATE rentas SET titulo = "", calificacion = 5, precio = 3000, numCuartos = 2, numBanios = 1, ciudad = "", colonia ="", calle="", numero=123, isActive=1 
WHERE rentaId = 6;