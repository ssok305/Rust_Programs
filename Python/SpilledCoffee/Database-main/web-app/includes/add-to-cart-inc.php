<?php

    session_start();

    require_once 'dbh-inc.php';

    if (!isset($_GET["product_id"]) || !isset($_GET["quantity"])) {
        header("location: ../products?error=badproductinfo");
        exit();
    }

    if ($_GET["quantity"] == 0 || $_GET["quantity"] == '') {
        header("location: ../products?error=selectquantity");
        exit();
    }

    $product = $_GET['product_id'];
    $quant = $_GET['quantity'];

    $sql = "UPDATE products SET quantity = quantity - ? WHERE product_id = ?;";
    $stmt = mysqli_stmt_init($conn);
    if (!mysqli_stmt_prepare($stmt, $sql)) {
        header("location: ../products?error=stmtfailed");
        exit();
    }

    mysqli_stmt_bind_param($stmt, "ii", $quant, $product);
    mysqli_stmt_execute($stmt);
    mysqli_stmt_close($stmt);

    $newItem = array();
    array_push($newItem, $product, $quant);

    if (!isset($_SESSION['cart'])) {
        $_SESSION['cart'] = array();
    }

    array_push($_SESSION['cart'], $newItem);

    // UPDATE table // query to update quantity
    // SET field = field - 1
    // WHERE id = $number
    // and field > 0

    header("location: ../products?addedtosessioncart");
    exit();
    