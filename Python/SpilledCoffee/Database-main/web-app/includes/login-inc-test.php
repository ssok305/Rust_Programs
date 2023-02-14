<?php

if (isset($_POST['submit'])) {

    require_once 'dbh-inc.php';
    require_once 'functions-inc.php';

    $username = $_POST["uid"];
    $pwd = $_POST["pass"];

    $errorEmpty = false;
    $errorUsername = false;

    if (empty($username) || empty($pwd)) {
        echo '<p>Fill in all fields!</p>';
        $errorEmpty = true;
    } elseif (!unameExists($conn, $username, $username)) {
        echo '<p>Username does not exist!</p>';
        $errorUsername = true;
    } else {
        $uidExists = unameExists($conn, $username, $username);
        $pswHashed = $uidExists["usersPwd"];
        $checkPwd = password_verify($pwd, $pswHashed);
        if ($checkPwd === false) {
            echo '<p>Incorrect password!<br>Try again.</p>';
        } else if ($checkPwd === true) {
            loginValidUser($conn, $uidExists);
            echo '';
        }
    }

} else {
    echo '<p>There was an error!</p>';
    exit();
}