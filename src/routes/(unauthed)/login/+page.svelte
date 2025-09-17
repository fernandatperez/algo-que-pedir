<script>
  import IconText from "$lib/IconText.svelte";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/components-css/icon.css";
  import "$lib/css/pages-css/1-login.css";
  import Input from "$lib/Input.svelte";
  import Password from "$lib/Password.svelte";
</script>

<section class="login-container">
  <main class="login-section">
    <!-- HEADER -->
    <IconText wrapperClass="header-section" />

    <!-- FORM -->
    <form class="form-container" id="form-login">
      <!-- FORM FIELD -->
      <!-- Chequear estos for y type -->
      <fieldset form="form-login" class="form-field" name="login-user">
        <div class="form-group">
          <Input
            description="Usuario*"
            value="HOLA! Ah re loco"
            input_type="normal"
            labelProps={{ class: "label-color", for: "username" }}
            inputProps={{
              class: "input-primary",
              type: "email",
              placeholder: "Usuario",
              id: "input-id",
              name: "username",
            }}
          />
          <!-- Este se va -->
          <Password
            label_for="text"
            label_text="Contraseña*"
            password_type="password"
            password_id="password-id"
          />
        </div>
      </fieldset>

      <!-- FORM ACTIONS -->
      <section class="form-actions">
        <button class="btn btn-primary btn-login"
          ><a href="/orders">Iniciar Sesión</a></button
        >
        <div class="register-section">
          <p>¿No tenes una cuenta?</p>
          <a href="/register" class="register-link">Registrate</a>
        </div>
      </section>
    </form>
  </main>
</section>
