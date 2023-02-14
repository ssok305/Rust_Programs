<html>
    <head>
        <title>Spilled Coffee - Email Confirmation</title>
    </head>
    <body>
    <table height="50" style="
            width: 100%;
            background-image: linear-gradient(rgba(255,255,255,0.8), rgba(255,255,255,0.8)),
                url('https://test.spilledcoffee.net/img/spilled-coffee.jpg');
            text-align:center;
            background-size: cover;
            background-position: center;
            ">
        <tr><td>
        <table align="center"
            style="
                max-width: 400px;
                background-color: #e3e3e3;
                font-family: sans-serif;
                padding: 15px;
                border-radius: 15px;
                margin-top: 15px;
                margin-bottom: 15px;
                text-align: left;
                font-size: 16px;
                box-shadow: 2px 2px 5px black;
        ">
        
        <tr><td style="
            display: flex;
            background-color: #666666;
            height: 50px;
            align-items: center;
            width: 540px;
            margin: -15px -15px 20px -15px;
            border-radius: 15px 15px 0 0;
        ">
        <h3 style="color:white; padding-left: 25px;"><em>Spilled Coffee</em></h3>
        </td></tr>
        <tr><td>

            <p style = "font-size: 16px; line-height: 1.5; text-align:center;">
                <?php echo $_SESSION['userName'] ?>,
                <br>Thank you for your purchase at <em>Spilled Coffee</em>!
                <br>Below are your confirmation details:
            </p>

            <br />

            <table cellspacing="10px" style="
                    border: 1px solid black;
                    width:500px; background: white;
                    border-radius: 10px;
                    box-shadow: 2px 2px 5px black;
                    width: 100%;
                ">
                <tr style="text-align: left;">
                <td colspan="4" style="display: flex; align-items: center;">
                    <img src="https://test.spilledcoffee.net/img/lightbulb.jpg" width="40px" height="40px">
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
                    <td><?php echo $_SESSION["useruid"] ?></td>
                    <td style="text-align:right;"><?php echo $order_id ?></td>
                </tr>
            </table>

            <br>
            <br>

            <table cellspacing="10px" style="border: 1px solid black; width: 500px; background: white; border-radius: 10px; box-shadow: 2px 2px 5px black; width: 100%;">
                <tr style="text-align: left;">
                    <td colspan="3" style="display: flex; align-items: center;">
                        <img style="display: inline-block;" src="https://test.spilledcoffee.net/img/price_tag.jpg" width="40px" height="40px">
                        <strong>YOUR ITEMS</strong>
                    </td>
                </tr>
                <tr style="text-align: left;">
                    <th>Product</th>
                    <th style="text-align: right;">Quantity</th>
                    <th style="text-align: right;">Price</th>
                </tr>
                <?php
                    for ($i = 0; $i < count($cart); $i++) {
                        $prod = $cart[$i][0];
                        $quant = $cart[$i][1];
                        $details = getProductByID($conn, $prod);
                        echo '<tr>';
                            echo '<td>'.$details[0].'</td>';
                            echo '<td style="text-align:right;">'.$quant.'</td>';
                            echo '<td style="text-align:right;">$'.number_format($details[1] * $quant, 2).'</td>';
                        echo '</tr>';
                    }
                ?>
                <tr><td colspan="3" style="border-bottom: 2px solid black;"></td></tr>
                <tr>
                    <th colspan="2" style="text-align: right;">Total:</th>
                    <td style = "text-align:right;">$<?php echo number_format($order_total, 2) ?></td>
                </tr>
            </table>

            <br>
            <br>

            <table cellspacing="10px" style="border: 1px solid black; width: 500px; background: white; border-radius: 10px; box-shadow: 2px 2px 5px black; width: 100%;">
                <tr style="text-align: left;">
                    <td colspan="3" style="display: flex; align-items: center;">
                        <img src="https://test.spilledcoffee.net/img/shipping_truck.jpg" width="60px" height="40px">
                        <strong>YOUR SHIPPING INFO</strong>
                    </td>
                </tr>
                <tr style="text-align: left;">
                    <th>Destination</th>
                    <th style="text-align:right;">Payment</th>
                </tr>
                <tr>
                    <td><?php echo $street.' '.$city.', '.$state.' '.$zip ?></td>
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
