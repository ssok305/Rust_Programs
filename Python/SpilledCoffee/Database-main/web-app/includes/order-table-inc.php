<div class="order-grid-full">
    <div class="col-1 header">ORDER #</div>
    <div class="col-2 header">DATE</div>
    <div class="col-3 header">QTY</div>
    <div class="col-4 header">TOTAL</div>
    <div class="col-5 header">STATUS</div>
    <div class="full-span-5"></div>
    <div class="col-1"><?php echo $order_id ?></div>
    <div class="col-2"><?php echo $order_date ?></div>
    <div class="col-3"><?php echo $order_quantity ?></div>
    <div class="col-4"><?php echo $order_total ?></div>
    <div class="col-5" <?php if ($order_status === "CANCELLED") { echo 'style="font-size: 20px;"'; } ?>><?php echo $order_status ?></div>
    <?php
        // date_default_timezone_set ('America/Denver');
        $order_time_dt = strtotime($order_date.' '.$order_time);
        $now_time = time();
        $cutoff = $order_time_dt + (24*60*60);
        $cancelWindow = $cutoff - $now_time;
        $hours = floor($cancelWindow / 3600);
        $minutes = floor(($cancelWindow / 60) % 60);
        $seconds = $cancelWindow % 60;
    ?>
    <div class="full-span-nb align-right">
        <?php
            // echo '<p>'.$order_time_dt.'</p>';
            // echo '<p>'.$order_time.'</p>';
            // echo '<p>'.$now_time.'</p>';
            // echo '<p>'.$cutoff.'</p>';
            // echo '<p>'.$cancelWindow.'</p>';
            // echo '<p>'.$hours.'</p>';
            // echo '<p>'.$minutes.'</p>';
            if ($order_status === "CANCELLED") {
                echo '';
            } else {
                if ($hours > 0 || $minutes > 0) {
                    echo '<p><i>You can cancel this order within '.$hours.' hrs '.$minutes.' min.</i>';
                    echo '<button class="btn btn-full-bw btn-cncl" onclick="showCancelConfirm(&quot;'.$order_id.'&quot;)">CANCEL</button></p>';
                } else {
                    echo '<p><i>You can no longer cancel this order.</i></p>';
                }
            }
        ?>
    </div>
</div>
<div class="order-grid-mobile">
    <div class="col-1 header">ORDER #</div>
    <div class="col-1"><?php echo $order_id ?></div>
    <div class="col-1 header">DATE</div>
    <div class="col-1"><?php echo $order_date ?></div>
    <div class="col-1 header">QTY</div>
    <div class="col-1"><?php echo $order_quantity ?></div>
    <div class="col-1 header">TOTAL</div>
    <div class="col-1"><?php echo $order_total ?></div>
    <div class="col-1 header">STATUS</div>
    <div class="col-1"><?php echo $order_status ?></div>
    <div class="full-span-nb align-right">
        <?php
            if ($order_status === "CANCELLED") {
                echo '';
            } else {
                if ($hours > 0 || $minutes > 0) {
                    echo '<p><i>You can cancel this order within '.$hours.' hrs '.$minutes.' min.</i>';
                    echo '<button class="btn btn-full-bw btn-cncl" onclick="showCancelConfirm(&quot;'.$order_id.'&quot;)">CANCEL</button></p>';
                } else {
                    echo '<p><i>You can no longer cancel this order.</i></p>';
                }
            }
        ?>
    </div>
</div>