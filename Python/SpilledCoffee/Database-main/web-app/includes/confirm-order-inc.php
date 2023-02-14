<?php

session_start();

if (isset($_POST['submit'])) {
    $userId = $_SESSION['userid'];
    $order_id = uniqid($userId.'_');
    $order_date = date('Y-m-d');
    $order_time = date('h:i');
    $street = $_POST['street'];
    $city = $_POST['city'];
    $state = $_POST['state'];
    $zip = $_POST['zipcode'];
    $order_quantity = $_POST['total_quantity'];
    $order_total = $_POST['total_price'];

    $cart = $_SESSION['cart'];

    require_once 'dbh-inc.php';

    $sql = "INSERT INTO orders (order_id, order_date, order_time, usersId, order_quantity, order_total,
                                shipping_street, shipping_city, shipping_state, shipping_zipcode)
                                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    $stmt = mysqli_stmt_init($conn);
    if (!mysqli_stmt_prepare($stmt, $sql)) {
        header("location: ../checkout?error=stmtfailed");
        exit();
    }

    mysqli_stmt_bind_param($stmt, "sssiidssss", $order_id, $order_date, $order_time, $userId,
                                    $order_quantity, $order_total, $street, $city, $state, $zip);
    mysqli_stmt_execute($stmt);
    mysqli_stmt_close($stmt);

    $sql2 = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?);";

    $stmt2 = mysqli_stmt_init($conn);
    if (!mysqli_stmt_prepare($stmt2, $sql2)) {
        header("location: ../checkout?error=stmtfailed");
        exit();
    }

    for ($i = 0; $i < count($cart); $i++) {
        $prod = $cart[$i][0];
        $quant = $cart[$i][1];
        mysqli_stmt_bind_param($stmt2, "sii", $order_id, $prod, $quant);
        mysqli_stmt_execute($stmt2);
    }

    mysqli_stmt_close($stmt2);

    unset($_SESSION['cart']);
    
    require_once 'functions-inc.php';
    ob_start();
    include './confirm-order-email-inc.php';
    $message = ob_get_clean();
    $to = $_SESSION['userEmail'];
    $from = "noreply@spilledcoffee.net";
    sendConfirmation($to, $from, $message);

    header("location: ../orders?message=ordersuccess");
    exit();

} else {
    header("location: ../cart?error=orderfailed");
    exit();
}