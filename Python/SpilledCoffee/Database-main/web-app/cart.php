<?php
    include_once 'header.php'
?>

<div class="row max-width">
    <div class="cart-container">
        <?php
            include_once 'includes/functions-inc.php';
            if (!isset($_SESSION['useruid'])) {
                header('Location: ./?error=illegalaccess');
            }
            if (isset($_GET['error'])) {
                echo '<div class="user-message">';
                echo '<div class="close-user-message">&times;</div>';
                $error = $_GET['error'];
                getErrorMessage($error);
                echo '</div>';
            }
            if (isset($_GET['removedfromcart'])) {
                echo '<div class="user-message">';
                echo '<div class="close-user-message">&times;</div>';
                    echo '<p>Item has been removed from your cart!</p>';
                echo '</div>';
            }
            if (empty($_SESSION['cart'])) {
                echo '<div class="user-message">';
                    echo '<p>Your cart is currently empty!<br>Check out our <a href="./products">products</a>.</p>';
                echo '</div>';
            } else {
                $gridOK = true;
                include_once 'cart-grid.php';
            }
        ?>
    </div>
</div>

<?php
    include_once 'footer.php';
?>