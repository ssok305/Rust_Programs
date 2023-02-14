<?php
    if (!$gridOK) {
        header('Location: ./?error=illegalaccess');
        exit();
    }
?>

<div class="row">
    <?php
        if (isset($_GET['addedtosessioncart'])) {
            echo '<div class="user-message">';
                echo '<div class="close-user-message">&times;</div>';
                echo '<p>Item added to you cart!<br><a class="btn btn-full" style="font-size: 80%;" href="cart">VIEW CART</a></p>';
            echo '</div>';
        } else if (isset($_GET['error'])) { 
            echo '<div class="user-message max-width">';
                echo '<div class="close-user-message">&times;</div>';
                $error = $_GET['error'];
                getErrorMessage($error);
            echo '</div>';
        }
    ?>
    <div class="grid-container max-width">
        <?php
            require_once 'includes/dbh-inc.php';

            $sql = "SELECT * FROM products;";

            $resultdata = mysqli_query($conn, $sql);

            while ($row = mysqli_fetch_assoc($resultdata)) {
                $product_title = $row["product_title"];
                $product_desc = $row["product_description"];
                $quantity = $row["quantity"];
                $product_id = $row["product_id"];
                $price = $row["price"];

                include 'products-grid-item.php';
            }
        ?>
        
    </div>
</div>