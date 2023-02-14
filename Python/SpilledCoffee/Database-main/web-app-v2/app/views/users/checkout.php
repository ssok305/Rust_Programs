<?php require APPROOT . '/views/inc/header.php'; ?>
<div class="fade-1s remove-fade"><?php flash('cart_message'); ?></div>
    <div class="row my-3 mx-auto" style="max-width:1100px">
        <div class="col-md-12 fade-1s remove-fade-slide-top">
            <h1>Checkout</h1>
        </div>
        <hr class="fade-1s remove-fade-x">
    </div>
    <div class="row g-5 mx-auto mb-5 pb-5 fade-prep" style="max-width: 1100px;">
      <div class="col-md-5 col-lg-4 order-md-last fade-1s remove-fade-slide-right">
        <h4 class="d-flex justify-content-between align-items-center mb-3">
          <span class="text-primary">Your cart</span>
          <span class="badge bg-primary rounded-pill"><?php echo cartItemCount(); ?></span>
        </h4>
        <ul class="list-group mb-3">
        <?php $total_price = 0; $total_quantity = 0; ?>
        <?php foreach($data as $key => $cartItem) : ?>
            <li class="list-group-item d-flex justify-content-between lh-sm">
                <div>
                <h6 class="my-0"><?php echo $cartItem['product_title']; ?></h6>
                <small class="text-muted">Qty: <?php echo $cartItem['reserved']; ?></small>
                </div>
                <?php $price = '$'.number_format($cartItem['sale_price']*$cartItem['reserved'], 2); ?>
                <span class="text-muted"><?php echo $price; ?></span>
            </li>
            <?php $total_price += $cartItem['sale_price']*$cartItem['reserved']; $total_quantity += $cartItem['reserved']; ?>
        <?php endforeach; ?>
            <li class="list-group-item d-flex justify-content-between">
                <span>Total (USD)</span>
                <strong><?php echo '$'.number_format($total_price, 2); ?></strong>
            </li>
        </ul>
      </div>

      <div class="col-md-7 col-lg-8 bg-white border border-1 rounded-3 p-4 fade-1s remove-fade-slide-left">
        <h4 class="mb-3">Billing address</h4>
        <form class="needs-validation was-validated" novalidate="" action="<?php echo URLROOT; ?>/users/checkout" method="post">
          <div class="row g-3">

            <div class="col-12">
              <label for="address" class="form-label">Address</label>
              <input type="text" class="form-control" id="street" name="street" placeholder="" required="">
              <div class="invalid-feedback">
                Please enter your shipping address.
              </div>
            </div>

            <div class="col-md-3">
              <label for="zip" class="form-label">City</label>
              <input type="text" class="form-control" id="city" name="city" placeholder="" required="">
              <div class="invalid-feedback">
                Zip code required.
              </div>
            </div>

            <div class="col-md-3">
              <label for="state" class="form-label">State</label>
              <select class="form-select" id="state" name="state" required="">
                <option value="">Choose...</option>
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
              <div class="invalid-feedback">
                Please provide a valid state.
              </div>
            </div>

            <div class="col-md-3">
              <label for="zip" class="form-label">Zip</label>
              <input type="text" class="form-control" id="zip" name="zip" placeholder="" required="">
              <div class="invalid-feedback">
                Zip code required.
              </div>
            </div>
          </div>

          <hr class="my-4">

          <h4 class="mb-3">Payment</h4>

          <div class="my-3">
            <div class="form-check">
              <input id="credit" name="paymentMethod" type="radio" class="form-check-input" checked="" required="" disabled>
              <label class="form-check-label" for="credit">Credit card</label>
            </div>
            <div class="form-check">
              <input id="debit" name="paymentMethod" type="radio" class="form-check-input" required="" disabled>
              <label class="form-check-label" for="debit">Debit card</label>
            </div>
            <div class="form-check">
              <input id="paypal" name="paymentMethod" type="radio" class="form-check-input" required="" disabled>
              <label class="form-check-label" for="paypal">PayPal</label>
            </div>
          </div>

          <div class="row gy-3">
            <div class="col-md-6">
              <label for="cc-name" class="form-label">Name on card</label>
              <input type="text" class="form-control" id="cc-name" placeholder="" required="" disabled>
              <small class="text-muted">Full name as displayed on card</small>
              <div class="invalid-feedback">
                Name on card is required
              </div>
            </div>

            <div class="col-md-6">
              <label for="cc-number" class="form-label">Credit card number</label>
              <input type="text" class="form-control" id="cc-number" placeholder="" required="" disabled>
              <div class="invalid-feedback">
                Credit card number is required
              </div>
            </div>

            <div class="col-md-3">
              <label for="cc-expiration" class="form-label">Expiration</label>
              <input type="text" class="form-control" id="cc-expiration" placeholder="" required="" disabled>
              <div class="invalid-feedback">
                Expiration date required
              </div>
            </div>

            <div class="col-md-3">
              <label for="cc-cvv" class="form-label">CVV</label>
              <input type="text" class="form-control" id="cc-cvv" placeholder="" required="" disabled>
              <div class="invalid-feedback">
                Security code required
              </div>
            </div>
          </div>

            <hr class="my-4">
            <input class="d-none" value="<?php echo $total_price; ?>" name="total_price" id="total_price">
            <input class="d-none" value="<?php echo $total_quantity; ?>" name="total_quantity" id="total_quantity">
            <button class="w-100 btn btn-primary btn-lg" type="submit">PLACE ORDER</button>
        </form>
      </div>
    </div>
<?php require APPROOT . '/views/inc/footer.php'; ?>

