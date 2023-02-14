<?php
    if (!$emailOK) {
        redirect('products/index');
    }
?>
<html>
    <head>
        <title>Spilled Coffee - Email Confirmation</title>
    </head>
    <body>
    <table height="50" style="
            width: 100%;
            background-image: linear-gradient(rgba(255,255,255,0.8), rgba(255,255,255,0.8)),
                url('<?php echo URLROOT; ?>/public/img/spilled-coffee.jpg');
            text-align:center;
            background-size: cover;
            background-position: center;
            ">
        <tr><td>
        <table style="
            max-width: 600px;
            background-color: #ffffff;
            font-family: sans-serif;
            padding: 15px;
            border-radius: 15px;
            margin: 15px auto;
            text-align: left;
            font-size: 16px;
            box-shadow: 0 .5rem 1rem rgba(0,0,0,.15)!important;
        ">
        
        <tr><td style="
            display: block;
            background-color: #666666;
            height: max-content;
            align-items: center;
            width: 100%;
            /* margin: -15px -15px 20px -15px; */
            border-radius: 15px;
        ">
        <h3 style="color:white; font-size: 1.5rem; margin: 15px 5px; padding-left: 25px;"><em>Spilled Coffee</em></h3>
        </td></tr>
        <tr><td>

            <p style = "font-size: 16px; margin: 20px 0px; text-align: center;">
                <?php echo $_SESSION['user_name'] ?>,
                <br>Thank you for your purchase at <em>Spilled Coffee</em>!
                <br>Below are your confirmation details:
            </p>

            <br />

            <table cellspacing="10px" style="
                    border: 1px solid grey; max-width: 600px; background: white; border-radius: 10px; width: 100%;
                ">
                <tr style="text-align: left;">
                <td colspan="4" style="display: flex; align-items: center;">
                    <img src="<?php echo URLROOT; ?>/public/img/lightbulb.jpg" width="40px" height="40px">
                    <strong>YOUR<br />INFORMATION</strong>
                </td>
                </tr>
                <tr style="text-align: left;">
                    <th>Date</th>
                    <th>Account</th>
                    <th style="text-align:right;">Confirmation #</th>
                </tr>
                <tr>
                    <td><?php echo $order_date ?></td>
                    <td><?php echo $_SESSION["user_name"] ?></td>
                    <td style="text-align:right;"><?php echo $order_id ?></td>
                </tr>
            </table>

            <br>
            <br>

            <table cellspacing="10px" style="border: 1px solid grey; max-width: 600px; background: white; border-radius: 10px; width: 100%;">
                <tr style="text-align: left;">
                    <td colspan="3" style="display: flex; align-items: center;">
                        <img style="display: inline-block;" src="<?php echo URLROOT; ?>/public/img/price_tag.jpg" width="40px" height="40px">
                        <strong>YOUR ITEMS</strong>
                    </td>
                </tr>
                <tr style="text-align: left;">
                    <th>Product</th>
                    <th style="text-align: right;">Quantity</th>
                    <th style="text-align: right;">Price</th>
                </tr>
                <?php foreach ($orderItems as $orderItem) : ?>
                    <tr>
                        <td><?php echo $orderItem->product_title; ?></td>
                        <td style="text-align:right;"><?php echo $orderItem->quantity; ?></td>
                        <td style="text-align:right;"><?php echo '$'.number_format($orderItem->sale_price * $orderItem->quantity, 2); ?></td>
                    </tr>
                <?php endforeach ?>
                <tr><td colspan="3" style="border-bottom: 2px solid black;"></td></tr>
                <tr>
                    <th colspan="2" style="text-align: right;">Total:</th>
                    <td style = "text-align:right;"><?php echo '$'.number_format($orderInfo->order_total, 2); ?></td>
                </tr>
            </table>

            <br>
            <br>

            <table cellspacing="10px" style="border: 1px solid grey; max-width: 600px; background: white; border-radius: 10px; width: 100%;">
                <tr style="text-align: left;">
                    <td colspan="3" style="display: flex; align-items: center;">
                        <img src="<?php echo URLROOT; ?>/public/img/shipping_truck.jpg" width="60px" height="40px">
                        <strong>YOUR SHIPPING INFO</strong>
                    </td>
                </tr>
                <tr style="text-align: left;">
                    <th>Destination</th>
                    <th style="text-align:right;">Payment</th>
                </tr>
                <tr>
                    <td>
                        <?php echo $orderInfo->shipping_street.', '.$orderInfo->shipping_city.', '.$orderInfo->shipping_state.' '.$orderInfo->shipping_zipcode; ?>
                    </td>
                    <td style="text-align:right;">****1234</td>
                </tr>
            </table>

            <br>
            <p style = "font-size: 16px; text-align:center; line-height: 1.5;">
                If you have any questions/concerns please reach out<br />to our customer service department.<br />
                A <em>Spilled Coffee</em> representative will be available to help you!
            </p>

            </td></tr>
        </table>
        </td></tr>
    </table>


    </body>
</html>
