<?php require APPROOT . '/views/inc/header.php'; ?>
    <div class="row mb-3 mx-auto" style="max-width:1100px;">
        <div class="fade-1s remove-fade"><?php flash('product_message'); ?></div>
        <div class="col-md-12 mt-3 fade-1s remove-fade-slide-top">
            <h1>Products</h1>
        </div>
        <hr class="fade-1s remove-fade-x">
    </div>
    <div class="container-xl">
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-2 my-0 justify-content-center bg-transparent">
            <?php foreach($data['products'] as $product) : ?>
                <div class="card mb-3 mx-1 my-3 bg-transparent border-0 fade-1s remove-fade-slide-bottom product-card"
                        id="product-card" style="min-width: 400px; max-width: 400px;">
                    <div class="row g-0 align-items-center">
                        <div class="col-md-12 d-flex align-items-center">
                            <img 
                                href="<?php echo URLROOT; ?>/products/show/<?php echo $product->product_id; ?>"
                                src="<?php echo URLROOT; ?>/public/img/coffee_can.png"
                                class="card-img-top mx-auto d-block img-fluid product-image"
                                alt="Picture of <?php echo $product->product_title; ?>"
                            >
                        </div>
                    </div>
                    <div class="row g-0 align-items-center">
                        <div class="col-md-12">
                            <div class="card-body h-100 text-center">
                                <h5 class="card-title h3 product-title fw-bold"><?php echo $product->product_title; ?></h5>
                                <p class="card-text"><?php echo $product->product_description; ?></p>
                                <p class="card-text fs-5 fw-bolder">$<?php echo $product->sale_price; ?></p>
                                <div class="row row-col-12 justify-content-center">
                                    <a href="<?php echo URLROOT; ?>/products/show/<?php echo $product->product_id; ?>"
                                        class="btn btn-dark btn-rounded w-75">View Product</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            <?php endforeach; ?>
        </div>
    </div>
<?php require APPROOT . '/views/inc/footer.php'; ?>