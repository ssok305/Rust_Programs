<?php
    session_start();
    if (!isset($_SESSION["useruid"]) || !isset($_SESSION["userid"])) {
        header('Location: ./?error=illegalaccess');
        exit();
    }
?>

<!DOCTYPE html>
<html>
    <head>
        <!-- <link rel="stylesheet" type="text/css" href="css/normalize.css"> -->
        <link rel="stylesheet" type="text/css" href="css/grid.css">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link rel="shortcut icon" type="image/png" href="img/favicon.png">
        <link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,300italic' rel="stylesheet" type='text/css'>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="js/jquery-3.6.0.js"></script>
        <!-- <script src="js/login-form.js"></script> -->
        <script src="js/script.js"></script>
        <title>Spilled Coffee</title>
    </head>
    <?php
        if (!$_SERVER['QUERY_STRING']) {
            echo '<body style="opacity: 0; transition: opacity 2s;" onload="document.body.style.opacity = 1">';
        } else {
            echo '<body>';
        }
    ?>
        <header>
            <nav>
                <div class="row">
                    <a href="./"><img src="img/coffee_cup_logo_small_invert.png" alt="spilled_coffee_logo" class="logo"></a>
                    <ul class="main-nav">
                        
                        <?php
                            if (isset($_SESSION["useruid"])) {
                                echo '<div class="dropdown"><li><a href="products">SHOP</a></li></div>';
                                echo '<div class="dropdown">';
                                    echo '<button class="dropbtn">'.$_SESSION["useruid"].'';
                                    echo '<i class="fa fa-caret-down" style="color: white;"></i>';
                                    echo '</button>';
                                        echo '<div class="dropdown-content">';
                                        echo '<a href="cart">View Cart</a>';
                                        echo '<a href="orders">View Orders</a>';
                                        echo '<a href="includes/logout-inc.php" class="">LOG OUT</a>';
                                    echo '</div>';
                                echo '</div>';
                                // echo '<li><button style="height:140px; width: 40px;"><a href="cart.php"><img src="img/cart_100.png"  class="cart-icon"></a></button></li>';       
                            } else {
                                echo '<li><a class="show-login-modal-link">Log in</button></li>';
                                echo '<li><a class="show-signup-modal-link">Sign up</button></li>';
                            }
                        ?>                     
                    </ul>
                </div>
            </nav>
        </header>
        <div class="wrapper">