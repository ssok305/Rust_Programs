<?php

function emptyInputSignup($name, $email, $username, $psw, $pswrep) {
    //$result;
    if (empty($name) || empty($email) || empty($username) || empty($psw) || empty($pswrep)) {
        $result = true;
    } else {
        $result = false;
    }
    return $result;
}

function invalidUname($username) {
    //$result;
    if (!preg_match("/^[a-z\d_]{2,20}$/i", $username)) { //check this
        $result = true;
    } else {
        $result = false;
    }
    return $result;
}

function invalidEmail($email) {
    //$result;
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) { 
        $result = true;
    } else {
        $result = false;
    }
    return $result;
}

function pwdMatch($psw, $pswrep) {
    //$result;
    if ($psw !== $pswrep) { 
        $result = true;
    } else {
        $result = false;
    }
    return $result;
}

function unameExists($conn, $username, $email) {
    $sql = "SELECT * FROM users WHERE usersUid = ?
        OR usersEmail = ?;";
    $stmt = mysqli_stmt_init($conn);
    if (!mysqli_stmt_prepare($stmt, $sql)) {
        // header("location: ../?error=stmtfailed");
        echo '<p>There was a problem with the site!<br>Try again later.</p>';
        exit();
    }

    mysqli_stmt_bind_param($stmt, "ss", $username, $email);
    mysqli_stmt_execute($stmt);

    $resultdata = mysqli_stmt_get_result($stmt);

    if ($row = mysqli_fetch_assoc($resultdata)) {
        return $row;
    } else {
        $result = false;
        return $result;
    }

    mysqli_stmt_close($stmt);
}

function createUser($conn, $name, $email, $username, $psw) {
    $sql = "INSERT INTO users (usersName, usersEmail, usersUid, usersPwd)
        VALUES (?, ?, ?, ?);";
    $stmt = mysqli_stmt_init($conn);
    if (!mysqli_stmt_prepare($stmt, $sql)) {
        header("location: ../?error=stmtfailed");
        exit();
    }

    $hashedPsw = password_hash($psw, PASSWORD_DEFAULT);

    mysqli_stmt_bind_param($stmt, "ssss", $username, $email, $username, $hashedPsw);
    mysqli_stmt_execute($stmt);
    mysqli_stmt_close($stmt);
    // header("location: ../?error=none");
    exit();

}

function emptyInputLogin($username, $psw) {
    //$result;
    if (empty($username) || empty($psw)) {
        $result = true;
    } else {
        $result = false;
    }
    return $result;
}

function loginUser($conn, $username, $psw) {
    $uidExists = unameExists($conn, $username, $username);

    if ($uidExists === false) {
        header("location: ../?error=wronglogin");
        exit();
    }

    $pswHashed = $uidExists["usersPwd"];
    $checkPwd = password_verify($psw, $pswHashed);

    if ($checkPwd === false) {
        header("location: ../?error=wronglogin");
        exit();
    } else if ($checkPwd === true) {
        session_start();
        $_SESSION["userid"] = $uidExists["usersId"];
        $_SESSION["useruid"] = $uidExists["usersUid"];
        $_SESSION["userEmail"] = $uidExists["usersEmail"];
        $_SESSION["userName"] = $uidExists["usersName"];
        $_SESSION["cart"] = array();
        header("location: ../products");
        exit();
    }
}

function loginValidUser($conn, $uidExists) {
    session_start();
    $_SESSION["userid"] = $uidExists["usersId"];
    $_SESSION["useruid"] = $uidExists["usersUid"];
    $_SESSION["userEmail"] = $uidExists["usersEmail"];
    $_SESSION["userName"] = $uidExists["usersName"];
    $_SESSION["cart"] = array();
}

function ordersExist($conn, $usersId) {
    $sql = "SELECT * FROM orders WHERE usersId = ?";

    $stmt = mysqli_stmt_init($conn);
    if (!mysqli_stmt_prepare($stmt, $sql)) {
        header("location: ./orders?error=stmtfailed");
        exit();
    }

    mysqli_stmt_bind_param($stmt, "s", $usersId);
    mysqli_stmt_execute($stmt);

    $resultdata = mysqli_stmt_get_result($stmt);

    if ($row = mysqli_fetch_assoc($resultdata)) {
        $result = true;
        return $result;
    } else {
        $result = false;
        return $result;
    }

    mysqli_stmt_close($stmt);
}

