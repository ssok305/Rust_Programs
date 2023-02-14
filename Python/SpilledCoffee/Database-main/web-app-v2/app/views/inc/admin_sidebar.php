<div class="row">
    <div class="col-3" style="height: 100vh;">
        <div class="d-flex flex-column p-3 text-white bg-dark h-100" style="position: fixed; width: 280px;">
        <a href="/" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none">
            <svg class="bi me-2" width="40" height="32"><use xlink:href="#bootstrap"></use></svg>
            <span class="fs-4">Sidebar</span>
        </a>
        <hr>
        <ul class="nav nav-pills flex-column mb-auto">
            <li class="nav-item">
            <a href="<?php echo URLROOT; ?>/admins" class="nav-link text-white">
                <svg class="bi me-2" width="16" height="16"><use xlink:href="#home"></use></svg>
                Home
            </a>
            </li>
            <li>
            <a href="<?php echo URLROOT; ?>/admins/reports" class="nav-link active">
                <svg class="bi me-2" width="16" height="16"><use xlink:href="#speedometer2"></use></svg>
                Reports
            </a>
            </li>
        </ul>
        <hr>
    </div>
</div>