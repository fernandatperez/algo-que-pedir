<script>
  import IconText from "$lib/IconText.svelte";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/components-css/icon.css";
  import "$lib/css/pages-css/1-login.css";
  import Input from "$lib/Input.svelte";
  import { InputTypes } from "$lib/types";
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
          <!-- Username -->
          <Input
            description="Usuario*"
            value=""
            input_type={InputTypes.Normal}
            labelProps={{
              for: "login-username",
            }}
            inputProps={{
              class: "input-primary",
              type: "email",
              placeholder: "Usuario",
              id: "login-username-id",
              name: "login-username",
            }}
          />
          <!-- Password -->
          <Input
            description="Contraseña*"
            value=""
            input_type={InputTypes.Hidden}
            labelProps={{
              for: "login-password",
            }}
            inputProps={{
              class: "input-primary",
              id: "login-password-id",
              name: "login-password",
            }}
          />
        </div>
      </fieldset>

      <!-- FORM ACTIONS -->
      <section class="form-actions">
        <a href="/orders" class="btn btn-primary btn-login">
            Iniciar Sesión
        </a>
        <!-- Como metemos validacion del back aca? No del real sino Mock -->
        <div class="register-section">
          <p>¿No tenes una cuenta?</p>
          <a href="/register" class="register-link">Registrate</a>
        </div>
      </section>
    </form>
  </main>
</section>
