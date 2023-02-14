<?php

    session_start();
    require_once 'dbh-inc.php';
    require 'functions-inc.php';

    if (!isset($_POST["submit"])) {
        header("location: ../orders?error=nocancel");
        exit();
    }

    // UPDATE STATUS TO CANCELLED
    $order_id = $_POST["order_id"];

    // $sql = "UPDATE products SET quantity = quantity + ? WHERE product_id = ?;";
    $sql = "UPDATE orders SET order_status = 'CANCELLED' WHERE order_id = ?;";
    $stmt = mysqli_stmt_init($conn);
    if (!mysqli_stmt_prepare($stmt, $sql)) {
        header("location: ../orders?error=stmtfailed");
        exit();
    }

    mysqli_stmt_bind_param($stmt, "s", $order_id);
    mysqli_stmt_execute($stmt);
    mysqli_stmt_close($stmt);

    // FIND QUANTITY AND PRODUCT ID OF ORDER
    $sql = "SELECT * FROM order_items WHERE order_id = ?;";
    $stmt = mysqli_stmt_init($conn);
    if (!mysqli_stmt_prepare($stmt, $sql)) {
        header("location: ../orders?error=stmtfailed");
        exit();
    }

    mysqli_stmt_bind_param($stmt, "s", $order_id);
    mysqli_stmt_execute($stmt);

    $resultdata = mysqli_stmt_get_result($stmt);
    $prod_array = array();

    while ($row = mysqli_fetch_assoc($resultdata)) {
        $prod_array[] = array($row['product_id'], $row['quantity']);
    }
    mysqli_stmt_close($stmt);


    // PUT QUANTITY BACK INTO INVENTORY
    for ($i = 0; $i < count($prod_array); $i++) {
        $sql = "UPDATE products SET quantity = quantity + ? WHERE product_id = ?;";
        $stmt = mysqli_stmt_init($conn);
        if (!mysqli_stmt_prepare($stmt, $sql)) {
            header("location: ../orders?error=stmtfailed");
            exit();
        }

        mysqli_stmt_bind_param($stmt, "ii", $prod_array[$i][1], $prod_array[$i][0]);
        mysqli_stmt_execute($stmt);
        mysqli_stmt_close($stmt);
    }

    // Variables for email
    $sql = "SELECT * FROM orders WHERE order_id = ?;";

    $stmt = mysqli_stmt_init($conn);

    if (!mysqli_stmt_prepare($stmt, $sql)) {
        header("location: ../orders.php?error=stmtfailed");
        exit();
    }

    mysqli_stmt_bind_param($stmt, "s", $order_id);
    mysqli_stmt_execute($stmt);

    $resultdata = mysqli_stmt_get_result($stmt);

    $row = mysqli_fetch_assoc($resultdata);

    $order_date = $row['order_date'];
    // $order_time = $row['order_time'];
    // $order_quantity = $row['order_quantity'];
    $order_total = $row['order_total'];
    // $order_status = $row['order_status'];

    mysqli_stmt_close($stmt);

    ob_start();
    include_once './cancel-order-email-inc.php';
    $message = ob_get_clean();
    $to = $_SESSION['userEmail'];
    $from = "noreply@spilledcoffee.net";
    sendCancelConfirmation($to, $from, $message);

    // header("location: ../orders?message=cancelsuccessful");
    exit();

