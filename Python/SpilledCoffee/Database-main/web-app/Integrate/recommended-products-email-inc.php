<html>
    <head>
        <title>Spilled Coffee - Reccomendations Email</title>
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
            width: 600px;
            margin: -15px -15px 20px -15px;
            border-radius: 15px 15px 0 0;">
                <h3 style="color:white; padding-left: 25px;"><em>Spilled Coffee</em></h3>
            </td></tr>
            
        <tr><td>

            <p style = "font-size: 16px; line-height: 1.5; text-align:center;">
               <Strong> 
               <?php echo $_SESSION['userName'] ?>,
               we miss you at <em>Spilled Coffee</em>!
                <br> Here are some products that we think that you might like: </Strong>
            </p>

            <br/>

            <table cellspacing="10px" style="border: 1px solid black; width: 500px; background: white; border-radius: 10px; box-shadow: 2px 2px 5px black; width: 100%;">
                <tr style="text-align: left;">
                    <td colspan="3" style="display: flex; align-items: center;">
                        <img style="display: inline-block;" src="https://test.spilledcoffee.net/img/price_tag.jpg" width="50px" height="50px">
                        <strong>Recommended Products</strong>
                    </td>
                </tr>
                
                
                <tr>
                <td style ="border-spacing: 5px; padding: 5px;
                padding-right: 10px; ">
                <a href="https://spilledcoffee.net/"target="_blank">
                <img src = "https://img.lovepik.com/photo/50068/8001.jpg_wh860.jpg" 
                alt="CoffeeBeans" border=2 height=200 width=250></img></a>
                </td>

                <td style ="border-spacing: 5px; padding: 5px;
                padding-right: 10px; ">
                <a href="https://spilledcoffee.net/"target="_blank">
                <img src = "https://images.squarespace-cdn.com/content/v1/5840d403bebafba4c28d6c4f/1551744193431-XTDT4WPT7MVPY1N39Z35/ke17ZwdGBToddI8pDm48kDu-OvKe9-yMBj32JSWknrt7gQa3H78H3Y0txjaiv_0fDoOvxcdMmMKkDsyUqMSsMWxHk725yiiHCCLfrh8O1z5QPOohDIaIeljMHgDF5CVlOqpeNLcJ80NK65_fV7S1UZNNUmsixw3l8iPy3vgDTPMwfMBbaTJA8uE3oWp8JUwqzkQXHaRS3Yhvu0vV6Jt1AA/Kopi_Luwak_Coffee1.jpg?format=2500w" 
                alt="CoffeeBeans" border=2 height=200 width=250></img></a>
                </td>

                </tr>

                <tr>
                    <td style=text-align:left><a href="https://spilledcoffee.net/"target="_blank" style="text-decoration:none;">
                    <?php echo $product1?>
                    </a></td>
                    <td style=text-align:left><a href="https://spilledcoffee.net/"target="_blank"style="text-decoration:none;">
                    <?php echo $product2?>
                    </a></td>
                </tr>
                <tr>
                    <td style=text-align:left>
                    <?php echo $price1?>
                    </td>
                    <td style=text-align:left>
                    <?php echo $price2?>
                    </td>
                </tr>

                <tr>

                <td>
                <a href="https://spilledcoffee.net/"target="_blank"><img src = "https://img.lovepik.com/photo/50068/8001.jpg_wh860.jpg" 
                    alt="CoffeeBeans" border=2 height=200 width=250></img></a>
                </td>

                <td>
                <a href="https://spilledcoffee.net/"target="_blank"><img src = "https://images.squarespace-cdn.com/content/v1/5840d403bebafba4c28d6c4f/1551744193431-XTDT4WPT7MVPY1N39Z35/ke17ZwdGBToddI8pDm48kDu-OvKe9-yMBj32JSWknrt7gQa3H78H3Y0txjaiv_0fDoOvxcdMmMKkDsyUqMSsMWxHk725yiiHCCLfrh8O1z5QPOohDIaIeljMHgDF5CVlOqpeNLcJ80NK65_fV7S1UZNNUmsixw3l8iPy3vgDTPMwfMBbaTJA8uE3oWp8JUwqzkQXHaRS3Yhvu0vV6Jt1AA/Kopi_Luwak_Coffee1.jpg?format=2500w" 
                    alt="CoffeeBeans" border=2 height=200 width=250></img></a>
                </td>
                </tr>

                <tr>
                    <td style=text-align:left><a href="https://spilledcoffee.net/"target="_blank"style="text-decoration:none;">
                    <?php echo $product3?>
                    </a></td>

                    <td style=text-align:left><a href="https://spilledcoffee.net/"target="_blank"style="text-decoration:none;">
                    <?php echo $product4?>
                    </a></td>
                </tr>
                <tr>
                    <td style=text-align:left>
                    <?php echo $price3?>
                    </td>

                    <td style=text-align:left>
                    <?php echo $price4?>
                    </td>
                </tr>
            </table>

            <br>

            <p style="font-size: 15px; text-align:left;line-height: 1.5;">
                Questions or Concerns?<br> Please reach our customer service department.<br>A <em>Spilled Coffee</em> representative will be available to help you!
            </p>

            <p>
                Copyright &copy;<script>document.write(new Date().getFullYear())</script>, Spilled Coffee
            </p>

            </td></tr>
            <br>
            
        </table>
        </td></tr>
        
    </table>
    </body>
</html>