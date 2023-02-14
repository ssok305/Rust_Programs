<?php

if (isset($_POST["submit"])) {
    
    $name = $_POST["fname"];
    $email = $_POST["email"];
    $username = $_POST["uname"];
    $psw = $_POST["psw"];
    $pswrep = $_POST["pswrep"];

    require_once 'dbh-inc.php';
    require_once 'functions-inc.php';

    if (emptyInputSignup($name, $email, $username, $psw, $pswrep) !== false) {
        // header("location: ../?error=emptyinput");
        echo '<p>Fill all fields!</p>';
        exit();
    }
    if (invalidUname($username) !== false) {
        // header("location: ../?error=invaliduname");
        echo '<p>Invalid username!</p>';
        exit();
    }
    if (invalidEmail($email) !== false) {
        // header("location: ../?error=invalidemail");
        echo '<p>Invalid email!</p>';
        exit();
    }
    if (pwdMatch($psw, $pswrep) !== false) {
        // header("location: ../?error=nopassmatch");
        echo '<p>Passwords don\'t match!</p>';
        exit();
    }
    if (unameExists($conn, $username, $email) !== false) {
        // header("location: ../?error=unametaken");
        echo '<p>Username already exists!</p>';
        exit();
    }

    createUser($conn, $name, $email, $username, $psw);
    echo '';
    exit();

} else {
    // header("location: ../");
    echo 'There was an error!';
    exit();
}