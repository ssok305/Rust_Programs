<?php require APPROOT . '/views/inc/header.php'; ?>
    <div class="row my-3 mx-auto" style="max-width: 1100px;">
        <div class="fade-1s remove-fade"><?php flash('cart_message'); ?></div>
        <div class="col-md-12 fade-1s remove-fade-slide-top">
            <h1>Your Cart</h1>
        </div>
        <hr class="fade-1s remove-fade-x">
    </div>
    <?php if(empty($_SESSION['cart']) && empty($_SESSION['guest_cart'])) : ?>
    <div class="container py-3 px-3 bg-light shadow rounded-3 text-secondary border border-2 text-center fs-3 fade-1s remove-fade-slide-bottom" style="max-width: 1000px;">
        <div class="container border border-1 rounded-3 py-4" style="max-width: 1000px;">
            <p class="d-inline">Your cart is currently empty!<br />Browse the </p>
            <a class="d-inline p-1 border border-1 btn btn-info btn-lg rounded-3" href="<?php echo URLROOT; ?>/products">PRODUCT CATALOG</a>
        </div>
    </div>
    <?php else : ?>
        <div class="container bg-light shadow rounded-3 text-secondary border border-2 fade-1s remove-fade-slide-bottom" style="max-width: 1000px;">
            <div class="container-fluid my-3 p-4 mx-auto border border-1 rounded-3 w-100">
                <div class="row border-bottom border-1 border-dark h4">
                    <div class="col-6 col-md-8">
                        <p>PRODUCT</p>
                    </div>
                    <div class="col-2 col-md-1 text-end">
                        <p>QTY</p>
                    </div>
                    <div class="col-2 col-md-2 text-end">
                        <p>PRICE</p>
                    </div>
                    <div class="col-1 col-md-1"></div>
                </div>
                <?php $total_price = 0; $total_quantity = 0; ?>
                <?php foreach($data as $key => $cartItem) : ?>
                    <div class="row fs-5 py-2">
                        <div class="col-6 col-md-8">
                            <p><?php echo $cartItem['product_title']; ?></p>
                        </div>
                        <div class="col-2 col-md-1 text-end">
                            <p><?php echo $cartItem['reserved']; ?></p>
                        </div>
                        <div class="col-2 col-md-2 text-end">
                            <?php $price = '$'.number_format($cartItem['sale_price']*$cartItem['reserved'], 2); ?>
                            <p><?php echo $price; ?></p>
                        </div>
                        <div class="col-2 col-md-1 text-end">
                            <a href="<?php echo URLROOT; ?>/users/removeCartItem/<?php echo $key; ?>" class="btn btn-danger btn-sm"><i class="fa fa-trash m-1"></i></a>
                        </div>
                    </div>
                    <?php $total_price += $cartItem['sale_price']*$cartItem['reserved']; $total_quantity += $cartItem['reserved']; ?>
                <?php endforeach; ?>
                <div class="row pt-3 border-top border-1 border-dark h5">
                    <div class="col-6 col-md-8 text-end">
                        <p>TOTAL:</p>
                    </div>
                    <div class="col-2 col-md-1 text-end">
                        <p><?php echo $total_quantity; ?></p>
                    </div>
                    <div class="col-2 col-md-2 text-end">
                        <p><?php echo '$'.number_format($total_price, 2); ?></p>
                    </div>
                    <div class="col-2 col-md-1"></div>
                </div>
                <div class="row">
                    <a href="<?php echo URLROOT; ?>/users/checkout" class="w-100 btn btn-primary btn-lg">Proceed to checkout</a>
                </div>
            </div>
        </div>
    <?php endif; ?>
<?php require APPROOT . '/views/inc/footer.php'; ?>