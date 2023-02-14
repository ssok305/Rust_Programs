<?php
    include_once 'header.php';
    include_once 'modal.php';
?>

<div class="row max-width">
    <?php
        if (!isset($_SESSION['useruid'])) {
            header('Location: ./?error=illegalaccess');
        }
        include_once 'includes/dbh-inc.php';
        include_once 'includes/functions-inc.php';
        $id = $_SESSION['userid'];
        // echo $id;

        if (!ordersExist($conn, $id)) {
            echo '<div class="user-message">';
                echo '<p>Your have no past orders!<br>Check out our <a href="./products">products</a>.</p>';
            echo '</div>';
        } else {
            echo '<h2>YOUR ORDER HISTORY</h2>';        
            getUserOrders($conn, $id);
        }
    ?>
</div>
  
<?php
    include_once 'footer.php';
?>