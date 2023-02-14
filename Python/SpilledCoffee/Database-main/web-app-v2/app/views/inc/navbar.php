<header class="p-3 bg-dark text-white">
  <div class="container" style="max-width: 1100px;">
    <div class="d-flex flex-wrap align-items-center justify-content-center">
      <a href="<?php echo URLROOT; ?>" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none me-3">
        <img src="<?php echo URLROOT; ?>/public/img/spilled_nav_logo_2.png" class="nav-logo">
      </a>

      <ul class="nav col-auto col-sm-auto me-sm-auto mb-2 justify-content-center mb-md-0">
        <li><a href="<?php echo URLROOT; ?>/products" class="nav-link px-2 text-white">Products</a></li>
        <li><a href="<?php echo URLROOT; ?>/pages/about" class="nav-link pe-4 ps-2 text-white">About</a></li>
      </ul>

      <div class="d-flex flex-no-wrap text-end">
        <?php if (!isLoggedIn()) : ?>
          <a href="<?php echo URLROOT; ?>/users/login" class="d-inline btn btn-outline-light me-2">Login</a>
          <a href="<?php echo URLROOT; ?>/users/register" class="d-inline btn btn-warning">Register</a>
          <li class="d-inline flex-nowrap">
            <a href="<?php echo URLROOT; ?>/users/cart" class="nav-link px-2 text-white ms-3">
            <i class="fa fa-shopping-cart pd-2 cart-icon"></i>
              <div class="container d-none d-sm-inline m-0 p-0">Cart</div>
              <sub>
                <div class="<?php echo (cartItemCount() > 0) ? 'd-inline-block' : 'd-none';?> d-inline-block badge bg-primary rounded-pill fw-bolder fs-6 p-0" id="cart-count" style="width:18px; height:18px">
                <?php echo (cartItemCount() > 0) ? cartItemCount() : '';?>
                </div>
              </sub>
            </a>
          </li>
        <?php else : ?>
          <ul class="nav col-auto col-sm-auto me-sm-auto mb-2 justify-content-center mb-md-0">
            <li>
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle border border-secondary border-1 rounded-3 text-white" href="#" id="dropdown01" data-bs-toggle="dropdown" aria-expanded="false">
                  Hey, <?php echo $_SESSION['user_name']; ?>
                </a>
                <ul class="dropdown-menu" aria-labelledby="dropdown01">
                  <li class="d-flex flex-nowrap d-block d-sm-none">
                    <a href="<?php echo URLROOT; ?>/users/cart" class="nav-link px-2 text-dark">
                    <i class="fa fa-shopping-cart m-1 cart-icon"></i>
                      Cart
                      <sub><div class="badge bg-primary rounded-pill fw-bolder" id="cart-count">
                        <?php echo (cartItemCount() > 0) ? cartItemCount() : '';?>
                      </div></sub>
                    </a>
                  </li>
                  <?php if ($_SESSION['ADMIN']) : ?>
                    <li><a class="dropdown-item text-danger" href="<?php echo URLROOT; ?>/admins">Admin</a></li>
                  <?php endif; ?>
                  <li><a class="dropdown-item" href="<?php echo URLROOT; ?>/orders">Orders</a></li>
                  <li><a class="dropdown-item" href="<?php echo URLROOT; ?>/users/logout">Log out</a></li>
                </ul>
              </li>
            </li>
            <li class="d-flex flex-nowrap d-none d-sm-block">
              <a href="<?php echo URLROOT; ?>/users/cart" class="nav-link px-2 text-white ms-3">
              <i class="fa fa-shopping-cart pd-2 cart-icon"></i>
                Cart
                <sub>
                  <div class="<?php echo (cartItemCount() > 0) ? 'd-inline-block' : 'd-none';?> d-inline-block badge bg-primary rounded-pill fw-bolder fs-6 p-0" id="cart-count" style="width:18px; height:18px">
                  <?php echo (cartItemCount() > 0) ? cartItemCount() : '';?>
                  </div>
                </sub>
              </a>
            </li>
          </ul>
        <?php endif; ?>
      </div>
    </div>
  </div>
</header>