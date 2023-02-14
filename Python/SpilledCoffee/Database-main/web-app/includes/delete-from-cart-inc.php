<?php

    session_start();

    require_once 'dbh-inc.php';

    if (!isset($_GET["cp"])) {
        header("location: ../products?error=noremove");
        exit();
    }

    $cart_position = $_GET['cp'];
    $product = $_SESSION['cart'][$cart_position][0];
    $quantity = $_SESSION['cart'][$cart_position][1];

    $sql = "UPDATE products SET quantity = quantity + ? WHERE product_id = ?;";
    $stmt = mysqli_stmt_init($conn);
    if (!mysqli_stmt_prepare($stmt, $sql)) {
        header("location: ../products?error=stmtfailed");
        exit();
    }

    mysqli_stmt_bind_param($stmt, "ii", $quantity, $product);
    mysqli_stmt_execute($stmt);
    mysqli_stmt_close($stmt);

    \array_splice($_SESSION['cart'], $cart_position, 1);

    header("location: ../cart?removedfromcart");
    exit();
    