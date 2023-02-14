<?php require APPROOT . '/views/inc/header.php'; ?>
    <div class="fade-1s remove-fade"><?php flash('order_message'); ?></div>
    <div class="container m-3">
        <a href="<?php echo URLROOT; ?>/orders" class="btn btn-light border-secondary fade-1s remove-fade-slide-top">
            <i class="fa fa-backward m-2"></i>
            Back
        </a>
    </div>
    <hr class="fade-1s remove-fade-x">
    <div class="row my-3 mx-auto w-100" style="max-width: 1100px;">
        <div class="col-md-6 fade-1s remove-fade-slide-left">
            <h1>ORDER #<?php echo $data['order_id']; ?></h1>
        </div>
    </div>
    <div class="container py-2 px-4 mb-3 bg-light shadow rounded-3 text-secondary border border-2 fade-1s remove-fade-slide-bottom" style="max-width: 1000px;">
        <div class="row my-3 mx-auto border border-1 rounded-3 p-4">
            <div class="row border-bottom border-1 border-dark h5">
                <div class="col-8 col-lg-9">
                    <p>ORDERED ON</p>
                </div>
                <div class="col-3 col-lg-1 text-end">
                    <p>QTY</p>
                </div>
                <div class="col-1 col-lg-1 text-end">
                    <p>TOTAL</p>
                </div>
                <div class="col-0 col-lg-1 d-none d-lg-block">
                    <p>STATUS</p>
                </div>
            </div>
            <div class="row fs-5 py-2 fw-light">
                <div class="col-8 col-lg-9">
                    <p><?php echo $data['ordered_at']; ?></p>
                </div>
                <div class="col-3 col-lg-1 text-end">
                    <p><?php echo $data['order_quantity']; ?></p>
                </div>
                <div class="col-1 col-lg-1 text-end">
                    <p><?php echo $data['order_total']; ?></p>
                </div>
                <div class="col-0 col-lg-1 d-none d-lg-block">
                    <p><?php echo $data['order_status']; ?></p>
                </div>
            </div>
            <div class="d-lg-none row fs-5 fw-light text-end">
                <div class="col-12 text-end p-1">
                    <div class="container border border-1 w-auto pull-right">
                        <p class="m-0"><strong>STATUS:</strong> <?php echo $data['order_status']; ?></p>
                    </div>
                </div>
            </div>
        </div>
        <h3>Items</h3>
        <div class="row mx-auto border border-1 rounded-3 p-4">
            <div class="row border-bottom border-1 border-dark h5">
                <div class="col-8 col-sm-8">
                    <p>PRODUCT</p>
                </div>
                <div class="col-2 col-sm-2 text-end">
                    <p>QTY</p>
                </div>
                <div class="col-2 text-end">
                    <p>PRICE</p>
                </div>
            </div>
            <?php $total_price = 0; $total_quantity = 0; ?>
            <?php foreach($data['order_items'] as $orderItem) : ?>
                <div class="row fs-5 py-2 fw-light">
                    <div class="col-8 col-sm-8">
                        <p><?php echo $orderItem->product_title; ?></p>
                    </div>
                    <div class="col-2 col-sm-2 text-end">
                        <p><?php echo $orderItem->quantity; ?></p>
                    </div>
                    <div class="col-2 text-end">
                        <?php $price = '$'.number_format($orderItem->sale_price*$orderItem->quantity, 2); ?>
                        <p><?php echo $price; ?></p>
                    </div>
                </div>
                <?php $total_price += $orderItem->sale_price*$orderItem->quantity; $total_quantity += $orderItem->quantity; ?>
            <?php endforeach; ?>
        </div>

        <div class="row pt-3 px-0 py-3 fs-5">
            <div class="col-12 col-md-6 pt-2">
                <div class="row mx-auto fs-5 me-0 border border-1 py-3 rounded-3">
                    <p class="h4">SHIPPING:</p>
                    <p class="m-0 ms-3 fw-light"><?php echo $data['shipping_street']; ?></p>
                    <p class="m-0 ms-3 fw-light"><?php echo $data['shipping_city'].', '.$data['shipping_state']; ?></p>
                    <p class="m-0 ms-3 fw-light"><?php echo $data['shipping_zipcode']; ?></p>
                </div>
            </div>
            <div class="col-12 col-md-6 pt-2 text-center">
                <div class="row d-flex align-items-center mx-auto me-0 border border-1 py-3 rounded-3 h-100">
                    <?php if (($data['now'] < $data['time']) && ($data['order_status'] != 'CANCELLED')) : ?>
                        <p>You can cancel at no charge within<br /><?php echo $data['timer_hrs'].' hrs '.$data['timer_min'].' min'; ?></p>
                        <button type="button" class="btn btn-danger mx-auto w-25" data-bs-toggle="modal" data-bs-target="#confirmModal">CANCEL</button>
                    <?php else : ?>
                        <p>You can no longer cancel this order.<p>
                    <?php endif; ?>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="confirmModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmModalLabel">Confirm Order Cancellation</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body text-center">
                Are you sure you want to cancel this order?<br /><em>This action can not be undone.</em>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <a href="<?php echo URLROOT; ?>/orders/cancel/<?php echo $data['order_id'];?>" type="button" class="btn btn-primary">Confirm</a>
            </div>
            </div>
        </div>
    </div>
<?php require APPROOT . '/views/inc/footer.php'; ?>