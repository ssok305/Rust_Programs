<div class="grid-item">
    <img src="img/coffee-171653_640.jpg">
    <h4 id="product-title"><?php echo $product_title ?></h4>
    <p id="product-description"><?php echo $product_desc ?></p>
    <p id="grid-price" class="grid-price"><?php echo '$'.$price ?></p>
    <div class="sub-grid-container">
        <div>
            <?php
                if ($quantity > 10) {
                    echo '<p class="stock-status">IN STOCK</p>';
                } else if ($quantity < 10 && $quantity > 0) {
                    echo '<p class="stock-status">IN STOCK</p>';
                    echo '<p class="stock-message">Only '. $quantity . ' left!</p>';
                } else {
                    echo '<p class="stock-status">OUT OF<br>STOCK</p>';
                }
            ?>
        </div>
        <div class="align-right">
            <?php
                if ($quantity > 10) {
                    echo '<p class="quant-label">QTY:</p>';
                    echo '<select id="'.$product_id.'quantity" name="quantity">';
                    echo '<option value="0"></option>';
                    for ($i = 1; $i <= 10; $i++) {
                        echo '<option value="'.$i.'">'.$i.'</option>';
                    }
                } else if ($quantity < 10 && $quantity > 0) {
                    echo '<p class="quant-label">QTY:</p>';
                    echo '<select id="'.$product_id.'quantity" name="quantity">';
                    echo '<option value="0"></option>';
                    for ($i = 1; $i <= $quantity; $i++) {
                        echo '<option value="'.$i.'">'.$i.'</option>';
                    }
                } else {
                    echo '';
                }
            ?>
            </select>
        </div>
        <button class="btn btn-full-bw grid-item-button" type="submit" onclick="redirect(<?php echo $product_id ?>);">ADD TO CART</button>
        <script src="js/redirect.js"></script>
    </div>
</div>