function getUserOrders ($conn, $id) {

    $sql = "SELECT * FROM orders WHERE usersId = ? ORDER BY order_date DESC, order_time DESC;";

    $stmt = mysqli_stmt_init($conn);

    if (!mysqli_stmt_prepare($stmt, $sql)) {
        header("location: ../orders.php?error=stmtfailed");
        exit();
    }

    mysqli_stmt_bind_param($stmt, "s", $id);
    mysqli_stmt_execute($stmt);

    $resultdata = mysqli_stmt_get_result($stmt);

    while ($row = mysqli_fetch_assoc($resultdata)) {
        $order_id = $row['order_id'];
        $order_date = $row['order_date'];
        $order_time = $row['order_time'];
        $order_quantity = $row['order_quantity'];
        $order_total = $row['order_total'];
        $order_status = $row['order_status'];
        include 'order-table-inc.php';
    }
    mysqli_stmt_close($stmt);
}

function getErrorMessage ($error) {
    if ($error === 'wronglogin') {
        $message = '<p>Your login information was incorrect or does not exist! Please try again.</p>';
    } else if ($error === 'emptyinput') {
        $message = '<p>Please fill in all fields!</p>';
    } else if ($error === 'invaliduname') {
        $message = '<p>Please enter valid username!</p>';
    } else if ($error === 'nopassmatch') {
        $message = '<p>Passwords do not match! Make sure you type the same password.</p>';
    } else if ($error === 'unametaken') {
        $message = '<p>That username is taken! Please pick a new username.</p>';
    } else if ($error === 'stmtfailed') {
        $message = '<p>Something went wrong! Try again or contact us.</p>';
    } else if ($error === 'none') {
        $message = '<p>Successfully signed up!</p>';
    } else if ($error === 'illegalaccess') {
        $message = '<p>You must be logged in to access that page!</p>';
    } else if ($error === 'illegalpage') {
        $message = '<p>You cannot access that!</p>';
    } else if ($error === 'selectquantity') {
        $message = '<p>You must choose a quantity to add an item to your cart!</p>';
    }
    echo $message;
}

function getUserMessage ($message) {
    if ($message === 'removedfromcart') {
        $userMessage = '<p>Your login information was incorrect or does not exist! Please try again.</p>';
    } 
    echo $userMessage;
}

function getProductByID ($conn, $id) {
    $sql = "SELECT * FROM products WHERE product_id = ?;";

    $stmt = mysqli_stmt_init($conn);
    if (!mysqli_stmt_prepare($stmt, $sql)) {
        header("location: ./cart?error=stmtfailed");
        exit();
    }

    mysqli_stmt_bind_param($stmt, "i", $id);
    mysqli_stmt_execute($stmt);

    $resultdata = mysqli_stmt_get_result($stmt);

    $row = mysqli_fetch_assoc($resultdata);

    $price = $row["price"];
    $product_title = $row["product_title"];

    return array(
        0 => $product_title,
        1 => $price
    );
}

function sendConfirmation ($emailTo, $emailFrom, $message) {
    $to = $emailTo;
    $subject = "Thank you for your purchase!";
    $headers[] = "MIME-Version: 1.0";
    $headers[] = "Content-type: text/html; charset=iso-8859-1";
    $headers[] = 'From: <'.$emailFrom.'>';

    mail($to,$subject,$message,implode("\r\n", $headers));
}

function sendCancelConfirmation ($emailTo, $emailFrom, $message) {
    $to = $emailTo;
    $subject = "Your order had been successfully cancelled!";
    $headers[] = "MIME-Version: 1.0";
    $headers[] = "Content-type: text/html; charset=iso-8859-1";
    $headers[] = 'From: <'.$emailFrom.'>';

    mail($to,$subject,$message,implode("\r\n", $headers));
}