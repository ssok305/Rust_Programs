<?php require APPROOT . '/views/inc/header.php'; ?>
    <div class="row mb-3 mx-auto" style="max-width:1100px;">
        <div class="fade-1s remove-fade"><?php flash('product_message'); ?></div>
        <div class="col-md-12 mt-3">
            <h1 class="fade-prep" style="opacity: 0;"><h1><?php echo $data['title'] ?></h1></h1>
            <hr>
        </div>
        <div class="container">
            <div class="row mx-auto text-center mt-5"></div>
                <img class="mx-auto d-block fade-1s remove-fade-x" src="<?php echo URLROOT; ?>/public/img/msudenver.gif" />
                <p class="text-center p-4 fs-3 mt-5">CS3250 - Team 1</p>
            </div>
        <hr>
        </div>
    </div>
<?php require APPROOT . '/views/inc/footer.php'; ?>