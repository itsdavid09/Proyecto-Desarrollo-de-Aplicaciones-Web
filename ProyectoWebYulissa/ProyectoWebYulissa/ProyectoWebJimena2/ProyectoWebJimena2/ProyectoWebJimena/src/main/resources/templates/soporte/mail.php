<?php

$errors = [];

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $nombre = isset($_POST['nombre']) ? strip_tags(trim($_POST['nombre'])) : '';
    $email = isset($_POST['email']) ? trim($_POST['email']) : '';
    $mensaje = isset($_POST['mensaje']) ? strip_tags(trim($_POST['mensaje'])) : '';

    if (empty($errors)) {
        $recipient = "gestiondetablas@gmail.com";

        $headers = "From: $nombre <$email>";

        if (mail($recipient, $mensaje, $headers)) {
            echo "Se envio un correo !";
        } else {
            echo "Hubo un error al mandar tu correo. Por favor intentalo nuevamente mas tarde.";
        }
    } else {
        echo "Se encontraron los siguientes errores:<br>";
        foreach ($errors as $error) {
            echo "- $error<br>";
        }
    }
} else {
    header("HTTP/1.1 403 Forbidden");
    echo "Permiso denegado.";
}
?>