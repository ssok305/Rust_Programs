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
        header("location: ../?error=emptyinput");
        exit();
    }
    if (invalidUname($username) !== false) {
        header("location: ../?error=invaliduname");
        exit();
    }
    if (invalidEmail($email) !== false) {
        header("location: ../?error=invalidemail");
        exit();
    }
    if (pwdMatch($psw, $pswrep) !== false) {
        header("location: ../?error=nopassmatch");
        exit();
    }
    if (unameExists($conn, $username, $email) !== false) {
        header("location: ../?error=unametaken");
        exit();
    }

    createUser($conn, $name, $email, $username, $psw);

} else {
    header("location: ../");
    exit();
}