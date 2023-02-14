<?php require APPROOT . '/views/inc/header_admin.php'; ?>
<div class="container-fluid m-0 p-0">
    <div class="row w-100">
        <div class="col-3" style="height: 100vh;">
            <div class="d-flex flex-column p-3 text-white bg-dark h-100" style="position: fixed; width: 280px;">
            <a href="/" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none">
                <svg class="bi me-2" width="40" height="32"><use xlink:href="#bootstrap"></use></svg>
                <span class="fs-4">Admin</span>
            </a>
            <hr>
            <ul class="nav nav-pills flex-column mb-auto">
                <li class="nav-item">
                    <a href="<?php echo URLROOT; ?>/admins" class="nav-link active">
                        Home
                    </a>
                    </li>
                    <li>
                    <a href="<?php echo URLROOT; ?>/admins/reports" class="nav-link text-white">
                        Reports
                    </a>
                    </li>
                    <li>
                    <a href="<?php echo URLROOT; ?>" class="nav-link text-white">
                        Exit Dashboard
                    </a>
                    </li>
                    <li>
                    <a href="<?php echo URLROOT; ?>/users/logout" class="nav-link text-white">
                        Log Out
                    </a>
                </li>
            </ul>
            <hr>
        </div>
    </div>
    <div class="col-9 p-5">
        <div class="container-fluid border border-2 bg-white shadow w-100 h-100 rounded-3" style="border-radius: 20px;">
            <h1 class="mt-5">Admin</h1>
            <p class="fs-5 m-0">Account: <?php echo $_SESSION['user_id']; ?></p>
            <p class="fs-5 m-0">Role: Admin</p>
        </div>
    </div>
</div>

<?php require APPROOT . '/views/inc/footer_admin.php'; ?>