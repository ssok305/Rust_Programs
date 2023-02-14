<html>
    <head>
        <title>Spilled Coffee - Recommendations Email</title>
    </head>
    <body>
    <table height="50" style="
        width: 100%;
        background-image: linear-gradient(rgba(255,255,255,0.8), rgba(255,255,255,0.8)),
            url('<?php echo URLROOT; ?>/public/img/spilled-coffee.jpg');
        text-align: center;
        background-size: cover;
        background-position: center;
    ">
        <tr>
        <td>
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
            border-radius: 15px;">
                <h3 style="color:white; font-size: 1.5rem; margin: 15px 5px; padding-left: 25px;"><em>Spilled Coffee</em></h3>
            </td></tr>
            
        <tr><td>

            <p style = "font-size: 16px; margin: 20px 0px; text-align: center;">
                <strong> 
                    <?php echo $_SESSION['user_name'] ?>, we miss you at <em>Spilled Coffee</em>!
                    <br>Here are some products that we think that you might like:
                </strong>
            </p>

            <br/>

            <table cellspacing="10px" style="border: 1px solid grey; max-width: 600px; background: white; border-radius: 10px; width: 100%;">
                <tr style="text-align: left;">
                    <td colspan="3" style="display: flex; align-items: center;">
                        <img style="display: inline-block;" src="<?php echo URLROOT; ?>/public/img/price_tag.jpg" width="50px" height="50px">
                        <strong>Recommended Products</strong>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: center;">
                        <?php for ($i = 0; $i < 4; $i++) : ?>
                        <table style="display: inline-block; max-width: 250px; margin: 15px 5px; text-align: center;" valign="top">
                            <tr>
                                <td>
                                    <img src="<?php echo URLROOT; ?>/public/img/coffee_can.png" style="width: 100px">
                                    <h3><?php echo $data['product_list'][$i]->product_title; ?></h3>
                                    <p><?php echo $data['product_list'][$i]->product_description; ?></p>
                                    <p style="background-color: black; color: white; padding: 10px 0px; border-radius: 10px;">
                                        <a style="color: white; text-decoration: none;" href="<?php echo URLROOT; ?>/products/show/<?php echo $data['product_list'][$i]->product_id; ?>">View Product</a>
                                    </p>
                                </td>
                            </tr>
                        </table>
                        <?php endfor; ?>
                    </td>
                </tr>
            </table>

            <br>

            <p style="font-size: 15px; text-align: center; line-height: 1.5;">
                Questions or Concerns?<br> Please reach our customer service department.<br>A <em>Spilled Coffee</em> representative will be available to help you!
                <br />
                Copyright &copy;<script>document.write(new Date().getFullYear())</script> Spilled Coffee
            </p>
            </td></tr>
            <br>
            
        </table>
        </td></tr>
        
    </table>
    </body>
</html>