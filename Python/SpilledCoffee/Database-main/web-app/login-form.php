<div class="login-form">
        <label for="uname"><b>Username</b></label>
        <input class = "login-input" type="text" placeholder="Username/Email..." name="uid" id="uid" required>
        <label for="psw"><b>Password</b></label>
        <input class = "login-input" type="password" placeholder="Password..." name="psw" id="psw" required>
        <div class="modal-message" id="login-message"></div>
        <button onclick="submitLogin()" name="submit" id="submit" class="btn modal-btn">Log in</button>
</div>