<?php
    if (!isset($_SESSION["useruid"]) || !isset($_SESSION["userid"])) {
        header('Location: ./?error=illegalaccess');
        exit();
    }
?>
<h2>SHIPPING INFORMATION</h2>
<div class="checkout-container">
    <p>This is a test website: do not enter sensitive or confidential information in this form!</p>
    <div class="checkout-form">
        <form class="checkout-form-grid" action="includes/confirm-order-inc.php" method="POST">
            <label for="street">Street Address</label>
            <input type="text" name="street" id="street" required>
            <label for="city">City</label>
            <input type="text" name="city" id="city" id="city" required>
            <label for="state">STATE</label>
            <div style="width: 100%">
            <select style="height:30px;" name="state" id="state" required>
                <option value="AL">AL</option>
                <option value="AK">AK</option>
                <option value="AR">AR</option>	
                <option value="AZ">AZ</option>
                <option value="CA">CA</option>
                <option value="CO">CO</option>
                <option value="CT">CT</option>
                <option value="DC">DC</option>
                <option value="DE">DE</option>
                <option value="FL">FL</option>
                <option value="GA">GA</option>
                <option value="HI">HI</option>
                <option value="IA">IA</option>	
                <option value="ID">ID</option>
                <option value="IL">IL</option>
                <option value="IN">IN</option>
                <option value="KS">KS</option>
                <option value="KY">KY</option>
                <option value="LA">LA</option>
                <option value="MA">MA</option>
                <option value="MD">MD</option>
                <option value="ME">ME</option>
                <option value="MI">MI</option>
                <option value="MN">MN</option>
                <option value="MO">MO</option>	
                <option value="MS">MS</option>
                <option value="MT">MT</option>
                <option value="NC">NC</option>	
                <option value="NE">NE</option>
                <option value="NH">NH</option>
                <option value="NJ">NJ</option>
                <option value="NM">NM</option>			
                <option value="NV">NV</option>
                <option value="NY">NY</option>
                <option value="ND">ND</option>
                <option value="OH">OH</option>
                <option value="OK">OK</option>
                <option value="OR">OR</option>
                <option value="PA">PA</option>
                <option value="RI">RI</option>
                <option value="SC">SC</option>
                <option value="SD">SD</option>
                <option value="TN">TN</option>
                <option value="TX">TX</option>
                <option value="UT">UT</option>
                <option value="VT">VT</option>
                <option value="VA">VA</option>
                <option value="WA">WA</option>
                <option value="WI">WI</option>	
                <option value="WV">WV</option>
                <option value="WY">WY</option>
            </select>
            </div>
            <label for="zip">ZIP CODE (US Only)</label>
            <input type="text" name="zipcode" id="zipcode" required>
            <label for="payment">Payment Information</label>
            <input type="text" name="payment" id="payment" placeholder="XXXX-XXXX-XXXX-XXXX" disabled>
            <input type="hidden" name="total_quantity" value="<?php echo $totalquantity; ?>">
            <input type="hidden" name="total_price" value="<?php echo $totalprice; ?>">
            <div style="width:100%"><button class="btn btn-full-bw btn-center" id="confirm-order-btn" type="submit" name="submit">CONFIRM ORDER</button></div>
        </form>
    </div>
</div>