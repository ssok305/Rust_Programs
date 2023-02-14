<?php
    include_once 'header-ns.php'
?>

<?php
    include_once 'includes/functions-inc.php';
    if (isset($_GET['error'])) {
        echo '<div class="user-message">';
            echo '<div class="close-user-message">&times;</div>';
            $error = $_GET['error'];
            getErrorMessage($error);
        echo '</div>';
    }
?>

<?php
    // include_once 'login.php'
?>

<?php
    // include_once 'signup.php';
    include_once 'modal.php';
?>

<div class="hero-text-box">
    <h1>Spilled Coffee</h1>
    <h3>Web App Test</h3>
    <?php
        if (isset($_SESSION["useruid"])) {
            echo '<a class="btn btn-full" href="products">VIEW PRODUCTS</a>';
            echo '<a class="btn btn-full" href="cart">VIEW CART</a>';
            echo '<a class="btn btn-full" href="orders">VIEW PAST ORDERS</a>';
        } else {
            $login = "login";
            echo '<button onclick="showModal(1)" class="btn btn-full">LOG IN</button>';
            echo '<button onclick="showModal(2)" class="btn btn-full">SIGN UP</button>';
        }
    ?>
</div>
  
<?php
    include_once 'footer.php'
?>