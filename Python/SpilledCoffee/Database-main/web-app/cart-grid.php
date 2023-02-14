<?php
    if (!$gridOK) {
        header('Location: ./?error=illegalpage');
        exit();
    }
?>

<h2>YOUR SHOPPING CART</h2>
    <div class="cart-grid-full">
        <div class="col-1 header">ITEM #</div>
        <div class="col-2 header">PRODUCT</div>
        <div class="col-3 header">QTY</div>
        <div class="col-4 header">PRICE</div>
        <div class="full-span-cart"></div>
        <?php
            $cart = $_SESSION['cart'];
            $totalprice = 0;
            $totalquantity = 0;
            for ($i = 0; $i < count($cart); $i++) {
                    
                require_once 'includes/dbh-inc.php';

                $sql = "SELECT * FROM products WHERE product_id = ?;";

                $stmt = mysqli_stmt_init($conn);
                if (!mysqli_stmt_prepare($stmt, $sql)) {
                    header("location: ./cart?error=stmtfailed");
                    exit();
                }

                mysqli_stmt_bind_param($stmt, "i", $cart[$i][0]);
                mysqli_stmt_execute($stmt);

                $resultdata = mysqli_stmt_get_result($stmt);

                $row = mysqli_fetch_assoc($resultdata);

                $product_title = $row["product_title"];
                $product_desc = $row["product_description"];
                $price = $row["price"];

                echo '<div class="col-1">';
                echo $i+1;
                echo '</div><div class="col-2">';
                echo $product_title;
                echo '</div><div class="col-3">';
                echo $cart[$i][1];
                echo '</div><div class="col-4">';
                echo number_format($price*$cart[$i][1], 2);
                echo '</div><div class="col-5">';
                // echo '<button class="btn btn-full-bw btn-table">';
                // echo '&nbsp&nbspUPDATE&nbsp&nbsp</button>';
                echo '<button class="btn btn-full-bw btn-cart-grid" type="submit"';
                    echo 'onclick="delRedirect('.$i.')">';
                echo '<script src="js/delRedirect.js"></script>';
                echo '&nbsp&nbspDELETE&nbsp&nbsp</button>';
                echo '</div>';

                mysqli_stmt_close($stmt);
                $totalprice += $price*$cart[$i][1];
                $totalquantity += $cart[$i][1];
            }
        ?>
        <div class="full-span-cart"></div>
        <div></div>
        <div class="col-3"><?php echo $totalquantity; ?></div>
        <div class="col-4"><?php echo number_format($totalprice, 2); ?></div>
        <div class="full-span-btn center-cell">
            <a href="checkout"><button class="btn btn-full-bw btn-cart-grid"><h2>&nbsp&nbspCHECKOUT&nbsp&nbsp</h3></button>
        </div>
    </div>