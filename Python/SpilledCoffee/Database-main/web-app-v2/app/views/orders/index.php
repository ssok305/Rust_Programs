<?php require APPROOT . '/views/inc/header.php'; ?> 
    <div class="row my-3 mx-auto" style="max-width:1100px">
        <div class="fade-1s remove-fade"><?php flash('order_message'); ?></div>
        <div class="col-md-12 fade-1s remove-fade-slide-top">
            <h1>Order History</h1>
        </div>
        <hr class="fade-1s remove-fade-x">
    </div>
    <?php if(empty($data)) : ?>
    <div class="container py-3 px-3 bg-light shadow rounded-3 text-secondary border border-2 text-center fs-3 fade-1s remove-fade-slide-bottom" style="max-width: 1000px;">
        <div class="container border border-1 rounded-3 py-4" style="max-width: 1000px;">
            <p class="d-inline">Your order history is currently empty!<br />Browse the </p>
            <a class="d-inline p-1 border border-1 btn btn-info btn-lg rounded-3" href="<?php echo URLROOT; ?>/products">PRODUCT CATALOG</a>
        </div>
    </div>
    <?php else : ?>
        <div class="container d-none d-md-block py-3 px-3 mb-3 bg-light shadow rounded-3 text-secondary border border-2 fade-1s remove-fade-slide-bottom" style="max-width: 1000px;">
            <div class="container-fluid border border-1 rounded-3 p-3">
                <div class="row border-bottom border-1 border-dark h5">
                    <div class="col-2">
                        <p>ORDER ID</p>
                    </div>
                    <div class="col-5">
                        <p>ORDERED ON</p>
                    </div>
                    <div class="col-1 text-end">
                        <p>QTY</p>
                    </div>
                    <div class="col-1 text-end">
                        <p>TOTAL</p>
                    </div>
                    <div class="col-1">
                        <p>STATUS</p>
                    </div>
                    <div class="col-2 text-end">
                        <p>ACTIONS</p>
                    </div>
                </div>
                <?php foreach($data as $key => $order) : ?>
                <div class="row fs-5 pb-2 pt-3 fw-light">
                    <div class="col-2">
                        <p><?php echo $order['order_id']; ?></p>
                    </div>
                    <div class="col-5">
                        <p><?php echo $order['ordered_at']; ?></p>
                    </div>
                    <div class="col-1 text-end">
                        <p><?php echo $order['order_quantity']; ?></p>
                    </div>
                    <div class="col-1 text-end">
                        <p><?php echo $order['order_total']; ?></p>
                    </div>
                    <div class="col-1 <?php echo ($order['order_status'] == 'CANCELLED') ? 'text-danger' : 'text-success'; ?>">
                        <p><?php echo $order['order_status']; ?></p>
                    </div>
                    <div class="col-2 text-end">
                        <a href="<?php echo URLROOT; ?>/orders/show/<?php echo $order['order_id']; ?>" class="btn btn-info btn-sm"><i class="fa fa-info m-1"></i></a>
                    </div>
                </div>
                <hr>
                <?php endforeach; ?>
            </div>
        </div>
        
        <?php foreach($data as $key => $order) : ?>
        <div class="container d-md-none py-3 px-3 mb-3 bg-light shadow rounded-3 text-secondary border border-2 fade-1s remove-fade-slide-bottom" style="max-width: 1000px;">
            <div class="container-fluid border border-1 rounded-3 p-3 fw-normal">
                <div class="row fs-5 fw-light">
                    <div class="col-5 fw-bold">
                        <p>ORDER ID</p>
                    </div>
                    <div class="col-7">
                        <p class="ps-4"><?php echo $order['order_id']; ?></p>
                    </div>
                    <div class="col-5 fw-bold">
                        <p>ORDERED AT</p>
                    </div>
                    <div class="col-7">
                        <p class="ps-4"><?php echo $order['ordered_at']; ?></p>
                    </div>
                    <div class="col-5 fw-bold">
                        <p>QUANTITY</p>
                    </div>
                    <div class="col-7">
                        <p class="ps-4"><?php echo $order['order_quantity']; ?></p>
                    </div>
                    <div class="col-5 fw-bold">
                        <p>TOTAL</p>
                    </div>
                    <div class="col-7">
                        <p class="ps-4"><?php echo '$'.$order['order_total']; ?></p>
                    </div>
                    <div class="col-5 fw-bold">
                        <p>STATUS</p>
                    </div>
                    <div class="col-7">
                        <p class="ps-4"><?php echo $order['order_status']; ?></p>
                    </div>
                    <div class="col-5 fw-bold">
                        <p>ACTIONS</p>
                    </div>
                    <div class="col-7">
                        <a href="<?php echo URLROOT; ?>/orders/show/<?php echo $order['order_id']; ?>" class="btn btn-info btn-sm ms-4"><i class="fa fa-info m-1"></i></a>
                    </div>
                </div>
            </div>
        </div>
        <?php endforeach; ?>
    <?php endif; ?>
<?php require APPROOT . '/views/inc/footer.php'; ?>