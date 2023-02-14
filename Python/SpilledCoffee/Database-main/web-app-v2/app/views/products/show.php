<?php require APPROOT . '/views/inc/header.php'; ?>
    <div class="container m-3">
        <a href="<?php echo URLROOT; ?>/products" class="btn btn-light border-secondary fade-1s remove-fade-slide-top">
            <i class="fa fa-backward m-2"></i>
            Back
        </a>
    </div>
    <hr class="fade-1s remove-fade-x">
    <div class="row my-3 mx-auto py-5 rounded-3 shadow bg-white fade-1s remove-fade-slide-bottom" style="max-width: 1100px;">
        <div class="col">
            <img src="<?php echo URLROOT; ?>/public/img/coffee_can.png" class="card-img-top mx-auto d-block w-50" alt="Picture of <?php echo $data['product']->product_title ?>">
        </div>
        <div class="col-8">
            <h1><?php echo $data['product']->product_title; ?></h1>
            <p><?php echo $data['product']->product_description; ?></p>
            <div class="row">
                <div class="col-auto">
                    <?php if ($data['product']->quantity > 0) : ?>
                        <p class="bg-success p-2 text-white rounded-3">IN STOCK</p>
                    <?php else : ?>
                        <p class="bg-danger p-2 text-white rounded-3">OUT OF STOCK</p>
                    <?php endif; ?>
                </div>
            </div>
            <form class="pull-left w-100" action="<?php echo URLROOT; ?>/products/addToCart/<?php echo $data['product']->product_id; ?>" method="post">
                <?php $quantity = $data['product']->quantity; ?>
                <p class="m-0">QTY:</p>
                    <?php if ($quantity > 10) : ?>
                        <?php $out = false; ?>
                        <select id="quantity" name="quantity" class="rounded-3 <?php echo (!empty($data['quantity_err'])) ? 'is-invalid' : '';?>">
                        <option value="0"></option>
                        <?php for ($i = 1; $i <= 10; $i++) : ?>
                            <option value="<?php echo $i; ?>"><?php echo $i; ?></option>
                        <?php endfor; ?>
                        <span class="invalid-feedback"><?php echo $data['quantity_err']; ?></span>
                    <?php elseif ($quantity < 10 && $quantity > 0) : ?>
                        <?php $out = false; ?>
                        <select id="quantity" name="quantity" class="<?php echo (!empty($data['quantity_err'])) ? 'is-invalid' : '';?>">
                        <option value="0"></option>
                        <?php for ($i = 1; $i <= $quantity; $i++) : ?>
                            <option value="<?php echo $i; ?>"><?php echo $i; ?></option>
                        <?php endfor; ?>
                        <span class="invalid-feedback"><?php echo $data['quantity_err']; ?></span>
                    <?php else : ?>
                        <?php $out = true; ?>
                        <select id="quantity" name="quantity" disabled>
                    <?php endif; ?>
                </select>
                <p class="fw-bolder fs-4">$<?php echo $data['product']->sale_price; ?></p>
                <hr>
                <?php if (!$out) : ?>
                    <input type="submit" value="ADD TO CART" class="btn btn-success">
                <?php else : ?>
                    <input type="submit" value="ADD TO CART" class="btn btn-success" disabled>
                <?php endif; ?>
            </form>
        </div>
    </div>
<?php require APPROOT . '/views/inc/footer.php'; ?>