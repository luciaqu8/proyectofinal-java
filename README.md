# proyectofinal-java
Proyecto Final de clases JAVA 2025

GET /productos:
Retorna la lista completa de todos los productos registrados.

GET /productos/{id}:
Busca un producto por su ID y devuelve sus datos si existe.

POST /productos/new:
Permite agregar un nuevo producto. Se debe enviar un JSON con los campos obligatorios como nombre, descripción, precio, stock, categoría, y link de imagen.

PUT /productos/update/{id}:
Actualiza los datos de un producto existente según el ID. Se debe enviar un JSON con los nuevos datos.

PUT /productos/{id}/descontar-stock?cantidad=n:
Descuenta una cantidad determinada del stock de un producto. Si la cantidad es mayor al stock disponible, devuelve un error.

DELETE /productos/delete/{id}:
Solicita confirmación antes de eliminar un producto. Devuelve un mensaje indicando que falta confirmación.

DELETE /productos/delete/{id}?confirm=true:
Elimina un producto definitivamente si el parámetro confirm=true está presente en la URL.

POST /usuarios/new:
Crea un nuevo usuario. Se debe enviar un JSON con los datos necesarios (nombre de usuario, email, etc.). Devuelve un mensaje con el nombre y el ID del usuario creado.

POST /usuarios/carrito/agregar:
Agrega un producto al carrito de un usuario. Requiere los parámetros userId, productoId y cantidad. Si no hay suficiente stock o el usuario no existe, devuelve un error.

GET /usuarios/carrito/ver:
Devuelve el contenido actual del carrito del usuario. Requiere el parámetro userId.

DELETE /usuarios/carrito/vaciar:
Vacía el carrito del usuario. Requiere el parámetro userId.

POST /usuarios/carrito/confirmar:
Confirma el contenido del carrito y genera un pedido. Requiere userId. Si el carrito está vacío, devuelve un error.

GET /usuarios/pedidos:
Muestra todos los pedidos realizados por un usuario. Requiere userId. Si el usuario no existe, devuelve error 404.

El frontend utilizado es el siguiente template:
https://github.com/emilianospinoso/proyecto-final-ecommerce